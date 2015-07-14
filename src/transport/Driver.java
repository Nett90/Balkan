package transport;

import java.awt.Color;

import javax.swing.*;

public class Driver extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel l_fname = new JLabel("Име:");
	private JLabel l_lname = new JLabel("Фамилия:");
	private JLabel l_address = new JLabel("Адрес:");
	private JLabel l_city = new JLabel("Град:");
	private JLabel l_egn = new JLabel("ЕГН:");
	private JLabel l_driver = new JLabel("Шофиор на:");
	private JLabel l_search = new JLabel("Търсене:");
	private JLabel l_change = new JLabel("Промени:");
	
	public JTextField t_fname = new JTextField();
	public JTextField t_lname = new JTextField();
	public JTextField t_address = new JTextField();
	public JTextField t_city = new JTextField();
	public JTextField t_egn = new JTextField();
	
	public JTextField t_search = new JTextField();
	
	public JRadioButton r_fname = new JRadioButton("Име"); 
	public JRadioButton r_family = new JRadioButton("Фамилия");
	public JRadioButton r_egn = new JRadioButton("ЕГН");
	
	private ButtonGroup r_group = new ButtonGroup();
		
	public JButton d_add = new JButton("Добави");
	public JButton d_edit = new JButton("Редактирам");
	public JButton d_delete = new JButton("Изтри");
	public JButton d_clean = new JButton("Изчисти");
	public JButton d_search = new JButton("Всички");
	public JButton d_allChange = new JButton("Всички промени");
	
	public JTextArea d_change = new JTextArea();
	
	private JScrollPane scroll = new JScrollPane(d_change,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public JComboBox<String> d_combo = new JComboBox<String>();
	
	
	public Driver(){
		this.setName("Шофиори");
		this.setLayout(null);
		
		init();
	}
	
	private void init(){
		
		l_fname.setBounds(10, 25, 50, 15);
		this.add(l_fname);
		t_fname.setBounds(90, 20, 100, 20);
		this.add(t_fname);
		
		l_lname.setBounds(10, 55, 70, 15);
		this.add(l_lname);
		t_lname.setBounds(90, 50, 100, 20);
		this.add(t_lname);
		
		l_address.setBounds(10, 85, 50, 15);
		this.add(l_address);
		t_address.setBounds(90, 80, 100, 20);
		this.add(t_address);
		
		l_city.setBounds(10, 115, 50, 15);
		this.add(l_city);
		t_city.setBounds(90, 110, 100, 20);
		this.add(t_city);
		
		l_egn.setBounds(10, 145, 50, 15);
		this.add(l_egn);
		t_egn.setBounds(90, 140, 100, 20);
		this.add(t_egn);
		
		l_driver.setBounds(10, 175, 100, 15);
		this.add(l_driver);
		d_combo.setBounds(90, 170, 100, 20);
		this.add(d_combo);
		
		d_add.setBounds(90, 200, 100, 20);
		this.add(d_add);
		
		d_edit.setBounds(200, 200, 120, 20);
		this.add(d_edit);
		
		d_delete.setBounds(330, 200, 100, 20);
		this.add(d_delete);
		
		d_clean.setBounds(330, 230, 100, 20);
		this.add(d_clean);
		
		d_allChange.setBounds(700, 25, 170, 20);
		this.add(d_allChange);
		
		r_group.add(r_fname);
		r_group.add(r_family);
		r_group.add(r_egn);
		r_fname.setSelected(true);
		
		r_fname.setBounds(230, 50, 100, 20);
		this.add(r_fname);
		r_family.setBounds(230, 80, 100, 20);
		this.add(r_family);
		r_egn.setBounds(230, 110, 100, 20);
		this.add(r_egn);
		
		l_search.setBounds(240, 25, 100, 15);
		this.add(l_search);
		t_search.setBounds(330, 20, 100, 20);
		this.add(t_search);
				
		l_change.setBounds(460, 25, 100, 15);
		this.add(l_change);
		
		d_search.setBounds(330, 50, 100, 20);
		this.add(d_search);
		
		d_change.setBackground(new Color(238, 238, 238));
		d_change.setEditable(false);
		d_change.setVisible(true);
        
		scroll.setBounds(460, 50, 410, 200);
		this.add(scroll);
	}
	
	
}
