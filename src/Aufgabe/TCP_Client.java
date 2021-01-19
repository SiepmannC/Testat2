package Aufgabe;
import java.net.*;
import java.io.*;

public class TCP_Client {
	
	
	public static final int serverPort = 7777; 
	
	public static void main (String[] args) {
	
		String hostname = "localhost";
		PrintWriter networkOut = null;
		BufferedReader networkIn = null;
		Socket s = null;
		
		try {
			s = new Socket(hostname, serverPort);
			System.out.println("Connected to echo server");
			networkIn = new BufferedReader( new InputStreamReader(s.getInputStream()));
			System.out.println(networkIn.readLine());
			BufferedReader userIn = new BufferedReader( new InputStreamReader(System.in));
			networkOut = new PrintWriter(s.getOutputStream());
			
			while(true) {
				String theLine = userIn.readLine();
				if(theLine.equals(".")) {
					networkOut.flush();
					break;
				}//if
				networkOut.println(theLine);
				networkOut.flush();
				System.out.println(networkIn.readLine());
			}//while
			
		}catch(IOException e){
			System.out.println("Bis hier");
			System.err.println(e);
		}
		finally {
			try {
				if(s != null) {
					System.out.println("Verbindung wird geschlossen");
					s.close();
				}//if				
			}catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			
		}//finally		
	}//main
}//class
