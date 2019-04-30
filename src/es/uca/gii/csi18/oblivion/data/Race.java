package es.uca.gii.csi18.oblivion.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Race extends Entity {
	
	private String _sName;
	
	//Preconditions: The received int needs to be an existing Id in the DB.
	//Postconditions: Get the Race with that Id and the others values from the DB.
	/**
	 * @param iId
	 * @throws Exception
	 */
	public Race(int iId) throws Exception {
		Connection con = null;
		
		try {
			con = Data.Connection();
			this.Initialize(iId, con);
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
		}
	}
	
	//Preconditions: Data is obtained from the db.
	//Postconditions: Creates an instance of Race with the given information. 
	/**
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	public Race(int iId, Connection con) throws Exception {
		try {
			this.Initialize(iId, con);
		}
		catch (SQLException ee) { throw ee; }
	}
	
	//Preconditions: 
	//Postconditions: Initialize the Race with the given Id.
	/**
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	private void Initialize(int iId, Connection con) throws Exception {
		ResultSet rs = null;
		
		try {
			rs = con.createStatement().executeQuery("SELECT Name FROM Race WHERE Id = "
				+ iId);
			rs.next();
			_iId = iId;
			_sName = rs.getString("Name");
			_bIsDeleted = false;
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
		}
	}
	
	public String getName() { return _sName; }
	
	public void setName(String sName) { _sName = sName; }
	
	public String toString() { return _sName; }
	
	//Preconditions: 
	//Postconditions: Get a Race after insert it in the DB.
	/**
	 * @param sName
	 * @return
	 * @throws Exception
	 */
	public static Race Create(String sName) throws Exception {
		Connection con = null;		//Connection variable initialized
		try {
			con = Data.Connection();	//Connection established
			con.createStatement().executeUpdate("INSERT INTO Race (Name) "
				+ "VALUES(" + Data.String2Sql(sName, true, false) 
				+ ");");	//Updating these new values in the DB
			
			return new Race(Data.LastId(con));	//Returning the new Race
		}
		catch (Exception ee) { throw ee; }
		finally {
			if (con != null) con.close();	//Closing connection
		}
	}
	
	//Preconditions: The race must exist in the db.
	//Postconditions: The race is deleted from the db.
	/**
	 * @throws Exception
	 */
	public void Update() throws Exception {
		try {
			super.Update("UPDATE Race SET Name = " + Data.String2Sql(_sName, true, false)
				+ " WHERE Id = " + _iId);
		}
		catch (SQLException ee) { throw ee; }
	}
	
	//Preconditions:
	//Postconditions: Returns an ordered list with all the races from the db.
	/**
	 * @return ArrayList<Race>
	 * @throws Exception
	 */
	public static ArrayList<Race> Select() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Name FROM Race"
				+ " ORDER BY Name");
			ArrayList<Race> aRace = new ArrayList<Race>();
			while (rs.next())	//Creating each Race with the quick-constructor.
				aRace.add(new Race(rs.getInt("Id"), con));
			return aRace;
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
}