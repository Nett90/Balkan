package transport;

import java.awt.Color;

import javax.swing.*;

public class Machine extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel l_marka = new JLabel("Марка:");
	private JLabel l_model = new JLabel("Модел:");
	private JLabel l_make = new JLabel("Произведен:");
	private JLabel l_places = new JLabel("Места:");
	private JLabel l_reg = new JLabel("Рег. номер:");
	private JLabel l_search = new JLabel("Търсене:");
	private JLabel l_change = new JLabel("Промени:");
	
	public JTextField t_marka = new JTextField();
	public JTextField t_model = new JTextField();
	public JTextField t_make = new JTextField();
	public JTextField t_places = new JTextField();
	public JTextField t_reg = new JTextField();
	public JTextField t_search = new JTextField();
	
	public JRadioButton r_marka = new JRadioButton("Марка");
	public JRadioButton r_reg = new JRadioButton("Рег. номер");
	
	private ButtonGroup r_group = new ButtonGroup();
		
	public JButton m_add = new JButton("Добави");
	public JButton m_edit = new JButton("Редактирам");
	public JButton m_delete = new JButton("Изтри");
	public JButton m_clean = new JButton("Изчисти");
	public JButton m_search = new JButton("Всички");
		
	public JTextArea m_change = new JTextArea();
	
	private JScrollPane scroll = new JScrollPane(m_change,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public Machine(){
		this.setName("Машини");
		this.setLayout(null);
		
		init();
	}
	
	private void init(){
		
		l_marka.setBounds(10, 25, 50, 15);
		this.add(l_marka);
		t_marka.setBounds(90, 20, 100, 20);
		this.add(t_marka);
		
		l_model.setBounds(10, 55, 50, 15);
		this.add(l_model);
		t_model.setBounds(90, 50, 100, 20);
		this.add(t_model);
		
		l_make.setBounds(10, 85, 80, 15);
		this.add(l_make);
		t_make.setBounds(90, 80, 100, 20);
		this.add(t_make);
		
		l_places.setBounds(10, 115, 50, 15);
		this.add(l_places);
		t_places.setBounds(90, 110, 100, 20);
		this.add(t_places);
		
		l_reg.setBounds(10, 145, 70, 15);
		this.add(l_reg);
		t_reg.setBounds(90, 140, 100, 20);
		this.add(t_reg);
		
		m_add.setBounds(90, 200, 100, 20);
		this.add(m_add);
		
		m_edit.setBounds(200, 200, 120, 20);
		this.add(m_edit);
		
		m_delete.setBounds(330, 200, 100, 20);
		this.add(m_delete);
		
		m_clean.setBounds(330, 230, 100, 20);
		this.add(m_clean);
		
		r_group.add(r_marka);
		r_group.add(r_reg);
		r_marka.setSelected(true);
		
		r_marka.setBounds(230, 50, 100, 20);
		this.add(r_marka);
		r_reg.setBounds(230, 80, 100, 20);
		this.add(r_reg);
		
		l_search.setBounds(240, 25, 100, 15);
		this.add(l_search);
		t_search.setBounds(330, 20, 100, 20);
		this.add(t_search);
		
		m_search.setBounds(330, 50, 100, 20);
		this.add(m_search);		

		l_change.setBounds(460, 25, 100, 15);
		this.add(l_change);
		
		m_change.setBackground(new Color(238, 238, 238));
		m_change.setEditable(false);
		m_change.setVisible(true);
		
		scroll.setBounds(460, 50, 410, 200);
		this.add(scroll);
	}
	
}
