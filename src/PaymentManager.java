import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class PaymentManager implements ManagerFunctions{
	
	//Data
	ArrayList<Employee> employees = new ArrayList<Employee>();
	int numberOfEmployees = 0;
	int syndicateMembers = 0;
	Scanner scanner = new Scanner(System.in);
	int undoCounter = 0;
	int redoCounter = 0;
	boolean canRedo = false;
	boolean lastWasUndo = false;
	boolean lastWasRedo = false;
	Stack primaryStack = new Stack();
	Stack secondaryStack = new Stack();
	
	//Constructor
	public PaymentManager(){}
	
	//Methods
	public boolean checkUndo(){
		if(!this.primaryStack.isEmpty()){
			this.secondaryStack.push(this.primaryStack.pop());
			this.lastWasUndo = true;
			this.lastWasRedo = false;
			this.undoCounter++;
			return true;
		}else{
			return false;
		}
	}
	public boolean checkRedo(){
		if(this.lastWasUndo || this.lastWasRedo && this.undoCounter >= (this.redoCounter+1)){
			this.primaryStack.push(this.secondaryStack.pop());
			this.lastWasRedo = true;
			this.lastWasUndo = false;
			this.redoCounter++;
			return true;
		}else{
			return false;
		}
	}
	
	public String randomInfoCatcher(String s){
		if(s.equals("<") && this.checkUndo()){
			return "";
		}else if(s.equals(">") && this.checkRedo()){
			return (String)this.primaryStack.peek();
		}else{
			if(s.equals("<") || s.equals(">")){
				System.out.println("You can't use this operator now!\n");
				return "error";
			}
			this.lastWasRedo = false;
			this.lastWasUndo = false;
			return s;
		}
	}
	
	public double numericalExceptionCatcher(int minOpt, int maxOpt){
		if(minOpt >= maxOpt){
            return -1;
        }
		String undoRedoChecker;
        double option = -1;
        while(option < minOpt || option > maxOpt){
            try{
            	undoRedoChecker = this.scanner.nextLine();
            	if(undoRedoChecker.equals("<") && this.checkUndo()){
        			return -1;
        		}else if(undoRedoChecker.equals(">") && this.checkRedo()){
        			return Double.parseDouble(((String)this.primaryStack.peek()));
        		}else if(undoRedoChecker.equals("error")){
        			return 0;
        		}
                
            	option = Double.parseDouble(undoRedoChecker);
                
            } catch(Exception e){
                option = -1;
                System.out.println("Please, select an option between "+minOpt+" & "+maxOpt+"\n");
                continue;
            } if(option < minOpt || option > maxOpt){
                option = -1;
                System.out.println("Please, select an option between "+minOpt+" & "+maxOpt+"\n");
            }
        }
        return option;
	}
	
	public String employeeTypeCatcher(){
		String type = this.scanner.nextLine().toLowerCase();
		
		while(!type.equals("hourly") && !type.equals("salaried") && !type.equals("commissioned")){
			if(type.equals("<") && this.checkUndo()){
				return "";
			}else if(type.equals(">") && this.checkRedo()){
				return (String)this.primaryStack.peek();
			}
			
			System.out.println("Please type a valid type (Hourly, Salaried or Commissioned): ");
			type = this.scanner.nextLine().toLowerCase();
		}
		this.lastWasRedo = false;
		this.lastWasUndo = false;
		
		return type;
	}
	
	public int getEmployeeIndex(String name){
		String currentName = null;
		
		for(int i = 0; i < this.employees.size(); i++){
			currentName = employees.get(i).getName().toLowerCase();

			if(currentName.equals(name)){
				return i;
			}
		}
		
		return -1;
	}
	
	public int searchEmployee(String question){
		
		int index = -1;
		String name;
		System.out.print(question);
		name = this.scanner.nextLine().toLowerCase();
		index = getEmployeeIndex(name);
		
		while(index == -1){
			System.out.println("Please, type a valid name or type 'back' to go back to the Main Menu.");
			name = this.scanner.nextLine().toLowerCase();
			if(name.equals("back")){
				return -1;
			}
			index = getEmployeeIndex(name);
		}
		
		return index;
	}
	
	public boolean testPaymentDay(int month, int date, int weekDay, int lastDate, int lastMonthLastDate, int index){
		Employee e = this.employees.get(index);
		String s = e.getPaymentAgenda();
		String[] agenda = s.split(" ");
		
		int lastDayPaid = e.getLastDayPaid();
		int lastMonthPaid = e.getLastMonthPaid();
		
		//Salaried case (monthly, date)
		if(agenda.length == 2){
			if(agenda[1].equals("$") && date == lastDate && lastMonthPaid != month){
				return true;
			}else if(!agenda[1].equals("$") && date == Integer.parseInt(agenda[1])){
				return true;
			}
		}
		//Hourly & Commissioned case (weekly, week interval, day of week)
		else{
			int interval = -1;
			if(Integer.parseInt(agenda[1]) == 1){
				interval = 1;
			}else{
				interval = 2;
			}
			
			if((lastDayPaid + (7*interval)) == date || ((lastDayPaid + (7*interval)) - lastMonthLastDate) == date || lastDayPaid == -1){
				if(agenda[2].toLowerCase().equals("friday") && weekDay == 6){
					return true;
				}else if(agenda[2].toLowerCase().equals("thursday") && weekDay == 5){
					return true;
				}else if(agenda[2].toLowerCase().equals("wednesday") && weekDay == 4){
					return true;
				}else if(agenda[2].toLowerCase().equals("tuesday") && weekDay == 3){
					return true;
				}else if(agenda[2].toLowerCase().equals("monday") && weekDay == 2){
					return true;
				}else if(agenda[2].toLowerCase().equals("sunday") && weekDay == 1){
					return true;
				}else if(agenda[2].toLowerCase().equals("saturday") && weekDay == 7){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public int mainMenuSelect(){
		int option;
		System.out.println("\n===============================\n"
				         + "Welcome to the Payment Manager!\n"
				         + "===============================\n"
				         + "What would you like to do?\n\n"
				         + "1) Add Employee\n"
				         + "2) Remove Employee\n"
				         + "3) Launch Timecard\n"
				         + "4) Launch Selling Result\n"
				         + "5) Launch Service Tax\n"
				         + "6) Edit Employee Details\n"
				         + "7) Run Payroll\n"
				         + "8) Edit Payment Agenda\n"
				         + "9) Exit Manager\n");
		option = (int)numericalExceptionCatcher(1,10);
		return option;
	}
	
	public void addEmployee(){
		String name = "";
		String address = "";
		String type = "";
		int paymentMethod = -1;
		double syndicateTax = 0;
		int syndicateId = -1;
		this.numberOfEmployees++;
		int id = this.numberOfEmployees;
		int finished = 0;
		
		//Receive all information
		System.out.print("Please, type the informations about the new Employee and hit ENTER or type < to undo or > to redo:\n");
		while(finished != 99){
			switch(finished){
				case 0:
					System.out.print("Name: ");
					name = this.randomInfoCatcher(this.scanner.nextLine());
					if(!name.equals("") && !name.equals("error")){
						this.primaryStack.push(name);
						finished++;
					}
					break;
				
				case 1:
					System.out.print("Address: ");
					address = this.randomInfoCatcher(this.scanner.nextLine());
					if(address.equals("")){
						finished--;
					}else if(!address.equals("error")){
						this.primaryStack.push(address);
						finished++;
					}
					break;
				
				case 2:
					System.out.print("Type: ");
					type = employeeTypeCatcher();
					if(type.equals("")){
						finished--;
					}else if(!type.equals("error")){
						this.primaryStack.push(type);
						finished++;
					}
					break;
				
				case 3:
					System.out.print("Payment Method: \n"
							   + "1) Send Paycheck Home.\n"
							   + "2) Give Paycheck in Hands.\n"
							   + "3) Bank Deposit.\n");
					paymentMethod = (int)numericalExceptionCatcher(1,3);
					if(paymentMethod == -1){
						finished--;
					}else if(paymentMethod != 0){
						this.primaryStack.push(paymentMethod);
						finished++;
					}
					break;
				
				case 4:
					System.out.print("Is he going to be part of the Syndicate?\n"
							       + "1) Yes\n"
							       + "2) No\n");
					double yOrN = numericalExceptionCatcher(1,2);
					if(yOrN != 0){
						if(yOrN == 1){
							this.syndicateMembers++;
							syndicateId = this.syndicateMembers;
							this.primaryStack.push(syndicateId);
							finished = 5;
						}else if(yOrN == 2){
							finished = 6;
						}else{
							finished--;
						}
					}
					break;
				
				case 5:
					System.out.print("How much is the Syndicate Tax for this Employee? ");
					syndicateTax = (double)(numericalExceptionCatcher(1,1000));
					if(syndicateTax == -1){
						finished--;
					}else if(syndicateTax != 0){
						this.primaryStack.push(syndicateTax);
						finished++;
					}
					break;
				
				//Create the kind of Employee according to Type and generate IDs
				case 6:
					if(type.equals("hourly")){
						double salaryPerHour = 0;
						System.out.print("How much is the Salary per Hour of this Employee? ");
						salaryPerHour = (double)(numericalExceptionCatcher(1,1000000));
						if(salaryPerHour == -1){
							if(syndicateId != -1){
								finished--;
							}else{
								finished -= 2;
							}
						}else if(salaryPerHour != 0){
							Hourly h = new Hourly(name, address, type, salaryPerHour, paymentMethod, "weekly 1 friday", syndicateId, id);
							if(h.getSyndicateId() != -1){
								h.setSyndicateTax(syndicateTax);
							}
							employees.add(h);
							finished = 99;
						}
					}else if(type.equals("salaried")){
						double salary = 0;
						System.out.print("How much is the Salary of this Employee? ");
						salary = (double)(numericalExceptionCatcher(1,1000000));
						if(salary == -1){
							if(syndicateId != -1){
								finished--;
							}else{
								finished -= 2;
							}
						}else if(salary != 0){
							Salaried s = new Salaried(name, address, type, salary, paymentMethod, "monthly $", syndicateId, id);
							if(s.getSyndicateId() != -1){
								s.setSyndicateTax(syndicateTax);
							}
							employees.add(s);
							finished = 99;
						}
					}else{
						double salary = 0;
						double commission = 0;
						System.out.print("How much is the Salary of this Employee? ");
						salary = (double)(numericalExceptionCatcher(1,1000000));
						if(salary == -1){
							if(syndicateId != -1){
								finished--;
							}else{
								finished -= 2;
							}
						}else if(salary != 0){
							System.out.print("How much is the commission of this Employee? (0-100%) ");
							commission = (double)(numericalExceptionCatcher(0,100));
							if(commission != -1){
								Commissioned c = new Commissioned(name, address, type, salary, commission, paymentMethod, "weekly 2 friday", syndicateId, id);
								if(c.getSyndicateId() != -1){
									c.setSyndicateTax(syndicateTax);
								}
								employees.add(c);
								finished = 99;
							}
						}
					}
				break;
			}
		}
		this.lastWasRedo = false;
		this.lastWasUndo = false;
		this.primaryStack.empty();
		this.secondaryStack.empty();
		this.undoCounter = 0;
		this.redoCounter = 0;
		System.out.println("Employee " + name + " added successfully!\n\n");
	}
	
	public void removeEmployee(){		
		int index = searchEmployee("Which Employee do you want to remove? ");
		
		if(index == -1){
			return;
		}
		
		String name = this.employees.get(index).getName();
		
		this.employees.remove(index);
		System.out.println(name + " was successfully removed from the Employees list!");
		return;
	}
	
	public void launchTimeCard(){
		int index = searchEmployee("For which Employee do you want to Launch a Timecard? ");
		
		if(index == -1){
			return;
		}
	
		if(!this.employees.get(index).getClass().equals(Hourly.class)){
			System.out.println("This Employee is not an Hourly Employee, so he cannot use Timecards.");
			return;
		} else {
			System.out.print("How many hours did "+employees.get(index).getName()+" worked today? ");
			int hours = (int)numericalExceptionCatcher(1,24);
			((Hourly)this.employees.get(index)).addCurrentSalary(hours);
			
			System.out.println(employees.get(index).getName()+"'s current salary increased!");
		}
			
	}
	
	public void launchSellingResult(){
		int index = searchEmployee("For which Employee do you want to Launch a Selling Result? ");
		
		if(index == -1){
			return;
		}
	
		if(!this.employees.get(index).getClass().equals(Commissioned.class)){
			System.out.println("This Employee is not a Commissioned Employee, so he cannot launch Selling Results.");
			return;
		} else {
			System.out.print("How much was the profit of this selling? ");
			double profit = numericalExceptionCatcher(1,1000000000);
			((Commissioned)this.employees.get(index)).addProfit(profit);
			
			System.out.println(employees.get(index).getName()+"'s current salary increased!");
		}
		
	}
	
	public void launchServiceTax(){
		int index = searchEmployee("For which Employee do you want to Launch a Service Tax? ");
		
		if(index == -1){
			return;
		}
		
		System.out.print("How much is the Tax to be added to the Employees account? ");
		double tax = numericalExceptionCatcher(1,100000);
		this.employees.get(index).addServiceTax(tax);
		
		System.out.println("The tax was successfully added to the Employee account!");
	}
	
	public void editEmployee(){
		int index = searchEmployee("Which Employee do you want to edit? ");
		int maxOption;
		Employee e = this.employees.get(index);
		
		if(index == -1){
			return;
		}
		
		System.out.println("\nThis is the current data from this Employee: \n\n"
				+ e.toString());
		
		String question = "\n\n======================================\n"
				+ "Which information do you want to edit?\n"
				         + "1) Name\n"
				         + "2) Address\n"
				         + "3) Type\n"
				         + "4) Payment Method\n"
				         + "5) Syndicate Tax\n";
		if(e.getClass().equals(Hourly.class)){
			question += "6) Salary/Hour\n"
					  + "7) Current Salary\n"
					  + "8) Back to Main Menu\n";
			maxOption = 8;
		}else if(e.getClass().equals(Commissioned.class)){
			question += "6) Salary\n"
					  + "7) Commission\n"
					  + "8) Current Salary\n"
					  + "9) Back to Main Menu\n";
			maxOption = 9;
		}else{
			question += "6) Salary\n"
					  + "7) Back to Main Menu\n";
			maxOption = 7;
		}
		
		System.out.print(question);
		
		int option = (int)numericalExceptionCatcher(1,maxOption);
		
		if(option == maxOption){
			return;
		}
		
		System.out.println("\nType the new data for this information: ");
		//General Questions
		switch(option){
			//Name
			case 1:
				e.setName(this.scanner.nextLine());
				break;
			//Address	
			case 2:
				e.setAddress(this.scanner.nextLine());
				break;
			//Type	
			case 3:
				String newType = employeeTypeCatcher();
				
				//Copy to new Object
				String name = e.getName();
				String address = e.getAddress();
				int paymentMethod = e.getPaymentMethod();
				double syndicateTax = e.getSyndicateTax();
				int syndicateId = e.getSyndicateId();
				int id = e.getId();
				
				//Ask about new information
				System.out.println("Now we need to set some other infos: ");
				if(newType.equals("salaried")){
					System.out.print("How much will be the new Salary of this Employee? ");
					double salary = numericalExceptionCatcher(1,10000000);
					
					this.employees.remove(index);
					Salaried s = new Salaried(name, address, newType, salary, paymentMethod, "mensal $", syndicateId, id);
					if(s.getSyndicateId() != -1){
						s.setSyndicateTax(syndicateTax);
					}
					this.employees.add(s);
				}else if(newType.equals("hourly")){
					System.out.print("How much will be the new Salary per Hour of this Employee? ");
					double salaryPerHour = numericalExceptionCatcher(1,10000000);
					
					this.employees.remove(index);
					Hourly h = new Hourly(name, address, newType, salaryPerHour, paymentMethod, "semanal 1 sexta", syndicateId, id);
					if(h.getSyndicateId() != -1){
						h.setSyndicateTax(syndicateTax);
					}
					this.employees.add(h);
				}else{
					System.out.print("How much will be the new Salary of this Employee? ");
					double salary = numericalExceptionCatcher(1,10000000);
					
					System.out.print("How much will be the new Commission of this Employee? (0-100%)");
					double commission = numericalExceptionCatcher(0,100);
					
					this.employees.remove(index);
					Commissioned c = new Commissioned(name, address, newType, salary, commission, paymentMethod, "semanal 2 sexta", syndicateId, id);
					if(c.getSyndicateId() != -1){
						c.setSyndicateTax(syndicateTax);
					}
					this.employees.add(c);
				}
				break;
			//Payment Method	
			case 4:
				System.out.print("Payment Method: \n"
						   + "1) Send Paycheck Home.\n"
						   + "2) Give Paycheck in Hands.\n"
						   + "3) Bank Deposit.\n");
				int newPaymentMethod = (int)numericalExceptionCatcher(1,3);
				e.setPaymentMethod(newPaymentMethod);
				break;
			//Syndicate Tax:	
			case 5:
				if(e.getSyndicateId() == -1){
					System.out.println("This Employee is part of no Syndicate.");
				}else{
					double newTax = numericalExceptionCatcher(0,1000);
					e.setSyndicateTax(newTax);
				}
				break;
		}
		
		//Salaried
		if(maxOption == 7){
			if(option == 6){
				double newSalary = numericalExceptionCatcher(0,10000000);
				((Salaried)e).setSalary(newSalary);
			}
		}
		//Hourly
		else if(maxOption == 8){
			if(option == 6){
				double newSalary = numericalExceptionCatcher(0,10000000);
				((Hourly)e).setSalaryPerHour(newSalary);
			}else{
				double newCurrentSalary = numericalExceptionCatcher(0,10000000);
				((Hourly)e).setCurrentSalary(newCurrentSalary);
			}
		}
		//Commissioned
		else{
			if(option == 6){
				double newSalary = numericalExceptionCatcher(0,10000000);
				((Commissioned)e).setSalary(newSalary);
			}else if(option == 7){
				double newCommission = numericalExceptionCatcher(0,100);
				((Commissioned)e).setCommission(newCommission);
			}else{
				double newCurrentSalary = numericalExceptionCatcher(0,10000000);
				((Commissioned)e).setCurrentSalary(newCurrentSalary);
			}
		}
		
		return;
	}
	
	
	public void runPayroll(){
		Set<Integer> MonthsWith30Days = new HashSet<Integer>();
		MonthsWith30Days.add(1); MonthsWith30Days.add(3); MonthsWith30Days.add(5); MonthsWith30Days.add(7);
		MonthsWith30Days.add(8); MonthsWith30Days.add(10); MonthsWith30Days.add(12);
		
		Set<Integer> MonthsWith31Days = new HashSet<Integer>();
		MonthsWith31Days.add(4); MonthsWith31Days.add(6);
		MonthsWith31Days.add(9); MonthsWith31Days.add(11);
		
		System.out.println("In which month are we? (1-12)");
		int month = (int)numericalExceptionCatcher(1,12);
		System.out.println("What is the date is today?");
		
		int date;
		int lastDay;
		int lastMonthLastDay;
		
		//Get date and lastDay
		if(MonthsWith30Days.contains(month)){
			 date = (int)numericalExceptionCatcher(1,30);
			 lastDay = 30;
		}else if(MonthsWith31Days.contains(month)){
			 date = (int)numericalExceptionCatcher(1,31);
			 lastDay = 31;
		}else{
			//February Case
			 date = (int)numericalExceptionCatcher(1,28);
			 lastDay = 28;
		}
		
		//Get lastMonthLastDay (The last day of the previous month)
		if(MonthsWith30Days.contains(month-1)){
			 lastMonthLastDay = 30;
		}else if(MonthsWith31Days.contains(month-1)){
			 lastMonthLastDay = 31;
		}else{
			//February Case
			 lastMonthLastDay = 28;
		}
		
		System.out.println("What is today's day of the week?\n"
						 + "1) Sunday\n"
						 + "2) Monday\n"
						 + "3) Tuesday\n"
						 + "4) Wednesday\n"
						 + "5) Thursday\n"
						 + "6) Friday\n"
						 + "7) Saturday");
		
		int weekDay = (int)numericalExceptionCatcher(1,7);
		
		boolean noPayments = true;
		Employee e = null;
		
		System.out.println("Paying Employees... \n-----------------------------------------------");
		for(int i = 0; i < this.employees.size(); i++){
			if(testPaymentDay(month, date, weekDay, lastDay, lastMonthLastDay, i)){
				e = this.employees.get(i);
				noPayments = false;
				String s = e.getName()+" paid, ";
				double payment = 0;
				//Get the payment depending on employee type
				if(e.getClass().equals(Salaried.class)){
					payment = ((Salaried)e).getSalary() - e.getServiceTaxes() - e.getSyndicateTax();
				}else if(e.getClass().equals(Hourly.class)){
					payment = ((Hourly)e).getCurrentSalary() - e.getServiceTaxes() - e.getSyndicateTax();
					((Hourly)e).resetCurrentSalary();
				}else{
					payment = ((Commissioned)e).getCurrentSalary() + ((Commissioned)e).getSalary() - e.getServiceTaxes() - e.getSyndicateTax();
					((Commissioned)e).resetCurrentSalary();
				}
				
				//Pay with payment method
				if(e.getPaymentMethod() == 1){
					s += "Paycheck of $" + payment + " sent home.";
				}else if(e.getPaymentMethod() == 2){
					s += "Paycheck of $" + payment + " delivered on hands.";
				}else{
					s += "Bank deposit of $" + payment + " made.";
				}
				
				//Set Last Day/Month Paid
				e.setLastDayPaid(date);
				e.setLastMonthPaid(month);
				
				System.out.println(s + "\n-----------------------------------------------");
			}
		}
		
		if(noPayments){
			System.out.println("No Payments to be made today.");
		}
	}
	
	public void editPaymentAgenda(){
		int index = searchEmployee("From which Employee do you want to edit his Payment Agenda? ");
		
		if(index == -1){
			return;
		}
		
		Employee e = this.employees.get(index);
		int option = -1;
		
		System.out.println("Currently this is the Payment Agenda from " + e.getName() + ": \n" + e.getPaymentAgenda());
		System.out.print("This is the possibility of change for this kind of Employee, if you want to change more, please, edit the type of Employee:");
		if(e.getClass().equals(Salaried.class)){
			System.out.println("1) Month Date.\n"
							+  "2) Back.");
			option = (int)numericalExceptionCatcher(1,2);
			
			if(option == 1){
				System.out.println("Type the new Payment Month Date for this Employee: ");
				option = (int)numericalExceptionCatcher(1,31);
				if(option == 30 || option == 31){
					e.setPaymentAgenda("monthly $");
				}else{
					e.setPaymentAgenda("monthly " + option);
				}
			}else{
				return;
			}
			
		}else{
			System.out.println("1) Day of the Week.\n"
							+  "2) Back.");
			
			option = (int)numericalExceptionCatcher(1,2);
			
			if(option == 1){
				System.out.print("Type the new Payment Day of The Week for this Employee [1(Sunday) to 7(Saturday)]: ");
				option = (int)numericalExceptionCatcher(1,7);
				String dayOfWeek = null;
				switch(option){
					case 1:
						dayOfWeek = "sunday";
					case 2:
						dayOfWeek = "monday";
					case 3:
						dayOfWeek = "tuesday";
					case 4:
						dayOfWeek = "wednesday";
					case 5:
						dayOfWeek = "thursday";
					case 6:
						dayOfWeek = "friday";
					case 7:
						dayOfWeek = "saturday";
				}
				if(e.getClass().equals(Hourly.class)){
					e.setPaymentAgenda("weekly 1 " + dayOfWeek);
					
				}else{
					e.setPaymentAgenda("weekly 2 " + dayOfWeek);
				}
			}else{
				return;
			}
		}
		
		System.out.println("The Payment Agenda from "+e.getName()+" was successfully edited!");
		
	}
	
	/*
	 * Method that runs the System, connecting all the other methods. A loop until the user chooses to Exit the program (case 10)
	 */
	public void run(){
		int option = -1;
		while(option != 9){
			option = mainMenuSelect();
			switch(option){
				case 1:
					addEmployee();
					break;
				case 2:
					removeEmployee();
					break;
				case 3:
					launchTimeCard();
					break;
				case 4:
					launchSellingResult();
					break;
				case 5:
					launchServiceTax();
					break;
				case 6:
					editEmployee();
					break;
				case 7:
					runPayroll();
					break;
				case 8:
					editPaymentAgenda();
					break;
				case 9:
					System.out.println("Thanks for using the Payment Manager!\n"
							+  "Created by João Gabriel");
					break;
			}
		}
	}

	public static void main(String args[]){
		PaymentManager pManager = new PaymentManager();
		pManager.run();
	}

}
