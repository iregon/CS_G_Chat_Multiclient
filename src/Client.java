import java.net.*;
import java.io.*;
import javax.swing.*;

public class Client {
	
	private Socket con;
	
	public Client(String host, int port) {
		try {
			con = new Socket(host, port);
			JFrame frame = new ClientFrame();
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
