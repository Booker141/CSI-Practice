package es.uca.gii.csi18.oblivion.gui;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import es.uca.gii.csi18.oblivion.data.*;

public class StudentsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Student> _aData;
	
	public StudentsTableModel(ArrayList<Student> aData) { _aData = aData; }
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public Object getValueAt(int iRow, int iCol) {
		switch (iCol) {
				case 0: return _aData.get(iRow).getName();
				case 1: return _aData.get(iRow).getAge();
				case 2: return _aData.get(iRow).getRace().getName();
				default: throw new IllegalStateException();
		}
	}

	public Student getData(int iRow) {
		return _aData.get(iRow);
	}
	
}
