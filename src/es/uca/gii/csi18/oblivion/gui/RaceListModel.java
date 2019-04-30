package es.uca.gii.csi18.oblivion.gui;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import es.uca.gii.csi18.oblivion.data.Race;

public class RaceListModel extends AbstractListModel<Race> implements ComboBoxModel<Race> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Race> _aData;
	private Object _oSelectedItem = null;
	
	public RaceListModel(List<Race> aList) {
		_aData = aList;
	}
	
	public Race getElementAt(int iIndex) { return _aData.get(iIndex); }
	
	public int getSize() { return _aData.size(); }
	
	public Object getSelectedItem() { return _oSelectedItem; }
	
	public void setSelectedItem(Object object) { _oSelectedItem = object; }
	
}
