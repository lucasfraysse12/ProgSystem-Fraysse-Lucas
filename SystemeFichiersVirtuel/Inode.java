public class Inode {

    private MemoryManager memoryManager;
    private int inodeNumber;

    public static final int INODE_SIZE = 128;
    public static final int DIRECT_POINTERS = 10;

    public Inode(MemoryManager memoryManager, int inodeNumber) {
        this.memoryManager = memoryManager;
        this.inodeNumber = inodeNumber;
    }

    public int getInodeOffset() {
        return MemoryManager.INODE_TABLE_OFFSET + inodeNumber * INODE_SIZE;
    }

    public int getFileType() {
        byte[] memory = memoryManager.getFilesystemMemory();
        return Utils.readInt(memory, getInodeOffset() + 4);
    }

    public int getFileSize() {
        byte[] memory = memoryManager.getFilesystemMemory();
		return Utils.readInt(memory, getInodeOffset() + 8);
    }

    public int[] getDirectPointers() {

        byte[] memory = memoryManager.getFilesystemMemory();

        int[] pointers = new int[DIRECT_POINTERS];

		for (int i = 0; i < 10; i++) {
			pointers[i] = Utils.readInt(memory, getInodeOffset() + 28 + i * 4);
		}
		
        return pointers;
    }
	
	public void writeToMemory(
        int fileType,
        int fileSize,
        long creationTime,
        long modificationTime,
        int[] directPointers,
        int indirectPointer,
        short permissions,
        int linkCount) {

		byte[] memory =
				memoryManager.getFilesystemMemory();

		int offset = getInodeOffset();

		Utils.writeInt(memory, 0, inodeNumber);
		Utils.writeInt(memory, 4, fileType);
		Utils.writeInt(memory, 8, fileSize);
		Utils.writeLong(memory, 12, creationTime);
		Utils.writeLong(memory, 20, modificationTime);
		for (int i = 0; i < 10; i++) {
			Utils.writeInt(memory, 28 + i * 4, directPointers[i]);
		}
		Utils.writeInt(memory, 68, indirectPointer);
		Utils.writeShort(memory, 72, permissions);
		Utils.writeInt(memory, 74, linkCount);
	}
}