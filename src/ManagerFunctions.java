
public interface ManagerFunctions {
	
	////////////////////////////////Catchers & Getters////////////////////////////////
	/*
	 * Methods to check if Undo or Redo functions can be used, returns true or false for this statement.
	 */
	public boolean checkUndo();
	public boolean checkRedo();
	
	/*
	 * Method to check if a random input (from many places in the system) is a undo(<) or a redo(>) and return the according value.
	 */
	public String randomInfoCatcher(String s);
	
	/*
	 * Method to catch the exceptions and errors from numerical input from the user when typing in the menus, return the valid option choose.
	 */
	public double numericalExceptionCatcher(int minOpt, int maxOpt);
	
	/* 
	 * Catcher for the employee type, preventing mistyping and returning the valid type.
	 */
	public String employeeTypeCatcher();
	
	/*
	 * Function to search for an employee index by name, if not find it, return null
	 */
	public int getEmployeeIndex(String name);
	
	/*
	 * Function to ask for an Employee by name or go back (returns -1) to Main Menu, receives a String for the question printed
	 */
	public int searchEmployee(String question);
	
	/*
	 * Tests if an Employee have to be paid this day or not. returns a boolean. 
	 */
	public boolean testPaymentDay(int month, int date, int weekDay, int lastDate, int lastMonthLastDate, int index);
	
	
	/////////////////////////////////Menu Options////////////////////////////////
	/*
	 * Method that prints the main menu and catches the option from the user, directing it to the other methods.
	 */
	public int mainMenuSelect();
	
	/*
	 * Function to create and add an Employee in the employees ArrayList.
	 */
	public void addEmployee();
	
	/*
	 * Method to remove an Employee by name from the system, keep asking for a name until the size of the employees list changes
	 */
	public void removeEmployee();
	
	/*
	 * For Hourly Employees only, the function search for the given employee, tests if it is really an Hourly Employee,
	 * than asks how many hours did him work and add the equivalent value to his currentSalary
	 */
	public void launchTimeCard();
	
	/*
	 * For Commissioned Employees only, the function search for the given employee, tests if it is really a Commissioned Employee,
	 * than asks how was the profit of the selling and add the equivalent commission to his currentSalary
	 */
	public void launchSellingResult();
	
	/*
	 * Method to launch a service tax to one employee, this tax will be deducted from the payment of this employee.
	 */
	public void launchServiceTax();
	
	/*
	 * Method that asks for a employee name and shows all this employee information,
	 * then edit it with the input given by the user. 
	 */
	public void editEmployee();
	
	/*
	 * Receives today's date and runs through the employees list searching employees that need to be paid. Prints the employees that were paid.
	 */
	public void runPayroll();
}
