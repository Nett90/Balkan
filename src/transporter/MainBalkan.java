package transporter;

import java.awt.EventQueue;
import java.sql.SQLException;

public class MainBalkan {

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 new MyForm();
				} catch (SQLException ex) {
				    ex.printStackTrace();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}	
				
		    }
		});

	}

}
