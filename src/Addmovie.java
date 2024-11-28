import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.JTableHeader;
import net.proteanit.sql.DbUtils;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Addmovie extends JFrame{
	ResultSet result;
	Hhelper hh = new Hhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	private String rowid = "";
	private int myrow = 0;
	private String sfrom = "";
	private JFrame pframe;
	Movalidation cvad = new Movalidation();	
	
	Addmovie(){
		initcomponents();
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
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (hh.whichpanel(cardPanel) == "tabla") {
					dispose();
				} else if (hh.whichpanel(cardPanel) == "edit") {
					cards.show(cardPanel, "tabla");
				} else {
					cards.show(cardPanel, "tabla");
				}
			}
		});	

		setTitle("Movies");
		setSize(1230, 510);
		setLayout(null);
		setLocationRelativeTo(null);
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setBorder(hh.line);
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 10, 1200, 450);
		tPanel = maketpanel();
		tPanel.setName("tabla");
	    ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(tPanel, "tabla");
       cardPanel.add(ePanel, "edit");
		add(cardPanel);
    cards.show(cardPanel, "tabla");	
//	cards.show(cardPanel, "edit");	
		
	}
	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBorder(hh.line);
		ttpanel.setBackground(new Color(230, 231, 232));
		ttpanel.setBounds(20, 20, 1100, 430);
		lbheader = new JLabel("Movies");
		lbheader.setBounds(30, 5, 300, 40);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 16));
		ttpanel.add(lbheader);
		
		rname = new JRadioButton("Title");
		rname.setHorizontalTextPosition(SwingConstants.LEFT);
		rname.setActionCommand("name");
		rname.setBounds(430, 20, 80, 25);
		rname.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel = new JRadioButton("Genre");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(510, 20, 90, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnGroup = new ButtonGroup();
		btnGroup.add(rname);
		btnGroup.add(rtel);		
		ttpanel.add(rname);
		ttpanel.add(rtel);
		
		txsearch = cTextField(25);
		txsearch.setBounds(600, 20, 200, 25);	
		ttpanel.add(txsearch);
		
		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(800, 20, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");		
		ttpanel.add(btnclear);
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
		btnsearch.setBounds(830, 20, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		ttpanel.add(btnsearch);
		 btnsearch.addActionListener(new ActionListener() {  
	         public void actionPerformed(ActionEvent e)
	         { 	          
	             int jelem = 0;
	             if (rname.isSelected()) {	           
	                 jelem= 1;
	             } else if (rtel.isSelected()) {	          
	                 jelem=2;
	             } else {
	                 String qual = "Did not choose !"; 
	                 JOptionPane.showMessageDialog(null, qual);  
	                 }   
	          
	             if (jelem< 1|| hh.zempty(txsearch.getText())) {
	            	 return;
	             }   
	              sqlgyart();	             
	         }
	     });
			movtable = hh.ztable();
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) movtable.getDefaultRenderer(Object.class);
			renderer.setHorizontalAlignment(SwingConstants.LEFT);
			movtable.setTableHeader(new JTableHeader(movtable.getColumnModel()) {
				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 25;
					return d;
				}
			});

			hh.madeheader(movtable);

			movtable.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					movtable.scrollRectToVisible(movtable.getCellRect(movtable.getRowCount() - 1, 0, true));
				}
			});

			jScrollPane1 = new JScrollPane(movtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			movtable.setModel(new DefaultTableModel(
					new Object[][] { 
						    { null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null},
							{ null, null, null, null, null, null, null},
							{ null, null, null, null, null, null, null},
							{ null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null },
							{ null, null, null, null, null, null, null, null}
							},
					new String[] { "Movieid", "Title", "Genre", "Year","Duration", "Poster", "Video"}
							));

			hh.setJTableColumnsWidth(movtable, 1150, 0, 40, 15,7,8,15,15);
			jScrollPane1.setViewportView(movtable);
			jScrollPane1.setBounds(30, 80, 1150, 200);
			ttpanel.add(jScrollPane1);

			btnnew = hh.cbutton("New");
			btnnew.setBounds(400, 320, 130, 30);
			btnnew.setBackground(hh.svoros);
			ttpanel.add(btnnew);
			btnnew.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
				  data_new();
				}
			});

			btnupdate = hh.cbutton("Update");
			btnupdate.setBounds(540, 320, 130, 30);
			btnupdate.setBackground(hh.szold);
			ttpanel.add(btnupdate);
			btnupdate.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					data_update();
				}
			});

			btndelete = hh.cbutton("Delete");
			btndelete.setBounds(680, 320, 130, 30);
			btndelete.setBackground(hh.skek);
			ttpanel.add(btndelete);
			btndelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					movdata_delete();
				}
			});
			return ttpanel;
	}
	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBorder(hh.line);
		eepanel.setBounds(20, 20, 1100, 430);
		eepanel.setBackground(new Color(230, 231, 232));
		lbpicture = new JLabel();
		lbpicture.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/titanic.png")));
		lbpicture.setBounds(400, 10, 780, 420);
		lbpicture.setBorder(hh.line);
		eepanel.add(lbpicture);

		lbtitle = hh.clabel("Title");
		lbtitle.setBounds(20, 20, 120, 20);
		eepanel.add(lbtitle);

		txtitle = cTextField(25);
		txtitle.setBounds(150, 20, 200, 25);
		eepanel.add(txtitle);
		txtitle.addKeyListener( hh.MUpper());

		lbgenre = hh.clabel("Genre");
		lbgenre.setBounds(20, 60, 120, 20);
		eepanel.add(lbgenre);

		txgenre = cTextField(25);
		txgenre.setBounds(150, 60, 200, 25);
		eepanel.add(txgenre);
		txgenre.addKeyListener( hh.MUpper());

		lbyear = hh.clabel("Year");
		lbyear.setBounds(20, 100, 120, 20);
		eepanel.add(lbyear);

		txyear = cTextField(25);
		txyear.setBounds(150, 100, 200, 25);
		eepanel.add(txyear);
		txyear.addKeyListener(hh.Onlynum());

		lbduration = hh.clabel("Duration");
		lbduration.setBounds(20, 140, 120, 20);
		eepanel.add(lbduration);	

		txduration = cTextField(25);
		txduration.setBounds(150, 140, 200, 25);
		eepanel.add(txduration);
		txduration.addKeyListener(hh.Onlynum());

		lbposter= hh.clabel("Poster");
		lbposter.setBounds(20, 180, 120, 20);
		eepanel.add(lbposter);		
		
		txposter = cTextField(25);
		txposter.setBounds(150, 180, 200, 25);
		eepanel.add(txposter);
		
		lbvideo = hh.clabel("Video");
		lbvideo.setBounds(20, 220, 120, 20);
		eepanel.add(lbvideo);

		txvideo = cTextField(25);
		txvideo.setBounds(150, 220, 200, 25);
		eepanel.add(txvideo);
	
		btnsave = hh.cbutton("Save");
		btnsave.setBounds(130, 300, 110, 30);
		btnsave.setBackground(hh.svoros);
		eepanel.add(btnsave);

		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = hh.cbutton("Cancel");
		btncancel.setBackground(hh.szold);
		btncancel.setBounds(250, 300, 110, 30);
		eepanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
				cards.show(cardPanel, "tabla");
			}
		});
		return eepanel;
	}

	private void table_update(String what) {
		String Sql;
		if (what == "") {
			Sql = "select movieid, title, genre, year, duration, poster, video from movies";
		} else {
			Sql = "select movieid, title, genre, year ,duration, poster, video from "
					+ "movies where "+what;
		}
		ResultSet res = dh.GetData(Sql);
		movtable.setModel(DbUtils.resultSetToTableModel(res));
		dh.CloseConnection();
		String[] fej = {  "Movieid", "Title", "Genre", "Year","Duration", "Poster", "Video"} ;
		((DefaultTableModel) movtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(movtable, 1150, 0, 40, 15,7,8,15,15);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) movtable.getDefaultRenderer(Object.class);	
		movtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				movtable.scrollRectToVisible(movtable.getCellRect(movtable.getRowCount() - 1, 0, true));
			}
		});
		if (movtable.getRowCount() > 0) {
			int row = movtable.getRowCount() - 1;
			movtable.setRowSelectionInterval(row, row);
		}
	}
	private void data_new() {
		clearFields();
		cards.show(cardPanel, "edit");
		txtitle.requestFocus();
	}
	
	private void data_update() {
		DefaultTableModel d1 = (DefaultTableModel) movtable.getModel();
		int row = movtable.getSelectedRow();
		myrow = 0;
		if (row < 0) {
			rowid = "";
			row = 0;
		} else {
			myrow = row;
			rowid = d1.getValueAt(row, 0).toString();
			txtitle.setText(d1.getValueAt(row, 1).toString());
			txgenre.setText(d1.getValueAt(row, 2).toString());
			txyear.setText(d1.getValueAt(row, 3).toString());
			txduration.setText(d1.getValueAt(row, 4).toString());			
			txposter.setText(d1.getValueAt(row, 5).toString());
			txvideo.setText(d1.getValueAt(row, 6).toString());			
			cards.show(cardPanel, "edit");
			txtitle.requestFocus();
		}
	}
	
	private void savebuttrun() {
		String sql = "";
		String jel = "";	
		String title = txtitle.getText();
		String genre = txgenre.getText();
		String year = txyear.getText();
		String duration = txduration.getText();
		String poster = txposter.getText();
		String video  = txvideo.getText();		
		 if (csvalidation(title, genre, year, duration, poster, video) == false) {
				return;
		  	}

		if (rowid != "") {
			jel = "UP";
			sql = "update  movies set title= '" + title+ "', genre= '" + genre + "'," + "year = '"
					+ year + "', duration = '" + duration + "'," + "poster= '" + poster + "', video= '" + video + "'"
					+ " where movieid = " + rowid;
		} else {
			sql = "insert into movies (title, genre, year ,duration ,poster, video) " + "values ('"
					+ title + "','" + genre + "'" + ",'" + year + "','" + duration + "','" + poster + "','"
					+ video + "')";
		}
		try {
			int flag = dh.Insupdel(sql);

			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (jel == "UP") {
					table_rowrefresh(title, genre, year, duration, poster, video);
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
		cards.show(cardPanel, "tabla");
	}
	private void table_rowrefresh(String title, String genre, String year, String duration, String poster,
			String video) {
		DefaultTableModel d1 = (DefaultTableModel) movtable.getModel();
		d1.setValueAt(title, myrow, 1);
		d1.setValueAt(genre, myrow, 2);
		d1.setValueAt(year, myrow, 3);
		d1.setValueAt(duration, myrow, 4);
		d1.setValueAt(poster, myrow, 5);
		d1.setValueAt(video, myrow, 6);	
	}
	private void clearFields() {		
		
		txtitle.setText("");
		txgenre.setText("");
		txyear.setText("");
		txduration.setText("");
		txposter.setText("");
		txvideo.setText("");
		rowid = "";
		myrow = 0;
	}
	private	int movdata_delete(){
		String sql ="delete from movies  where movieid =";
		Boolean error = false;
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) movtable.getModel();
		int sIndex = movtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}

//		if (dd.cannotdelete("select movieid from movies  where movieid ="+ iid)==true) {
//			 JOptionPane.showMessageDialog(null, "You can not delete this movie !");
//			 return flag;
//		}	
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag == 1)
				d1.removeRow(sIndex);
		}
		return flag;
	}

	 private void sqlgyart() {		 
		 String sql="";
		 String ss = txsearch.getText().trim().toLowerCase();
		 if (rname.isSelected()) {
			 sql = "lower(title) LIKE '%"+ ss + "%' order by title "
					+ " COLLATE NOCASE ASC";
		 }else {
			 sql = "genre LIKE '%"+ ss + "%' order by genre COLLATE NOCASE ASC";
		 }		  
		   table_update(sql);
	 }
	 private Boolean csvalidation(String title,String gerne, String year, String duration,
				String poster, String video) {
			Boolean ret = true;
			 ArrayList<String> err = new ArrayList<String>();
			
			 if (!cvad.ftitlevalid(title)) {
				 err.add(cvad.mess);			
			    	ret = false;
			}
			 if (!cvad.fgernevalid(gerne)) {
				 err.add(cvad.mess);			
			    	ret = false;
			}
			 if (!cvad.yearvalid(year)) {				
				 err.add(cvad.mess);			
		    	ret = false;
		}
			 if (!cvad.durationvalid(duration)) {				
				 err.add(cvad.mess);			
		    	ret = false;
		}
			
			 if (err.size() > 0) {
	             JOptionPane.showMessageDialog(null, err.toArray(),"Error message",1);					               
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
		//		textField.addFocusListener(dFocusListener);
				textField.setText("");
				textField.setDisabledTextColor(Color.magenta);
				return textField;
			}	
	
	public static void main(String args[]) {
		Addmovie dd = new Addmovie();	
		dd.setVisible(true);
	}
	CardLayout cards;
	JRadioButton rname, rtel;
	ButtonGroup btnGroup = new ButtonGroup();
	JPanel cardPanel, tPanel, ePanel;
	JLabel lbheader, lbpicture, lbtitle, lbgenre, lbyear, lbduration, lbvideo, lbposter;
	JTextField txsearch, txtitle, txgenre, txyear, txduration, txposter, txvideo;
	JButton btnsearch, btnclear, btnnew, btndelete, btnupdate, btnsave,btncancel;
	JScrollPane jScrollPane1;
	JTable movtable;

}
