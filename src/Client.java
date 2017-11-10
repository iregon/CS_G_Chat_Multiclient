import java.net.*;
import java.io.*;

public class Client {
	
	private Socket con;
	
	public Client(String host, int port) {
		try {
			con = new Socket(host, port);
		}
		catch(Exception e) {
			
		}
	}

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 3400;
		new Client(host, port);
	}

}
