package it.polito.tdp.country.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

import it.polito.tdp.country.exception.CountryBordersException;

public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/countries?user=root&password=salva_root";
	static private DBConnect instance = null;
	private static DataSource ds;
	
	private DBConnect() {
		instance = this;
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else {
			return instance;
		}
	}

	public Connection getConnection() throws CountryBordersException {
		if(ds==null){
			try {
				ds=DataSources.pooledDataSource(DataSources.unpooledDataSource(jdbcUrl));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CountryBordersException("Cannot get connection " + jdbcUrl, e);
			}
		}
		
		try {

			Connection conn = ds.getConnection();
			return conn;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new CountryBordersException("Cannot get connection " + jdbcUrl, e);
		}
	}

	public void closeResources(Connection c, Statement s, ResultSet rs) throws CountryBordersException{
		try {
			rs.close();
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
//			throw new CountryBordersException("Errore nella chiusura del resultset: " + sqle.getMessage(), sqle);
		}
		try {
			s.close();
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
//			throw new CountryBordersException("Errore nella chiusura dello statement: " + sqle.getMessage(), sqle);
		}
		try {
			c.close();
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
//			throw new CountryBordersException("Errore nella chiusura della conessione: " + sqle.getMessage(), sqle);
		}
	}
	
	
}


