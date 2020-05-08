package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class WelcomePage extends JFrame {

	private JPanel welcomePagePanel;
	/**
	 * Create the frame.
	 */
	public WelcomePage() {
		setTitle("Welcome");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		welcomePagePanel = new JPanel();
		welcomePagePanel.setBackground(Color.PINK);
		welcomePagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		welcomePagePanel.setLayout(new BorderLayout(0, 0));
		setContentPane(welcomePagePanel);
		
		JLabel lblConnectFour = new JLabel("Connect Four");
		lblConnectFour.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectFour.setFont(new Font("Times New Roman", Font.BOLD, 40));
		welcomePagePanel.add(lblConnectFour, BorderLayout.NORTH);
		
		JPanel chosePanel = new JPanel();
		welcomePagePanel.add(chosePanel, BorderLayout.CENTER);
		chosePanel.setLayout(new GridLayout(3, 1, 0, 0));
		
		JButton playButton = new JButton("Play");
		playButton.setBackground(Color.WHITE);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chessBoard c = new chessBoard();
				c.run();
			}
		});
		playButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
		chosePanel.add(playButton);
		
		JButton settingButton = new JButton("Options");
		settingButton.setBackground(Color.WHITE);
		settingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				settingPage t = new settingPage();
				t.run();
			}
		});

		settingButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
		chosePanel.add(settingButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBackground(new Color(255, 255, 255));
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		exitButton.setFont(new Font("Yu Gothic UI", Font.BOLD, 30));
		chosePanel.add(exitButton);
		
		Component hSWest = Box.createHorizontalStrut(20);
		welcomePagePanel.add(hSWest, BorderLayout.WEST);
		
		Component hSEast = Box.createHorizontalStrut(20);
		welcomePagePanel.add(hSEast, BorderLayout.EAST);
		
		JLabel lblCopyright = new JLabel("@scyq 18105226 ≥¬”Ó«‰");
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		lblCopyright.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		welcomePagePanel.add(lblCopyright, BorderLayout.SOUTH);
	}
	

}
