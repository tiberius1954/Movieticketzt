import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

public class Rescancel extends JFrame{
	ResultSet result;
	Hhelper hh = new Hhelper();
	 Hhelper.ClientsTableButtonRenderer hss = new Hhelper.ClientsTableButtonRenderer();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	JDateChooser sdate = new JDateChooser(new Date());
	String ssdate;
	DefaultTableModel model;
	String nowdate =hh.currentDate();
	
	Rescancel() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		hh.iconhere(this);
	}
	private void initcomponents(){
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		UIManager.getDefaults().put("JToggleButton.disabledText", Color.RED);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Seat reservation cancel");
		setSize(650, 500);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = hh.fflabel("SEAT RESERVATION CANCEL");
		lbheader.setBounds(30, 5, 250, 30);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(20,60,80,25);
		add(lbphone);
		
		txphone = hh.cTextField(12);
		txphone.setBounds(110,60,120,25);
		add(txphone);
		txphone.addKeyListener(hh.Onlyphone());
		
		btnsearch = hh.cbutton("Search");
		btnsearch.setBounds(240,60,80,25);
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnsearch.setBorder(hh.borderf2);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hh.zempty(txphone.getText())) {
					return;
				}
			 	sqlgyart();
			}
		});
		lbname = hh.clabel("");
		lbname.setBounds(330,60,200,25);
		lbname.setBorder(hh.borderf);
		lbname.setHorizontalAlignment(JLabel.CENTER);
		add(lbname);
		
		tpanel = new JPanel(null);	
		tpanel.setBounds(10, 120, 610, 200);
		tpanel.setBorder(hh.borderf);
		// tpanel.setBorder(line);
		add(tpanel);

		String[] columnNames = { "Seatid","Title","Date","Time","Seatno", "Mark" };
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 5 ? Boolean.class : super.getColumnClass(columnIndex);
			}
		};

		rtable = hh.ztable();
		rtable.setBorder(hh.borderf);
		rtable.setModel(model);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();	
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		rtable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		rtable.setPreferredScrollableViewportSize(rtable.getPreferredSize());
		hh.madeheader(rtable);
		hh.setJTableColumnsWidth(rtable, 606, 0, 40, 20,15,13,12);
		scroll = new JScrollPane();
		scroll.setViewportView(rtable);
		scroll.setBounds(2, 2, 606, 195);
		tpanel.add(scroll);		
		
		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.svoros);
		btncancel.setBounds(250, 370, 150, 30);
		add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			   if (model.getRowCount() > 0) {
				   canceling();
			   }
			}
		});
	
		setVisible(true);
	
		
	}
	private void canceling() {
		Boolean jel = Boolean.FALSE;
		ArrayList<String> myseats =new ArrayList<String>();
		int rowCount = model.getRowCount();
		if (rowCount==0) {
			return;
		}
	
		for (int row = 0; row < rowCount; row++) {
		if (model.getValueAt(row, 0) != null) {
			Boolean bbb = ((Boolean) model.getValueAt(row, 5));
			if (bbb == true) {
				String seatid = model.getValueAt(row, 0).toString();
				myseats.add(seatid);
			}
		}
	}
		int size = myseats.size();
		if(size == 0) {
			return;
		}
		String text ="update seatreservation set reserved = 'cancel'  where seatid in( '";		
		for (int i = 0; i < myseats.size(); i++) { 	
			text =text +myseats.get(i) +"','";			
            }
		text = text.substring(0,text.length() - 3);
		 text = text +"')";
		int flag = dh.Insupdel(text);	
    	   model.setColumnCount(0);
    	   txphone.setText("");    	
    	   lbname.setText("");
	}
	
	private void sqlgyart() {
		String stext = txphone.getText().trim();;	
	
		if (!hh.zempty(stext)) {		
               table_update(stext);
		} else {
			JOptionPane.showMessageDialog(null, "Empty condition !", "Error", 1);
			return;
		}
	}
	private void table_update(String what) {
		String Sql;	
		String mycid ="";
		String name ="";
		Sql= "select cid, name from customer where phone = '"+ what +"'";
		try {
		ResultSet rs = dh.GetData(Sql);
		  while (rs.next()) {	
		    	mycid = rs.getString ("cid");
		    	name = rs.getString("name");
	       	}		
		dh.CloseConnection();
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}		
		  if (hh.zempty(mycid)) {		
				  return;
     	}
		  lbname.setText(name);
			Sql = "select s.seatid, m.title, w.sdate, w.stime, s.seatno from seatreservation s "
					+ "  join shows w on s.showid = w. show_id  join movies m on w.movieid = m.movieid "
					+ " where s.cid = '" + mycid +"'" + " and s.reserved='yes'"
					+ " and  w.sdate >= date('" + nowdate +"')";	
			model.setRowCount(0);
		  try {	
		  ResultSet rs = dh.GetData(Sql);
		  while (rs.next()) {	
		    	String seatid = rs.getString ("seatid");
		    	String title = rs.getString ("title");
		    	String sdate= rs.getString ("sdate");
		    	String stime = rs.getString ("stime");
		    	String seatno = rs.getString ("seatno");
		    	Boolean jel = Boolean.FALSE;
		    	model.addRow(new Object[] { seatid, title, sdate, stime, seatno, jel}); 
	       	}		
		  
		dh.CloseConnection();
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
	
		String[] fej = { "Seatid","Title","Date","Time","Seatno", "Mark" };
		((DefaultTableModel) rtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(rtable, 606, 0, 50, 20,10,10,10);
		//DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) rtable.getDefaultRenderer(Object.class);	
		rtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rtable.scrollRectToVisible(rtable.getCellRect(rtable.getRowCount() - 1, 0, true));
			}
		});
		if (rtable.getRowCount() > 0) {
			int row = rtable.getRowCount() - 1;
			rtable.setRowSelectionInterval(row, row);
		}
	}
	
	
	public static void main(String[] args) {		
		Rescancel rr =	new Rescancel();			
	}
JLabel lbheader, lbphone, lbname;
JTextField txphone, txname;
JButton btnsearch, btncancel;
JPanel tpanel;
JTable rtable;
JScrollPane scroll;
}
