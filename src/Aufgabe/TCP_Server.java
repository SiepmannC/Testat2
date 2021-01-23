package Aufgabe;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TCP_Server {
    private final static int DEFAULT_PORT = 7777;


    public static void main(String[] args) {
        PrintWriter out;
        Socket connection = null;
        BufferedReader networkIn;
        try {
            ServerSocket server = new ServerSocket(DEFAULT_PORT);
            while (true) {
                try {
                    connection = server.accept();
                    networkIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    out = new PrintWriter(connection.getOutputStream());
                    out.println("Bitte Befehl eingeben:");
                    out.flush();
                    String line = networkIn.readLine();
                    System.out.println(line);

                    if (line != null) {
                        if (line.startsWith("SAVE")) {
                            System.out.println("SAVE startet..");
                            String message = line.substring(5);
                            System.out.println(message);
                            // In ein Dokument schreiben
                            try {
                                while (true) { // Falls Key belegt sein sollte, wird ein neuer erstellt
                                    long key = (long) (Math.random() * 1000000000);
                                    String filename = "File_" + key + ".txt";
                                    String fullPath = System.getProperty("user.home") + "\\Desktop\\Messages\\" + filename;
                                    File file = new File(fullPath);
                                    if (file.createNewFile()) {
                                        System.out.println("File mit dem Namen: " + file.getName() + " erstellt");
                                        FileWriter myWriter = new FileWriter(fullPath);
                                        myWriter.write(message);
                                        myWriter.close();
                                        System.out.println("Successfully wrote to the file.");
                                        out.println("KEY " + key);
                                        out.flush();
                                        break;
                                    } else {
                                        System.out.println("Key bereits vergeben, neuer Key wird erstellt");

                                    }
                                }
                            } catch (IOException e) {
                                System.out.println("Schreiben geht nicht");
                                e.printStackTrace();
                            }
                        } else if (line.startsWith("GET")) {
                            String key = line.substring(4);
                            String filename = "File_" + key + ".txt";
                            String fullPath = System.getProperty("user.home") + "\\Desktop\\Messages\\" + filename;
                            try {
                                File file = new File(fullPath);
                                Scanner myReader = new Scanner(file);
                                String data = "OK ";
                                while (myReader.hasNextLine()) {
                                    data += myReader.nextLine();
                                }
                                System.out.println(data);
                                myReader.close();
                                out.println(data);
                                out.flush();
                            } catch (FileNotFoundException e) {
                                System.out.println("File mit dem Key: " + key + " wurde nicht gefunden");
                                out.println("FAILED");
                                out.flush();
                            }
                        } else {
                            try {
                                out.println("Befehl nicht erkannt");
                                out.flush();
                                System.out.println("Falscher Befehl, Verbindung wird geschlossen");
                                connection.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    try {
                        System.out.println("Verbindung wird geschlossen");
                        out.flush();
                        connection.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (connection != null) connection.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}
