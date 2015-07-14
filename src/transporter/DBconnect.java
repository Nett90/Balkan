package transporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBconnect {

	static Connection conn = null;
	
	public static Connection connect(){
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/C:/Users/fmi/balkanS", "sa", "");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
		} catch (SQLException e){
			System.out.println("Няма връзка с базата данни?!?");			
			System.exit(1);
			//e.printStackTrace();
		}
		return conn;
	}
	
	public static ResultSet selectAll() throws SQLException {
		conn = connect();
		String sql = " SELECT  d.fname,d.lname,d.address,d.sity,d.egn,m.marka, "
				+" m.model,m.make,m.places,m.reg FROM driver d INNER JOIN machine m "  
                +" ON d.machine_id = m.id_machine ORDER BY d.fname ASC ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}

    public static ResultSet selectAllDriver() throws SQLException{
    	conn = connect();
    	String sql = "SELECT fname,lname,address,sity,egn FROM DRIVER";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
    }
	
    public static ResultSet selectAllMachine() throws SQLException{
    	conn = connect();
    	String sql = "SELECT marka,model,make,places,reg FROM MACHINE";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
    }
    
	public static ResultSet comboRefresh() throws SQLException{
		conn = connect();
		String sql = "SELECT id_machine,reg FROM MACHINE";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public static int getIDmachine(String reg) throws SQLException{
		int id = 0;
		conn = connect();
		String sql = "SELECT id_machine FROM MACHINE WHERE reg = '" + reg + "'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
        while(result.next()){
		id = result.getInt("id_machine");
        }
		return id;
	}
	
	public static int getIDdriver(long egn) throws SQLException{
		int id = 0;
		conn = connect();
		String sql = "SELECT id_driver FROM DRIVER WHERE egn = '" + egn + "'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
        while(result.next()){
		id = result.getInt("id_driver");
        }
		return id;
	}
	
    public static int getIndex(long egn) throws SQLException{
		int index = 0;
		conn = connect();
		String sql = "SELECT machine_id FROM DRIVER WHERE egn = '" + egn + "'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
        while(result.next()){
		index = result.getInt("machine_id");
        }
		return index;
    }
	
	public static ResultSet searchDriver() throws SQLException{
		conn = connect();
		String sql = "SELECT fname,lname,address,sity,egn FROM Driver ORDER BY fname ASC; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public static ResultSet searchNameDriver(String fname) throws SQLException{
		conn = connect();
		String sql = "SELECT fname,lname,address,sity,egn FROM Driver WHERE BY fname = '" + fname +"'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public static ResultSet searchFamelyDriver(String family) throws SQLException{
		conn = connect();
		String sql = "SELECT fname,lname,address,sity,egn FROM Driver WHERE BY lname = '" + family +"'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public static ResultSet searchEGNDriver(long egn) throws SQLException{
		conn = connect();
		String sql = "SELECT fname,lname,address,sity,egn FROM Driver WHERE BY egn = '" + egn +"'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public static ResultSet searchMachine() throws SQLException{
		conn = connect();
		String sql = "SELECT marka,model,make,places,reg  FROM MACHINE ORDER BY make ASC; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
	
	public static ResultSet searchMarkaMachine(String marka) throws SQLException{
		conn = connect();
		String sql = "SELECT marka,model,make,places,reg FROM MACHINE WHERE marka= '"+ marka +"'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}	
	
	public static ResultSet searchAllFirstName(String fname) throws SQLException{
		conn = connect();
		String sql = "SELECT fname,lname,address,sity,egn FROM Driver WHERE fname = '" + fname +"'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}	
	
	public static ResultSet searchAllFamilyName(String lname) throws SQLException{
		conn = connect();
		String sql = "SELECT fname,lname,address,sity,egn FROM Driver WHERE lname = '" + lname +"'; ";
		PreparedStatement prep = conn.prepareStatement(sql);
		ResultSet result = prep.executeQuery();
		return result;
	}
}
