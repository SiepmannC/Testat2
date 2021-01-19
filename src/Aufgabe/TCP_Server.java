package Aufgabe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TCP_Server {
    public final static int DEFAULT_PORT = 7777;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        PrintWriter out = null;
        Socket connection = null;
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                try {
                    connection = server.accept();
                    out = new PrintWriter(connection.getOutputStream());
                    Date now = new Date();
                    out.println(now.toString());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
