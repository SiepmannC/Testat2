package Aufgabe;

import java.net.*;
import java.io.*;

public class TCP_Client {

    private static final int serverPort = 7777;

    public static void main(String[] args) {

        String hostname = "localhost";
        PrintWriter networkOut;
        BufferedReader networkIn;
        Socket s = null;

        try {
            // Verbinden und BufferedReader sowie Printwriter initialisieren
            s = new Socket(hostname, serverPort);
            System.out.println("Client1 Verbunden");
            networkIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(networkIn.readLine());
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            networkOut = new PrintWriter(s.getOutputStream());

            while (true) {
                String theLine = userIn.readLine();
                // Session freiwillig beenden mit END
                if (theLine.equals("END")) {
                    networkOut.flush();
                    break;
                }//if
                // Empfangen vom Server und absenden
                networkOut.println(theLine);
                networkOut.flush();
                System.out.println(networkIn.readLine());
            }//while

        } catch (IOException e) {
            System.err.println(e.toString());
        } finally {
            try {
                if (s != null) {
                    System.out.println("Es besteht keine Verbindung");
                    s.close();
                    //main(args); Diese Zeile wiedereinfügen, wenn der Client sich immer wieder neu verbinden soll
                }//if
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }//finally
    }//main
}//class
