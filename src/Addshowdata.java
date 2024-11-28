import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;
import net.proteanit.sql.DbUtils;

public class Addshowdata extends JFrame {
	ResultSet result;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	JDateChooser sdate = new JDateChooser(new Date());

	Addshowdata() {
		initcomponents();
		try {
			dd.moviecombofill(cmbmovies);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getContentPane().setBackground(new Color(241, 241, 242));
		table_update("");
		hh.iconhere(this);
	}

	private void initcomponents() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Shows");
		setSize(1250, 550);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = new JLabel("Shows");
		lbheader.setBounds(30, 5, 60, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);
		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(10, 40, 1220, 480);
		bPanel.setBackground(new Color(230, 231, 232));
		ePanel = new JPanel(null);
		ePanel.setBounds(10, 10, 400, 440);
		ePanel.setBorder(hh.line);
		tPanel = new JPanel(null);
		tPanel.setBounds(411, 10, 800, 440);
		tPanel.setBorder(hh.line);
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);

		lbmovie = hh.clabel("Movies");
		lbmovie.setBounds(5, 50, 100, 20);
		ePanel.add(lbmovie);

		cmbmovies = hh.cbcombo();
		cmbmovies.setName("movie");
		cmbmovies.setBounds(110, 50, 280, 25);
		ePanel.add(cmbmovies);

		lbdate = hh.clabel("Show date");
		lbdate.setBounds(5, 100, 100, 20);
		ePanel.add(lbdate);

		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(110, 100, 120, 25);
		ePanel.add(sdate);

		lbtime = hh.clabel("Show time");
		lbtime.setBounds(5, 150, 100, 20);
		ePanel.add(lbtime);

		String rtime = "06:00";
		startmodel = new SpinnerDateModel();
		starttime = hh.cspinner(startmodel);
		starttime.setBounds(110, 150, 70, 25);
		hh.madexxx(starttime, "T");
		starttime.setName("starttime");
		ePanel.add(starttime);
		starttime.setValue(hh.stringtotime(rtime));
		((JSpinner.DefaultEditor) starttime.getEditor()).getTextField().addFocusListener(sFocusListener);
		starttime.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				starttime.requestFocus();
			}
		});

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(150, 230, 130, 30);
		btnsave.setBackground(hh.svoros);
		ePanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.szold);
		btncancel.setBounds(150, 270, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(150, 310, 130, 30);
		btndelete.setBackground(hh.skek);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(stable, "delete from shows  where show_id =");
				clearFields();
			}
		});

		stable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) stable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		stable.setTableHeader(new JTableHeader(stable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(stable);
		stable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				stable.scrollRectToVisible(stable.getCellRect(stable.getRowCount() - 1, 0, true));
			}
		});
		stable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		jScrollPane1 = new JScrollPane(stable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		stable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null } },
				new String[] { "Show_id", "Movieid", "Title", "Date", "Time", "Duration" }));
		hh.setJTableColumnsWidth(stable, 780, 0, 0, 50, 13, 13, 14);
		jScrollPane1.setViewportView(stable);
		jScrollPane1.setBounds(10, 10, 780, 340);
		tPanel.add(jScrollPane1);
		
		lbsearch= new JLabel("Search");
		lbsearch.setBounds(210, 380, 60, 25);
		lbsearch.setFont(new Font("tahoma", Font.BOLD, 16));
		tPanel.add(lbsearch);		

		txsearch = cTextField(25);
		txsearch.setBounds(270, 380, 200, 25);
		tPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(470, 380, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		tPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				table_update("");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(hh.borderf2);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(505, 380, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		tPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hh.zempty(txsearch.getText())) {
					return;
				}
				sqlgyart();
			}
		});
	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select s.show_id, s.movieid, m.title, s.sdate, s.stime, m.duration from shows s "
					+ "join movies m on s.movieid = m.movieid order by title, sdate, stime";
		} else {
			Sql = "select s.show_id, s.movieid, m.title, s.sdate, s.stime, m.duration from shows s "
					+ "join movies m on s.movieid = m.movieid  where " + what;
		}
		ResultSet res = dh.GetData(Sql);
		stable.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = { "Showid", "Movieid", "Title", "Date", "Time", "Duration" };
		((DefaultTableModel) stable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(stable, 780, 0, 0, 50, 13, 13, 13);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) stable.getDefaultRenderer(Object.class);
//		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
//		stable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";
		int movieid = ((Movie) cmbmovies.getSelectedItem()).getMovieid();
		String title = ((Movie) cmbmovies.getSelectedItem()).getTitle();
		String smovieid = hh.itos(movieid);
		JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
		String sttime = intf.getText();
		String ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();

		if (hh.zempty(ssdate) || hh.zempty(sttime) || movieid <= 0) {
			JOptionPane.showMessageDialog(null, "Please fill All Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  shows set movieid = '" + smovieid + "', sdate= '" + ssdate + "', stime = '" + sttime
					+ "' where show_id = " + rowid;
		} else {
			sql = "insert into shows (movieid, sdate, stime) " + "values ('" + smovieid + "','" + ssdate + "','" + sttime
					+ "')";
		}
		try {
			int flag = dh.Insupdel(sql);

			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(smovieid, title, ssdate, sttime);
				} else {
					table_update("");
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}

		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
	}

	void table_rowrefresh(String movieid,  String title,String sdate, String stime) {
		DefaultTableModel d1 = (DefaultTableModel) stable.getModel();
		d1.setValueAt(movieid, myrow, 1);
		d1.setValueAt(title, myrow, 2);
		d1.setValueAt(sdate, myrow, 3);
		d1.setValueAt(stime, myrow, 4);
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		DefaultTableModel d1 = (DefaultTableModel) stable.getModel();
		int row = stable.getSelectedRow();
		if (row >= 0) {
			String cnum = stable.getValueAt(row, 1).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbmovies, number);
			cmbmovies.updateUI();
			Date date;
			try {
				String dd = d1.getValueAt(row, 3).toString();
				date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
				sdate.setDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String starttime = stable.getValueAt(row, 4).toString();
			startmodel.setValue(hh.stringtotime(starttime));
			rowid = stable.getValueAt(row, 0).toString();
			myrow = row;
		}
	}

	private final FocusListener sFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
		}

		@Override
		public void focusLost(FocusEvent e) {
			JComponent b = (JComponent) e.getSource();
			JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
			String intime = intf.getText();
			if (hh.correcttime(intime) == true) {
				JOptionPane.showMessageDialog(null, "Correct time is 00:00 - 24:00 !");
			}
		}
	};

	private void clearFields() {
		cmbmovies.setSelectedIndex(-1);
		cmbmovies.requestFocus();
		String rtime = "06:00";
		starttime.setValue(hh.stringtotime(rtime));
		Date date = new Date();
		sdate.setDate(date);
		stable.clearSelection();
		rowid = "";
		myrow = 0;
	}
	private void sqlgyart() {
		 String sql="";
		 String ss = txsearch.getText().trim().toLowerCase();		
			 sql = "lower(title) LIKE '%"+ ss + "%' order by title, sdate, stime COLLATE NOCASE ASC";			
		   table_update(sql);
	}
	 public JTextField cTextField(int hossz) {
			JTextField textField = new JTextField(hossz);
			textField.setFont(hh.textf);
			textField.setBorder(hh.borderf);
			textField.setBackground(hh.feher);
			textField.setPreferredSize(new Dimension(250, 30));
			textField.setCaretColor(Color.RED);
			textField.putClientProperty("caretAspectRatio", 0.1);	
	//		textField.addFocusListener(dFocusListener);
			textField.setText("");
			textField.setDisabledTextColor(Color.magenta);
			return textField;
		}	
	public static void main(String args[]) {
		Addshowdata dd = new Addshowdata();	
		dd.setVisible(true);
	}

	JLabel lbheader, lbmovie, lbdate, lbtime, lbsearch;
	JPanel bPanel, ePanel, tPanel;
	JComboBox cmbmovies;
	SpinnerDateModel startmodel;
	JSpinner starttime;
	JButton btnsave, btncancel, btndelete, btnsearch, btnclear;
	JTable stable;
	JScrollPane jScrollPane1;
	JTextField txsearch;
}
