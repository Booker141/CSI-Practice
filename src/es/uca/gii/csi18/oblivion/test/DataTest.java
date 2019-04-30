package es.uca.gii.csi18.oblivion.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Ignore;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.oblivion.data.Data;

class DataTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Ignore
	@Test
	void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT * FROM Student;");
			
			int i = 0;
			while (rs.next()) {
				System.out.println(rs.getString("ID") + " " + rs.getString("Name") + " " + rs.getString("Age"));
				i++;
			}
			
			int icol = rs.getMetaData().getColumnCount();
			
			assertEquals(2, i);
			assertEquals(3, icol);
		}
		catch (SQLException ee) { throw ee; }
		finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}
	
	@Test
	void testString2Sql() {
		assertEquals("hola", Data.String2Sql("hola", false, false));
		assertEquals("'hola'", Data.String2Sql("hola", true, false));
		assertEquals("%hola%", Data.String2Sql("hola", false, true));
		assertEquals("'%hola%'", Data.String2Sql("hola", true, true));
		assertEquals("O''Connell", Data.String2Sql("O'Connell", false, false));
		assertEquals("'O''Connell'", Data.String2Sql("O'Connell", true, false));
		assertEquals("%''Smith ''%", Data.String2Sql("'Smith '", false, true));
		assertEquals("'''Smith '''", Data.String2Sql("'Smith '", true, false));
		assertEquals("'%''Smith ''%'", Data.String2Sql("'Smith '", true, true));
	}
	
	@Test
	void testBoolean2Sql() {
		assertEquals(0, Data.Boolean2Sql(false));
		assertEquals(1, Data.Boolean2Sql(true));
	}
}
