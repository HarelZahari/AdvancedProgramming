import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccess {

    /**
     * Treat the file as an array of (unsigned) 8-bit values and sort them in-place
     * using a bubble-sort algorithm. You may not read the whole file into memory!
     * 
     * @param file
     */
    public static void sortBytes(RandomAccessFile file) throws IOException {
        int a, b;
        for (int i = 0; i < file.length(); i++) { // Implement bubble sort on file
            for (int j = 0; j < file.length() - i - 1; j++) {
                file.seek(j);
                a = file.read();
                file.seek(j + 1);
                b = file.read();
                if (a > b) { // Find a swap
                    file.seek(j);
                    file.write(b);
                    file.seek(j + 1);
                    file.write(a);
                }
            }
        }
    }

    /**
     * Treat the file as an array of unsigned 24-bit values (stored MSB first) and
     * sort them in-place using a bubble-sort algorithm. You may not read the whole
     * file into memory!
     * 
     * @param file
     * @throws IOException
     */
    public static void sortTriBytes(RandomAccessFile file) throws IOException {
        byte[] a = new byte[3];
        byte[] b = new byte[3];
        for (int i = 0; i < file.length(); i = i + 3) {
            for (int j = 0; j < file.length() - i - 4; j = j + 3) { // Implement bubble sort on file 3 bytes represent
                                                                    // one number
                file.seek(j);
                file.read(a);
                file.seek(j + 3);
                file.read(b);
                if (threeBytestoInt(a) > threeBytestoInt(b)) { // Create the numbers and check if swap needed
                    file.seek(j);
                    file.write(b);
                    file.seek(j + 3);
                    file.write(a);
                }
            }
        }
    }

    /**
     * The function get array of byte size 3 (MSB is first) and create the int
     * representation
     * 
     * @param a
     * @return convertedInt
     */
    public static int threeBytestoInt(byte[] a) {
        int convertedInt = 0;
        for (int i = 0; i < 3; i++) {
            convertedInt <<= 8; // Create the number representation
            convertedInt |= a[i];
        }
        return convertedInt;
    }
}
