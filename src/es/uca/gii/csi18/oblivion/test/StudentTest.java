package es.uca.gii.csi18.oblivion.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.uca.gii.csi18.oblivion.data.Data;
import es.uca.gii.csi18.oblivion.data.Race;
import es.uca.gii.csi18.oblivion.data.Student;

class StudentTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Test
	void testConstructor() throws Exception {
		int iId = 1;
		Student student = new Student(iId);		//Creating a Student with an existing ID in the db.
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Name, Age FROM Student "
					+ "WHERE Id = " + iId);		//Searching it in the db.
			rs.next();
			assertEquals(rs.getInt("Id"), student.getId());
			assertEquals(rs.getString("Name"), student.getName());		//Coincidences?
			assertEquals(rs.getInt("Age"), student.getAge());
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testCreate() throws Exception {
		Student student = Student.Create(new Race(4), "Chema", 36);	//Creating a random Student.
		Connection con = null;	
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Name, Age FROM Student "	//Searching that Student to
					+ "WHERE Id = " + student.getId() + ";");	//verify if it was created in the db.
			rs.next();
			assertEquals(rs.getInt("Id"), student.getId());
			assertEquals(rs.getString("Name"), student.getName());	//Existing?
			assertEquals(rs.getInt("Age"), student.getAge());
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testDelete() throws Exception {
		Student student = Student.Create(new Race(2), "DarkElf96", 689);	//Creating a random Student.
		student.Delete();		//Deleting that Student.
		Connection con = null;
		ResultSet rs = null;
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Name FROM Student WHERE Id = "
					+ Data.LastId(con));		//Searching the same Student.
			
			assertEquals(false, rs.last());		//Coincidences?
			assertEquals(true, student.getIsDeleted());	//The Student object is updated?
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
			Student student = Student.Create(new Race(2), "Kimudo Jeato", 16);	//Creating a random Student.
			student.setName("Kidumo Teajo");	
			student.setAge(61);		//Modifying that Student.
			student.Update();	//Updating it in the db.
			
			student = new Student(student.getId());	//Reading the Student with same ID from the db.
			assertEquals("Kidumo Teajo", student.getName());
			assertEquals(61, student.getAge());	//Coincidences?
			student.Delete();
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (con != null) con.close();
		}
	}
	
	@Test
	void testSelect() throws Exception {
		Student student = Student.Create(new Race(1), "Luis", 24);		//Creating two random Students with similar names.
		Student student2 = Student.Create(new Race(2), "Luis Miguel", 57);
		ArrayList<Student> aStudent = Student.Select(null, "Luis", null);	//Sending a short name to select students.
		assertEquals(2, aStudent.size());
		assertEquals("Luis", aStudent.get(0).getName());
		assertEquals(24, aStudent.get(0).getAge());
		assertEquals("Luis Miguel", aStudent.get(1).getName());
		assertEquals(57, aStudent.get(1).getAge());
		student.Delete();
		student2.Delete();
	}

}
