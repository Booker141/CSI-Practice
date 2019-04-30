package es.uca.gii.csi18.oblivion.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import java.sql.SQLException;

import es.uca.gii.csi18.oblivion.util.Config;

public class Data {
    public static String getPropertiesUrl() { return "./db.properties"; }
    public static Connection Connection() throws Exception {
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards)
    {
    	return (bAddQuotes ? "'" : "") + (bAddWildcards ? "%" : "") + s.replace("'", "''")
    		+ (bAddWildcards ? "%" : "") + (bAddQuotes ? "'" : "");
    }
    
    public static int Boolean2Sql(boolean b)
    {
    	return b ? 1 : 0;
    }
    
    public static int LastId(Connection con) throws Exception {
    	ResultSet rs = null;
    	Properties properties = Config.Properties(getPropertiesUrl());
    	try {
    		rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));
    		rs.next();
    		
    		return rs.getInt(1);
    	}
    	catch (SQLException ee) { throw ee; }
    	finally {
    		if (rs != null) rs.close();
    	}
    }
}
