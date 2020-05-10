package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import java.awt.event.MouseMotionAdapter;

public class chessBoard extends JFrame {

    private JPanel playPanel;
    private JTextArea stepsInfo;
    private Datas theData;
    private int xCoordinate = 0;
    private int yCoordinate = 0;
    private int motionX = 50;
    boolean hasWinner = false;
    public int finalWinner = -1;
    private double count = 0;

    private class chessPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Color DeepSkyBlue = new Color(0,191,255);
            Color SteelBlue = new Color(70,130,180);
            Color alphaGrey = new Color(190,190,190,200);

            // draw the background rectangle
            g.setColor(Color.pink);
            g.fillRect(xCoordinate, yCoordinate, Main.chessBoardHorizon*50, Main.chessBoardVertic*50);


            // draw the background block
            for (int i = 0; i < Main.chessBoardVertic; i++) {
                if (i % 2 == 0) {
                    for (int j = 0; j < Main.chessBoardHorizon; j++) {
                        if (j % 2 == 0) {
                            g.setColor(SteelBlue);
                            g.fillRect(50 * j, 50 * i, 50, 50);
                        }
                        else {
                            g.setColor(DeepSkyBlue);
                            g.fillRect(50 * j, 50 * i, 50, 50);
                        }
                    }
                }

                else {
                    for (int j = 0; j < Main.chessBoardHorizon; j++) {
                        if (j % 2 == 0) {
                            g.setColor(DeepSkyBlue);
                            g.fillRect(50 * j, 50 * i, 50, 50);
                        }
                        else {
                            g.setColor(SteelBlue);
                            g.fillRect(50 * j, 50 * i, 50, 50);
                        }
                    }
                }
            }

            // draw the chosen line hint
            g.setColor(alphaGrey);
            if (motionX <= 50 * Main.chessBoardHorizon) {
                int temp = motionX / 50;
                g.fillRect(temp * 50, 0, 50, 50 * Main.chessBoardVertic);
            }


            // draw circles
            for (int i = 0; i < Main.chessBoardHorizon; i++) {
                for (int j = 0; j < Main.chessBoardVertic; j++) {

                    // empty chess
                    if (theData.chessInfo[i][j] == -1)
                        continue;

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

            if (hasWinner) {
                Color winPageColor = new Color(211,211,211,200);
                g.setColor(winPageColor);
                g.fillRect(xCoordinate, yCoordinate, Main.chessBoardHorizon* 55, Main.chessBoardVertic * 55);

                g.setColor(Color.white);
                g.setFont(new Font("Microsoft Yahei", Font.BOLD, 30));

                int x = (Main.chessBoardHorizon / 2) * 10;
                int y = (Main.chessBoardVertic / 2) * 10;

                // PVE
                if (Main.model == 0) {
                    if (finalWinner == 0) {
                        g.drawString("You Win! " + Main.name, x, y);
                        g.drawString("Steps:" + Datas.steps, x + 50, y + 50);
                    }
                    else if (finalWinner == 1) {
                        g.drawString("You Loser! " + Main.name, x, y);
                        g.drawString("Steps:" + Datas.steps, x + 50, y + 50);

                    }
                    else if (finalWinner == 3){
                        g.drawString("Tie", x, y);
                        g.drawString("Steps:" + Datas.steps, x + 50, y + 50);
                    }
                    else;
                }

                else if (Main.model == 1){
                    if (finalWinner == 0) {
                        g.drawString("Red Win!", x, y);
                        g.drawString("Steps:" + Datas.steps, x + 50, y + 50);
                    }
                    else if (finalWinner == 1) {
                        g.drawString("Yellow Win!", x, y);
                        g.drawString("Steps:" + Datas.steps, x + 50, y + 50);
                    }
                    else if (finalWinner == 3){
                        g.drawString("Tie!", x, y);
                        g.drawString("Steps:" + Datas.steps, x + 50, y + 50);
                    }
                    else;
                }
                else;
            }



        } // paintComponent
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


        // draw the chosen hint
        cPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                motionX = e.getX();
                repaint();
            }
        });


        cPanel.addMouseListener(new MouseAdapter() {
            int mouseX = -1;
            int mouseY = -1;
            boolean flg = false;	// whether update correctly
            int winner = -1;

            @Override
            public void mouseClicked(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();

                if (Main.model == 1 && !hasWinner) {
                    // have to put on the top
                    if (mouseY <= 50 && (mouseX <= Main.chessBoardHorizon * 50)) {
                        int last = Datas.chessOrder;
                        flg = theData.updateChessInfo(mouseX / 50);

                        // update steps information
                        if (last == 0 && flg) {
                            stepsInfo.append("Red: line " + ((mouseX/50)+1) + "\n");
                            Datas.steps++;
                        }
                        else if (last == 1 && flg) {
                            stepsInfo.append("Yellow: line " + ((mouseX/50)+1) + "\n");
                            Datas.steps++;
                        }
                        else {
                            stepsInfo.append("The line is full!! \n");
                        }

                    }
                    repaint();

                    // judge
                    if (flg)  winner = theData.checkWin();
                    if (winner == 0 || winner == 1) {
                        finalWinner = winner;
                        hasWinner = true;
                        repaint();
                    }
                }

                // AI Mode
                else if (Main.model == 0 && !hasWinner){
                    flg = false;

                    // easy mode
                    if (Main.difficulty == 0) {

                        if (mouseY <= 50 && (mouseX <= Main.chessBoardHorizon * 50)) {
                            flg = theData.updateChessInfo(mouseX / 50);
                            if (flg) {
                                stepsInfo.append("You: line " + ((mouseX/50)+1) + "\n");
                                Datas.steps++;
                            }
                            else {
                                stepsInfo.append("The line is full!! \n");
                            }
                        }

                        repaint();
                        if (flg)  winner = theData.checkWin();
                        if (winner == 0) {
                            finalWinner = winner;
                            hasWinner = true;
                            repaint();
                        }

                        flg = false;
                        while(!flg) {
                            int pos = theData.easyAI();
                            flg = theData.updateChessInfo(pos);
                            if (flg) {
                                stepsInfo.append("AI: line" + pos + "\n");
                                Datas.steps++;
                            }
                        }

                        repaint();
                        if (theData.checkWin() == 1) {
                            finalWinner = 1;
                            hasWinner = true;
                            repaint();
                        }

                    }

                    // hard mode
                    else {

                        if (mouseY <= 50 && (mouseX <= Main.chessBoardHorizon * 50)) {
                            flg = theData.updateChessInfo(mouseX / 50);
                            if (flg) {
                                stepsInfo.append("You: line " + ((mouseX/50)+1) + "\n");
                                Datas.steps++;
                            }
                            else {
                                stepsInfo.append("The line is full!! \n");
                            }
                        }

                        repaint();
                        if (flg)  winner = theData.checkWin();
                        if (winner == 0) {
                            finalWinner = winner;
                            hasWinner = true;
                            repaint();
                        }

                        flg = false;
                        int cnt = 0;
                        while(!flg) {
                            cnt++;
                            int pos = theData.hardAI();
                            if (cnt > 5) {
                                boolean find = false;
                                while (!find) {
                                    pos = theData.easyAI();
                                    for (int i = Main.chessBoardVertic-1; i >=0; i--)
                                        if (theData.chessInfo[pos][i] == -1)
                                            find = true;
                                }
                            }
                            flg = theData.updateChessInfo(pos);
                            if (flg) {
                                stepsInfo.append("AI: line" + pos + "\n");
                                Datas.steps++;
                            }
                        }

                        repaint();
                        if (theData.checkWin() == 1) {
                            finalWinner = 1;
                            hasWinner = true;
                            repaint();
                        }

                    }
                }

                else;

            }
        });





        playPanel.add(cPanel, BorderLayout.CENTER);



        JPanel infoPanel = new JPanel();
        playPanel.add(infoPanel, BorderLayout.EAST);
        infoPanel.setLayout(new BorderLayout(0, 0));

        JButton restartButton = new JButton("Restart");
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                chessBoard c = new chessBoard();
                c.run();
                Datas.chessOrder = 0;
                Datas.modelAIchessOrder = 0;
                Datas.steps = 0;
            }
        });
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
                WelcomePage frame = new WelcomePage();
                frame.setVisible(true);
            }
        });
        infoPanel.add(backButton, BorderLayout.SOUTH);

//		// animation
//		class ChessBoardThread implements Runnable{
//			public void run() {
//				while(true) {
//					if (1 - count < 0.000000001) {
//						count = 0.01;
//					}
//					else {
//						repaint();
//						count += 0.03;
//					}
//
//					try
//				}
//			}
//		}


    }

    public void run() {
        setVisible(true);
    }

}
