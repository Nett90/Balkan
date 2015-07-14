package transport;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MyForm extends JFrame {
	private static final long serialVersionUID = 1L;

	private static Connection myConn = null;
	private static PreparedStatement myPS = null;
	private static ResultSet myResult = null;
	
	private Driver driver = new Driver();
	private Machine machine = new Machine();
	
	private int selectIDdriver = 0; 
	private int selectIDmachine = 0;
	
	private JTable table = new JTable();
	private JScrollPane scroller = new JScrollPane(table);
	
	private JTabbedPane tabs = new JTabbedPane();
	
	private JPanel tableP = new JPanel();
	
	
	public MyForm() throws SQLException{
		super("Balkan");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.setLayout(new GridLayout(2, 1));
		
		tabs.add(driver);
		tabs.add(machine);
		
		this.add(tabs);
		this.add(tableP);
		
		
		init();
		this.setVisible(true);
	}
	
	public void init() throws SQLException{
		startTable();
		myCombo();
		
		
		table.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) { 
		    try {
				printRow(table);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}}
		});
		driver.d_clean.addActionListener(new CleanDriver());
		machine.m_clean.addActionListener(new CleanMachine());
		driver.d_add.addActionListener(new AddDriver());
        machine.m_add.addActionListener(new AddMachin());
		driver.d_edit.addActionListener(new EditDriver());
		machine.m_edit.addActionListener(new EditMachine());
		driver.d_delete.addActionListener(new DeleteDriver());
		machine.m_delete.addActionListener(new DeleteMachine());
		driver.d_search.addActionListener(new SearchDriver());
		machine.m_search.addActionListener(new SearchMachine());
		driver.d_allChange.addActionListener(new AllChange());
	}
	
	class AddDriver implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String fname = driver.t_fname.getText();
			String lname = driver.t_lname.getText();
			String address = driver.t_address.getText();
			String sity = driver.t_city.getText();		
			long egn = 0;

			myConn = DBconnect.connect();
			try {
				egn = Long.parseLong(driver.t_egn.getText());
				String fullData = " Име: " + fname +", Фамилея: "+ lname +", адрес: "+ address 
					+ ", град: " + sity + ", ЕГН: " + egn + ", с кола: " + driver.d_combo.getSelectedItem().toString();
			
				if(driver.d_combo.getSelectedItem().equals("Без кола")){
				   myPS = myConn.prepareStatement("insert into driver values(null,?,?,?,?,?,null);");
					myPS.setString(1, fname);
					myPS.setString(2, lname);
					myPS.setString(3, address);
					myPS.setString(4, sity);
					myPS.setLong(5, egn);	
				}else{
				
				    int id = DBconnect.getIDmachine(driver.d_combo.getSelectedItem().toString());
					myPS = myConn.prepareStatement("insert into driver values(null,?,?,?,?,?,?);");

					myPS.setString(1, fname);
					myPS.setString(2, lname);
					myPS.setString(3, address);
					myPS.setString(4, sity);
					myPS.setLong(5, egn);	
				    myPS.setInt(6, id);
				}		
				myPS.execute();
				myPS.close();
				myConn.close();
				cleanDriver();
				startTable();				
				change("Нов шофиор: ", fullData);
			    }catch (NumberFormatException nfe) {
					System.out.println("Празно поле или невярна информация!");
					//ex.printStackTrace();
				} catch (SQLException sx) {
					System.out.println("Има празно поле или ЕГН-то е вече записано!");
					//ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

		}		
	}
	
	class AddMachin implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String marka = machine.t_marka.getText();
			String model = machine.t_model.getText();
			int make = 0;
			int places = 0;
			String reg = machine.t_reg.getText();
			
			
			
			myConn = DBconnect.connect();
			try {
			    make = Integer.parseInt(machine.t_make.getText());
			    places = Integer.parseInt(machine.t_places.getText());
			    
				String fullData = " Марка: " + marka + ", модел: " + model +
					", произведен: " + make + ", места: " + places + ", рег. номер: " + reg;
				
				myPS = myConn.prepareStatement("insert into machine values(null,?,?,?,?,?);");
				myPS.setString(1, marka);
				myPS.setString(2, model);
				myPS.setInt(3, make);
				myPS.setInt(4, places);
				myPS.setString(5, reg);
				myPS.execute();
				myPS.close();
				myConn.close();
				myCombo();
				cleanMachine();
				searchMachineTable();
				change("Нов машина: ", fullData);
			    }catch (NumberFormatException nfe) {
					System.out.println("Празно поле или невярна информация!");
					//ex.printStackTrace();
				} catch (SQLException ex) {
					System.out.println("Има празно поле или рег.номер е вече записан!");
					//ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

		}
	}
		
	class EditDriver implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String fname = driver.t_fname.getText();
			String lname = driver.t_lname.getText();
			String address = driver.t_address.getText();
			String sity = driver.t_city.getText();
			long egn = 0;
			int selectIDd = selectIDdriver;
			
		if(selectIDd != 0){	
			myConn = DBconnect.connect();
			try {
				egn = Long.parseLong(driver.t_egn.getText());
				
				String fullData = " Име: " + fname +", Фамилея: "+ lname +", адрес: "+ address 
						+ ", град: " + sity + ", ЕГН: " + egn + ", с кола: " + driver.d_combo.getSelectedItem().toString();
				
				if(driver.d_combo.getSelectedItem().equals("Без кола")){
					   myPS = myConn.prepareStatement("UPDATE DRIVER SET fname=?,lname=?,address=?,sity=?,egn=?, machine_id=null WHERE id_driver = ?;");
						myPS.setString(1, fname);
						myPS.setString(2, lname);
						myPS.setString(3, address);
						myPS.setString(4, sity);
						myPS.setLong(5, egn);
						myPS.setInt(6, selectIDd);
				} else {
			    int id = DBconnect.getIDmachine(driver.d_combo.getSelectedItem().toString());	
				myPS = myConn.prepareStatement("UPDATE DRIVER SET fname=?,lname=?,address=?,sity=?,egn=?,machine_id=? WHERE id_driver = ?;");
				myPS.setString(1, fname);
				myPS.setString(2, lname);
				myPS.setString(3, address);
				myPS.setString(4, sity);
				myPS.setLong(5, egn);
				myPS.setInt(6, id);
				myPS.setInt(7, selectIDd);
				}
				myPS.execute();
				myPS.close();
				myConn.close();
				cleanDriver();
				myCombo();
				startTable();
				change("Редактиран шофиор: ", fullData);
				}catch (NumberFormatException nfe) {
					System.out.println("Празно поле или невярна информация!");
					//ex.printStackTrace();
				} catch (SQLException ex) {
					System.out.println("Има празно поле или ЕГН-то е вече записано!");
					//ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}else{
				System.out.println("Не може да се редактира");
			}
		}			
	}
	
	class EditMachine implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			String marka = machine.t_marka.getText();
			String model = machine.t_model.getText();
			int make = 0;
			int places = 0;
			String reg = machine.t_reg.getText();
			int selectIDm = selectIDmachine;
					
		if(selectIDm != 0) { 
			myConn = DBconnect.connect();
			try {
				make = Integer.parseInt(machine.t_make.getText());
				places = Integer.parseInt(machine.t_places.getText());
				
				String fullData = " Марка: " + marka + ", модел: " + model +
						", произведен: " + make + ", места: " + places + ", рег. номер: " + reg;
				
				myPS = myConn.prepareStatement("UPDATE MACHINE SET marka=?,model=?,make=?,places=?,reg=? WHERE id_machine = ?;");
				myPS.setString(1, marka);
				myPS.setString(2, model);
				myPS.setInt(3, make);
				myPS.setInt(4, places);
				myPS.setString(5, reg);
				myPS.setInt(6, selectIDm);
				
				myPS.execute();
				myPS.close();
				myConn.close();
				cleanMachine();
				myCombo();
				searchMachineTable();
				change("Редактирана машина: ", fullData);
		        }catch (NumberFormatException nfe) {
					System.out.println("Празно поле или невярна информация!");
					//ex.printStackTrace();
				} catch (SQLException ex) {
					System.out.println("Има празно поле или рег.номер е вече записан!");
					//ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		    }else{
		    		System.out.println("Не може да се редактита!");
		    }		
		}			
	}
	
	class DeleteDriver implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String fname = driver.t_fname.getText();
			String lname = driver.t_lname.getText();
			String address = driver.t_address.getText();
			String sity = driver.t_city.getText();
			long egn = 0;
			
			myConn = DBconnect.connect();
			
			try {
				egn = Long.parseLong(driver.t_egn.getText());
				
				String fullData = " Име: " + fname +", Фамилея: "+ lname +", адрес: "+ address 
						+ ", град: " + sity + " ЕГН: " + egn + ", с кола: " + driver.d_combo.getSelectedItem().toString();
				
				myPS= myConn.prepareStatement("DELETE FROM DRIVER WHERE egn = ?");
				myPS.setLong(1, egn);
			    myPS.execute();
			    myPS.close();
			    myConn.close();
			    myCombo();
			    cleanDriver();
			    searchDriverTable();
			    change("Изтрит шофиор: ", fullData);
		        }catch (NumberFormatException nfe) {
					System.out.println("Празно поле или невярна информация!");
					//ex.printStackTrace();
				} catch (SQLException e) {
					System.out.println("Проблеми с базата данни!");
					//e.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	
	class DeleteMachine implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String marka = machine.t_marka.getText();
			String model = machine.t_model.getText();
			int make = 0;
			int places = 0;
			String reg = machine.t_reg.getText();
								
			myConn = DBconnect.connect();
			
			try {
				    make = Integer.parseInt(machine.t_make.getText());
				    places = Integer.parseInt(machine.t_places.getText());
				
				    String fullData = " Марка: " + marka + ", модел: " + model +
						", произведен: " + make + ", места: " + places + ", рег. номер: " + reg;
				
					myPS= myConn.prepareStatement("DELETE FROM MACHINE WHERE reg = ?");
					myPS.setString(1, reg);
				    myPS.execute();
				    myPS.close();
				    myConn.close();
				    myCombo();
				    cleanMachine();
				    searchMachineTable();
				    change("Изтрита машина: ", fullData);
			        }catch (NumberFormatException nfe) {
						System.out.println("Празно поле или невярна информация!");
						//ex.printStackTrace();
					} catch (SQLException e) {
						System.out.println("Проблеми с базата данни!");
						//e.printStackTrace();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
		}
	}
	
	class SearchDriver implements ActionListener {
		boolean flag = true;
		public void actionPerformed(ActionEvent arg0) {
			String sql = "SELECT * FROM DRIVER";			
			
			if(driver.t_search.getText().equals("")) {
				if(flag){
					try {
						searchDriverTable();
					} catch (SQLException e) {
						System.out.println("Проблеми с базата данни!");
						//e.printStackTrace();
					}
					driver.d_search.setText("Търсене");
					flag = false;
				}else{
					try {
						startTable();					
					} catch (SQLException e) {
						System.out.println("Проблеми с базата данни!");
						//e.printStackTrace();
					}
				    driver.d_search.setText("Всички"); 
				    flag = true;
				    }
			}			
			else{
				if(driver.r_family.isSelected()) {		
					String lname = driver.t_search.getText();
					sql = "SELECT * FROM DRIVER WHERE lname = '" + lname + "';";
					try {
						searchingAllFamilyName(lname);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else if (driver.r_egn.isSelected()) {
					long egn = Long.parseLong(driver.t_search.getText());
				    sql = "SELECT * FROM DRIVER WHERE egn = '" + egn + "';";
				}
			    else{
			    	String  fname = driver.t_search.getText();
			    	sql = "SELECT * FROM DRIVER WHERE fname = '" + fname + "';";
			    	try {
						searchingAllFirstName(fname);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
					
			    myConn = DBconnect.connect();
			
			    try {
			    	
				    String fullData = driver.t_search.getText();
				    
					myPS = myConn.prepareStatement(sql);
					myResult = myPS.executeQuery();
					
					while(myResult.next()){
						driver.t_fname.setText(myResult.getString("fname"));
						driver.t_lname.setText(myResult.getString("lname"));
						driver.t_address.setText(myResult.getString("address"));
						driver.t_city.setText(myResult.getString("sity"));
						driver.t_egn.setText(myResult.getString("egn"));
					}
					
					selectIDdriver = DBconnect.getIDdriver(Long.parseLong(driver.t_egn.getText()));
					
					myResult.close();
					myPS.close();
					myConn.close();
						if(!fullData.equals("")){
						change("Търсен шофиор: ", fullData);
						}
				    } catch (SQLException ex) {	     
				    	System.out.println("Проблеми с базата данни!");
					    //ex.printStackTrace();
				    } catch (Exception ex) {
				    	ex.printStackTrace();
				    }
			   }    	 
		}	
	}
	
	class SearchMachine implements ActionListener{
		boolean flag = true;
		public void actionPerformed(ActionEvent arg0) {
			String sql = "SELECT * FROM MACHINE";
			
			if(machine.t_search.getText().equals("")){
				if(flag){
					try {
						searchMachineTable();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					machine.m_search.setText("Търсене");
					flag = false;
				}else{
					try {
						startTable();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					machine.m_search.setText("Всички");
					flag = true; 
				}	
			}
			else{
				if(machine.r_reg.isSelected()){
					String reg = machine.t_search.getText();
					sql = "SELECT * FROM MACHINE WHERE reg = '" + reg + "';";
				}
			    else{
			    	String marka = machine.t_search.getText();
			    	sql = "SELECT * FROM MACHINE WHERE marka = '" + marka + "';";
			    	try {
						searchingAllMark(marka);					
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			
			    String fullData = machine.t_search.getText();
				
			    myConn = DBconnect.connect();
			
			    try {
				myPS = myConn.prepareStatement(sql);
				myResult = myPS.executeQuery();
							
				while(myResult.next()){
					machine.t_marka.setText(myResult.getString("marka"));
					machine.t_model.setText(myResult.getString("model"));
					machine.t_make.setText(myResult.getString("make"));
					machine.t_places.setText(myResult.getString("places"));
					machine.t_reg.setText(myResult.getString("reg"));
				}
					
				selectIDmachine = DBconnect.getIDmachine(machine.t_reg.getText());
				
				myResult.close();
				myPS.close();
				myConn.close();
					if(!fullData.equals("")){
						change("Търсена машина: ", fullData);
						}
			    } catch (SQLException ex) {	     
			    	System.out.println("Проблеми с базата данни!");
				    //ex.printStackTrace();
			    }catch (Exception ex) {
			    	ex.printStackTrace();
			    }
		    }
		}
	}
	
	class AllChange implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			
			String path = "C:\\Users\\Nedyalko\\workspace\\Eclipse\\balkanS~\\Changes.txt";
			try {	
			    File file = new File(path);

			    BufferedReader reader = new BufferedReader(
			       new InputStreamReader(
			         new FileInputStream(file), "UTF8"));

			    String str;

			    while ((str = reader.readLine()) != null) {
			    	driver.d_change.append(str + "\n");
			    	machine.m_change.append(str + "\n");
			    }
			            reader.close();
			    } catch (FileNotFoundException e){
			    	System.out.println("Файла не е намерен");
			        //e.printStackTrace();
			    } catch (UnsupportedEncodingException e) {
			        System.out.println("Проблем с формата на файла!" + e.getMessage());
			        //e.printStackTrace();
			    } catch (IOException e) {
			        System.out.println("Проблем с файла!");
			        //e.printStackTrace();
			    }catch (Exception e){
			        System.out.println(e.getMessage());
			        e.printStackTrace();
			    }			
		}
	}
	
	public void printRow(JTable table) throws SQLException{
		int row = table.getSelectedRow();
		int countColum = table.getColumnCount();
		long egn = 0;
		String reg = null;
		
		TableModel model = table.getModel();
		if(countColum == 10){
			driver.t_fname.setText(model.getValueAt(row, 0 ).toString());
			driver.t_lname.setText(model.getValueAt(row, 1 ).toString());
			driver.t_address.setText(model.getValueAt(row, 2 ).toString());
			driver.t_city.setText(model.getValueAt(row, 3 ).toString());
			driver.t_egn.setText(model.getValueAt(row, 4 ).toString());		
			driver.d_combo.setSelectedItem(model.getValueAt(row, 9).toString());
			machine.t_marka.setText(model.getValueAt(row, 5 ).toString());
			machine.t_model.setText(model.getValueAt(row, 6 ).toString());
			machine.t_make.setText(model.getValueAt(row, 7 ).toString());
			machine.t_places.setText(model.getValueAt(row, 8 ).toString());
			machine.t_reg.setText(model.getValueAt(row, 9 ).toString());
			
		    egn = Long.parseLong(model.getValueAt(row, 4).toString());
		    reg = model.getValueAt(row, 9).toString(); 
		}
		if(countColum == 5 && tabs.getSelectedIndex() == 0 && (model.getValueAt(row,3) instanceof java.lang.String)){
			driver.t_fname.setText(model.getValueAt(row, 0 ).toString());
			driver.t_lname.setText(model.getValueAt(row, 1 ).toString());
			driver.t_address.setText(model.getValueAt(row, 2 ).toString());
			driver.t_city.setText(model.getValueAt(row, 3 ).toString());
			driver.t_egn.setText(model.getValueAt(row, 4 ).toString());
			egn = Long.parseLong(model.getValueAt(row, 4).toString());
			int index = DBconnect.getIndex(egn);
			driver.d_combo.setSelectedIndex(index);
					    
		}
		if(countColum == 5 && tabs.getSelectedIndex() == 1 && (model.getValueAt(row,3) instanceof Integer)){
			machine.t_marka.setText(model.getValueAt(row, 0 ).toString());
			machine.t_model.setText(model.getValueAt(row, 1 ).toString());
			machine.t_make.setText(model.getValueAt(row, 2 ).toString());
			machine.t_places.setText(model.getValueAt(row, 3 ).toString());
			machine.t_reg.setText(model.getValueAt(row, 4 ).toString());
			
		    reg = model.getValueAt(row, 4).toString(); 
		}
			
		if(egn != 0 || reg != null){
		selectIDdriver = DBconnect.getIDdriver(egn);
		selectIDmachine = DBconnect.getIDmachine(reg);
		}
	}
	
	public void returnIDmachine() throws SQLException {
		int id = DBconnect.getIDmachine(driver.d_combo.getSelectedItem().toString());
		System.out.println(id);
		DBconnect.getIDmachine(driver.d_combo.getSelectedItem().toString());
	}
	
	class CleanDriver implements ActionListener{
		public void actionPerformed(ActionEvent e) { 
			cleanDriver();
		}
	}
	
	private void cleanDriver(){
				driver.t_fname.setText(null);
				driver.t_lname.setText(null);
				driver.t_address.setText(null);
				driver.t_city.setText(null);
				driver.t_egn.setText(null);
				driver.t_search.setText(null);
				selectIDdriver = 0;
				driver.d_combo.setSelectedIndex(0);
	}
	
    class CleanMachine implements ActionListener {
		public void actionPerformed(ActionEvent e) { 
			cleanMachine();
		}		
	}
    private void cleanMachine(){
    			machine.t_marka.setText(null);
				machine.t_model.setText(null);
				machine.t_make.setText(null);
				machine.t_places.setText(null);
				machine.t_reg.setText(null);
				machine.t_search.setText(null);
				selectIDmachine = 0;
				
    }
	
	private void startTable() throws SQLException{
		scroller.setPreferredSize(new Dimension(750,250));
		tableP.add(scroller);
		myResult = DBconnect.selectAll();
		String [] tableName = {"Име","Фамилия","Адрес","Град","ЕГН","Марка","Модел","Година","Места","Рег. номер"};
		table.setModel(new TableBalkan (myResult,tableName)) ;
	}
	
	private void searchDriverTable() throws SQLException{
		scroller.setPreferredSize(new Dimension(750,250));
		tableP.add(scroller);
		myResult = DBconnect.selectAllDriver();
		String [] tableName = {"Име","Фамилия","Адрес","Град","ЕГН"};
		table.setModel(new TableBalkan(myResult,tableName));
	}
	
	private void searchMachineTable() throws SQLException{
		scroller.setPreferredSize(new Dimension(750,250));
		tableP.add(scroller);
		myResult = DBconnect.selectAllMachine();
		String [] tableName = {"Марка","Модел","Година","Места","Рег. номер"};
		table.setModel(new TableBalkan(myResult,tableName));
	}
	
	private void searchingAllMark(String marka) throws SQLException{
		//String marka = machine.m_search.getText();
		scroller.setPreferredSize(new Dimension(750,250));
		tableP.add(scroller);
		myResult = DBconnect.searchMarkaMachine(marka);
		String [] tableName = {"Марка","Модел","Година","Места","Рег. номер"};
		table.setModel(new TableBalkan(myResult,tableName));
	}
	
	private void searchingAllFirstName(String fname) throws SQLException{
		//String marka = machine.m_search.getText();
		scroller.setPreferredSize(new Dimension(750,250));
		tableP.add(scroller);
		myResult = DBconnect.searchAllFirstName(fname);
		String [] tableName = {"Име","Фамилия","Адрес","Град","ЕГН"};
		table.setModel(new TableBalkan(myResult,tableName));
	}
	
	private void searchingAllFamilyName(String lname) throws SQLException{
		//String marka = machine.m_search.getText();
		scroller.setPreferredSize(new Dimension(750,250));
		tableP.add(scroller);
		myResult = DBconnect.searchAllFamilyName(lname);
		String [] tableName = {"Име","Фамилия","Адрес","Град","ЕГН"};
		table.setModel(new TableBalkan(myResult,tableName));
	}
	
	private void myCombo() throws SQLException{
		myResult = DBconnect.comboRefresh();
		driver.d_combo.removeAllItems();
		driver.d_combo.addItem("Без кола");
		while(myResult.next()){
			driver.d_combo.addItem(myResult.getString("reg"));
		}
	}
	
	private void change(String action, String value) {
		
		String date = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss ").format(new Date());
		String values = date + "  " + action + " " + value + "\n";
		
		driver.d_change.append(values);
		machine.m_change.append(values);
		
		String file = "C:\\Users\\Nedyalko\\workspace\\Eclipse\\BalkanS~\\Changes.txt";
			
		try{
			File myFile = new File(file);
				if(!myFile.exists()){
					myFile.createNewFile();
				}
				
			Writer writer = new BufferedWriter(
					new OutputStreamWriter(
					new FileOutputStream(file, true), "UTF-8"));
			writer.write(values);
			writer.close();
		} catch (UnsupportedEncodingException e) {
	        System.out.println("Проблем с формата на файла.");
	        //e.printStackTrace();
	    } catch (IOException e) {
	        System.out.println("Проблем с файла.");
	        //e.printStackTrace();
	    }catch (Exception e){
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    }
	}
}









