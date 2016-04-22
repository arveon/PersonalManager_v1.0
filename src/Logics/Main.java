package Logics;

import GUI.MainWindow;

/**
 * Class contains the main method used to launch the program
 * @author Aleksejs Loginovs
 *
 */
public class Main 
{
	public static void main(String[] args)
	{
		AppointmentList list = ListLoader.loadList();		
		
		System.out.println("Testing");
		MainWindow mainWindow = new MainWindow(list);
		mainWindow.toggleVisible();
	}
}
