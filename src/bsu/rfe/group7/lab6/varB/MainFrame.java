package bsu.rfe.group7.lab6.varB;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrame extends JFrame {

	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;

	private JMenuItem pauseMenuItem;
	private JMenuItem resumeMenuItem;
	private JMenuItem magnetismMenuItem;
	
	private Field field = new Field();

	public MainFrame() {
		super("Программирование и синхронизация потоков");
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();
		
		setLocation((kit.getScreenSize().width - WIDTH)/2, 
		(kit.getScreenSize().height - HEIGHT)/2);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu ballMenu = new JMenu("Мячи");
		Action addBallAction = new AbstractAction("Добавить мяч") {
			public void actionPerformed(ActionEvent event) {
				field.addBall();
				if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
					pauseMenuItem.setEnabled(true);
				}
			}
		};
		menuBar.add(ballMenu);
		ballMenu.add(addBallAction);
		
		JMenu controlMenu = new JMenu("Управление");
		menuBar.add(controlMenu);
		Action pauseAction = new AbstractAction("Приостановить движение"){
			public void actionPerformed(ActionEvent event) {
				field.pause();
				pauseMenuItem.setEnabled(false);
				if(!magnetismMenuItem.isSelected()) {
					resumeMenuItem.setEnabled(true);
				}
			}
		};
		
		pauseMenuItem = controlMenu.add(pauseAction);
		pauseMenuItem.setEnabled(false);
		Action resumeAction = new AbstractAction("Возобновить движение") {
			public void actionPerformed(ActionEvent event) {
				field.resume();
				pauseMenuItem.setEnabled(true);
				resumeMenuItem.setEnabled(false);
			}
		};
		resumeMenuItem = controlMenu.add(resumeAction);
		resumeMenuItem.setEnabled(false);
		
		JMenu magnetism = new JMenu("Магнетизм");
		Action onMagnetism = new AbstractAction("Добавить магнетизм") {
			public void actionPerformed(ActionEvent event) {
				field.setMagnetism(magnetismMenuItem.isSelected());
				
				if(!magnetismMenuItem.isSelected() && pauseMenuItem.isEnabled()) {
					field.resume();
				}
				if(!magnetismMenuItem.isSelected() && !pauseMenuItem.isEnabled()) {
					resumeMenuItem.setEnabled(true);
				}
			}
		};
		menuBar.add(magnetism);
		magnetismMenuItem = new JCheckBoxMenuItem(onMagnetism);
		magnetism.add(magnetismMenuItem);
	
		getContentPane().add(field, BorderLayout.CENTER);

	}
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
