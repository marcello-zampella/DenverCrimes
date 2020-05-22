package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import it.polito.tdp.crimes.model.Event;
import it.polito.tdp.crimes.model.Reato;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public ArrayList<String> getCategorieEventi(){
		String sql = "SELECT distinct e.offense_category_id AS evento FROM `events` e" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ArrayList<String> list = new ArrayList<String>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("evento"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public ArrayList<LocalDate> getDate(){
		String sql = "SELECT DISTINCT SUBSTRING(e.reported_date, 6, 2) AS mesi FROM `events` e" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ArrayList<LocalDate> list = new ArrayList<LocalDate>() ;
			
			ResultSet res = st.executeQuery() ;
			ArrayList<Integer> num= new ArrayList<Integer>();
			while(res.next()) {
				try {
					
					num.add(res.getInt("mesi"));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			Collections.sort(num);
			for (Integer i: num) {
			list.add(LocalDate.of(1111, i, 1));
			}
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public ArrayList<Reato> getEventi(String categoria, Month month){
		String sql = "SELECT distinct e.offense_type_id AS tipo, e.neighborhood_id AS quartiere  \n " + 
				" FROM `events` e \n " + 
				" WHERE e.offense_category_id=? AND e.reported_date LIKE ? " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setString(2, "%-0"+(month.getValue()+1)+"-%");
			ArrayList<Reato> list = new ArrayList<Reato>() ;
			
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				try {list.add(new Reato (res.getString("tipo"), res.getString("quartiere")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public ArrayList<String> getTipoReatoPerCategoria(String categoria, Month month){
		String sql = "SELECT distinct e.offense_type_id AS tipo \n " + 
				" FROM `events` e \n " + 
				" WHERE e.offense_category_id=? AND e.reported_date LIKE ? " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setString(2, "%-0"+(month.getValue()+1)+"-%");
			ArrayList<String> list = new ArrayList<String>() ;
			
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				try {list.add(res.getString("tipo"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public int getContatoreCrimini(String primo, String secondo, Month month) {
		String sql = "SELECT DISTINCT e.offense_type_id, e2.offense_type_id, count(distinct e.neighborhood_id) AS valore \n " + 
				"FROM `events` e, `events` e2 \n " + 
				"WHERE e.offense_type_id=? \n " + 
				" AND e.reported_date LIKE ? \n " + 
				" AND e2.reported_date LIKE ? AND e.neighborhood_id=e2.neighborhood_id AND e2.offense_type_id=? \n " + 
				" GROUP BY e.offense_type_id " ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, primo);
			st.setString(2, "%-0"+(month.getValue()+1)+"-%");
			st.setString(3, "%-0"+(month.getValue()+1)+"-%");
			st.setString(4, secondo);
			
			ResultSet res = st.executeQuery() ;
			res.next();
			int risultato=res.getInt("valore");
			conn.close();
			return risultato ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1 ;
		}
	}
}
