import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

public class seatreservation extends JFrame {
	ResultSet result;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	JDateChooser sdate = new JDateChooser(new Date());
	String ssdate;
	DefaultTableModel model;

	seatreservation() {
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		hh.iconhere(this);
	}

	private void initcomponents() {
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
		setTitle("Seat reservation");
		setSize(650, 530);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = hh.fflabel("SEAT RESERVATION");
		lbheader.setBounds(30, 5, 200, 30);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);
		jScrollPane1 = new JScrollPane();
		qpanel = new JPanel(null);		
		qpanel.setBounds(40, 50, 550, 80);
		qpanel.setBorder(hh.borderf);
		
		tpanel = new JPanel(null);
		tpanel.setBounds(40, 130, 550, 250);
		add(qpanel);
		add(tpanel);
		
		lbdate = hh.clabel("Movie date");
		lbdate.setBounds(40, 30, 120, 25);
		qpanel.add(lbdate);

		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(180, 30, 120, 25);
		qpanel.add(sdate);
		
		
		btnfilter = hh.cbutton("Filter");
		btnfilter.setBackground(hh.slilla);
		btnfilter.setBounds(310, 30, 100, 25);
		qpanel.add(btnfilter);		
		btnfilter.addActionListener(filtlistener);
		
		String[] columnNames = { "Show_id","Movieid", "Title","Time"};
		Object[][] data = {};
		model = new DefaultTableModel(data, columnNames); // {};
		table = hh.ztable();
		hh.madeheader(table);
		table.setModel(model);
		hh.setJTableColumnsWidth(table, 530, 0, 0, 80, 20);
		jScrollPane1.setViewportView(table);
		jScrollPane1.setBounds(0, 30, 550, 200);
		tpanel.add(jScrollPane1);
	
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableListMouseClicked(evt);
			}
		});
		
		btnnext = hh.cbutton("Next");
		btnnext.setBackground(hh.svoros);
		btnnext.setBounds(270, 400, 130, 25);
		add(btnnext);
		btnnext.addActionListener(listener);

	}
	private void tableListMouseClicked(java.awt.event.MouseEvent evt) {
		int row = table.getSelectedRow();
		if (row >= 0)
			Hhelper.showid = table.getValueAt(row, 0).toString();
		    Hhelper.movieid = table.getValueAt(row, 1).toString();      		
	}

	ActionListener filtlistener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {		
			ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
           String sql = "select s.show_id, s.movieid, m.title, s.stime from shows s  "
					+ "join movies m on s.movieid = m.movieid "
					+ " where " + "sdate >= date('" + ssdate +"') order by title, sdate, stime " ;		
			table_update(sql);
		}
	};
	private void table_update(String sql) {	
		ResultSet res = dh.GetData(sql);
		table.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "Showid", "Movieid", "Title","Time" };
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(table, 530, 0, 0, 80, 20);   
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
	}


	ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			if (row >= 0) {
				Hhelper.showid = table.getValueAt(row, 0).toString();
			    Hhelper.movieid = table.getValueAt(row, 1).toString();  				
				Session ses = new Session();
 	  		    ses.setVisible(true);
				dispose();				
			}		
		}
	};

	public static void main(String[] args) {
		seatreservation se = new seatreservation();
		se.setVisible(true);
	}

	JLabel lbheader, lbshows, lbdate, lbmovie;
	JPanel qpanel, tpanel;
	 JScrollPane jScrollPane1;
	 JComboBox cmbmovies;
	 JButton btnfilter, btnnext;
	 JTable table;
}
