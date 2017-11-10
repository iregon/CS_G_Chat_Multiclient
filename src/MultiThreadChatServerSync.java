import java.io.PrintStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.net.ServerSocket;

// the Server class
public class MultiThreadChatServerSync {
	// The server socket.
	private static ServerSocket serverSocket = null;
	// The client socket.
	private static Socket clientSocket = null;

	// This chat server can accept up to maxClientsCount clients' connections.
	//  private static final int maxClientsCount = 10;
	//  private static final clientThread[] threads = new clientThread[maxClientsCount];
  
	private static final ArrayList<clientThread> threads = new ArrayList<clientThread>();
	private static final ArrayList<Room> rooms = new ArrayList<Room>();

	public static void main(String args[]) {
		
		//Add default room
		rooms.add(new Room("Main"));

		// The default port number.
		int portNumber = 2222;
		if (args.length < 1) {
			System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n" + "Now using port number=" + portNumber);
		} else {
			portNumber = Integer.valueOf(args[0]).intValue();
		}

	    /*
	     * Open a server socket on the portNumber (default 2222). Note that we can
	     * not choose a port less than 1023 if we are not privileged users (root).
	     */
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.err.println("ERRORE: " + e.getMessage());
		}
		
		//Create the GUI
		ServerFrame sf = new ServerFrame();
		JFrame frame = sf;
		frame.setTitle("MyChatApp Server - " + serverSocket.getLocalSocketAddress());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

	    /*
	     * Create a client socket for each connection and pass it to a new client
	     * thread.
	     */
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				
				sf.addLog("New user: " + clientSocket.getRemoteSocketAddress());
				threads.add(new clientThread(clientSocket, threads));
				threads.get(threads.size() - 1).start();
//		        int i = 0;
//		        for (i = 0; i < maxClientsCount; i++) {
//		          if (threads[i] == null) {
//		            (threads[i] = new clientThread(clientSocket, threads)).start();
//		            break;
//		          }
//		        }
//		        if (i == maxClientsCount) {
//		          PrintStream os = new PrintStream(clientSocket.getOutputStream());
//		          os.println("Server too busy. Try later.");
//		          os.close();
//		          clientSocket.close();
//		        }
			} catch (IOException e) {
				System.err.println("ERRORE: " + e.getMessage());
			}
		}
	}  
}