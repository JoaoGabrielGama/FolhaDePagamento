
public class Salaried extends Employee{
	//Data
	private double salary;
	
	//Constructor
	public Salaried(String name, String address, String type, double salary, int payment, String agenda, int syndicateId, int id){
		super(name, address, type, payment, agenda, syndicateId, id);
		this.salary = salary;
	}
	
	//Setters & Getters
	public void setSalary(double salary){ this.salary = salary; }
	
	public double getSalary(){ return this.salary; }
	public String toString(){
		String s = super.toString();
		s += "\nSalary: "+this.salary;	
		
		return s;
	}
}
