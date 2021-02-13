package project;

import java.util.LinkedList;
import java.util.Queue;

public class CustomerQueue {
	
	private Queue<Customer> queue = new LinkedList<>();
	
	public void enqueue(Customer customer) {
		queue.add(customer);
		
	}
	
	public Customer dequeue () {
		return queue.remove();
	}
	
	public int getSize() {
		
		return queue.size();
	}
	
	
	

}
