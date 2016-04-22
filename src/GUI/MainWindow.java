package GUI;
import Logics.Appointment;
import Logics.AppointmentList;
import Logics.ListLoader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.GridBagConstraints;
import java.awt.Dimension;

import java.util.ArrayList;

/**
 * Class represents the main program frame and contains methods used to handle it
 * @author arveon
 *
 */
public class MainWindow extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean visible;
	private AppointmentList list;
	ListSelectionModel model;
	
	private JPanel content;
	
	private JButton exit;
	private JButton save;
	
	private JButton addNew;
	private JButton edit;
	private JButton remove;
	
	private Table appTable;
	private JScrollPane tableScroll;
	
	/**
	 * Constructor used to create the window
	 * @param list list of the appointments loaded from file
	 */
	public MainWindow(AppointmentList list)
	{
		this.list = list;
		addWindowListener(new WinListen());
		buildWindow();
		visible = false;
		
		pack();
		setSize(new Dimension(456,489));
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Personal Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Method used to initialise all window components and add them to the frame
	 */
	public void buildWindow()
	{
		content = new JPanel();
		content.setLayout(new GridBagLayout());
		
		//initialise buttons
		exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(150,30));
		exit.addActionListener(new MainActionListener(this));
		save = new JButton("Save");
		save.setPreferredSize(new Dimension(150,30));
		save.addActionListener(new MainActionListener(this));
		
		addNew = new JButton("New");
		addNew.setPreferredSize(new Dimension(150,30));
		addNew.addActionListener(new MainActionListener(this));
		edit = new JButton("Edit");
		edit.setPreferredSize(new Dimension(150,30));
		edit.addActionListener(new MainActionListener(this));
		edit.setEnabled(false);
		remove = new JButton("Remove");
		remove.setPreferredSize(new Dimension(150,30));
		remove.addActionListener(new MainActionListener(this));
		remove.setEnabled(false);
		
		//SET UP THE TABLE
		//set up the table data array and fil it in
		
	
		appTable = new Table(getNewTableModel());
		appTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableScroll = new JScrollPane(appTable);
		tableScroll.setPreferredSize(new Dimension(450,400));
		//add listeners to the table
		appTable.addMouseListener(new TableClickListener());
		this.model = appTable.getSelectionModel();
		this.model.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent event)
			{
				if(appTable.getSelectionModel().isSelectionEmpty())
				{
					edit.setEnabled(false);
					remove.setEnabled(false);
				}
				else
				{
					edit.setEnabled(true);
					remove.setEnabled(true);
				}
			}
		});
		
		//add elements to the frame
		GridBagConstraints constr = new GridBagConstraints();
		constr.gridx = 0;
		constr.gridy = 0;
		content.add(addNew, constr);
		
		constr.gridx = 1;
		content.add(edit, constr);
		
		constr.gridx = 2;
		content.add(remove, constr);
		
		constr.fill = GridBagConstraints.BOTH;
		constr.gridx = 0;
		constr.gridwidth = 3;
		constr.gridy = 1;
		content.add(tableScroll, constr);
		
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridwidth = 1;
		constr.gridx = 2;
		constr.gridy = 2;
		content.add(exit,constr);
		
		constr.gridx = 1;
		content.add(save, constr);
		
		add(content);
	}
	
	/**
	 * Method returns the list of the appointments that should be used by the program
	 * @return the currently used list of appointments
	 */
	public AppointmentList getAppointmentList()
	{
		return list;
	}
	
	/**
	 * Method used to toggle the visibility of the main frame
	 */
	public void toggleVisible()
	{
		setVisible(!visible);
	}
	
	/**
	 * Method used to confirm if user really wants to exit the program
	 */
	public void exit()
	{
		int confirmation = 1;
		String message = "Are you sure that you want to exit? All unsaved changes will be lost";
		confirmation = JOptionPane.showConfirmDialog(null, message, "Exit confirmation", JOptionPane.YES_NO_OPTION);
		if(confirmation == 0)
		{
			System.exit(-1);
		}
	}
	
	/**
	 * Method used to confirm appointment removal and process it
	 */
	public void confirmAndRemove()
	{
		int confirmation = 1;
		String message =  "Are you sure you want to remove that appointment?";
		confirmation = JOptionPane.showConfirmDialog(null, message, "Delete confirmation", JOptionPane.YES_NO_OPTION);
		if(confirmation == 0)
		{
			Appointment removed = list.getAppointmentAt(appTable.getSelectedRow());
			list.removeAppointment(removed, list.getRoot());
		}
	}
	
	/**
	 * Method used to call the file saving process and show the error/success message
	 */
	public void save()
	{
		boolean success = ListLoader.saveList(list);
		if(success)
		{
			JOptionPane.showMessageDialog(null, "Your appointment list was successfully saved.");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sorry, the appointment list wasn't saved.");
		}
	}
	
	/**
	 * Class used to set the list of the appointments for this object
	 * @param list
	 */
	public void setList(AppointmentList list)
	{
		this.list = list;
	}
	
	/**
	 * Method used to update the table model of the table that displays the appointments
	 * @return new updated table model
	 */
	public DefaultTableModel getNewTableModel()
	{
		ArrayList<Appointment> applist = list.getAllAppointments();
		list.printAllAppointments();
		String[][] appDataList = new String[applist.size()][Appointment.getFieldNames().length];
		for(int i = 0; i < applist.size(); i++)
		{
			String date = Integer.toString(applist.get(i).getDate());
			appDataList[i][0] = applist.get(i).getPlace();
			appDataList[i][2] = applist.get(i).getTime().substring(0, 2) + ":" + applist.get(i).getTime().substring(2, 4);
			appDataList[i][1] = date.substring(6, 8) + "/" + date.substring(4,6) + "/" + date.substring(0, 4);
		}
		
		DefaultTableModel model = new DefaultTableModel(appDataList, Appointment.getFieldNames());
		
		return model;
	}
	
	/**
	 * Method used to update the table and refresh it
	 */
	public void updateTable()
	{
		appTable.setModel(getNewTableModel());
	}
	
	/**
	 * Class represents the JTable 
	 * @author Aleksejs Loginovs
	 */
	public class Table extends JTable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor used to initialise the table and set it's table model to the one received
		 * @param model
		 */
		public Table(DefaultTableModel model)
		{
			super();
			this.setModel(model);
		}
		
		@Override
		public boolean isCellEditable(int row, int column)
		{
			return false;
		}

	}
	
	/**
	 * Class used as a mouse listener and listens to mouse clicks in the table
	 * @author Aleksejs Loginovs
	 *
	 */
	public class TableClickListener implements MouseListener
	{
		/**
		 * Method gets called when one of the rows is clicked
		 */
		public void mousePressed(MouseEvent event)
		{
			JTable table = (JTable)event.getSource();
			Point mousePoint = event.getPoint();
			int row = table.rowAtPoint(mousePoint);
			if(event.getClickCount() == 2)
			{
				Appointment selectedApp = list.getAppointmentAt(row);
				JOptionPane.showMessageDialog(null,"Description: " + selectedApp.getDescription() + "\n" + "Place: " + selectedApp.getPlace() + "\nTime: " +selectedApp.getTime()
												+ "\nDate: " + selectedApp.getDate(), "Appointment - " + row, JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		//methods not used
		public void mouseReleased(MouseEvent event){}		
		public void mouseExited(MouseEvent event){}
		public void mouseClicked(MouseEvent event){}
		public void mouseEntered(MouseEvent event){}
	}
	
	/**
	 * Class used to listen to button clicks on the main frame
	 * 
	 * @author Aleksejs Loginovs
	 */
	public class MainActionListener implements ActionListener 
	{
		private MainWindow mainwindow;
		/**
		 * Constructor that 
		 * @param mainwindow instance of a main window class that is currently being used
		 */
		public MainActionListener(MainWindow mainwindow){super(); this.mainwindow = mainwindow;}
		
		/**
		 * Method used to listen to button clicks
		 */
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == exit)
			{
				mainwindow.exit();
			}
			else if(event.getSource() == save)
			{
				mainwindow.save();
			}
			else if(event.getSource() == addNew)
			{
				new AddAppointmentWindow(mainwindow);
			}
			else if(event.getSource() == remove)
			{
				confirmAndRemove();
				updateTable();
			}
			else if(event.getSource() == edit)
			{
				new EditAppointmentWindow(mainwindow, list.getAppointmentAt(appTable.getSelectedRow()));
			}
		}
	}
	
	/**
	 * Class used to listen to window manipulations
	 * @author arveon
	 *
	 */
	public class WinListen implements WindowListener
	{
		/**
		 * Method called when user attempts to close window and asks for his confirmation
		 */
		@Override
		public void windowClosing(WindowEvent e) 
		{
			MainWindow window = (MainWindow)e.getSource();
			window.exit();
		}

		//NOT USED METHODS
		@Override
		public void windowDeactivated(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent e){}

	}
}
