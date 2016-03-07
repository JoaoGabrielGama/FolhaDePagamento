
public abstract class Employee {
	
	//Data
	private String name;
	private String address;
	private String type;
	private int paymentMethod;
	private String paymentAgenda;
	private int lastDayPaid = -1;
	private int lastMonthPaid = -1;
	private double syndicateTax = 0;
	private int syndicateId = -1;
	private double serviceTaxes = 0;
	private int id;
	
	//Constructor
	public Employee(String name, String address, String type, int payment, String agenda, int syndicateId, int id){
		this.name = name;
		this.address = address;
		this.type = type;
		this.paymentMethod = payment;
		this.paymentAgenda = agenda;
		this.syndicateId = syndicateId;
		this.id = id;
	}
	
	//Setters & Getters
	public void setName(String name){ this.name = name; }
	public void setAddress(String address){ this.address = address; }
	public void setType(String type){ this.type = type; }
	public void setPaymentMethod(int payment){ this.paymentMethod = payment; }
	public void setPaymentAgenda(String agenda){ this.paymentAgenda = agenda; }
	public void setLastDayPaid(int day){ this.lastDayPaid = day; }
	public void setLastMonthPaid(int month){ this.lastMonthPaid = month; }
	public void setSyndicateTax(double tax){ this.syndicateTax = tax; }
	public void addServiceTax(double tax){ this.serviceTaxes += tax; }
	
	public String getName(){ return this.name; }
	public String getAddress(){ return this.address; }
	public String getType(){ return this.type; }
	public int getPaymentMethod(){ return this.paymentMethod; }
	public String getPaymentAgenda(){ return this.paymentAgenda; }
	public int getLastDayPaid(){ return this.lastDayPaid; }
	public int getLastMonthPaid(){ return this.lastMonthPaid; }
	public double getSyndicateTax(){ return this.syndicateTax; }
	public int getSyndicateId(){ return this.syndicateId; }
	public int getId(){ return this.id; }
	public double getServiceTaxes(){ return this.serviceTaxes; }
	public String toString(){
		String s = "Name: "+this.name
				 + "\nAddress: "+this.address
				 + "\nType: "+this.type
				 + "\nID: "+this.id;
		if(this.syndicateId != -1){
			s += "\nSyndicate ID: "+this.syndicateId
			   + "\nSyndicate Tax: "+this.syndicateTax;
		}else {
			s += "\nSyndicate ID: This Employee is part of no Syndicate.";
		}
		
		s += "\nService Taxes: "+this.serviceTaxes; 
		
		if(this.paymentMethod == 1){
			s += "\nPayment Method: Send Paycheck Home";
		}else if(this.paymentMethod == 2){
			s += "\nPayment Method: Give Paycheck in Hands";
		}else{
			s += "\nPayment Method: Bank Deposit";
		}
		
		s += "\nPayment Agenda: "+this.paymentAgenda;
		
		return s;
	}
}
