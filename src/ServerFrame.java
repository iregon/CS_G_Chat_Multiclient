import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class ServerFrame extends JFrame{
	
	private ArrayList<clientThread> threads = new ArrayList<clientThread>();
	private JPanel p_mainPanel;
	private JPanel p_log;
	private JPanel p_manageUsers;
	private JTextArea ta_log;
	private JTabbedPane tp_tabbedPane;

	public ServerFrame(ArrayList<clientThread> threads) {
		super();
		this.threads = threads;
		buildGUI();
	}
	
	public void buildGUI() {
		Container c = this.getContentPane();
    	
    	p_mainPanel = new JPanel();
    	p_mainPanel.setLayout(new BorderLayout());
    	
    	c.add(p_mainPanel);
    	
    	tp_tabbedPane = new JTabbedPane();
    	p_log = new JPanel();
    	
    	ta_log = new JTextArea(20, 50);
        ta_log.setEditable(false);
        ta_log.setLineWrap(true);
        p_log.add(new JScrollPane(ta_log), BorderLayout.CENTER);
        
        tp_tabbedPane.add("Log", p_log);
        
        p_manageUsers = new JPanel();
        
        
        tp_tabbedPane.addTab("Manage users", p_manageUsers);
        
        p_mainPanel.add(tp_tabbedPane);
	}
}
