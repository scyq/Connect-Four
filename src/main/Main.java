package main;

import java.awt.*;
import javax.swing.*;




public class Main {
	public static int chessBoardHorizon = 7;
	public static int chessBoardVertic = 6;
	public static String name = "User";
	// 0-easy 1-hard
	public static int difficulty = 0;
	
	// 0-AI 1-Person
	public static int model = 1;
	
	public static void main (String[] args) {
		/*
		 * 
		 * Launch the welcome page
		 * 
		 */
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePage frame = new WelcomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}