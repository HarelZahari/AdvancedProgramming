import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Implements a persistent dictionary that can be held entirely in memory. When
 * flushed, it writes the entire dictionary back to a file.
 * 
 * The file format has one keyword per line: word:def1 word2:def2 ...
 * 
 * Note that an empty definition list is allowed (in which case the entry would
 * have the form: word:
 * 
 */
public class InMemoryDictionary extends TreeMap<String, String> implements PersistentDictionary {
    private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
    private File dictFile;

    public InMemoryDictionary(File dictFile) {
        this.dictFile = dictFile;
        if (!this.dictFile.exists())
            try {
                this.dictFile.createNewFile(); // Create the file if it does not exist
            } catch (Exception e) {
                System.out.println("Had exception: " + e);
            }
    }

    @Override
    public void open() throws IOException {
        String currentLine, key, value;
        BufferedReader bufferRead = null;
        try {
            bufferRead = new BufferedReader(new FileReader(this.dictFile));
            while ((currentLine = bufferRead.readLine()) != null) { // Read until EOF
                key = currentLine.substring(0, currentLine.indexOf(":")); // Get the key from line
                value = currentLine.substring(currentLine.indexOf(":") + 1); // Get the value from line
                this.put(key, value); // Add the line to the treemap
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
            return;
        } finally {
            bufferRead.close();
        }
    }

    @Override
    public void close() throws IOException {
        BufferedWriter bufferWrite = null;
        try {
            bufferWrite = new BufferedWriter(new FileWriter(this.dictFile));
            for (String key : this.keySet()) {
                bufferWrite.write(key + ":" + this.get(key) + "\n"); // Add all lines from treemap
            }
        } finally {
            this.clear(); // Clean treemap
            bufferWrite.close();
        }
    }

}
