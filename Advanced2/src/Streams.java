import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Streams {
    /**
     * Read from an InputStream until a quote character (") is found, then read
     * until another quote character is found and return the bytes in between the
     * two quotes. If no quote character was found return null, if only one, return
     * the bytes from the quote to the end of the stream.
     * 
     * @param in
     * @return A list containing the bytes between the first occurrence of a quote
     *         character and the second.
     */
    public static List<Byte> getQuoted(InputStream in) throws IOException {
        List<Byte> quoteList = new ArrayList<>();
        int currentByte;
        while ((currentByte = in.read()) != -1) { // Read until EOF
            if ((char) currentByte == '\"') { // Check if we start a quote
                currentByte = in.read();
                while (currentByte != -1 && (char) currentByte != '\"') {
                    quoteList.add((byte) currentByte); // Add bytes of quote to list
                    currentByte = in.read();
                }
                return quoteList;
            }
        }
        return null; // There is not a quote on file
    }

    /**
     * Read from the input until a specific string is read, return the string read
     * up to (not including) the endMark.
     * 
     * @param in
     *            the Reader to read from
     * @param endMark
     *            the string indicating to stop reading.
     * @return The string read up to (not including) the endMark (if the endMark is
     *         not found, return up to the end of the stream).
     */
    public static String readUntil(Reader in, String endMark) throws IOException {
        StringBuilder sBuilder = new StringBuilder();
        StringBuilder sEndMark = new StringBuilder();
        BufferedReader bufferReader = new BufferedReader(in); // Read file in chunks
        boolean endMarkFlag = false;
        int currentChar;
        while (((currentChar = bufferReader.read()) != -1) && !endMarkFlag) { // Read from buffer until EOF or EndMark
                                                                              // were found
            if ((char) currentChar == endMark.charAt(0)) { // EndMark Potential
                sEndMark.append((char) currentChar);
                for (int i = 1; i < endMark.length(); i++) {
                    currentChar = bufferReader.read();
                    if ((currentChar != -1) && (endMark.charAt(i) == (char) currentChar)) { // Check if it EOF or if it
                                                                                            // the next char of EndMark
                        sEndMark.append((char) currentChar);
                    } else { // Its not and EndMark we add sEndMark string builder to the sBuilder
                        sBuilder.append(sEndMark);
                        sEndMark.setLength(0);
                        sBuilder.append((char) currentChar);
                        i = endMark.length();
                    }
                    if (endMark.equals(sEndMark.toString())) {// Find a match to EndMark
                        endMarkFlag = true;
                    }
                }
            } else { // For sure not EndMark
                sBuilder.append((char) currentChar);
            }
        }
        return sBuilder.toString();
    }

    /**
     * Copy bytes from input to output, ignoring all occurrences of badByte.
     * 
     * @param in
     * @param out
     * @param badByte
     */
    public static void filterOut(InputStream in, OutputStream out, byte badByte) throws IOException {
        int currentChar;
        while ((currentChar = in.read()) != -1) { // Read until EOF
            if ((byte) currentChar != badByte) // Check if the current byte is badByte
                out.write(currentChar); // Not a badByte, so write to output file
        }
    }

    /**
     * Read a 48-bit (unsigned) integer from the stream and return it. The number is
     * represented as five bytes, with the most-significant byte first. If the
     * stream ends before 5 bytes are read, return -1.
     * 
     * @param in
     * @return the number read from the stream
     */
    public static long readNumber(InputStream in) throws IOException {
        int currentByte, byteIndex = 0;
        long numberResult = 0;
        ;
        while (((currentByte = in.read()) != -1) && (byteIndex < 5)) { // Read 5 bytes of file if exist
            byteIndex++;
            numberResult <<= 8;
            numberResult |= currentByte; // Create the number representation
        }
        if (byteIndex < 5) // Check if in file were at least 5 bytes
            return -1;
        return numberResult;
    }
}
