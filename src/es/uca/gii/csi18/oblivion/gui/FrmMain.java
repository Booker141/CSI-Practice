package es.uca.gii.csi18.oblivion.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gesti\u00F3n de Estudiantes");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mitNew = new JMenu("Nuevo");
		menuBar.add(mitNew);
		
		JMenuItem mitNewStudent = new JMenuItem("Estudiante");
		mitNewStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrStudent ifrStudent = new IfrStudent(null);
				ifrStudent.setBounds(10, 27, 244, 192);
				frame.getContentPane().add(ifrStudent);
				ifrStudent.setVisible(true);
			}
		});
		mitNew.add(mitNewStudent);
		
		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);
		
		JMenuItem mitSearchStudent = new JMenuItem("Estudiante");
		mitSearchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrStudents ifrStudents = new IfrStudents(frame);
				ifrStudents.setBounds(12, 28, 244, 192);
				frame.getContentPane().add(ifrStudents, 0);
				ifrStudents.setVisible(true);
			}
		});
		mitSearch.add(mitSearchStudent);
		frame.getContentPane().setLayout(null);
	}
}
