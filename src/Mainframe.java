import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventObject;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;

public class Mainframe extends JFrame implements ActionListener {
	static JMenuBar mb;	  
    static JMenu x;  
    static JMenuItem m1, m2, m3;
    Hhelper hh = new Hhelper();
    
	Mainframe() {
		menumaker();
		initcomponents();
		this.getContentPane().setBackground(new Color(241, 241, 242));
		hh.iconhere(this);
	}

	private void initcomponents() {
		setSize(1100, 710);
		setLayout(null);
		setLocationRelativeTo(null);

		lblefttop = new JLabel();
		lblefttop.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/bfkep.png")));
//		lblefttop.setBounds(0, 0, 550, 340);
		add(lblefttop);

		lbleftbottom = new JLabel();
		lbleftbottom.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/bakep.png")));
		// lbleftbottom.setBounds(0, 340, 550, 340);
		add(lbleftbottom);

		lbrighttop = new JLabel();
		lbrighttop.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/jfkep.png")));
		// lbrighttop.setBounds(550, 0, 550, 340);
		add(lbrighttop);

		lbrightbottom = new JLabel();
		lbrightbottom.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/jakep.png")));
		// lbrightbottom.setBounds(550, 340, 550, 340);
		add(lbrightbottom);

		int x = -550;
		int y = -340;
		int y1 = 680;
		int y2 = 1100;
		int x1 = 1100;
		for (int i = 0; i < 10; i++) {
			lblefttop.setBounds(x, y, 550, 340);
			lbleftbottom.setBounds(x, y1, 550, 340);
			lbrighttop.setBounds(x1, y, 550, 340);
			lbrightbottom.setBounds(x1, y1, 550, 340);

			x = x + 55;
			y = y + 34;
			y1 = y1 - 34;
			x1 = x1 - 55;
			setVisible(true);
			try {
				Thread.sleep(40);
			} catch (Exception e) {
				System.out.println("Error:" + e);
			}
		}

		lbmainpic = new JLabel();
		lbmainpic.setIcon(new ImageIcon(ClassLoader.getSystemResource("images/cinemax.jpg")));
		lbmainpic.setBounds(0, 0, 1100, 680);
		add(lbmainpic);
		lblefttop.setVisible(false);
		lbrighttop.setVisible(false);
		lbleftbottom.setVisible(false);
		lbrightbottom.setVisible(false);

		ImageIcon seaticon = new ImageIcon(Mainframe.class.getResource("images/seatreservedx.png"));
		ImageIcon showicon = new ImageIcon(Mainframe.class.getResource("images/showtimex.png"));
		ImageIcon movieicon = new ImageIcon(Mainframe.class.getResource("images/wickedx.png"));
		ImageIcon cancelicon = new ImageIcon(Mainframe.class.getResource("images/cancelx.png"));

		mainToolbar = new JToolBar();
		mainToolbar.setBounds(175, 230, 725, 130);
		mainToolbar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		add(mainToolbar);

		btnseatres = new JButton(seaticon);
		btnseatres.setSize(160, 120);
		btnseatres.setToolTipText("Seat reservation");
		btnseatres.addActionListener(this);
		mainToolbar.add(btnseatres);
		mainToolbar.addSeparator();
		
		btncancel = new JButton(cancelicon);
		btncancel.setSize(160, 120);
		btncancel.setToolTipText("Cancel reservation");
		btncancel.addActionListener(this);
		mainToolbar.add(btncancel);
		mainToolbar.addSeparator();

		btnshows = new JButton(showicon);
		btnshows.setSize(160, 120);
		btnshows.setToolTipText("Show times");
		btnshows.addActionListener(this);
		mainToolbar.add(btnshows);
		mainToolbar.addSeparator();

		btnmovie = new JButton(movieicon);
		btnmovie.setSize(160, 120);
		btnmovie.setToolTipText("Movies");
		btnmovie.addActionListener(this);
		mainToolbar.add(btnmovie);
		mainToolbar.addSeparator();		

		lbmainpic.add(mainToolbar);

		addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				int x, y, d;
				x = 1000;
				y = 600;
				d = 10;
				while (x > 0 && y > 0) {
					setSize(x, y);
					x = x - 2 * d;
					y = y - d;
					setVisible(true);
					try {
						Thread.sleep(10);
					} catch (Exception e) {
						System.out.println("Error:" + e);
					}
				}
				dispose();
			}
		});

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btncancel) {
		   Rescancel re = new Rescancel();
		   re.setVisible(true);
		} else if (e.getSource() == btnseatres) {
			seatreservation re = new seatreservation();
			re.setVisible(true);
		} else if (e.getSource() == btnshows) {
			Addshowdata sh = new Addshowdata();
			sh.setVisible(true);
		} else if (e.getSource() == btnmovie) {
			Addmovie sh = new Addmovie();
			sh.setVisible(true);
		}
	}
	private void menumaker() {
		// create a menu bar		
		 mb = new JMenuBar();
		  x = new JMenu("Menu");
		  m1 = new JMenuItem("Exit");
		  m1.setActionCommand("exit");
	        m2 = new JMenuItem("Settings");
	        m2.setActionCommand("settings");
	        m3 = new JMenuItem("Prices");	    
	        m3.setActionCommand("prices");
	        x.add(m1);
	        x.add(m2);
	        x.add(m3);
	        mb.add(x);
	        setJMenuBar(mb);
	        MenuItemListener menuItemListener = new MenuItemListener();			
			m1.addActionListener(menuItemListener);
			m2.addActionListener(menuItemListener);
			m3.addActionListener(menuItemListener);
	}
	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String par = e.getActionCommand();
			if (par == "exit") {
				System.exit(0);
			} else if (par == "settings") {
				Settings se = new Settings();
				  se.setVisible(true);
			}else if (par == "prices") {
				Prices pr = new Prices();
				pr.setVisible(true);
			}
		}};

	public static void main(String[] args) {
		Mainframe me = new Mainframe();
	}

	JLabel lbmainpic, lblefttop, lbrighttop, lbleftbottom, lbrightbottom;
	JToolBar mainToolbar;
	JButton btnseatres, btnshows, btnmovie, btncancel;
	

}
