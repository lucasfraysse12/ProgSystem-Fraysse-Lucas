import java.util.*;

public class VirtualFileSystem {

    private MemoryManager memoryManager;

    public VirtualFileSystem() {
        this.memoryManager = new MemoryManager();
    }

    private int allocateInode() {

        byte[] memory = memoryManager.getFilesystemMemory();

        for (int i = 0; i < memoryManager.MAX_INODES; i++) {
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
}