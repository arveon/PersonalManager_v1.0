package Logics;

import java.util.ArrayList;

/**
 * Class represents the tree of objects and contains methods to handle them
 * 
 * @author Aleksejs Loginovs
 *
 */
public class AppointmentList 
{
	private Appointment root;
	
	public static String[] errors;
	
	/**
	 * Default class constructor that sets the root to null
	 */
	public AppointmentList()
	{
		root = null;
		initialiseErrors();
	}
	
	/**
	 * Class constructor that sets the root to the received value
	 * @param root
	 */
	public AppointmentList(Appointment root)
	{
		this.root = root;
		initialiseErrors();
	}
	
	/**
	 * Method is used to add appoitments to the list to the appropriate place in the list
	 * @param newAppointment represents the new appointment to be added
	 * @param currentApp the appointment from which to start the search (needed for the recursion, equal to root when just launched)
	 * @return error/success message id
	 */
	public int addAppointment(Appointment newApp, Appointment currentApp)
	{
		int adding = 2;
		if(currentApp == null)//if the list is empty, set it's root to the new appointment
		{
			root = newApp;
			adding = 1;
		}
		else
		{
			long curTime = currentApp.getDatetime();//time of the currently processed appointment
			long newTime = newApp.getDatetime();//time of the appointment that is being added
			if(newTime < curTime)//if new is earlier than the appointment currently being processed
			{	
				if(currentApp == root)//if earlier than root, add instead of root and update root
				{
					root.setPrevious(newApp);
					newApp.setNext(root);
					root = newApp;
					adding = 1;
				}
				else//if earlier than usual appointment, place on the place of that usual appointment
				{
					currentApp.getPrevious().setNext(newApp);//connect 'first' to new
					currentApp.setPrevious(newApp);//connect 'current' to new
					newApp.setPrevious(currentApp.getPrevious());//connect 'new' to 'first'
					newApp.setNext(currentApp);//connect 'new' to 'current'
					adding = 1;
				}
			}
			else if(newTime > curTime)//if new is later than the appointment currently being processed
			{
				//if reached the end of the list, add to the end of the list
				if(currentApp.getNext() == null)
				{
					currentApp.setNext(newApp);
					newApp.setPrevious(currentApp);
					adding = 1;
				}
				else//if didn't reach the end of the list, continue traversing
				{
					adding = addAppointment(newApp, currentApp.getNext());
				}
			}
			else//if there is already appointment at that time, send an error message
			{
				adding = 3;
			}
		}
		return adding;
	}
	
	/**
	 * Method is used to remove the old appointment from the list and add a new one with updated fields
	 * @param oldAppointment appointment that should be in the list and is about to be replaced
	 * @param newAppointment appointment that should replace the existing one
	 * @return error/success message id
	 */
	public int editAppointment(Appointment oldAppointment, Appointment newAppointment)
	{
		int removing = 8;
		int adding = 2;
		int editing = 5;
		removing = removeAppointment(oldAppointment, root);
		adding = addAppointment(newAppointment, root);
		
		editing = Integer.parseInt(adding + "" + removing);
		if(editing!=17)
		{
			editing = 6;
		}
		else
		{
			editing = 4;
		}
		
		return editing;
	}
	
	/**
	 * Class removes the given appointment
	 * @param toRemove appointment to be removed
	 * @param currentApp always starts as root, required for recursion
	 * @return error/success message id
	 */
	public int removeAppointment(Appointment toRemove, Appointment currentApp)
	{
		int removing = 8;
		
		if(currentApp == null)
		{
			removing = 9;
		}
		else
		{
			if(currentApp.getDatetime() == toRemove.getDatetime())
			{
				if(currentApp!=root)
				{
					if(currentApp.getNext() == null)
					{
						currentApp.getPrevious().setNext(currentApp.getNext());
					}
					else
					{
						currentApp.getPrevious().setNext(currentApp.getNext());
						currentApp.getNext().setPrevious(currentApp.getPrevious());
					}
				}
				else
				{
					root = currentApp.getNext();
				}
				removing = 7;
			}
			else
			{
				System.out.println("Traversed");
				removing = removeAppointment(toRemove, currentApp.getNext());
			}
		}
		return removing;
	}
	
	
	/**
	 * Method checks if the appointment list is empty or not
	 * @return true if there are no appointments in the list, false if there are appointments
	 */
	public boolean isListEmpty()
	{
		if(root == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Method returns an arraylist of all appointments
	 * @return arraylist of all appointments in the list
	 */
	public ArrayList<Appointment> getAllAppointments()
	{
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		Appointment curApp = root;
		while(curApp!=null)
		{
			list.add(curApp);
			curApp = curApp.getNext();
		}
		
		return list;
	}
	
	/**
	 * Method used to get a apointment at the given index
	 * @param index number of the appointment
	 * @return an appointment
	 */
	public Appointment getAppointmentAt(int index)
	{
		Appointment app;
		
		Appointment curApp = root;
		int i = 0;
		while(i < index)
		{
			curApp = curApp.getNext();
			i++;
		}
		
		if(curApp == root && index != 0)
		{
			app = null;
		}
		else
		{
			app = curApp;
		}
		return app;
	}
	
	/**
	 * Prints all the appointments in the list in the console
	 */
	public void printAllAppointments()
	{
		Appointment curApp = root;
		
		if(isListEmpty())
		{
			System.out.println("The list is empty");
			return;
		}
		
		while(curApp!=null)
		{
			curApp.printDetails();
			curApp = curApp.getNext();
		}
	}
	
	/**
	 * Method used to display error messages in console
	 * @param errorNumber message to be displayed
	 */
	public void displayMessage(int errorNumber)
	{
		try
		{
			System.out.println(AppointmentList.errors[errorNumber]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			displayMessage(4);
		}
	}
	
	/**
	 * Method used to get root of the list
	 * @return root of the list
	 */
	public Appointment getRoot()
	{
		return root;
	}
	
	/**
	 * Method is used to initialise all error messages to their default values
	 */
	public void initialiseErrors()
	{
		errors = new String[20];
		
		errors[0] = "Displaying error - no such error";
		
		errors[1] = "Adding success - appointment was successfully added.";
		errors[2] = "Adding error - variable not updated.";
		errors[3] = "Adding error - timeslot is taken.";
		
		errors[4] = "Editing success - appointment was successfully edited";
		errors[5] = "Editing error - variable not updated";
		errors[6] = "Editing error - couldn't edit the appointment";
		
		errors[7] = "Removing success - appointment was successfully removed";
		errors[8] = "Removing error - variable not updated";
		errors[9] = "Removing error - couldn't find the appointment to remove";
		
		
	}
}
