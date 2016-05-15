package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import control.FileManager;
import control.FlowManager;
import control.Handler;
import data.Model;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ScriptsPanel extends JPanel {


	private static final long serialVersionUID = 1L;

	private JComboBox comboScripts;
    private DefaultComboBoxModel comboModel;
	private JLabel lblFlow;
	private JButton buttonAdd, buttonUp, buttonDown, buttonClear, buttonSave, buttonNew, buttonSaveas;
	private JPanel buttonPanel, scriptListPanel;
	private JScrollPane scriptsListScrollPane;
	private JList scriptsList;
	private DefaultListModel scriptsListModel;
    private JScrollPane scriptsLoadScrollPane;
    private JList scriptLoad;
    private DefaultListModel scriptsLoadModel;
	private MainFrame mainFrame;
	private FlowManager flowManager;

	public ScriptsPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.setBorder(new BevelBorder(BevelBorder.LOWERED));
		flowManager = Handler.getInstance().getFlowManager();
		initComponents();
		setButtonPanel();
		setScriptListPanel();
		addComponents();
		addActionListeners();
	}

	private void setScriptListPanel() {
		scriptListPanel.setLayout(new GridBagLayout());

		scriptListPanel.add(scriptsListScrollPane, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		scriptListPanel.add(buttonUp, new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.SOUTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), -20, -16));
		scriptListPanel.add(buttonDown, new GridBagConstraints(2, 1, 1, 1, 0.0, 1.0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), -20, -16));

	}

	private void setButtonPanel() {
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.add(buttonNew, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(buttonClear, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(buttonSaveas, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		buttonPanel.add(buttonSave, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

	}

	private void addComponents() {
		this.setLayout(new GridBagLayout());
		this.add(lblFlow, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));
		this.add(comboScripts, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));
		this.add(buttonAdd, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(6, 6, 6, 6), 0, 0));
		this.add(buttonPanel, new GridBagConstraints(0, 1, 3, 1, 1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));
		this.add(scriptListPanel, new GridBagConstraints(0, 2, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(6, 6, 6, 6), 0, 0));

	}

	private void addActionListeners() {
		scriptsList.addMouseListener(new PopClickListener(new popUpMenu(), scriptsList));
		buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonAddActionPerformed();
			}

		});
		buttonNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonNewActionPerformed();
			}

		});
		buttonClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonClearActionPerformed();
			}

		});
		buttonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonSaveActionPerformed();
			}

		});
		buttonUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveScript("up");
			}

		});
		buttonDown.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				moveScript("down");
			}

		});
		buttonSaveas.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buttonSaveasActionPerformed();
			}

		});
	}

	private void initComponents() {
		lblFlow = new JLabel("Script");
		buttonAdd = new JButton("Add");
		buttonNew = new JButton("New");
		buttonClear = new JButton("Clear");
		buttonSave = new JButton("Save");
		buttonSaveas = new JButton("Save as");
		buttonSaveas.setEnabled(false);
		buttonUp = new JButton();
		buttonDown = new JButton();
		buttonUp.setIcon(new ImageIcon(FileManager.getInstance().getImage("up")));
		buttonDown.setIcon(new ImageIcon(FileManager.getInstance().getImage("down")));
		resizebuttons();
		buttonPanel = new JPanel();
		scriptListPanel = new JPanel();
		comboScripts = new JComboBox();
		comboModel = new DefaultComboBoxModel(flowManager.getScriptsArray());
		comboScripts.setModel(comboModel);
		scriptsListModel = new DefaultListModel();
		scriptsList = new JList(scriptsListModel);
		scriptsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scriptsList.setCellRenderer(new scriptListCellRenderer());
		scriptsListScrollPane = new JScrollPane(scriptsList);
		scriptsListScrollPane.setBorder(BorderFactory.createTitledBorder("Scripts List"));

      /*  scriptsLoadModel = new DefaultListModel();
        scriptLoad = new JList(scriptsLoadModel);
        scriptLoad.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scriptLoad.setCellRenderer(new scriptListCellRenderer());
        scriptsLoadScrollPane = new JScrollPane(scriptLoad);
        scriptsLoadScrollPane.setBorder(BorderFactory.createTitledBorder("Scripts Load"));*/
	}

	private void resizebuttons() {
		buttonAdd.setMinimumSize(new Dimension(60, 22));
		buttonAdd.setPreferredSize(new Dimension(60, 22));
		buttonNew.setMinimumSize(new Dimension(60, 22));
		buttonNew.setPreferredSize(new Dimension(60, 22));
		buttonSaveas.setPreferredSize(new Dimension(60, 22));
		buttonSaveas.setPreferredSize(new Dimension(60, 22));
		buttonClear.setMinimumSize(new Dimension(60, 22));
		buttonClear.setPreferredSize(new Dimension(60, 22));
		buttonSave.setMinimumSize(new Dimension(60, 22));
		buttonSave.setPreferredSize(new Dimension(60, 22));
	}

	private void moveScript(String move) {

		int index = flowManager.moveScript(move, scriptsList.getSelectedIndex());
		setScriptsList();
		scriptsList.setSelectedIndex(index);

	}

	private void buttonAddActionPerformed() {

		scriptsListModel.addElement(flowManager.addScriptToList(comboScripts.getSelectedIndex()).getName());

	}

	private void buttonNewActionPerformed() {

		flowManager.setSelectedFlowIndex(-1);
		buttonClearActionPerformed();
	}

	private void buttonClearActionPerformed() {

		scriptsListModel = new DefaultListModel();
		scriptsList.setModel(scriptsListModel);
		flowManager.clear();
		buttonSaveas.setEnabled(false);

	}

	private void buttonSaveActionPerformed() {
		// if (!flowManager.getScriptsInOrder().isEmpty()) {
		// String flowName = JOptionPane.showInputDialog("Enter flow name :");
		// if (flowName != null) {
		// flowName = flowName + ".flw";
		// try {
		try {
			if (flowManager.saveScripts()) {
				mainFrame.getFlowsPanel().setFlows();
				mainFrame.getFlowsPanel().setSelectedFlow(flowManager.getFlows().size() - 1);
			}

		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save scripts. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save scripts. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		// JOptionPane.showMessageDialog(mainFrame, "Flow is saved");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		//
		// }
		// } else {
		// JOptionPane.showMessageDialog(mainFrame, "Please set a flow to
		// save");
		// }
	}

	private void buttonSaveasActionPerformed() {
		try {
			if (flowManager.saveNewScripts()) {
				mainFrame.getFlowsPanel().setFlows();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save scripts. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(Handler.getInstance().getMainFrame(), "Couldn't save scripts. Exceptin " + e,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setScripts(int index) throws FileNotFoundException {
		flowManager.setSelectedFlow(index);
		setScriptsList();
	}

	public void setScriptsList() {

		scriptsListModel = new DefaultListModel();
		for (Model script : flowManager.getScriptsInOrder()) {
			scriptsListModel.addElement(script.getName());
		}
		scriptsList.setModel(scriptsListModel);
		repaint();
		buttonSaveas.setEnabled(true);
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

					flowManager.deleteScript(scriptsList.getSelectedIndex());
					setScriptsList();

				}
			});
		}

	}

	@SuppressWarnings("serial")
	class scriptListCellRenderer extends JLabel implements ListCellRenderer {
		public scriptListCellRenderer() {
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList paramlist, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {

			setText(value.toString());

			switch (flowManager.getScriptsInOrder().get(index).getStatus()) {
			case NOTRUNNING:
				setForeground(Color.BLACK);
				setBackground(Color.WHITE);
				break;
			case RUNNING:
				setForeground(Color.BLACK);
				setBackground(new Color(204, 229, 255));
				break;
			case SUCCESS:

				setForeground(Color.BLACK);
				setBackground(new Color(51, 255, 51));
				break;

			case FAILED:
				setBackground(new Color(255, 102, 102));
				setForeground(Color.BLACK);

				break;
			}
			if (isSelected) {
				setBackground(Color.LIGHT_GRAY);
				setForeground(Color.BLACK);
			}
			return this;
		}
	}

}
