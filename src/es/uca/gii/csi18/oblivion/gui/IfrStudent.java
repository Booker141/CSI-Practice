package es.uca.gii.csi18.oblivion.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi18.oblivion.data.Race;
import es.uca.gii.csi18.oblivion.data.Student;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class IfrStudent extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtAge;
	private Student _student = null;

	/**
	 * Create the frame.
	 */
	public IfrStudent(Student student) {
		_student = student;
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
		setTitle("Estudiante");
		setBounds(100, 100, 510, 300);
		getContentPane().setLayout(null);
		
		JLabel lblName = new JLabel("Nombre");
		lblName.setBounds(10, 26, 37, 14);
		getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(55, 23, 86, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAge = new JLabel("Edad");
		lblAge.setBounds(23, 51, 24, 14);
		getContentPane().add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setBounds(55, 48, 86, 20);
		getContentPane().add(txtAge);
		txtAge.setColumns(10);

		JLabel lblRace = new JLabel("Raza");
		lblRace.setBounds(171, 26, 46, 14);
		getContentPane().add(lblRace);
		
		JComboBox<Race> cmbRace = new JComboBox<Race>();
		try {
			cmbRace.setModel(new RaceListModel(Race.Select()));
		}
		catch (Exception ee) {
			JOptionPane.showMessageDialog(null, "Hubo algún problema al acceder a las Razas: ",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
		cmbRace.setBounds(203, 23, 70, 20);
		getContentPane().add(cmbRace);
		
		if (student != null) {
			txtName.setText(student.getName());
			txtAge.setText(String.valueOf(student.getAge()));
			cmbRace.getModel().setSelectedItem(student.getRace());
		}
		
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (cmbRace.getModel().getSelectedItem() == null)
						throw new Exception("No se ha elegido una raza.");
					if (_student == null)
						_student = Student.Create((Race)cmbRace.getModel().getSelectedItem(),
							txtName.getText(), Integer.parseInt(txtAge.getText().trim()));
					else {
						_student.setRace((Race)cmbRace.getModel().getSelectedItem());
						_student.setName(txtName.getText());
						_student.setAge(Integer.parseInt(txtAge.getText().trim()));
						_student.Update();
					}
				}
				catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,
						"El campo Edad debe ser numérico.",
						"Error", JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception ee) {
					JOptionPane.showMessageDialog(null,
						"Hubo algún problema al guardar los datos del estudiante: " + ee.getMessage(),
						"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		butSave.setBounds(33, 82, 89, 23);
		getContentPane().add(butSave);
		
	}
}
