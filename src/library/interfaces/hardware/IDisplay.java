package library.interfaces.hardware;

import javax.swing.JPanel;

import library.panels.borrow.ABorrowPanel;

public interface IDisplay {

	public JPanel getDisplay();
	public void setDisplay(JPanel panel, String identifier);

}
