package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPopupMenu;

@SuppressWarnings("rawtypes")
public class PopClickListener extends MouseAdapter {
	JPopupMenu popUpMenu;
	JList list;

	public PopClickListener(JPopupMenu popUpMenu, JList list) {

		this.popUpMenu = popUpMenu;
		this.list = list;
	}

	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger())
			doPop(e);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger())
			doPop(e);
	}

	private void doPop(MouseEvent e) {
		if (list.getSelectedIndex() >= 0) {
			popUpMenu.show(e.getComponent(), e.getX(), e.getY());
		}
	}

}
