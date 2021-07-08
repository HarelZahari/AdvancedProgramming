package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HelloServer {

    public static final String ERR_MESSAGE = "IO Error!";
    public static final String LISTEN_MESSAGE = "Listening on port: ";
    public static final String HELLO_MESSAGE = "hello ";
    public static final String BYE_MESSAGE = "bye";
    private ServerSocket serverSocket;

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    /**
     * Listen on the first available port in a given list.
     * 
     * <p>
     * Note: Should not throw exceptions due to ports being unavailable
     * </p>
     * 
     * @return The port number chosen, or -1 if none of the ports were available.
     * 
     */
    public int listen(List<Integer> portList) throws IOException {
        for (int currentPort : portList) {
            try { // Try to listen to current port if can not connect continue to next port
                this.serverSocket = new ServerSocket(currentPort);
                return currentPort;
            } catch (IOException e) {
                // Trying the next port
            }
        }
        return -1;
    }

    /**
     * Listen on an available port. Any available port may be chosen.
     * 
     * @return The port number chosen.
     */
    public int listen() throws IOException {
        this.serverSocket = new ServerSocket(0); // Automatically allocated , free port.
        return this.serverSocket.getLocalPort(); // return the allocated port/
    }

    /**
     * 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed by
     * the port number (and a newline) to sysout. If there's an IOException at this
     * stage, exit the method.
     * 
     * 2. Run in a loop; in each iteration of the loop, wait for a client to
     * connect, then read a line of text from the client. If the text is
     * {@link #BYE_MESSAGE}, send {@link #BYE_MESSAGE} to the client and exit the
     * loop. Otherwise, send {@link #HELLO_MESSAGE} to the client, followed by the
     * string sent by the client (and a newline) After sending the hello message,
     * close the client connection and wait for the next client to connect.
     * 
     * If there's an IOException while in the loop, or if the client closes the
     * connection before sending a line of text, send the text {@link #ERR_MESSAGE}
     * to sysout, but continue to the next iteration of the loop.
     * 
     * *: in any case, before exiting the method you must close the server socket.
     * 
     * @param sysout
     *            a {@link PrintStream} to which the console messages are sent.
     * 
     * 
     */
    public void run(PrintStream sysout) {
        BufferedReader bufferedReader = null; // Initialize objects for connection
        PrintWriter printWriter = null;
        Socket clientSocket = null;
        try {
            int listenPort = listen(); // Try to listen port and notify if succeed
            sysout.println(LISTEN_MESSAGE + listenPort);
        } catch (IOException e) {
            return;
        }
        while (true) { // Loop for listen new request
            try {
                clientSocket = this.serverSocket.accept(); // wait until request
                bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                String currentLine = bufferedReader.readLine();
                if (currentLine.equals(BYE_MESSAGE)) { // Check if the client asked to logout or no
                    printWriter.println(BYE_MESSAGE);
                } else {
                    printWriter.println(HELLO_MESSAGE + currentLine);
                }
            } catch (IOException e) {
                sysout.println(ERR_MESSAGE);
                continue;
            } finally {
                try { // Try to close all kind of connection between server and client
                    if (bufferedReader != null)
                        bufferedReader.close();
                    if (printWriter != null)
                        printWriter.close();
                    clientSocket.close();
                } catch (IOException e) {
                    sysout.println(ERR_MESSAGE);
                    return;
                }
            }
        }
    }

    public static void main(String args[]) {
        HelloServer server = new HelloServer();
        server.run(System.err);
    }

}
