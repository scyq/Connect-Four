package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class chessBoard extends JFrame {

	private JPanel playPanel;
	private JTextArea stepsInfo;
	private Datas theData;
	private int xCoordinate = 0;
	private int yCoordinate = 0;
	
	private class chessPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			// draw the background rectangle
			g.setColor(Color.pink);
			g.fillRect(xCoordinate, yCoordinate, Main.chessBoardHorizon*50, Main.chessBoardVertic*50);
			
			// draw circles
			for (int i = 0; i < Main.chessBoardHorizon; i++) {
				for (int j = 0; j < Main.chessBoardVertic; j++) {
					
					// empty chess
					if (theData.chessInfo[i][j] == -1) {
						g.setColor(Color.white);
						g.fillOval(50 * i, 50 * j, 50, 50);;
					}
					
					else if (theData.chessInfo[i][j] == 0) {
						g.setColor(Color.red);
						g.fillOval(50 * i, 50 * j, 50, 50);
					}
					
					else if (theData.chessInfo[i][j] == 1) {
						g.setColor(Color.yellow);
						g.fillOval(50 * i, 50 * j, 50, 50);
					}
				}
			}
			
		}
	}
	
	// data information

	public chessBoard() {
		
		theData = new Datas();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200 + 60 * Main.chessBoardHorizon, 60 * Main.chessBoardVertic);
		playPanel = new JPanel();
		playPanel.setBackground(Color.PINK);
		playPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(playPanel);
		playPanel.setLayout(new BorderLayout(0, 0));
		
		
		
		chessPanel cPanel = new chessPanel();
		cPanel.setSize(50 * Main.chessBoardHorizon, 50 * Main.chessBoardVertic);
		cPanel.setBackground(Color.pink);
		cPanel.addMouseListener(new MouseAdapter() {
			int mouseX = -1;
			int mouseY = -1;
			boolean flg = false;	// whether update correctly
			int winner = -1;
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseX=e.getX();
				mouseY=e.getY();
				// have to put on the top
				if (mouseY <= 50 && Main.model == 1 && (mouseX <= Main.chessBoardHorizon * 50)) {
					int last = Datas.chessOrder;
					flg = theData.updateChessInfo(mouseX / 50);
					
					// update steps information
					if (last == 0 && flg) {
						stepsInfo.append("Red put the pawn in line " + ((mouseX/50)+1) + "\n");
						Datas.steps++;
					}
					else if (last == 1 && flg) {
						stepsInfo.append("Yellow put the pawn in line " + ((mouseX/50)+1) + "\n");
						Datas.steps++;
					}
					
				}
				repaint();
				
				// judge
				if (flg)  winner = theData.checkWin();
				if (winner == 0 || winner == 1) dispose();
			}
		});
		playPanel.add(cPanel, BorderLayout.CENTER);
				
		
		
		JPanel infoPanel = new JPanel();
		playPanel.add(infoPanel, BorderLayout.EAST);
		infoPanel.setLayout(new BorderLayout(0, 0));
		
		JButton restartButton = new JButton("Restart");
		infoPanel.add(restartButton, BorderLayout.NORTH);
		
		stepsInfo = new JTextArea();
		stepsInfo.setEditable(false);
		stepsInfo.setColumns(20);
		
		// set the scroll of steps information
		JScrollPane stepsInfoJSP = new JScrollPane(stepsInfo);
		stepsInfoJSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		stepsInfoJSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		infoPanel.add(stepsInfoJSP, BorderLayout.CENTER);
		
		
		JButton backButton = new JButton("Back to Menu");
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		infoPanel.add(backButton, BorderLayout.SOUTH);
		
	}
	
	public void run() {
		setVisible(true);
	}

}
