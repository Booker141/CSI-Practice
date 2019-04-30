package es.uca.gii.csi18.oblivion.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends Entity {
	private Race _race;
	private String _sName;
	private int _iAge;
	
	//Preconditions: The received int needs to be an existing Id in the DB.
	//Postconditions: Get the Student with that Id and the others values from the DB.
	/**
	 * @param iId
	 * @throws Exception
	 */
	public Student(int iId) throws Exception	{
		Connection con = null;	//Connection initialized
		try {
			con = Data.Connection();	//Connection established
			this.Initialize(iId, con);
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();	//Closing the connection to the DB.
		}
	}
	
	//Preconditions: 
	//Postconditions: Creates the Student with iId using the con Connection.
	/**
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	public Student(int iId, Connection con) throws Exception {
		try {
			this.Initialize(iId, con);
		}
		catch (SQLException ee) { throw ee; }
	}
	
	//Preconditions: 
	//Postconditions: Initialize the Student with the given Id.
	/**
	 * @param iId
	 * @param con
	 * @throws Exception
	 */
	private void Initialize(int iId, Connection con) throws Exception {
		ResultSet rs = null;
		
		try {
			rs = con.createStatement().executeQuery("SELECT * FROM Student WHERE "
				+ "Id = " + iId);  //Getting results from the query
			rs.next();		//Taking the only row
			_iId = rs.getInt("Id");
			_race = new Race(rs.getInt("Id_Race"), con);
			_sName = rs.getString("Name");	//Assigning the row values
			_iAge = rs.getInt("Age");
			_bIsDeleted = false;
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
		}
	}
	
	public Race getRace() { return _race; }
	
	public void setRace(Race race) { _race = race; }
	
	public String getName() { return _sName; }
	
	public void setName(String sName) {	_sName = sName; }
	
	public int getAge() { return _iAge;	}
	
	public void setAge(int iAge) { _iAge = iAge; }
	
	//Preconditions:
	//Postconditions: Get a string that represents all the info about a Student.
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return super.toString() + ":" + _iId + ":" + _race.toString() + ":" + _sName
			+ ":" + _iAge;
	}
	
	//Preconditions: 
	//Postconditions: Get a Student after insert it in the DB.
	/**
	 * @param race
	 * @param sName
	 * @param iAge
	 * @return Student
	 * @throws Exception
	 */
	public static Student Create(Race race, String sName, int iAge) throws Exception {
		Connection con = null;		//Connection variable initialized
		try {
			Student.Validate(iAge);
			con = Data.Connection();	//Connection established
			con.createStatement().executeUpdate("INSERT INTO Student (Id_Race, Name, Age) "
				+ "VALUES(" + race.getId() + ", " + Data.String2Sql(sName, true, false) 
				+ ", " + iAge + ");");	//Updating these new values in the DB
			
			return new Student(Data.LastId(con));	//Returning the new Student
		}
		catch (Exception ee) { throw ee; }
		finally {
			if (con != null) con.close();	//Closing connection
		}
	}
	
	//Preconditions: The student must exist in the db.
	//Postconditions: The student is deleted from the db.
	/**
	 * @throws Exception
	 */
	public void Update() throws Exception {
		try {
			Student.Validate(_iAge);
			super.Update("UPDATE Student SET Id_Race = "
				+ _race.getId() + ", Name = " + Data.String2Sql(_sName, true, false)
				+ ", Age = " + _iAge + " WHERE Id = " + _iId);
		} 
		catch (SQLException ee) { throw ee; }
	}
	
	//Preconditions:
	//Postconditions: Returns a list that contains Students with same age and similar name.
	/**
	 * @param sRace
	 * @param sName
	 * @param iAge
	 * @return ArrayList<Student>
	 * @throws Exception
	 */
	public static ArrayList<Student> Select(String sRace, String sName, Integer iAge)
		throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Student.Id FROM Student "
				+ "JOIN Race ON Id_Race = Race.Id " + Student.Where(sRace, sName, iAge));
			ArrayList<Student> aStudent = new ArrayList<Student>();
			while (rs.next())	//Creating each student with the quick-constructor
				aStudent.add(new Student(rs.getInt("Id"), con));
			return aStudent;
		}
		catch (Exception ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	//Preconditions: 
	//Postconditions: Returns a WHERE sentence.
	/**
	 * @param sRace
	 * @param sName
	 * @param iAge
	 * @return String
	 */
	private static String Where(String sRace, String sName, Integer iAge) {
		String sResult = "";
		if (sRace != null && !sRace.isEmpty())
			sResult += "Race.Name LIKE " + Data.String2Sql(sRace, true, true) + " AND ";
		if (sName != null && !sName.isEmpty())
			sResult += "Student.Name LIKE " + Data.String2Sql(sName, true, true) + " AND ";
		if (iAge != null)
			sResult += "Age = " + iAge + " AND ";
		if (sResult.length() == 0)
			return "";
		else
			return "WHERE " + sResult.substring(0, sResult.length()-5);
	}	

	//Preconditions:
	//Postconditions: Throws an exception if the age is under 0.
	/**
	 * @param iAge
	 * @throws Exception
	 */
	private static void Validate(Integer iAge) throws Exception {
		if (iAge < 0) throw new Exception ("La Edad introducida no es valida.");
	}
	
}