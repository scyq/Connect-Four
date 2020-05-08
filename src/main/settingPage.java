package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class settingPage extends JFrame {

	private JPanel settingPagePanel;
	private JTextField txtUser;


	/**
	 * Create the frame.
	 */
	
	public void run() {
		this.setVisible(true);
	}
	
	public settingPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		settingPagePanel = new JPanel();
		settingPagePanel.setBackground(Color.PINK);
		settingPagePanel.setForeground(Color.BLACK);
		settingPagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(settingPagePanel);
		settingPagePanel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel nameSettingPanel = new JPanel();
		nameSettingPanel.setBackground(Color.PINK);
		settingPagePanel.add(nameSettingPanel);
		nameSettingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblName = new JLabel("     Name");
		lblName.setFont(new Font("Times New Roman", Font.BOLD, 20));
		nameSettingPanel.add(lblName);
		
		txtUser = new JTextField();
		txtUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.name = txtUser.getText();
			}
		});
		txtUser.setText(Main.name);
		nameSettingPanel.add(txtUser);
		txtUser.setColumns(20);
		
		JPanel chessBoardSize = new JPanel();
		chessBoardSize.setBackground(Color.PINK);
		settingPagePanel.add(chessBoardSize);
		
		JLabel lblChessBoardSize = new JLabel("Chess Board Size");
		lblChessBoardSize.setFont(new Font("Times New Roman", Font.BOLD, 20));
		chessBoardSize.add(lblChessBoardSize);
		
		JComboBox cbSizeH = new JComboBox();
		cbSizeH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.chessBoardHorizon = cbSizeH.getSelectedIndex()+6;
			}
		});
		cbSizeH.setBackground(Color.PINK);
		cbSizeH.setModel(new DefaultComboBoxModel(new String[] {"6", "7", "8", "9", "10"}));
		cbSizeH.setSelectedIndex(1);
		chessBoardSize.add(cbSizeH);
		
		JComboBox cbSizeV = new JComboBox();
		cbSizeV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.chessBoardVertic = cbSizeV.getSelectedIndex() + 6;
			}
		});
		cbSizeV.setBackground(Color.PINK);
		cbSizeV.setModel(new DefaultComboBoxModel(new String[] {"6", "7", "8", "9", "10"}));
		cbSizeV.setSelectedIndex(0);
		chessBoardSize.add(cbSizeV);
		
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setBackground(Color.PINK);
		settingPagePanel.add(difficultyPanel);
		
		JLabel lblDifficulty = new JLabel("Difficulty");
		lblDifficulty.setFont(new Font("Times New Roman", Font.BOLD, 20));
		difficultyPanel.add(lblDifficulty);
		
		JRadioButton rbEasy = new JRadioButton("Stupid");
		rbEasy.setBackground(Color.PINK);
		if (Main.difficulty == 0) rbEasy.setSelected(true);
		rbEasy.setFont(new Font("SimSun", Font.PLAIN, 12));
		difficultyPanel.add(rbEasy);
		
		JRadioButton rbHard = new JRadioButton("Incredible");
		rbHard.setBackground(Color.PINK);
		rbHard.setHorizontalAlignment(SwingConstants.CENTER);
		difficultyPanel.add(rbHard);
		
		JPanel modelPanel = new JPanel();
		modelPanel.setBackground(Color.PINK);
		settingPagePanel.add(modelPanel);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		modelPanel.add(lblModel);
		
		JRadioButton rbAI = new JRadioButton("vs AI");
		rbAI.setBackground(Color.PINK);
		rbAI.setHorizontalAlignment(SwingConstants.TRAILING);
		modelPanel.add(rbAI);
		
		JRadioButton rbP = new JRadioButton("vs Person");
		rbP.setBackground(Color.PINK);
		rbP.setSelected(true);
		modelPanel.add(rbP);
		
		JButton returnButton = new JButton("Back");
		returnButton.setBackground(Color.PINK);
		returnButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		returnButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		settingPagePanel.add(returnButton);
		
		
	}

}