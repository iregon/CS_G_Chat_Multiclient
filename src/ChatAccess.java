import java.io.*;
import java.net.*;
import java.util.*;

/** Chat client access */
class ChatAccess extends Observable {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private String name = null;
    private Room room = new Room("Main");

    @Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }

    /** Create socket, and receiving thread */
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());

        Thread receivingThread = new Thread() {
            @Override
            public void run() {
                try {
                    ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
                    String line;
                    while ((line = ((Message)reader.readObject()).getText()) != null)
                        notifyObservers(line);
                } catch (IOException ex) {
                } catch (ClassNotFoundException ex) {
                    notifyObservers(ex);
                }
            }
        };
        receivingThread.start();
    }

//    private static final String CRLF = "\r\n"; // newline

    /** Send a line of text */
    public void send(String text) {
        try {
        	if(name == null) name = text;
            outputStream.writeObject(new Message("MSG", name, room.getName(), text));
            outputStream.flush();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }

    /** Close the socket */
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
}