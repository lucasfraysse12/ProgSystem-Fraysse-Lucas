import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager = new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory = memoryManager.getFilesystemMemory();

        for (int i = 0; i < MemoryManager.MAX_INODES; i++) {
            int offset = MemoryManager.INODE_TABLE_OFFSET + (i * Inode.INODE_SIZE);
            if (Utils.readInt(memory, offset + 4) == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean createFile(String directory, String filename) {

        int inodeNum = allocateInode();

        if (inodeNum == -1) {
            return false;
        }

        // TODO:
        // Construire l'inode.
        // L'initialiser comme fichier vide.

        Inode in = new Inode(memoryManager, inodeNum);

        in.writeToMemory(1, 
                         0, 
                         System.currentTimeMillis(), 
                         System.currentTimeMillis(), 
                         new int[Inode.DIRECT_POINTERS], 
                         0,
                         (short) 0,
                         1);

        return true;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public boolean writeFile(int inodeNum, byte[] data) {

        int blocksNeeded =
                (data.length
                + MemoryManager.BLOCK_SIZE - 1)
                / MemoryManager.BLOCK_SIZE;

        if (blocksNeeded > Inode.DIRECT_POINTERS) {
            return false;
        }

        int[] blockPointers = new int[Inode.DIRECT_POINTERS];

        for (int i = 0; i < blocksNeeded; i++) {
            blockPointers[i] = memoryManager.allocateBlock();
            if (blockPointers[i] == -1) {
                return false;
            }
        }
        

        byte[] memory = memoryManager.getFilesystemMemory();

        int bytesRemaining = data.length;

        int dataSrcOffset = 0;

        for (int i = 0; i < blocksNeeded; i++) {
            int numeroBlock = blockPointers[i];
            int aCopier = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int blockOffset = numeroBlock * MemoryManager.BLOCK_SIZE;
            System.arraycopy(data, dataSrcOffset, memory, blockOffset, aCopier);
            dataSrcOffset += aCopier;
            bytesRemaining -= aCopier;
        }

        
        Inode in = new Inode(memoryManager, inodeNum);
        in.writeToMemory(1,
                         data.length,
                         System.currentTimeMillis(),
                         System.currentTimeMillis(),
                         blockPointers,
                         0,
                         (short) 0,
                         1);

        return true;
    }

    public byte[] readFile(int inodeNum) {

        Inode inode = new Inode(memoryManager, inodeNum);

        int fileSize = inode.getFileSize();

        if (fileSize == 0) {
            return new byte[0];
        }

        byte[] fileData = new byte[fileSize];

        byte[] memory = memoryManager.getFilesystemMemory();

        int[] blockPointers = inode.getDirectPointers();

        int bytesRemaining = fileSize;
        int destOffset = 0;

        int blocksNeeded = (fileSize + MemoryManager.BLOCK_SIZE - 1) / MemoryManager.BLOCK_SIZE;

        for (int i = 0; i < blocksNeeded; i++) {
            int blockNum = blockPointers[i];
            int bytesToCopy = Math.min(bytesRemaining, MemoryManager.BLOCK_SIZE);
            int blockOffset = blockNum * MemoryManager.BLOCK_SIZE;

            System.arraycopy(memory, blockOffset, fileData, destOffset, bytesToCopy);

            destOffset += bytesToCopy;
            bytesRemaining -= bytesToCopy;
        }

        return fileData;
    }

    public boolean deleteFile(int inodeNum) {

        Inode in = new Inode(memoryManager, inodeNum);
        int fileSize = in.getFileSize();

        int blocksNeeded = (fileSize + MemoryManager.BLOCK_SIZE - 1) / MemoryManager.BLOCK_SIZE;
        int[] blockPointers = in.getDirectPointers();

        for (int i = 0; i < blocksNeeded; i++) {
            int blockNum = blockPointers[i];
            if (blockNum != 0) {
                memoryManager.setBlockUsed(blockNum, false);
            }
        }

        in.writeToMemory(0, 0, 0L, 0L, new int[Inode.DIRECT_POINTERS], 0, (short) 0, 0);

        return true;

    }


}