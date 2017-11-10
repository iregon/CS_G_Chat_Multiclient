import java.util.*;
import javax.swing.*;

public class ClientFrame extends JFrame implements Observer{
	
	public ClientFrame() {
		super("Client");
		
		setVisible(true);
		setLocation(20, 20);
		setSize(100, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
}
