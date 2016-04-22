package GUI;

import Logics.AppointmentList;
import Logics.Appointment;

import javax.swing.JPanel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Class contains methods to handle adding the appointment
 * @author Aleksejs Loginovs
 */
public class AddAppointmentWindow
{
	private AppointmentList list;
	
	//window content
	private JPanel content;
	private JTextArea descArea;
	private JTextField placeField;
	private JLabel placeLabel;
	private JLabel startDateTimeLabel;
	private JLabel descLabel;
	private JScrollPane descScroll;
	
	private JComboBox<String> year;
	private JComboBox<String> month;
	private JComboBox<String> day;
	private JComboBox<String> hour;
	private JComboBox<String> minute;
	
	/**
	 * Default method that initialises all the content and shows the window
	 * @param window an instance of the main window frame
	 */
	public AddAppointmentWindow(MainWindow window)
	{
		this.list = window.getAppointmentList();
		content = new JPanel();
		content.setLayout(new GridBagLayout());
		
		descLabel = new JLabel("Description:");
		descArea = new JTextArea();
		descArea.setLineWrap(true);
		descArea.setWrapStyleWord(true);
		descScroll = new JScrollPane();
		descScroll.setViewportView(descArea);
		descScroll.setPreferredSize(new Dimension(200,150));
		
		placeLabel = new JLabel("Place:");
		placeField = new JTextField();
		placeField.setPreferredSize(new Dimension(180,20));
		
		startDateTimeLabel = new JLabel("Start date & time:");
		
		initializeTimeBoxes();
		
		addElementsToFrame();
		
		//show the window as a dialog window
		String[] options = {"Cancel", "Submit"};
		int res = JOptionPane.showOptionDialog(null, content, "Personal Manager - Add", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(res == 1)
		{
			//create a new appointments using variables
			String y = (String)year.getSelectedItem();
			String m = (String)month.getSelectedItem();
			String d = (String)day.getSelectedItem();
			String h =  (String)hour.getSelectedItem();
			String min = (String)minute.getSelectedItem();
			int msg = list.addAppointment(new Appointment(descArea.getText(), (String)placeField.getText(), y, m, d, h, min), list.getRoot());
			JOptionPane.showMessageDialog(null, AppointmentList.errors[msg]);
			if(msg == 1)
			{
				window.setList(list);
				window.updateTable();
			}
		}
	}
	
	/**
	 * Method is used to add existing elements on their designated positions on the panel
	 */
	public void addElementsToFrame()
	{
		JLabel colon2 = new JLabel(":");
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,5,0,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		content.add(placeLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,5,5,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		content.add(placeField, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,5,0,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridy = 4;
		constraints.gridwidth = 3;
		content.add(startDateTimeLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,5,5,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridwidth = 1;
		constraints.gridy = 5;
		content.add(year, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,5,5,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 1;
		constraints.gridy = 5;
		content.add(month, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,0,5,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 5;
		content.add(day, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,5,0,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 0;
		constraints.gridy = 6;
		content.add(hour, constraints);
		
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(0,0,5,0);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridy = 6;
		constraints.gridx = 1;
		content.add(colon2, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,0,5,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridx = 2;
		constraints.gridy = 6;
		content.add(minute, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(10,0,0,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.gridwidth = 2;
		constraints.gridy = 0;
		constraints.gridx = 4;
		content.add(descLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0,0,0,5);
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.gridheight = 7;
		constraints.gridx = 4;
		constraints.gridy = 1;
		content.add(descScroll, constraints);
	}
	
	/**
	 * Method used to initialise and fill in combo boxes used to set up time
	 */
	public void initializeTimeBoxes()
	{
		//set up year combobox
		//add elements to it
		Integer[] years = new Integer[100];
		String[] yearsStr = new String[100];
		for(int i = 0; i < years.length; i++)
		{
			years[i] = new Integer(2016 + i);
			yearsStr[i] = Integer.toString(years[i]);
		}
		year = new JComboBox<String>(yearsStr);
		
		//set up days combo box
		Integer[] days = new Integer[31];
		String[] daysStr = new String[31];
		for(int i = 0; i < days.length; i++)
		{
			days[i] = new Integer(i+1);
			daysStr[i] = Integer.toString(days[i]);
			if(daysStr[i].length() < 2)
			{
				daysStr[i] = "0" + daysStr[i];
			}
		}
		day = new JComboBox<String>(daysStr);
		day.setModel(new DefaultComboBoxModel<String>(daysStr));
		
		//set up months combo box
		Integer[] months = new Integer[12];
		String[] monthsStr = new String[12];
		for(int i = 0; i < months.length; i++)
		{
			months[i] = new Integer(i+1);
			monthsStr[i] = Integer.toString(months[i]);
			if(monthsStr[i].length() < 2)
			{
				monthsStr[i] = "0" + monthsStr[i];
			}
		}
		month = new JComboBox<String>(monthsStr);
		month.addItemListener(new ItemListener(){
			/**
			 * method is used to update day box values depending on the month selected
			 */
			public void itemStateChanged(ItemEvent event)
			{
				@SuppressWarnings("unchecked")
				JComboBox<String> month = (JComboBox<String>)event.getSource();
				String str = (String)month.getSelectedItem();
				int m = Integer.parseInt(str);
				int number = 31;
				if(m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12)
				{
					number = 31;
				}
				else if(m == 2)
				{
					number = 29;
				}
				else
				{
					number = 30;
				}
				//update the combo box information with a new array
				Integer[] days = new Integer[number];
				String[] daysStr = new String[number];
				for(int i = 0; i < days.length; i++)
				{
					days[i] = new Integer(i+1);
					daysStr[i] = Integer.toString(days[i]);
					if(daysStr[i].length() < 2)
					{
						daysStr[i] = "0"+daysStr[i];
					}
				}
				day.setModel(new DefaultComboBoxModel<String>(daysStr));
			}
		});
		
		//set up hours combo box
		Integer[] hours = new Integer[24];
		String[] hoursStr = new String[24];
		for(int i = 0; i < hours.length; i++)
		{
			hours[i] = new Integer(i);
			hoursStr[i] = "" + hours[i];
			if(hoursStr[i].length() < 2)
			{
				hoursStr[i] = "0"+hoursStr[i];
			}
		}
		hour = new JComboBox<String>(hoursStr);
		
		//set up minutes combo box
		Integer[] minutes = new Integer[59];
		String[] minutesStr = new String[59];
		for(int i = 0; i < minutes.length; i++)
		{
			minutes[i] = new Integer(i);
			minutesStr[i] = "" + minutes[i];
			if(minutesStr[i].length() < 2)
			{
				minutesStr[i] = "0" + minutesStr[i];
			}
		}
		minute = new JComboBox<String>(minutesStr);
	}	
}
