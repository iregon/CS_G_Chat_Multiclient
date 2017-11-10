import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/** Chat client UI */
class ClientFrame extends JFrame implements Observer {
	
	private JTextArea textArea;
	private JTextField inputTextField;
	private JButton sendButton;
	private JPanel p_mainPanel;
	private ChatAccess chatAccess;
	
    public ClientFrame(ChatAccess chatAccess) {
        this.chatAccess = chatAccess;
        chatAccess.addObserver(this);
        buildGUI();
    }

    /** Builds the user interface */
    private void buildGUI() {
    	Container c = this.getContentPane();
    	
    	p_mainPanel = new JPanel();
    	p_mainPanel.setLayout(new BorderLayout());
    	
    	c.add(p_mainPanel);
    	
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        p_mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        Box box = Box.createHorizontalBox();
        p_mainPanel.add(box, BorderLayout.SOUTH);
        inputTextField = new JTextField();
        sendButton = new JButton("Send");
        box.add(inputTextField);
        box.add(sendButton);

        // Action for the inputTextField and the goButton
        ActionListener sendListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if (str != null && str.trim().length() > 0)
                    chatAccess.send(str);
                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            }
        };
        inputTextField.addActionListener(sendListener);
        sendButton.addActionListener(sendListener);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                chatAccess.close();
            }

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
    }

    /** Updates the UI depending on the Object argument */
    public void update(Observable o, Object arg) {
        final Object finalArg = arg;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(finalArg.toString());
                textArea.append("\n");
            }
        });
    }
}