package es.uca.gii.csi18.oblivion.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.oblivion.data.Data;
import es.uca.gii.csi18.oblivion.data.Race;

class RaceTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Test
	void testConstructor() throws Exception {
		int iId = 1;
		Race race = new Race(iId);		//Creating a Race with an existing ID in the db.
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Name FROM Race "
					+ "WHERE Id = " + iId);		//Searching it in the db.
			rs.next();
			assertEquals(rs.getInt("Id"), race.getId());
			assertEquals(rs.getString("Name"), race.getName());		//Coincidences?
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testSelect() throws Exception {
		ArrayList<Race> aRace = Race.Select();
		assertEquals(4, aRace.size());		//Set the first param as the number of races that exists in the db in this moment.
		boolean bIsLesserThan = aRace.get(0).getName().compareTo(aRace.get(1).getName()) < 0;
		assertEquals(true, bIsLesserThan);
		bIsLesserThan = aRace.get(1).getName().compareTo(aRace.get(2).getName()) < 0;
		assertEquals(true, bIsLesserThan);
	}

	@Test
	void testCreate() throws Exception {
		Race race = Race.Create("Poturians");	//Creating a random Race.
		Connection con = null;	
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Name FROM Race "	//Searching that Race to
					+ "WHERE Id = " + race.getId() + ";");	//verify if it was created in the db.
			rs.next();
			assertEquals(rs.getInt("Id"), race.getId());
			assertEquals(rs.getString("Name"), race.getName());	//Existing?
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testDelete() throws Exception {
		Race race = Race.Create("Rabittos");	//Creating a random Race.
		race.Delete();		//Deleting that Race.
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Name FROM Race WHERE Id = "
					+ Data.LastId(con));		//Searching the same Race.
			
			assertEquals(false, rs.last());		//Coincidences?
			assertEquals(true, race.getIsDeleted());	//The Race object is updated?
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testUpdate() throws Exception {
		Connection con = null;
		try {
			con = Data.Connection();
			Race race = Race.Create("Crustaceans");	//Creating a random Race.
			race.setName("Crustaceos");		//Modifying that Race.
			race.Update();	//Updating it in the db.
			
			race = new Race(race.getId());	//Reading the Race with same ID from the db.
			assertEquals("Crustaceos", race.getName());	//Coincidences?
			race.Delete();
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
		}
	}
}
