import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;

public class Zmakebill extends JFrame {
	Hhelper hh = new Hhelper();
	Hhelper.StringUtils hss = hh.new StringUtils();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	ResultSet rs;
	Connection con;
	Statement stmt;
	String custname = "";
	String custphone = "";
	ArrayList<String> seats = new ArrayList<String>();
	String nowdate = hh.currentDate();
	private String cinname, cinaddress, cinphone, sdate, prefix;
	private int billnumber;
	private String sbillnumber = "";
	String ssumtotal = "";
	int rows =0;

	Zmakebill(ArrayList mylist) {
		seats = mylist;
		initcomponents();
     	hh.iconhere(this);
		savebill();
	}

	Zmakebill() {
		initcomponents();
	}

	private void initcomponents() {
		setSize(480, 480);
		setLayout(null);
		setLocationRelativeTo(null);
		 addWindowListener((WindowListener) new WindowAdapter() {
		       public void windowClosing(WindowEvent windowEvent){		    			
				 Session ses = new Session();		      
		         dispose();
		       }        
		    });  


		setTitle("Make a bill");

		lbheader = hh.clabel("Billing");
		lbheader.setBounds(30, 5, 60, 40);
		// lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);

		tpanel = new JPanel(null);
		tpanel.setBackground(new Color(230, 231, 232));
		tpanel.setBounds(20, 60, 420, 280);
		tpanel.setBorder(hh.line);

		textPane = new JTextPane();
		textPane.setSize(400, 400);
		textPane.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
	//	billPane = new javax.swing.JScrollPane();
    	billPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		billPane.setBounds(10, 10, 400, 260);
		billPane.setViewportView(textPane);
		tpanel.add(billPane);
		add(tpanel);

		btnprint = hh.cbutton("Print");
		btnprint.setBounds(220, 380, 60, 30);
		btnprint.setBackground(hh.svoros);
		add(btnprint);
		btnprint.addActionListener(printlistener);
		setVisible(true);
	}

	private void savebill() {
		int size = seats.size();
		if (size == 0) {
			return;
		}
		Object data[][] = new Object[size][2];
		String text = "";
		String sql = " select   seatno, price, cid, showid from seatreservation where showid ='" + hh.showid + "'"
				+ " and reserved ='yes' and seatno in('";
		for (int i = 0; i < size; i++) {
			text = text + seats.get(i) + "','";
		}
		text = text.substring(0, text.length() - 3);
		text = text + "')";
		sql = sql + text;
		int ii = 0;
		String showid = "";
		String cid = "";
		int total = 0;
		try {
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				String seatno = rs.getString("seatno");
				String price = rs.getString("price");
				cid = rs.getString("cid");
				showid = rs.getString("showid");
				data[ii][0] = seatno;
				data[ii][1] = price;
				total = total + hh.stoi(price);
				ii++;
			}
			dh.CloseConnection();
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql error");
		}
		String sbillnum = makebillnumber();
		try {
			sql = "insert into billhead (cid, bdate,total, billnumber,showid)  values ('" + cid + "','" + nowdate + "',"
					+ total + ",'" + sbillnum + "','" + showid + "')";
			int flag = dh.Insupdel(sql);
			int bid = dd.table_maxid("SELECT MAX(bid) AS max_id from billhead");
		
			for (int r = 0; r < size; r++) {
				sql = "insert into billitem (seatno, price,bid)  values ";
				sql = sql + " ('" + data[r][0] + "','" + data[r][1] + "','" + bid + "')";
				flag = dh.Insupdel(sql);
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql error");
		}
	   String  header = hss.center("BILL", 50)+"\n";
		header += hss.center(cinname, 50)+"\n";
		header+= hss.center(cinaddress, 50)+"\n";
		header+="phone: " + cinphone+"\n";
		header += "Date: " + nowdate + hh.padl(" Bill nummer: " + sbillnumber, 35)+"\n";
		header+= "\n";
		String movie_title = dd.searchthemovie();
		header+= movie_title+"\n";
		header+= "\n";
	   rows = 15;
		String output = header;
	
		String s = String.format("%15s %10s\n", "Seatno", "Price");
		String s1 = String.format("%28s\n", "--------------------");
		output = output + s + s1;
		for (int r = 0; r < size; r++) {
			String line = String.format("%15s %10s\n", data[r][0], data[r][1]);
			output += line;
			rows++;
		}
		String lin2 = String.format("%28s \n", "--------------------");
		output += lin2;
		String sTotal = String.format("%15s", "Total");
		String stotal = String.format("%9s\n", total);
		output += sTotal + "  " +stotal;
		textPane.setText(output);		
	}


	private String makebillnumber() {
		String ret = "";
		try {
			String sql = " select * from params where parid = '1'";
			con = dh.getConnection();
			rs = dh.GetData(sql);
			if (rs.next()) {
				cinname = rs.getString("name");
				cinaddress = rs.getString("address") + ", " + rs.getString("city") + ", " + rs.getString("postal_code")
						+ " " + rs.getString("country");
				cinphone = rs.getString("phone");
				billnumber = rs.getInt("billnumber") + 1;
				sbillnumber = hh.itos(billnumber);
				prefix = rs.getString("prefix");
				sbillnumber = prefix + hh.padLeftZeros(sbillnumber, 5) + "  ";
			}
			dh.CloseConnection();
			sql = " update  params set billnumber =" + billnumber;
			int flag = dh.Insupdel(sql);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return sbillnumber;
	}
	ActionListener printlistener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			billprint();		
			 Session ses = new Session();		      
	         dispose();
	}
	};
	private void billprint() {
	    try {	    	
	    	PrinterJob job = PrinterJob.getPrinterJob();
			job.setJobName("Billing");

	       PageFormat pageFormat = job.defaultPage();
	       Paper paper = new Paper();	
	       paper.setSize(500.0, (double) (paper.getHeight() + rows * 10.0));
	       paper.setImageableArea(rows, rows, paper.getWidth() - rows * 2, paper.getHeight() - rows * 2);
	       pageFormat.setPaper(paper);
	       pageFormat.setOrientation(PageFormat.PORTRAIT);
	       job.setPrintable(textPane.getPrintable(null, null), pageFormat);
	       job.print();
	       dispose();
	    } 
	    catch ( Exception e ) 
	    {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		Zmakebill se = new Zmakebill();
	}

	JLabel lbheader, lbcustomer, lbcustomer1;
	JPanel tpanel;
	JTable billtable;
	JScrollPane billPane;
	JButton btnprint;
	JTextPane textPane;
}
