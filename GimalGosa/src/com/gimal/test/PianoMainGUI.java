package com.gimal.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

public class PianoMainGUI extends JFrame {

	JCheckBox select_sound[][] = new JCheckBox[7][20];
	JCheckBox note_check[][] = new JCheckBox[7][20];
	JMenuItem mi[] = new JMenuItem[4];
	JButton note_button[] = new JButton[7];
	JButton control_button[] = new JButton[3];

	File sound_do = new File("do.wav");
	File sound_re = new File("re.wav");
	File sound_mi = new File("mi.wav");
	File sound_fa = new File("fa.wav");
	File sound_sol = new File("sol.wav");
	File sound_la = new File("la.wav");
	File sound_ti = new File("ti.wav");

	public PianoMainGUI() {
		Container ct = getContentPane();

		JPanel northP, north_1, north_2, centerP, southP;
		String note_str[] = { "도", "레", "미", "파", "솔", "라", "시" };
		String mi_str[] = { "Save", "Open", "Reset", "Logout" };
		String control_str[] = { "STOP", "PLAY", "PAUSE" };

		// 메뉴바
		JMenuBar menu = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		for (int i = 0; i < mi.length; i++) {
			mi[i] = new JMenuItem(mi_str[i]);
			mi[i].addActionListener(new Menu_Event());
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
			note_button[i].addActionListener(new Note_ButtonEvent());
			north_1.add(note_button[i]);
		}

		// 메인 (체크박스)
		north_2 = new JPanel();
		north_2.setLayout(new GridLayout(7, 1, 10, 10));

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 20; j++) {
				note_check[i][j] = new JCheckBox(note_str[i], false);
				note_check[i][j].setBorderPainted(true);
				note_check[i][j].addItemListener(new Note_CheckEvent());
				north_2.add(note_check[i][j]);
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
			control_button[i].addActionListener(new Control_ButtonEvent());
			southP.add(control_button[i]);
		}

		// 버튼컬러
		note_button[0].setBackground(new Color(255, 0, 0)); // 빨
		note_button[1].setBackground(new Color(255, 100, 0)); // 주
		note_button[2].setBackground(new Color(255, 255, 0)); // 노
		note_button[3].setBackground(new Color(0, 255, 0)); // 초
		note_button[4].setBackground(new Color(0, 100, 255)); // 파
		note_button[5].setBackground(new Color(0, 0, 255)); // 남
		note_button[6].setBackground(new Color(100, 0, 255)); // 보

		// 글꼴컬러
		note_button[0].setForeground(new Color(0, 255, 0));
		note_button[1].setForeground(new Color(0, 0, 255));
		note_button[2].setForeground(new Color(100, 0, 255));
		note_button[3].setForeground(new Color(255, 0, 0));
		note_button[4].setForeground(new Color(255, 100, 0));
		note_button[5].setForeground(new Color(255, 50, 0));
		note_button[6].setForeground(new Color(255, 255, 0));

		// 글꼴
		for (int i = 0; i < note_button.length; i++) {
			note_button[i].setFont(new Font("MapleStory Bold", Font.BOLD, 40));
		}

		// 컨테이너에 붙이기
		ct.add(northP, BorderLayout.NORTH);
		ct.add(centerP, BorderLayout.CENTER);
		ct.add(southP, BorderLayout.SOUTH);

		// 여백
		northP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		centerP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		southP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// 기본설정
		setTitle("Piano");
		setSize(1400, 750);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class Sound {
		// note버튼 이벤트 1옥타브 올리고 싶으면 + 붙이기 ex) sound_do+

		public void stream_do() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_do);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(도) 음원이 존재하지 않습니다.");
			}
		}

		public void stream_re() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_re);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(레) 음원이 존재하지 않습니다.");
			}
		}

		public void stream_mi() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_mi);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(미) 음원이 존재하지 않습니다.");
			}
		}

		public void stream_fa() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_fa);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(파) 음원이 존재하지 않습니다.");
			}
		}

		public void stream_sol() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_sol);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(솔) 음원이 존재하지 않습니다.");
			}
		}

		public void stream_la() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_la);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(라) 음원이 존재하지 않습니다.");
			}
		}

		public void stream_ti() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(sound_ti);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "(시) 음원이 존재하지 않습니다.");
			}
		}

	}

	class Menu_Event implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String action = e.getActionCommand();
			// 메뉴바 이벤트
			if (action == "Save") {
				JOptionPane.showMessageDialog(null, "Save Test");
			} else if (action == "Open") {
				JOptionPane.showMessageDialog(null, "Open Test");
			} else if (action == "Reset") {

				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 20; j++) {
						note_check[i][j].setSelected(false);
					}
				}

			} else if (action == "Logout") {
				JOptionPane.showMessageDialog(null, "Logout Test");
			}
		}
	}

	class Note_ButtonEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String action = e.getActionCommand();
			Sound sound = new Sound();
			if (action == "도") {
				sound.stream_do();
			} else if (action == "레") {
				sound.stream_re();
			} else if (action == "미") {
				sound.stream_mi();
			} else if (action == "파") {
				sound.stream_fa();
			} else if (action == "솔") {
				sound.stream_sol();
			} else if (action == "라") {
				sound.stream_la();
			} else if (action == "시") {
				sound.stream_ti();
			}
		}
	}

	class Note_CheckEvent implements ItemListener {
		// checkBox 이벤트
		Sound sound = new Sound();

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[0][j]) {
						note_check[0][j].setBackground(new Color(255, 0, 0));
						note_check[0][j].setForeground(new Color(0, 0, 255));
					}
				} else {
					if (e.getSource() == note_check[0][j]) {
						note_check[0][j].setBackground(new Color(238, 238, 238));
						note_check[0][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[0][j] = note_check[0][j];
			}

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[1][j]) {
						note_check[1][j].setBackground(new Color(255, 100, 0));
						note_check[1][j].setForeground(new Color(0, 255, 0));
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					if (e.getSource() == note_check[1][j]) {
						note_check[1][j].setBackground(new Color(238, 238, 238));
						note_check[1][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[1][j] = note_check[1][j];
			}

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[2][j]) {
						note_check[2][j].setBackground(new Color(255, 255, 0));
						note_check[2][j].setForeground(new Color(100, 0, 255));
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					if (e.getSource() == note_check[2][j]) {
						note_check[2][j].setBackground(new Color(238, 238, 238));
						note_check[2][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[2][j] = note_check[2][j];
			}

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[3][j]) {
						note_check[3][j].setBackground(new Color(0, 255, 0));
						note_check[3][j].setForeground(new Color(255, 0, 0));
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					if (e.getSource() == note_check[3][j]) {
						note_check[3][j].setBackground(new Color(238, 238, 238));
						note_check[3][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[3][j] = note_check[3][j];
			}

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[4][j]) {
						note_check[4][j].setBackground(new Color(0, 100, 255));
						note_check[4][j].setForeground(new Color(255, 100, 0));
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					if (e.getSource() == note_check[4][j]) {
						note_check[4][j].setBackground(new Color(238, 238, 238));
						note_check[4][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[4][j] = note_check[4][j];
			}

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[5][j]) {
						note_check[5][j].setBackground(new Color(0, 0, 255));
						note_check[5][j].setForeground(new Color(255, 50, 0));
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					if (e.getSource() == note_check[5][j]) {
						note_check[5][j].setBackground(new Color(238, 238, 238));
						note_check[5][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[5][j] = note_check[5][j];
			}

			for (int j = 0; j < 20; j++) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (e.getSource() == note_check[6][j]) {
						note_check[6][j].setBackground(new Color(100, 0, 255));
						note_check[6][j].setForeground(new Color(255, 255, 0));
					}
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					if (e.getSource() == note_check[6][j]) {
						note_check[6][j].setBackground(new Color(238, 238, 238));
						note_check[6][j].setForeground(new Color(0, 0, 0));
					}
				}
				select_sound[6][j] = note_check[6][j];
			}
		}
	}

	class Control_ButtonEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Sound sound = new Sound();
			String action = e.getActionCommand();
			boolean stp = false;

			if (action == "PLAY") {
				if (!stp) {
					new Thread() {
						public void run() {
							try {
								// 도 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[0][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[0][i].isSelected()) {
										sound.stream_do();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}

						}
					}.start();

					new Thread() {
						public void run() {
							try {
								// 레 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[1][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[1][i].isSelected()) {
										sound.stream_re();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}
						}
					}.start();

					new Thread() {
						public void run() {
							try {
								// 미 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[2][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[2][i].isSelected()) {
										sound.stream_mi();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}
						}
					}.start();

					new Thread() {
						public void run() {
							try {
								// 파 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[3][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[3][i].isSelected()) {
										sound.stream_fa();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}
						}
					}.start();

					new Thread() {
						public void run() {
							try {
								// 솔 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[4][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[4][i].isSelected()) {
										sound.stream_sol();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}
						}
					}.start();

					new Thread() {
						public void run() {
							try {
								// 라 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[5][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[5][i].isSelected()) {
										sound.stream_la();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}
						}
					}.start();

					new Thread() {
						public void run() {
							try {
								// 시 출력
								for (int i = 0; i < 20; i++) {
									if (!select_sound[6][i].isSelected()) {
										Thread.sleep(500);
									} else if (select_sound[6][i].isSelected()) {
										sound.stream_ti();
										Thread.sleep(500);
									}
								}
							} catch (Exception ae) {
								JOptionPane.showMessageDialog(null, "실행중 오류 발생");
							}
						}
					}.start();
				}else {

				}

			} else if (action == "STOP") {
				
				
			} else if (action == "PAUSE") {

			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new PianoMainGUI();
	}
}