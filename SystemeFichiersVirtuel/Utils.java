public class Utils {

    public static int writeInt(byte[] memory, int offset, int value) {
        memory[offset] = (byte) (value >> 24);
		memory[offset + 1] = (byte) (value >> 16);
		memory[offset + 2] = (byte) (value >> 8);
		memory[offset + 3] = (byte) value; 
        return 4;
    }

    public static int readInt(byte[] memory, int offset) {
        return ((memory[offset] & 0xFF) << 24) |
               ((memory[offset + 1] & 0xFF) << 16) |
               ((memory[offset + 2] & 0xFF) << 8) |
               (memory[offset + 3] & 0xFF);
    }

    public static int writeShort(byte[] memory, int offset, short value) {
        memory[offset] = (byte) (value >> 8);
		memory[offset + 1] = (byte) value;
        return 2;
    }

    public static short readShort(byte[] memory, int offset) {
        return (short)(((memory[offset] & 0xFF) << 8) |
		       (memory[offset + 1] & 0xFF));
    }

    public static int writeLong(byte[] memory, int offset, long value) {
        memory[offset] = (byte) (value >> 56);
        memory[offset + 1] = (byte) (value >> 48);
        memory[offset + 2] = (byte) (value >> 40);
        memory[offset + 3] = (byte) (value >> 32);
        memory[offset + 4] = (byte) (value >> 24);
        memory[offset + 5] = (byte) (value >> 16);
        memory[offset + 6] = (byte) (value >> 8);
        memory[offset + 7] = (byte) value;
        return 8;
    }

    public static long readLong(byte[] memory, int offset) {
        return (long)(((memory[offset] & 0xFF) << 56) |
               ((memory[offset + 1] & 0xFF) << 48) |
               ((memory[offset + 2] & 0xFF) << 40) |
               ((memory[offset + 3] & 0xFF) << 32) |
               ((memory[offset + 4] & 0xFF) << 24) |
               ((memory[offset + 5] & 0xFF) << 16) |
               ((memory[offset + 6] & 0xFF) << 8) |
               (memory[offset + 7] & 0xFF));
    }

    public static int writeString(byte[] memory, int offset, String str, int maxLength) {

        byte[] strEnBytes = str.getBytes();
        if (strEnBytes.length <= maxLength) {
            for (int i = 0; i < strEnBytes.length; i++) {
                memory[offset + i] = strEnBytes[i];
            }
            
        }
        // TODO:
        // 1. Convertir la chaîne en octets.
        // 2. Copier les octets sans dépasser maxLength.
        // 3. Nettoyer le reste de la zone avec des zéros.

        return maxLength;
    }

    public static String readString(
        byte[] memory,
        int offset,
        int maxLength) {

        // TODO:
        // Lire jusqu'au premier octet nul
        // ou jusqu'à maxLength.

        return "";
    }
}