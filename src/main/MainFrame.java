package main;

import javax.swing.*;

class MyFrame extends JFrame{
	public MyFrame() {
		super("Connect Four"); //Title
		setSize(800, 600); // WINDOW SIZE
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// EXIT BOTTOM
	}
}



public class MainFrame {
	public static void main (String[] args) {
		MyFrame f = new MyFrame();
		f.setVisible(true);

	}
}
