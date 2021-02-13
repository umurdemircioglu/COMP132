package project;

public class Customer {
	
	private static int customerNumber = 1;
	private int arrivalTime;
	private int customerId;
	private int serviceTime;
	
	public Customer(int arrivalTime) {
		this.arrivalTime = arrivalTime;
		this.customerId = customerNumber;
		customerNumber++;
		this.serviceTime = 0;
		
	}
	

	public int getCustomerID() {
		return this.customerId;
	}
	
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	public void setServiceTime(int a) {
		this.serviceTime = a;
	}
	public int getServiceTime() {
		return this.serviceTime;
	}
	
	
}


