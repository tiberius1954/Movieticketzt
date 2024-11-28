import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.proteanit.sql.DbUtils;

public class Prices extends JFrame {
	ResultSet result;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	private String sfrom = "";

	Prices() {
		initcomponents();
		try {
			dd.moviecombofill(cmbmovies);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(3);
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
		setTitle("Prices");
		setSize(900, 500);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = hh.clabel("Prices");
		lbheader.setBounds(20, 5, 60, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		add(lbheader);
		bPanel = new JPanel();
		bPanel.setLayout(null);
		bPanel.setBounds(10, 40, 890, 400);
		bPanel.setBackground(new Color(230, 231, 232));
		ePanel = new JPanel(null);
		ePanel.setBounds(10, 10, 380, 390);
		ePanel.setBorder(hh.line);
		tPanel = new JPanel(null);
		tPanel.setBounds(390, 10, 460, 390);
		tPanel.setBorder(hh.line);
		bPanel.add(ePanel);
		bPanel.add(tPanel);
		add(bPanel);

		lbmovie = hh.clabel("Movies");
		lbmovie.setBounds(5, 50, 70, 20);
		ePanel.add(lbmovie);

		cmbmovies = hh.cbcombo();
		cmbmovies.setName("movie");
		cmbmovies.setBounds(85, 50, 280, 25);
		ePanel.add(cmbmovies);

		lbprice = hh.clabel("Price");
		lbprice.setBounds(5, 100, 70, 20);
		ePanel.add(lbprice);

		txprice = hh.cTextField(10);
		txprice.setBounds(85, 100, 120, 25);
		txprice.setHorizontalAlignment(JTextField.RIGHT);
		;
		ePanel.add(txprice);

		btnsave = hh.cbutton("Save");
		btnsave.setBounds(130, 160, 130, 30);
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
		btncancel.setBounds(130, 210, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});
		btndelete = hh.cbutton("Delete");
		btndelete.setBounds(130, 260, 130, 30);
		btndelete.setBackground(hh.skek);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dd.data_delete(ptable, "delete from prices  where pid =");
				clearFields();
			}
		});

		ptable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) ptable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ptable);
		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
		ptable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tableMouseClicked(evt);
			}
		});

		jScrollPane1 = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "pid", "movieid", "Title", "Price" }));
		hh.setJTableColumnsWidth(ptable, 440, 0, 0, 80, 20);
		jScrollPane1.setViewportView(ptable);
		jScrollPane1.setBounds(10, 10, 440, 360);
		tPanel.add(jScrollPane1);

		setVisible(true);

	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select p.pid, p.movieid, m.title, p.price from prices p "
					+ "join movies m on p.movieid = m.movieid order by m.title";
		} else {
			Sql = "select p.pid, p.movieid, m.title, p.price from prices p "
					+ "join movies m on p.movieid = m.movieid  where " + what + " order by m.title";
		}
		try {
			ResultSet res = dh.GetData(Sql);
			ptable.setModel(DbUtils.resultSetToTableModel(res));
			dh.CloseConnection();
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert error 3");
		}
		String[] fej = { "pid", "Movieid", "Title", "Price" };
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ptable, 440, 0, 0, 80, 20);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) ptable.getDefaultRenderer(Object.class);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		ptable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	}

	private void tableMouseClicked(java.awt.event.MouseEvent evt) {
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		int row = ptable.getSelectedRow();
		if (row >= 0) {
			String cnum = ptable.getValueAt(row, 1).toString();
			int number = 0;
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			hh.setSelectedValue(cmbmovies, number);
			cmbmovies.updateUI();
			txprice.setText(ptable.getValueAt(row, 3).toString());
			rowid = ptable.getValueAt(row, 0).toString();
			myrow = row;
		}
	}

	private void savebuttrun() {
		String sql = "";
		String jel = "";
		int movieid = ((Movie) cmbmovies.getSelectedItem()).getMovieid();
		String title = ((Movie) cmbmovies.getSelectedItem()).getTitle();
		String smovieid = hh.itos(movieid);
		String price = txprice.getText();

		if (hh.zempty(price) || movieid <= 0) {
			JOptionPane.showMessageDialog(null, "Please fill All Fields");
			return;
		}
		if (rowid != "") {
			jel = "UP";
			sql = "update  prices set movieid = '" + smovieid + "', price= '" + price + "' where pid = " + rowid;
		} else {
			sql = "insert into prices(movieid, price) " + "values ('" + smovieid + "','" + price + "')";
		}
		try {
			int flag = dh.Insupdel(sql);

			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(smovieid, title, price);
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

	void table_rowrefresh(String movieid, String title, String price) {
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		d1.setValueAt(movieid, myrow, 1);
		d1.setValueAt(title, myrow, 2);
		d1.setValueAt(price, myrow, 3);
	}

	private void clearFields() {
		cmbmovies.setSelectedIndex(-1);
		cmbmovies.requestFocus();
		txprice.setText("");
		rowid = "";
		myrow = 0;
	}

	public static void main(String args[]) {
		new Prices().setVisible(true);
	}

	JLabel lbheader, lbmovie, lbprice;
	JTextField txprice;
	JPanel bPanel, ePanel, tPanel;
	JComboBox cmbmovies;
	JTable ptable;
	JScrollPane jScrollPane1;
	JButton btnsave, btncancel, btndelete;
}
