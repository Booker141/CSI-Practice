package es.uca.gii.csi18.oblivion.gui;

import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi18.oblivion.data.Race;
import es.uca.gii.csi18.oblivion.data.Student;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrStudents extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtAge;
	private JTable tabResult;
	private Container pnlParent;

	/**
	 * Create the frame.
	 */
	public IfrStudents(JFrame frame) {
		pnlParent = frame;
		setClosable(true);
		setResizable(true);
		setTitle("Students");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblName = new JLabel("Nombre");
		panel.add(lblName);
		
		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAge = new JLabel("Edad");
		panel.add(lblAge);
		
		txtAge = new JTextField();
		panel.add(txtAge);
		txtAge.setColumns(10);
		
		JLabel lblRace = new JLabel("Raza");
		panel.add(lblRace);
		
		JComboBox<Race> cmbRace = new JComboBox<Race>();
		try {
			cmbRace.setModel(new RaceListModel(Race.Select()));
		}
		catch (Exception ee) {
			JOptionPane.showMessageDialog(null, "Hubo algún problema al acceder a las Razas: ",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
		cmbRace.setEditable(true);
		panel.add(cmbRace);
		
		JButton btnSearch = new JButton("Buscar");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Integer iAge = (txtAge.getText().length() == 0) ? null
						: Integer.parseInt(txtAge.getText().trim());
					String sRace = (cmbRace.getSelectedItem() == null) ? "" :
						((Race)cmbRace.getSelectedItem()).toString();
					
					tabResult.setModel(new StudentsTableModel(
						Student.Select((sRace.isEmpty()) ? null : sRace,
						txtName.getText(), iAge)));
				}
				catch (Exception ee) {
					ee.printStackTrace();
					JOptionPane.showMessageDialog(null,
						"Hubo un problema al realizar la busqueda: " + ee.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		panel.add(btnSearch);
		
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int iRow = ((JTable)e.getSource()).getSelectedRow();
					Student student = ((StudentsTableModel)tabResult.getModel()).getData(iRow);
					if (student != null) {
						IfrStudent ifrStudent = new IfrStudent(student);
						ifrStudent.setBounds(10, 27, 244, 192);
						pnlParent.add(ifrStudent, 0);
						ifrStudent.setVisible(true);
					}
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);

	}

}
