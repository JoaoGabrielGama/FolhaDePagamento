
public class Commissioned extends Employee{
	
	//Data
	private double salary;
	private double commission;
	private double currentSalary = 0;
	private int salaryWeekInterval = 2;
	
	//Constructor
	public Commissioned(String name, String address, String type, double salary, double commission, int payment, String agenda, int syndicateId, int id){
		super(name, address, type, payment, agenda, syndicateId, id);
		this.salary = salary;
		this.commission = commission;
	}
	
	//Setters & Getters
		public void setSalary(double salary){ this.salary = salary; }
		public void setCommission(double commission){ this.commission = commission; }
		public void resetCurrentSalary(){ this.currentSalary = 0; }
		public void setCurrentSalary(double cSalary){ this.currentSalary = cSalary; }
		public void addProfit(double profit){ this.currentSalary += (profit * (this.commission/100)); }
		public void addSalary(){ this.currentSalary += this.salary; }
		public void setSalaryWeekInterval(int interval){ this.salaryWeekInterval = interval; }
		
		
		public double getSalary(){ return this.salary; }
		public double getCommission(){ return this.commission; }
		public double getCurrentSalary(){ return this.currentSalary;}
		public int getSalaryWeekInterval(){ return this.salaryWeekInterval; }
		public String toString(){
			String s = super.toString();
			s += "\nSalary: "+this.salary
			   + "\nCommission: "+this.commission+"%"	
			   + "\nCurrent Salary: "+this.currentSalary;	
			
			return s;
		}

}
