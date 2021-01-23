package Aufgabe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCP_Client2 {
	
	
	private static final int serverPort = 7777;
	
	public static void main (String[] args) {
	
		String hostname = "localhost";
		PrintWriter networkOut;
		BufferedReader networkIn;
		Socket s = null;
		
		try {
			s = new Socket(hostname, serverPort);
			System.out.println("Client2 Verbunden");
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
			System.err.println(e.toString());
		}
		finally {
			try {
				if(s != null) {
					System.out.println("Es besteht keine Verbindung");
					s.close();
					//main(new String[2]);
				}//if				
			}catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}//finally		
	}//main
}//class
