package transporter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableBalkan extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private ResultSet result = null;
	private ArrayList<Object> arr = new ArrayList<Object>();
	private int columnCount = 0;
	private int rowCount = 0;
	private String[] columnNames;
	
	public TableBalkan(ResultSet result,String [] names){
		try {
			createModel(result);
		} catch (SQLException e) {
				e.printStackTrace();
		}
		
		this.columnNames = names;        
	}
	
	public void createModel(ResultSet res) throws SQLException{
		
		this.result = res;
		
		ResultSetMetaData metaData = result.getMetaData();
		columnCount = metaData.getColumnCount();     
		
		while(result.next()){
			
			Object[] row = new Object[columnCount];
			
			for(int i=0;i<columnCount;i++){
				row[i] = result.getObject(i+1);
			}
			arr.add(row);
			rowCount++;
		 }		
	}

	public int getColumnCount() {
		
		return columnCount;
	}

	public int getRowCount() {
		
		return rowCount;
	}

	public Object getValueAt(int rowIndex, int colIndex) {
		
		Object[] row = (Object[])arr.get(rowIndex);
		
		return row[colIndex];
	}
	
	public String getColumnName(int colIndex){
		
		return columnNames[colIndex];       
	}
}
