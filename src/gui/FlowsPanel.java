package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import control.FileManager;
import control.FlowManager;
import control.Handler;
import control.LogManager;
import control.ZipFileManager;
import data.Model;

@SuppressWarnings("rawtypes")
public class FlowsPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel;
	private JLabel lblStatus;
	private JButton buttonUp, buttonDown, buttonRun, buttonZip;
	private JCheckBox checkRunAll;
	private JScrollPane flowsListScrollPane;
	private JList flowsList;
	private DefaultListModel flowsListModel;
	private MainFrame mainFrame;
	private FlowManager flowManager;
	public static final Dimension DIMENSION = new Dimension(MainFrame.DIMENSION.width / 2, MainFrame.DIMENSION.height);

	public FlowsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

	}

	public void init() throws IOException {
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		flowManager = Handler.getInstance().getFlowManager();
		initComponents();
		setButtonPanel();
		addComponents();
		addActionListeners();
	}

	private void initComponents() throws IOException {
		buttonPanel = new JPanel();
		lblStatus = new JLabel("");
		buttonUp = new JButton();
		buttonDown = new JButton();
		buttonRun = new JButton("Run");
		buttonRun.setMinimumSize(new Dimension(60, 22));
		buttonRun.setPreferredSize(new Dimension(60, 22));
		buttonRun.setEnabled(false);
		buttonZip = new JButton("Zip CUTS files");
		buttonZip.setMinimumSize(new Dimension(60, 22));
		buttonZip.setPreferredSize(new Dimension(95, 22));
		buttonZip.setEnabled(false);
		checkRunAll = new JCheckBox("Run all");
		buttonUp.setIcon(new ImageIcon(FileManager.getInstance().getImage("up")));
		buttonDown.setIcon(new ImageIcon(FileManager.getInstance().getImage("down")));
		flowsList = new JList<Object>();
		setFlows();

		flowsListScrollPane = new JScrollPane(flowsList);
		flowsListScrollPane.setBorder(BorderFactory.createTitledBorder("Flows"));

	}

	private void setButtonPanel() {
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.add(lblStatus, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(buttonZip, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(checkRunAll, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(buttonRun, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

	}

	private void addComponents() {
		this.setLayout(new GridBagLayout());
		this.add(flowsListScrollPane, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(buttonUp, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.SOUTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), -20, -16));
		this.add(buttonDown, new GridBagConstraints(1, 1, 1, 1, 0.0, 1.0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), -20, -16));
		this.add(buttonPanel, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.NORTH,
				GridBagConstraints.HORIZONTAL, new Insets(10, 6, 6, 6), 0, 0));

		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.setMinimumSize(DIMENSION);
		this.setPreferredSize(DIMENSION);
	}

	private void addActionListeners() {
		flowsList.addMouseListener(new PopClickListener(new popUpMenu(), flowsList));
		flowsList.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				flowListSelectionChanged();

			}
		});
		flowsList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				flowListSelectionChanged();
			}
		});

		buttonRun.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonRunActionPerformed();
			}

		});
		buttonZip.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonZipActionPerformed();
			}

		});
		buttonUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveFLow("up");
			}

		});
		buttonDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveFLow("down");
			}

		});
	}

	private void moveFLow(String move) {


		try {
			int index = flowManager.moveFlow(move, flowsList.getSelectedIndex());
			flowManager.saveFlow();
			setFlows();
			flowsList.setSelectedIndex(index);
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save flow. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save flow. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked" })
	public void setFlows() {
		flowsListModel = new DefaultListModel();
		for (Model flow : flowManager.getFlows()) {
			flowsListModel.addElement(flow.getName());
		}
		flowsList.setModel(flowsListModel);
	}

	private void buttonRunActionPerformed() {

		if (flowManager.getScriptsInOrder().isEmpty()) {
			flowsList.setSelectedIndex(0);
		}
		try {
			LogManager.getInstance().startLogging();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save log. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save log. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		if (flowManager.run(checkRunAll.isSelected(), flowsList.getSelectedIndex())) {

			buttonRun.setText("Stop");
			flowsList.setEnabled(false);
			mainFrame.setState(MainFrame.ICONIFIED);

		} else {
			flowCompleted();
		}
	}

	private void buttonZipActionPerformed() {

		try {
			if (ZipFileManager.getInstance().zipCUTS()) {
				buttonZip.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "No files found", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Zip failed " + e, "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			buttonZip.setEnabled(true);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Zip failed " + e, "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			buttonZip.setEnabled(true);
		}

	}

	public void flowCompleted() {
		try {
			LogManager.getInstance().endLogging();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save log. Exception " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save log. Exception " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		setStatus("Completed");
		flowsList.setEnabled(true);
		buttonRun.setText("Run");
		buttonZip.setEnabled(true);
	}

	private void flowListSelectionChanged() {
		if (flowsList.getSelectedIndex() >= 0) {

			try {
				mainFrame.getScriptsPanel().setScripts(flowsList.getSelectedIndex());
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Flow file missing " + e, "Error",
						JOptionPane.ERROR_MESSAGE);

				e.printStackTrace();
			}
		}
	}

	public void setSelectedFlow(int index) {
		flowsList.setSelectedIndex(index);

	}

	class popUpMenu extends JPopupMenu {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JMenuItem menuDelete;

		public popUpMenu() {
			menuDelete = new JMenuItem("delete");
			add(menuDelete);
			menuDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						flowManager.deleteFlow(flowsList.getSelectedIndex());
					} catch (UnsupportedEncodingException e1) {
						JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(),
								"Couldn't save flow. Exceptin " + e, "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(),
								"Couldn't save flow. Exceptin " + e, "Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					setFlows();

				}
			});
		}

	}

	public void enableZip(boolean enable) {
		buttonZip.setEnabled(enable);
	}

	public void enableRun(boolean enable) {
		buttonRun.setEnabled(enable);

	}

	public void setStatus(String status) {
		lblStatus.setText(status);
	}
}
