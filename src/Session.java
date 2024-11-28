import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import net.proteanit.sql.DbUtils;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Session extends JFrame {
	Hhelper hh = new Hhelper();
	String letters[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K" };
	String sn = "";
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	ArrayList<String> myseats = new ArrayList<String>();
	String movie_title = "";
	String nowdate =hh.currentDate();

	Session() {
		movie_title = dd.searchthemovie();
		initcomponents();
		hh.iconhere(this);
	}

	private void initcomponents() {
		setSize(1100, 680);
		setLayout(null);
		setLocationRelativeTo(null);

		lbscreen = new JLabel();
		lbscreen.setBackground(Color.BLUE);
		lbscreen.setForeground(new Color(0, 0, 204));
		lbscreen.setBounds(200, 20, 300, 40);
		lbscreen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbscreen.setFont(new java.awt.Font("Tahoma", 1, 20));
		lbscreen.setText("SCREEN THIS WAY");
		lbscreen.setBorder(hh.bb);
		add(lbscreen);

		lbtitle = new JLabel();
		lbtitle.setBackground(Color.BLUE);
		lbtitle.setForeground(new Color(0, 0, 204));
		lbtitle.setBounds(700, 20, 360, 40);
		lbtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbtitle.setFont(new java.awt.Font("Tahoma", 1, 12));
		lbtitle.setText(movie_title);
		lbtitle.setBorder(hh.bb);
		add(lbtitle);

		lbaisle = new JLabel();
		lbaisle.setBackground(Color.BLUE);
		lbaisle.setForeground(new Color(0, 0, 204));
		lbaisle.setBounds(300, 345, 150, 40);
		lbaisle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbaisle.setFont(new java.awt.Font("Tahoma", 1, 20));
		lbaisle.setText("AISLE");
		add(lbaisle);

		xPanel = new JPanel(null);
		xPanel.setBounds(40, 100, 616, 230);
		xPanel.setBorder(hh.line2);
		yPanel = new JPanel(null);
		yPanel.setBounds(40, 400, 616, 195);
		yPanel.setBorder(hh.line2);

		tbuttons = new ColoredTButton[11][15];
		JButton Button[] = new JButton[5];
		int x = 10;
		int y = 10;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 15; j++) {
				String title = String.valueOf(j + 1);
				tbuttons[i][j] = new ColoredTButton();
				tbuttons[i][j].setText(title);
				tbuttons[i][j].setBounds(x, y, 35, 30);
				tbuttons[i][j].setMargin(new Insets(0, 0, 0, 0));
				tbuttons[i][j].setBorder(hh.borderz);
				sn = letters[i] + title;
				tbuttons[i][j].setName(sn);
				tbuttons[i][j].setContentAreaFilled(false);
				tbuttons[i][j].setOpaque(true);
				tbuttons[i][j].setBackground(Color.orange);
				tbuttons[i][j].setFocusPainted(false);
				tbuttons[i][j].setFocusable(false);
				xPanel.add(tbuttons[i][j]);

				tbuttons[i][j].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						buttActionPerformed(evt);
					}
				});
				x = x + 40;
			}
			y = y + 35;
			x = 10;
		}
		add(xPanel);
		x = 10;
		y = 10;
		for (int i = 6; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				String title = String.valueOf(j + 1);
				tbuttons[i][j] = new ColoredTButton();
				tbuttons[i][j].setText(title);
				tbuttons[i][j].setBounds(x, y, 35, 30);
				tbuttons[i][j].setMargin(new Insets(0, 0, 0, 0));
				tbuttons[i][j].setBorder(hh.borderz);
				sn = letters[i] + title;
				tbuttons[i][j].setName(sn);
				tbuttons[i][j].setContentAreaFilled(false);
				tbuttons[i][j].setOpaque(true);
				tbuttons[i][j].setBackground(Color.orange);
				tbuttons[i][j].setFocusPainted(false);
				tbuttons[i][j].setFocusable(false);
				yPanel.add(tbuttons[i][j]);

				tbuttons[i][j].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						buttActionPerformed(evt);
					}
				});
				x = x + 40;
			}
			y = y + 35;
			x = 10;
		}
		add(yPanel);

		enablequestion();

		JLabel[] sign = new JLabel[12];
		JLabel[] rsign = new JLabel[12];
		x = 10;
		y = 113;
		for (int i = 0; i < 6; i++) {
			sign[i] = new JLabel(letters[i], SwingConstants.CENTER);
			sign[i].setFont(new java.awt.Font("Tahoma", 1, 16));
			sign[i].setBounds(x, y, 22, 22);
			add(sign[i]);
			rsign[i] = new JLabel(letters[i], SwingConstants.CENTER);
			rsign[i].setFont(new java.awt.Font("Tahoma", 1, 16));
			rsign[i].setBounds(x + 650, y, 22, 22);
			add(rsign[i]);
			y = y + 35;
		}
		y = 415;
		for (int i = 6; i < 11; i++) {
			sign[i] = new JLabel(letters[i], SwingConstants.CENTER);
			sign[i].setFont(new java.awt.Font("Tahoma", 1, 16));
			sign[i].setBounds(x, y, 22, 22);
			add(sign[i]);
			rsign[i] = new JLabel(letters[i], SwingConstants.CENTER);
			rsign[i].setFont(new java.awt.Font("Tahoma", 1, 16));
			rsign[i].setBounds(x + 650, y, 22, 22);
			add(rsign[i]);
			y = y + 35;
		}

		epanel = new JPanel(null);
		epanel.setBounds(710, 100, 340, 200);
		epanel.setBorder(hh.borderf);
		add(epanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(10, 40, 60, 25);
		epanel.add(lbname);

		txname = hh.cTextField(25);
		txname.setBounds(90, 40, 200, 25);
		epanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 80, 70, 25);
		epanel.add(lbphone);

		txphone = hh.cTextField(25);
		txphone.setBounds(90, 80, 200, 25);
		epanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());

		btntotable = hh.cbutton("Put the seats to the table");
		btntotable.setBackground(hh.svoros);
		btntotable.setBounds(75, 140, 230, 30);
		epanel.add(btntotable);
		btntotable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txname.getText();
				String phone = txphone.getText();
				if (totableverify(name, phone) == true) {
					txname.requestFocus();
				} else {
					tableupload();
				}
			}
		});

		tpanel = new JPanel(null);
		tpanel.setBounds(710, 320, 340, 200);
		// tpanel.setBorder(hh.borderf);
		add(tpanel);

		String[] columnNames = { "Seat", "Price", "Delete" };
		Object[][] data = {};
		model = new DefaultTableModel(data, columnNames) {
			boolean[] canEdit = new boolean[] { false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		btable = hh.ztable();
		btable.setBorder(hh.borderf);
		btable.setModel(model);

		btable.getColumnModel().getColumn(2).setCellRenderer(new ClientsTableButtonRenderer());
		btable.getColumnModel().getColumn(2).setCellEditor(new ClientsTableRenderer(new JCheckBox()));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		btable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		btable.setPreferredScrollableViewportSize(btable.getPreferredSize());
		hh.madeheader(btable);
		hh.setJTableColumnsWidth(btable, 338, 30, 30, 40);
		scroll = new JScrollPane();
		scroll.setViewportView(btable);
		scroll.setBounds(2, 2, 338, 195);
		tpanel.add(scroll);

		btnreservation = hh.cbutton("Seat reservation");
		btnreservation.setBackground(hh.svoros);
		btnreservation.setBounds(810, 540, 160, 30);
		add(btnreservation);
		btnreservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toreservation();
			Zmakebill  mk = new Zmakebill(myseats);
			dispose();
			}
		});
		setVisible(true);
	}

	private void buttActionPerformed(java.awt.event.ActionEvent e) {
		JToggleButton target = (JToggleButton) e.getSource();
		String ezaz = target.getName();
		if (target.isSelected()) {
			if (!myseats.contains(ezaz)) {
				myseats.add(ezaz);
			}
		} else {
			myseats.remove(ezaz);
		}
	}

	private void toreservation() {
		if (model.getRowCount() == 0) {
			return;
		}
		String mycid = "";
		String name = txname.getText();
		String phone = txphone.getText();
		String Sql = "select cid from customer where phone='" + phone + "'";
		try {
			Connection con = dh.getConnection();
			ResultSet rs = dh.GetData(Sql);
			while (rs.next()) {
				mycid = rs.getString("cid");
			}
			dh.CloseConnection();
			if (hh.zempty(mycid)) {
				Sql = "insert into customer (name, phone) " + "values ('" + name + "','" + phone + "')";
				int flag = dh.Insupdel(Sql);
				if (flag == 1) {
					hh.ztmessage("Success", "Message");
				} else {
					JOptionPane.showMessageDialog(null, "sql error !");
				}
				int myid = dd.table_maxid("SELECT MAX(cid) AS max_id from customer");
				mycid = hh.itos(myid);
			}
			System.out.println(model.getRowCount());
			for (int i = 0; i < model.getRowCount(); i++) {
				String seatno = model.getValueAt(i, 0).toString();   
				String price = model.getValueAt(i, 1).toString();
				String reserved = "yes";
				Sql = "insert into seatreservation (seatno, reserved, showid,price, cid, date) " + "values ('" + seatno
						+ "','" + reserved + "','" + hh.showid + "','" + price + "','" + mycid +"','"+ nowdate+ "')";
				int flag = dh.Insupdel(Sql);
			}

		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
	}

	class ClientsTableButtonRenderer extends JButton implements TableCellRenderer {
		public ClientsTableButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setForeground(Color.black);
			setBackground(UIManager.getColor("Button.background"));
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	public class ClientsTableRenderer extends DefaultCellEditor {
		private JButton button;
		private String label;
		private boolean clicked;
		private int row, col;
		private JTable tabla;

		public ClientsTableRenderer(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					if (col == 2) {
						int result = JOptionPane.showConfirmDialog(null, "Would  you like to delete this row ?",
								"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							String seatno = tabla.getValueAt(row, 0).toString();
							((DefaultTableModel) tabla.getModel()).removeRow(row);
							whichseat(seatno);
							myseats.remove(seatno);
						}
					}
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.tabla = table;
			this.row = row;
			this.col = column;
			button.setForeground(Color.black);
			button.setBackground(UIManager.getColor("Button.background"));
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			clicked = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (clicked) {
				// JOptionPane.showMessageDialog(button, "Column +row " + col + ", " + row + " -
				// Clicked!");
			}
			clicked = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	private void whichseat(String seatno) {
		int i, j;
		String sval = seatno.substring(0, 1);
		i = Hhelper.findinarr(letters, sval);
		String sj = seatno.length() > 2 ? seatno.substring(seatno.length() - 2) : seatno.substring(seatno.length() - 1);
		j = hh.stoi(sj);
		j = j - 1;
		tbuttons[i][j].setSelected(false);
	}

	private void tableupload() {
		String price = "";
		String Sql = "SELECT price  FROM  prices where movieid = '" + hh.movieid + "'";
		try {
			ResultSet rs = dh.GetData(Sql);
			if (rs.next()) {
				price = rs.getString("price");
			}
			dh.CloseConnection();
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql select hiba");
		}
		String text = "";
		for (int i = 0; i < myseats.size(); i++) {
			text = myseats.get(i);
			model.addRow(new Object[] { text, price, "Delete" });
		}
	}

	private Boolean totableverify(String name, String phone) {
		Boolean ret = false;
		if (hh.zempty(name) == true || hh.zempty(phone) == true || myseats.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please  fill customer name  and phone !");
			ret = true;
		}
		if (myseats.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You did not choose seat !");
			ret = true;
		}
		return ret;
	}

	private void enablequestion() {
		String seatno;
		String Sql = "select seatno from seatreservation where reserved = 'yes' and showid= " + hh.showid;
		ArrayList<String> List = new ArrayList<>();
		try {
			ResultSet rs = dh.GetData(Sql);
			while (rs.next()) {
				seatno = rs.getString("seatno");
				List.add(seatno);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		int size = List.size();
		if (size > 0) {
			for (int i = 0; i < 11; i++) {
				for (int j = 0; j < 15; j++) {
					seatno = tbuttons[i][j].getName();
					if (List.contains(seatno)) {
						tbuttons[i][j].setEnabled(false);
					}
				}
			}
		}
	}

	
	public static void main(String[] args) {
		UIManager.getDefaults().put("JToggleButton.disabledText", Color.RED);
		Session se = new Session();
	}

	public class ColoredTButton extends JToggleButton {
		@Override
		public void paintComponent(Graphics g) {
			Color bg;
			if (isSelected()) {
				// bg = Color.RED;
				bg = hh.lpiros;
			} else {
				// bg = Color.green;
				bg = hh.vvzold;
			}
			setBackground(bg);
			super.paintComponent(g);
		}
	}

	JPanel xPanel, yPanel, tpanel, epanel;
	ColoredTButton tbuttons[][];
	JLabel lbscreen, lbaisle, lbtitle;
	JLabel lbname, lbphone;
	JTable btable;
	DefaultTableModel model;
	JScrollPane scroll;
	JTextField txname, txphone;
	JButton btntotable, btnreservation;

}
