package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class HelloClient {

    Socket clientSocket;

    public static final int COUNT = 10;

    /**
     * Connect to a remote host using TCP/IP and set {@link #clientSocket} to be the
     * resulting socket object.
     * 
     * @param host
     *            remote host to connect to.
     * @param port
     *            remote port to connect to.
     * @throws IOException
     */
    public void connect(String host, int port) throws IOException {
        this.clientSocket = new Socket(host, port);
    }

    /**
     * Perform the following actions {@link #COUNT} times in a row: 1. Connect to
     * the remote server (host:port). 2. Write the string in myname (followed by
     * newline) to the server 3. Read one line of response from the server, write it
     * to sysout (without the trailing newline) 4. Close the socket.
     * 
     * Then do the following (only once): 1. send {@link HelloServer#BYE_MESSAGE} to
     * the server (followed by newline). 2. Read one line of response from the
     * server, write it to sysout (without the trailing newline)
     * 
     * If there are any IO Errors during the execution, output
     * {@link HelloServer#ERR_MESSAGE} (followed by newline) to sysout. If the error
     * is inside the loop, continue to the next iteration of the loop. Otherwise
     * exit the method.
     * 
     * @param sysout
     * @param host
     * @param port
     * @param myname
     */
    public void run(PrintStream sysout, String host, int port, String myname) {
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            for (int i = 0; i <= COUNT; i++) {
                try {
                    connect(host, port); // Connect to server
                    bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); // Create connection between objects and server
                    printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
                    if (i != 10) {
                        printWriter.println(myname); // Send myname 10 times to server
                    } else {
                        printWriter.println(HelloServer.BYE_MESSAGE); // At the 11 time send bye msg
                    }
                    sysout.print(bufferedReader.readLine() + " "); // Print the server respond
                } catch (IOException e) {
                    sysout.println(HelloServer.ERR_MESSAGE);
                    continue;
                }
                closeConnection(bufferedReader, printWriter, clientSocket); // logout from server
            }
        } catch (IOException e) {
            sysout.println(HelloServer.ERR_MESSAGE);
        } finally {
            try {
                closeConnection(bufferedReader, printWriter, clientSocket); // logout from server
            } catch (IOException e) {
                sysout.println(HelloServer.ERR_MESSAGE);
                return;
            }
        }
    }

    // Close connection between currentServer and client that on socket parameter,
    // and close BufferReader and PrintWriter
    private static void closeConnection(BufferedReader bufferedReader, PrintWriter printWriter, Socket socket) throws IOException {
        {
            if (bufferedReader != null) // Close all kind of connection between server and client
                bufferedReader.close();
            if (printWriter != null)
                printWriter.close();
            socket.close();
        }
    }

    public static void main(String args[]) {
        HelloClient client = new HelloClient();
        client.run(System.out, "localhost", 55720, "Harel");
    }
}
