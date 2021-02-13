package project;

public class SimulationStatistics {

	private int maxqueuelength;
	private int longestwaittime;
	private double averagewaittime;
	private double averageservicetime; 
	private double averagearrivalinterval;
	private int numberofcustomers;
	
	public SimulationStatistics(){
		super();
	}

	public int getMaxqueuelength() {
		return maxqueuelength;
	}

	public void setMaxqueuelength(int maxqueuelength) {
		if(this.maxqueuelength < maxqueuelength) {
			this.maxqueuelength = maxqueuelength;
		}
	}

	public int getLongestwaittime() {
		return longestwaittime;
	}

	public void setLongestwaittime(int longestwaittime) {
		if(this.longestwaittime < longestwaittime) {
			this.longestwaittime = longestwaittime;
		}
	}

	public double getAveragewaittime() {
		return averagewaittime / numberofcustomers;
	}

	public void setAveragewaittime(int averagewaittime) {
		this.averagewaittime += averagewaittime;
		this.setLongestwaittime(averagewaittime);
	}

	public double getAverageservicetime() {
		return averageservicetime / numberofcustomers;
	}

	public void setAverageservicetime(double averageservicetime) {
		this.averageservicetime += averageservicetime;
	}

	public double getAveragearrivalinterval() {
		return averagearrivalinterval / numberofcustomers;
	}

	public void setAveragearrivalinterval(double averagearrivalinterval) {
		this.averagearrivalinterval+= averagearrivalinterval;
	}
	
	public int getnumberofcustomers() {
		return numberofcustomers;
	}
	
	public void setnumberofcustomers() {
		numberofcustomers++;
	}
	
	public String statmessage(Customer customer) {
		StringBuilder str = new StringBuilder();
		str.append("Number of Customers: " + customer.getCustomerID() + " \n " );
		str.append("Average wait Time: " + getAveragewaittime()+ " \n ");
		str.append("Average service Time: " + getAverageservicetime()+ " \n ");
		str.append("Maximum Wait Time: " + getLongestwaittime()+ " \n ");
		str.append("Maximum Queue Length: " + getMaxqueuelength()+ " \n ");
		return str.toString();
	}
	
}
