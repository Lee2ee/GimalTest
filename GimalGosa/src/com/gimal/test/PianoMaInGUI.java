package com.gimal.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

class Piano extends JFrame implements ActionListener {
	public Piano() {
		Container ct = getContentPane();

		JPanel northP, north_1, north_2, centerP, southP;
		String note_str[] = { "도", "레", "미", "파", "솔", "라", "시" };
		String mi_str[] = { "Save", "Open", "Reset", "Logout" };
		String control_str[] = {"STOP", "PLAY", "PAUSE"};
		JMenuItem mi[] = new JMenuItem[4];
		JButton note_button[] = new JButton[7];
		JCheckBox note_check[] = new JCheckBox[7];
		JButton control_button[] = new JButton[3];
		
		// 메뉴바
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		for (int i = 0; i < mi.length; i++) {
			mi[i] = new JMenuItem(mi_str[i]);
			mi[i].addActionListener(this);
			fileMenu.add(mi[i]);
		}
		menu.add(fileMenu);
		setJMenuBar(menu);

		// 메인 (버튼)
		northP = new JPanel();
		northP.setLayout(new BorderLayout(1, 2));

		north_1 = new JPanel();
		north_1.setLayout(new GridLayout(7, 1, 10, 10));
		for (int i = 0; i < note_button.length; i++) {
			note_button[i] = new JButton(note_str[i]);
			note_button[i].setPreferredSize(new Dimension(200, 60));
			note_button[i].addActionListener(this);
			north_1.add(note_button[i]);
		}
		

		// 메인 (체크박스)
		north_2 = new JPanel();
		north_2.setLayout(new GridLayout(7, 1, 10, 10));
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 20; j++) {
				note_check[i] = new JCheckBox(note_str[i]);
				north_2.add(note_check[i]);
			}
		}
		northP.add(north_1, BorderLayout.WEST);
		northP.add(north_2, BorderLayout.CENTER);
		
		// 프로그레스 바
		centerP = new JPanel();
		centerP.setLayout(new GridLayout(1, 1, 10, 10));
		JProgressBar pb = new JProgressBar();
		pb.setValue(0);
		pb.setStringPainted(true);
		Border pbborder = BorderFactory.createTitledBorder("진행률...");
		pb.setBorder(pbborder);
		centerP.add(pb);
		
		// 정지 시작 일시정지 버튼
		southP = new JPanel();
		southP.setLayout(new GridLayout(1, 3, 100, 10));
		for (int i = 0; i < control_button.length; i++) {
			control_button[i] = new JButton(control_str[i]);
			control_button[i].setPreferredSize(new Dimension(10, 80));
			control_button[i].addActionListener(this);
			southP.add(control_button[i]);
		}
		
		// 컨테이너에 붙이기
		ct.add(northP, BorderLayout.NORTH);
		ct.add(centerP, BorderLayout.CENTER);
		ct.add(southP, BorderLayout.SOUTH);
		
		// 여백
		northP.setBorder(BorderFactory.createEmptyBorder(10 , 10, 10 , 10));
		centerP.setBorder(BorderFactory.createEmptyBorder(10 , 10, 10 , 10));
		southP.setBorder(BorderFactory.createEmptyBorder(10 , 10, 10 , 10));

		// 기본설정
		setTitle("Piano");
		setSize(1400, 750);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String action = e.getActionCommand();
		// 메뉴바 이벤트
		if (action.equals("Save")) {
			JOptionPane.showMessageDialog(null, "Save Test");
		} else if (action == "Open") {
			JOptionPane.showMessageDialog(null, "Open Test");
		} else if (action == "Reset") {
			JOptionPane.showMessageDialog(null, "Reset Test");
		} else if (action == "Logout") {
			JOptionPane.showMessageDialog(null, "Logout Test");
		}

		// note버튼 이벤트
		if (action.equals("도")) {
			JOptionPane.showMessageDialog(null, "도 Test");
		} else if (action == "레") {
			JOptionPane.showMessageDialog(null, "레 Test");
		} else if (action == "미") {
			JOptionPane.showMessageDialog(null, "미 Test");
		} else if (action == "파") {
			JOptionPane.showMessageDialog(null, "파 Test");
		} else if (action == "솔") {
			JOptionPane.showMessageDialog(null, "솔 Test");
		} else if (action == "라") {
			JOptionPane.showMessageDialog(null, "라 Test");
		} else if (action == "시") {
			JOptionPane.showMessageDialog(null, "시 Test");
		}

	}
}

public class PianoMaInGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Piano p = new Piano();

	}

}
