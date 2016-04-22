package Logics;

/**
 * Class represents an appointment instance and contains methods to handle it
 * 
 * @author Aleksejs Loginovs
 *
 */
public class Appointment 
{
	private static final String[] fieldnames = 
	{
		"Place",
		"Date",
		"Time"
	};

	private String time;
	private int date;
	private long datetime;
	private String description;
	private String place;
	private Appointment previous;
	private Appointment next;
	
	/**
	 * Default constructor sets field values to their default values
	 */
	public Appointment()
	{
		next = null;
		date = Integer.parseInt("20160420");
		time = "0000";
		datetime = Long.parseLong(date + "" + time);
		description = "NOT_SPECIFIED";
		place = "NOT_SPECIFIED";
	}
	
	/**
	 * Used only for testing
	 * @param datetime 
	 */
	public Appointment(int date, String time)
	{
		next = null;
		this.date = date;
		this.time = time;
		datetime = Long.parseLong(date + "" + time);
		description = "NOT_SPECIFIED";
		place = "NOT_SPECIFIED";
	}
	
	/**
	 * Constructor that initialises the fields to the given values
	 * @param description description of the appointment
	 * @param place the place where appointment is held
	 * @param year year when appointment is held
	 * @param month month of the appointment
	 * @param day day of the appointment
	 * @param hour hour of the appointment
	 * @param minute minute of the appointment
	 */ 
	public Appointment(String description, String place, String year, String month, String day, String hour, String minute)
	{
		next = null;
		date = Integer.parseInt(year + "" + month + "" + day);
		time = hour + "" + minute;
		datetime = Long.parseLong(date + "" + time);
		this.description = description;
		this.place = place;
	}
	
	/**
	 * Prints the appointment details in the console
	 */
	public void printDetails()
	{
		System.out.println("------------------");
		System.out.println("Description: " + description);
		System.out.println("Place: " + place);
		System.out.println("Date: " + date);
		System.out.println("Time: " + time);
		System.out.println();
	}
	
	/**
	 * Method used to get the description of the appointment
	 * @return description description of the appointment
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Method used to get the place of the appointment
	 * @return place place of the appointment
	 */
	public String getPlace()
	{
		return place;
	}
	
	/**
	 * Method used to get the full date+time of the appointment
	 * @return date+time in one line
	 */
	public long getDatetime()
	{
		return datetime;
	}
	
	/**
	 * Method used to get the date of the appointment
	 * @return date in one line
	 */
	public int getDate()
	{
		return date;
	}
	
	/**
	 * Method used to get the time of the appointment
	 * @return time of the appointment
	 */
	public String getTime()
	{
		return time;
	}
	
	/**
	 * Method used to get the next appointment
	 * @return nextAppointment appointment that follows this appointment
	 */
	public Appointment getNext()
	{
		return next;
	}
	
	/**
	 * Method used to get the previous appointment
	 * @return previous appointment that precedes this appointment
	 */
	public Appointment getPrevious()
	{
		return previous;
	}
	
	/**
	 * Method used to change the appointment description to the one received
	 * @param description new description for the appointment
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * Method used to set date and time of the appointment
	 * @param year new year of the appointment
	 * @param month new month of the appointment
	 * @param day new day of the appointment
	 * @param hours represents the hour time of the appointment
	 * @param minutes represents the minute time of the appointment
	 */
	public void setDatetime(int year, int month, int day, String hours, String minutes)
	{
		this.date = Integer.parseInt(year + "" + month + "" + day);
		this.time = hours + "" + minutes;
		datetime = Integer.parseInt(date + "" + time);
	}
	
	/**
	 * Method used to set the next appointment
	 * @param nextAppointment appointment that will follow this appointment
	 */
	public void setNext(Appointment nextAppointment)
	{
		this.next = nextAppointment;
	}
	
	/**
	 * Method used to set the previous appointment
	 * @param previous appointment that precedes this appointment
	 */
	public void setPrevious(Appointment previous)
	{
		this.previous = previous;
	}
	
	/**
	 * Method used to get the names of the fields of this class that are going to be displayed in the table
	 * @return array of field names
	 */
	public static String[] getFieldNames()
	{
		return fieldnames;
	}
}
