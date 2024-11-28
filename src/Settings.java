
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

public class Settings extends JFrame{
	ResultSet rs;
	 Connection con = null;
	 PreparedStatement pst = null;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid="";
	private int myrow=0;
	
	Settings(){
		initcomponents();
		reading();
		hh.iconhere(this);
		txname.requestFocus();		
	}
	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));

		setTitle("Settings");
		cp = getContentPane();
		cp.setBackground(new Color(230, 231, 232));
		setSize(910, 500);
		setLayout(null);
		setLocationRelativeTo(null);	 
	
		
		lbheader = hh.clabel("Settings");
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		lbheader.setBounds(20, 20, 100, 25);
	
		add(lbheader);
			
		lpanel = new JPanel(null);		
		lpanel.setBounds(20,70, 530,290);
	    lpanel.setBackground(new Color(230, 231, 232));
	    lpanel.setBorder(hh.line);	   
		add(lpanel);		
	
		lbname = hh.clabel("Name of cinema:");
		lbname.setBounds(10, 30, 120, 25);
		lpanel.add(lbname);

		txname = cTextField(25);
		txname.setBounds(170, 30, 340, 25);
		lpanel.add(txname);
		txname.addKeyListener(hh.MUpper());
		
		lbaddress = hh.clabel("Address:");
		lbaddress.setBounds(10, 70, 120, 25);
		lpanel.add(lbaddress);

		txaddress = cTextField(25);
		txaddress.setBounds(170, 70, 340, 25);
		lpanel.add(txaddress);
		txaddress.addKeyListener(hh.MUpper());
		
		lbcity = hh.clabel("City:");
		lbcity.setBounds(10, 110, 120, 25);
		lpanel.add(lbcity);

		txcity = cTextField(25);
		txcity.setBounds(170, 110, 340, 25);
		lpanel.add(txcity);
		txcity.addKeyListener(hh.MUpper());
		
		lbpostalcode = hh.clabel("Postalcode:");
		lbpostalcode.setBounds(10, 150, 120, 25);
		lpanel.add(lbpostalcode);

		txpostalcode = cTextField(25);
		txpostalcode.setBounds(170, 150, 340, 25);
		lpanel.add(txpostalcode);
		
		lbphone = hh.clabel("Phone:");
		lbphone.setBounds(10, 190, 120, 25);
		lpanel.add(lbphone);

		txphone = cTextField(25);
		txphone.setBounds(170, 190, 340, 25);
		lpanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());
		
		lbemail = hh.clabel("Email:");
		lbemail.setBounds(10, 230, 120, 25);
		lpanel.add(lbemail);

		txemail = cTextField(25);
		txemail.setBounds(170, 230, 340, 25);
		lpanel.add(txemail);		
		
		rpanel = new JPanel(null);
		rpanel.setBounds(560,70, 310,290);	
	    rpanel.setBackground(new Color(230, 231, 232));	
		rpanel.setBorder(hh.line);
		add(rpanel);
		
		lbprefix = hh.clabel("Prefix in billnumber........ :");
		lbprefix.setBounds(10, 30, 200, 25);
		rpanel.add(lbprefix);

		txprefix = cTextField(2);
		txprefix.setBounds(220, 30, 50, 25);
		rpanel.add(txprefix);	
		txprefix.addKeyListener(hh.Onlyalphabet(txprefix));
		
		lbcurrentyear = hh.clabel("Current year..................... :");
		lbcurrentyear.setBounds(10, 70, 200, 25);
		rpanel.add(lbcurrentyear);

		txcurrentyear= cTextField(25);
		txcurrentyear.setBounds(220, 70, 50, 25);
		rpanel.add(txcurrentyear);
		txcurrentyear.addKeyListener(hh.Onlynum());
		
		lbvochernumber = hh.clabel("Sequential number in bill");
		lbvochernumber.setBounds(10, 110, 200, 25);
		rpanel.add(lbvochernumber);

		txbillnumber= cTextField(25);
		txbillnumber.setBounds(220, 110, 70, 25);
		txbillnumber.setHorizontalAlignment(JTextField.RIGHT);
		rpanel.add(txbillnumber);
		txbillnumber.addKeyListener(hh.Onlynum());	
		
		btnsave = hh.cbutton("Save");
		btnsave.setBounds(330, 390, 100, 25);
		btnsave.setBackground(hh.svoros);
		add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				 savebuttrun();
			}
		});
		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.szold);
		btncancel.setBounds(440, 390, 100, 25);
		add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			dispose();
			}
		});	
		setVisible(true);
	}
	private void reading() {
		try {
			 String sql = " select * from params where parid = '1'";  
			con = dh.getConnection();
			rs = dh.GetData(sql);
			if (rs.next()) {
			      txname.setText(rs.getString("name"));
			      txaddress.setText(rs.getString("address"));
			      txcity.setText(rs.getString("city"));
			      txpostalcode.setText(rs.getString("postal_code"));
			      txphone.setText(rs.getString("phone"));
			     txemail.setText(rs.getString("email"));
			      txprefix.setText(rs.getString("prefix"));
			      txcurrentyear.setText(rs.getString("currentyear"));
			     txbillnumber.setText(rs.getString("billnumber"));						      
			} 
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}	  	
	   }
	private void  savebuttrun() {
		String sql = "";
		String jel = "";		
		String name = txname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String address = txaddress.getText();
		String city = txcity.getText();	
		String postalcode = txpostalcode.getText();
		String currentyear = txcurrentyear.getText();
		String prefix = txprefix.getText();
		String billnumber = txbillnumber.getText();		
		

		if (ppvalidation(name, address, city, currentyear, prefix) == false) {
			return;
		}		
		sql = "update  params set name= '" + name + "', phone= '" + phone + "'," + "email = '" + email
				+ "', address = '" + address + "'," + "city= '" + city + "', currentyear= '" + currentyear + "',"
				+ "postal_code = '" + postalcode + "', prefix='" + prefix + "', billnumber='" + billnumber
				+  "' where parid = '1'";
		int flag = dh.Insupdel(sql);
		if (flag == 1) {
			hh.ztmessage("Success", "Message");
		}		
	}
		private Boolean ppvalidation(String name, String address, String city, String currentyear, String prefix) {
			Boolean ret = true;
			ArrayList<String> err = new ArrayList<String>();

			if (hh.zempty(name)) {
				err.add("Name is empty !");
				ret = false;
		}
			if (hh.zempty(address)) {
				err.add("Addres is empty !");
				ret = false;
		}
			if (hh.zempty(city)) {
				err.add("Address is empty !");
				ret = false;
		}
			if (hh.zempty(currentyear)) {
				err.add("Current year is empty");
				ret = false;
		}
			if (hh.zempty(prefix)) {
				err.add("Prefix is empty !");
				ret = false;
		}
			if (err.size() > 0) {
				JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
			}
			return ret;
		}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.putClientProperty("caretAspectRatio", 0.1);
		// textField.addFocusListener(dFocusListener);
		textField.setText("");
		textField.setDisabledTextColor(Color.magenta);
		return textField;
	}
	public static void main(String args[]) {
		Settings st = new Settings();	
	
	}
JLabel lbheader;
JPanel fejpanel,lpanel, rpanel;;
Container cp;
JComboBox cmbcountries;
JTextField txname, txpostalcode, txaddress, txcity, txphone, txemail, txbillnumber, txprefix, txcurrentyear;
JLabel lbname, lbpostalcode, lbaddress, lbcity, lbphone, lbemail, lbvochernumber,  lbprefix, lbcurrentyear;
JButton btnsave, btncancel;
}
