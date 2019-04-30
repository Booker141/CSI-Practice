package es.uca.gii.csi18.oblivion.data;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Entity {
	
	protected int _iId;
	protected boolean _bIsDeleted;
	
	public int getId() { return _iId; }
	
	public boolean getIsDeleted() { return _bIsDeleted; }
	
	//Preconditions: The query string has no SQL syntax error.
	//Postconditions: The entity is updated in the db.
	/**
	 * @param sQuery
	 * @throws Exception
	 */
	protected void Update(String sQuery) throws Exception {
		if (_bIsDeleted)
			throw new Exception("La entidad elegida ya ha sido eliminada.");
		Connection con = null;
		
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate(sQuery);
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
		}
	}
	
	//Preconditions: 
	//Postconditions: The entity is erased from the db.
	/**
	 * @throws Exception
	 */
	public void Delete() throws Exception {
		if (_bIsDeleted)
			throw new Exception("La entidad seleccionada ya no existe.");
		
		Connection con = null;
		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM " +
				this.getClass().getSimpleName() + " WHERE Id = " + _iId);
			
			_bIsDeleted = true;
		} 
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
		}
	}
	
}