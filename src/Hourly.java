
public class Hourly extends Employee{
	
	//Data
	private double salaryPerHour;
	private double currentSalary = 0;
	private int salaryWeekInterval = 2;
	
	
	
	//Constructor
	public Hourly(String name, String address, String type, double salaryPerHour, int payment, String agenda, int syndicateId, int id){
		super(name, address, type, payment, agenda, syndicateId, id);
		this.salaryPerHour = salaryPerHour;
	}
	
	//Setters & Getters
	public void setSalaryPerHour(double salary){ this.salaryPerHour = salary; }
	public void resetCurrentSalary(){ this.currentSalary = 0; }
	public void setCurrentSalary(double cSalary){ this.currentSalary = cSalary; }
	public void setSalaryWeekInterval(int interval){ this.salaryWeekInterval = interval; }
	public void addCurrentSalary(double hours){
		if(hours > 8){
			double extraHours = hours - 8;
			this.currentSalary += ((8 * this.salaryPerHour) + (extraHours * (1.5 * this.salaryPerHour)));
			
		}else{
			this.currentSalary += (hours * this.salaryPerHour);
		}
	}
	
	public double getSalaryPerHour(){ return this.salaryPerHour; }
	public double getCurrentSalary(){ return this.currentSalary; }
	public int getSalaryWeekInterval(){ return this.salaryWeekInterval; }
	public String toString(){
		String s = super.toString();
		s += "\nSalary/Hour: "+this.salaryPerHour
		   + "\nCurrent Salary: "+this.currentSalary;	
		
		return s;
	}
}
