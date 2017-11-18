package it.polito.tdp.country.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.country.exception.CountryBordersException;
import it.polito.tdp.country.model.Country;
import it.polito.tdp.country.model.CountryPair;

public class CountryDAO {

	public List<Country> retrieveListCountries() throws CountryBordersException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Country> countries = new ArrayList<>();
		
		try{
			c = DBConnect.getInstance().getConnection();
			ps = c.prepareStatement("SELECT stateabb, ccode, statenme FROM country order by statenme");
			rs=ps.executeQuery();
			
			while(rs.next()){
				Country country = new Country(rs.getString("stateabb"), rs.getInt("ccode"), rs.getString("statenme"));
				System.out.println("<retrieveListCountries> country: " + country);
				countries.add(country);
			}
			
			return countries;
			
			
		} catch(CountryBordersException cbe){
			throw cbe;
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new CountryBordersException("Errore SQL: " + sqle.getMessage(), sqle);
			
		} finally {
			DBConnect.getInstance().closeResources(c, ps, rs);
		}
		
		
	}

	public List<CountryPair> retrieveListaCountryPairAdiacenti() throws CountryBordersException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CountryPair> countryPairs = new ArrayList<>();
		
		try{
			c = DBConnect.getInstance().getConnection();
			ps = c.prepareStatement("SELECT c1.StateNme as c1Name, cont.state1no, cont.state1ab, c2.StateNme as c2Name, cont.state2no, cont.state2ab, cont.year, cont.conttype " +
									"FROM country c1, country c2, contiguity cont " +
									"WHERE cont.state1no = c1.CCode " +
									"AND cont.state2no = c2.CCode " +
									"AND cont.conttype = 1");
			rs=ps.executeQuery();
			
			while(rs.next()){
				Country c1 = new Country(rs.getString("state1ab"), rs.getInt("state1no"), rs.getString("c1Name"));
				Country c2 = new Country(rs.getString("state2ab"), rs.getInt("state2no"), rs.getString("c2Name"));
				System.out.println("<retrieveListaCountryPairAdiacenti> country 1: " + c1);
				System.out.println("<retrieveListaCountryPairAdiacenti> country 2: " + c2);
				CountryPair cp = new CountryPair(c1, c2, rs.getInt("year"), rs.getInt("conttype"));
				countryPairs.add(cp);
			}
			
			return countryPairs;
			
			
		} catch(CountryBordersException cbe){
			throw cbe;
			
		} catch(SQLException sqle){
			sqle.printStackTrace();
			throw new CountryBordersException("Errore SQL: " + sqle.getMessage(), sqle);
			
		} finally {
			DBConnect.getInstance().closeResources(c, ps, rs);
		}
		
	}

}
