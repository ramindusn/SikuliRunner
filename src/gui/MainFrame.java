package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import control.FileManager;
import control.Handler;
import control.LogManager;


public class MainFrame extends JFrame {
 
    private static final long serialVersionUID = 1L;

    private JMenuBar menuBar;
    private JMenu menu;
    private JCheckBoxMenuItem menuItemLog;
    private JMenuItem menuItmExit;
    private ScriptsPanel scriptsPanel;
    private FlowsPanel flowsPanel;
    public static final String LOOKANDFEEL = "com.jtattoo.plaf.fast.FastLookAndFeel";
    public static Dimension DIMENSION;

    public MainFrame() {

    }

    public void init() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
	    UnsupportedLookAndFeelException {
	DIMENSION = new Dimension(900, 500);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setTitle("Sikuli Runner");
	this.setIconImage(FileManager.getInstance().getImage("icon"));

	UIManager.setLookAndFeel(LOOKANDFEEL);
	
	this.setSize(DIMENSION);
	this.setMinimumSize(DIMENSION);
	this.setPreferredSize(DIMENSION);
	
	this.pack();
	this.setLocationRelativeTo(null);

	initComponents();
	addComponents();
	addActionListeners();

    }

    private void addComponents() {
	menuBar.add(menu);
	menu.add(menuItemLog);
	menu.add(menuItmExit);
	this.setJMenuBar(menuBar);

	this.setLayout(new GridBagLayout());
	this.add(flowsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	this.add(scriptsPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
		GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    }

    private void addActionListeners() {

	menuItemLog.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		LogManager.getInstance().setLoggingEnabled(menuItemLog.isSelected());
	    }
	});
	menuItmExit.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		exitCheck();
	    }
	});
	this.addWindowListener(new java.awt.event.WindowAdapter() {
	    @Override
	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

		exitCheck();

	    }
	});
    }

    private void initComponents() throws IOException {
	menuBar = new JMenuBar();
	menu = new JMenu("Menu");

	menuItemLog = new JCheckBoxMenuItem("Enable logging");
	menuItemLog.setSelected(LogManager.getInstance().isLoggingEnabled());
	menuItmExit = new JMenuItem("Exit");
	scriptsPanel = new ScriptsPanel(this);
	flowsPanel = new FlowsPanel(this);
	flowsPanel.init();
    }

    private void exitCheck() {
	int d = JOptionPane.showConfirmDialog(Handler.getInstance().getMainFrame(), "Are you sure you want to exit?",
		"Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	if (d == JOptionPane.YES_OPTION) {

	    System.exit(0);
	}

    }

    public ScriptsPanel getScriptsPanel() {
		return scriptsPanel;
    }

    public FlowsPanel getFlowsPanel() {
		return flowsPanel;
    }

}
