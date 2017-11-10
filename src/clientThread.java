import java.io.*;
import java.net.*;
import java.util.*;

// For every client's connection we call this class
public class clientThread extends Thread{
	private String clientName = null;
	private ObjectInputStream is = null;
	private ObjectOutputStream os = null;
	private Socket clientSocket = null;
	private final ArrayList<clientThread> threads;
	private Room room = new Room("Main");
	
	public clientThread(Socket clientSocket, ArrayList<clientThread> threads) {
	    this.clientSocket = clientSocket;
	    this.threads = threads;
	    this.clientName = clientSocket.getInetAddress().getHostAddress();
//	    maxClientsCount = threads.size();
	  }
	
	  public void run() {
//	    int maxClientsCount = this.maxClientsCount;
	    ArrayList<clientThread> threads = this.threads;
	
	    try {
	      /*
	       * Create input and output streams for this client.
	       */
	      is = new ObjectInputStream(clientSocket.getInputStream());
	      os = new ObjectOutputStream(clientSocket.getOutputStream());
	      String name;
	      
	      while (true) {
	        os.writeObject(new Message("MSG", null, this.clientName, this.room.getName(), "Insert a name"));
	        name = ((Message)is.readObject()).getText().trim();
	        if (name.indexOf('@') == -1) {
	          break;
	        } else {
	          os.writeObject(new Message("ERR", null, this.clientName, this.room.getName(), "The name should not contain '@' character."));
	        }
	      }
	
	      /* Welcome the new the client. */
	      os.writeObject(new Message("MSG", null, this.clientName, this.room.getName(), "Welcome " + name + " to our chat room.\nTo leave enter /quit in a new line."));
	      synchronized (this) {
//	        for (int i = 0; i < threads.size(); i++) {
//	          if (threads.get(i) == this) {
//	            clientName = "@" + name;
//	            break;
//	          }
//	        }
	        
	        for (int i = 0; i < threads.size(); i++) {
	          if (threads.get(i) != null && threads.get(i) != this) {
	        	  threads.get(i).os.writeObject(new Message("MSG", this.clientName, this.room.getName(), "*** A new user " + name + " entered the chat room !!! ***"));
	          }
	        }
	      }
	      /* Start the conversation. */
	      while (true) {
	        Message msg = (Message)is.readObject();
	        if (msg.getText().startsWith("/quit")) {
	          break;
	        }
	        /* If the message is private sent it to the given client. */
	        if (msg.getText().startsWith("@")) {
	          String[] words = msg.getText().split("\\s", 2);
	          if (words.length > 1 && words[1] != null) {
	            words[1] = words[1].trim();
	            if (!words[1].isEmpty()) {
	              synchronized (this) {
	                for (int i = 0; i < threads.size(); i++) {
	                  if (threads.get(i) != null && threads.get(i) != this && threads.get(i).clientName != null && threads.get(i).clientName.equals(words[0])) {
	                	  threads.get(i).os.writeObject(new Message("MSG", this.clientName, words[0], this.room.getName(), "<" + name + "> " + words[1]));
	                    /*
	                     * Echo this message to let the client know the private
	                     * message was sent.
	                     */
	                    this.os.writeObject(new Message("MSG", this.clientName, words[0], this.room.getName(), ">" + name + "> " + words[1]));
	                    break;
	                  }
	                }
	              }
	            }
	          }
	        } else {
	          /* The message is public, broadcast it to all other clients. */
	          synchronized (this) {
	            for (int i = 0; i < threads.size(); i++) {
	              if (threads.get(i) != null && threads.get(i).clientName != null) {
	            	  threads.get(i).os.writeObject(new Message("MSG", this.clientName, this.room.getName(), "<" + name + "> " + msg.getText()));
	              }
	            }
	          }
	        }
	      }
	      synchronized (this) {
	        for (int i = 0; i < threads.size(); i++) {
	          if (threads.get(i) != null && threads.get(i) != this && threads.get(i).clientName != null) {
	        	  threads.get(i).os.writeObject(new Message("MSG", this.clientName, this.room.getName(), "*** The user " + name + " is leaving the chat room !!! ***"));
	          }
	        }
	      }
	      os.writeObject(new Message("MSG", this.clientName, this.room.getName(), "*** Bye " + name + " ***"));
	
	      /*
	       * Clean up. Set the current thread variable to null so that a new client
	       * could be accepted by the server.
	       */
	      synchronized (this) {
	        for (int i = 0; i < threads.size(); i++) {
	          if (threads.get(i) == this) {
	        	  threads.remove(i);
	          }
	        }
	      }
	      /*
	       * Close the output stream, close the input stream, close the socket.
	       */
	      is.close();
	      os.close();
	      clientSocket.close();
	    } catch (IOException e) {
	    } catch (ClassNotFoundException e) {
			System.err.println("ERRORE: " + e.getMessage());
		}
	  }
}