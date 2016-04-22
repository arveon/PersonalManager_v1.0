package Logics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;

import java.util.ArrayList;

/**
 * This class handles list loading and saving
 * 
 * @author Aleksejs Loginovs
 */
public class ListLoader 
{
	/**
	 * Method used to save given appointment list to the default file
	 * @param list represents a list of appointments to be saved
	 * @return true if saving was successful and false if it was unsuccessful
	 */
	public static boolean saveList(AppointmentList list)
	{
		boolean success = false;
		String filePath  = "appList.list";
		
		FileOutputStream stream = null;
		PrintWriter writer = null;
		
		try
		{
			stream = new FileOutputStream(filePath, false);
			writer = new PrintWriter(stream);
			ArrayList<Appointment> appArray = list.getAllAppointments();
			
			for(Appointment temp: appArray)
			{
				writer.println("Description: " + temp.getDescription());
				writer.println("Place: " + temp.getPlace());
				writer.println("Date: " + temp.getDate());
				writer.println("Time: " + temp.getTime());
			}
			
			writer.close();
			success = true;
		}
		catch(IOException e)
		{
			success = false;
		}
		
		return success;
	}
	
	/**
	 * The method is used to load appointment list from the default load file
	 * @return the list of appointments from file or null if there are none
	 */
	public static AppointmentList loadList()
	{
		AppointmentList list = new AppointmentList();
		String filePath = "appList.list";
		
		FileReader stream = null;
		BufferedReader reader = null;
		
		File file = new File(filePath);
		if(file.exists())
		{
			try
			{
				stream = new FileReader(filePath);
				reader = new BufferedReader(stream);
				
				String line = reader.readLine();
				while(line != null)
				{
					String[] data;
					String description = "EMPTY";
					String place = "EMPTY";
					String date = "00000000";
					String time = "0000";
					if(line.substring(0, 11).equals("Description"))
					{
						data = line.split(": ");
						if(data.length >1)
						{
							description = data[1];
						}
						line = reader.readLine();
					}
					
					if(line.substring(0,5).equals("Place"))
					{
						data = line.split(": ");
						if(data.length > 1)
						{
							place = data[1];
						}
						line = reader.readLine();
					}
					
					if(line.substring(0,4).equals("Date"))
					{
						data = line.split(": ");
						if(data.length > 1)
						{
							date = data[1];
						}
						line = reader.readLine();
					}
					
					if(line.substring(0,4).equals("Time"))
					{
						data = line.split(": ");
						if(data.length > 1)
						{
							time = data[1];
						}
						line = reader.readLine();
					}
					list.addAppointment(new Appointment(description, place, date.substring(0, 4), date.substring(4,6), date.substring(6,8), time.substring(0, 2), time.substring(2,4)), list.getRoot());
				}
			}
			catch(IOException e)
			{
				System.out.println("File wasnt' loaded");
			}
		}
		
		
		
		return list;
	}
}
