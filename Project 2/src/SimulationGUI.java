//THIS CODE IS MY OWN WORK. I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
//I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. 
//NAME: Umur Demircioğlu

package project;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.awt.event.ActionEvent;

public class SimulationGUI extends JFrame{

	private JTextField maxArrivalTime;
	private JTextField maxSimulationTime;
	private JTextField maxServiceTime;
	private JLabel servicetimelabel;
	private JLabel simulationtimelabel;
	private JLabel arrivaltimelabel;
	private JButton startButton;
	private JButton stopButton;
	public Timer timer;
	private JLabel currentSimulationTime;
	private JLabel currentQueueLength;
	private JLabel totalServiceTime;
	private JPanel simulationPanel;
	private JPanel variablesPanel;
	private JPanel simulationStatisticsAreaPanel;
	private JTextArea simulationStatisticsArea;
	private CustomerQueue queue;
	private Customer customer;
	private int simtime;
	private int arrvtime;
	private int servtime;
	static private Random rand = new Random();
	private int clock = 0;
	private Customer currentcustomer;
	private int leavetime;
	private SimulationStatistics simStats;
	private int totservtime;


	public SimulationGUI () {
		super("Simulation");
		this.queue = new CustomerQueue();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,400);
		setVisible(true);
		setLayout(new GridLayout(3,1));

		timer = new Timer(100, new TimerActionListener());
		simStats = new SimulationStatistics();

		Border variablesBorder = BorderFactory.createTitledBorder("Variables Panel");
		variablesPanel = new JPanel();
		variablesPanel.setBorder(variablesBorder);
		variablesPanel.setLayout(new GridLayout(4,2));


		simulationtimelabel = new JLabel("Max Simulation Time");
		variablesPanel.add(simulationtimelabel);
		maxSimulationTime = new JTextField();
		variablesPanel.add(maxSimulationTime);

		arrivaltimelabel = new JLabel("Max Arrival Time");
		variablesPanel.add(arrivaltimelabel);
		maxArrivalTime = new JTextField();
		variablesPanel.add(maxArrivalTime);

		servicetimelabel = new JLabel("Max Service Time");
		variablesPanel.add(servicetimelabel);
		maxServiceTime = new JTextField();
		variablesPanel.add(maxServiceTime);

		startButton = new JButton("Start");
		variablesPanel.add(startButton);

		stopButton = new JButton("Stop");
		variablesPanel.add(stopButton);

		add(variablesPanel);



		Border simulationBorder = BorderFactory.createTitledBorder("Simulation Panel");
		simulationPanel = new JPanel();
		simulationPanel.setBorder(simulationBorder);
		simulationPanel.setLayout(new GridLayout(3,0));

		currentSimulationTime = new JLabel("Current Simulation Time:");
		simulationPanel.add(currentSimulationTime);

		currentQueueLength = new JLabel("Current Queue Length:");
		simulationPanel.add(currentQueueLength);

		totalServiceTime = new JLabel("Total Service Time:");
		simulationPanel.add(totalServiceTime);

		add(simulationPanel); 


		Border simulationStatisticsAreaBorder = BorderFactory.createTitledBorder("Simulation Statistics Panel");
		simulationStatisticsAreaPanel = new JPanel();
		simulationStatisticsAreaPanel.setBorder(simulationStatisticsAreaBorder);

		simulationStatisticsArea = new JTextArea();
		simulationStatisticsAreaPanel.add(simulationStatisticsArea);
		simulationStatisticsArea.setEditable(false);
		add(simulationStatisticsAreaPanel);


		startButton.addActionListener(new startPressed());
		stopButton.addActionListener(new startPressed());


	}
	public static void main(String[] args) {
		SimulationGUI asd = new SimulationGUI();
	}


	private class startPressed implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!timer.isRunning() && e.getSource() == startButton) {
				try {
					simtime = Integer.parseInt(maxSimulationTime.getText());
					arrvtime = Integer.parseInt(maxArrivalTime.getText());
					servtime = Integer.parseInt(maxServiceTime.getText());

					int arrivalstart = randomArrivalTime(arrvtime);
					int servicestart = randomServiceTime(servtime);
					
					if(customer == null) {
						customer = new Customer(arrivalstart);
						customer.setServiceTime(servicestart);
					}
					
				}
				catch(NumberFormatException e1) {
					e1.printStackTrace();
					System.out.println("error");
				}
				timer.start();

			}else if(e.getSource() == stopButton){
				timer.stop();
			}
		}
		public int randomArrivalTime(int arrvtime) {
			return rand.nextInt(arrvtime)+1;

		}
		public int randomServiceTime(int servtime) {
			return rand.nextInt(servtime)+1;

		}

	}


	private class TimerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			clock++;
			
		
			
			if(clock == customer.getArrivalTime() && customer.getCustomerID() == 1) {
				leavetime = customer.getArrivalTime();	
			}
			
			if(clock <= simtime && clock == customer.getArrivalTime()) {
				queue.enqueue(customer);
				int nextarrivaltime = clock + randomArrivalTime(arrvtime);
				
				customer = new Customer(nextarrivaltime);
				simStats.setnumberofcustomers();
				
			}
			
			if(clock >= leavetime) {
				if(queue.getSize() > 0) {
					currentcustomer = queue.dequeue();
					currentcustomer.setServiceTime(randomServiceTime(servtime));
					leavetime = clock + currentcustomer.getServiceTime();
					totservtime += currentcustomer.getServiceTime();
					simStats.setAverageservicetime(currentcustomer.getServiceTime());
					int averagewaittime = leavetime - currentcustomer.getArrivalTime();
					simStats.setAveragewaittime(averagewaittime);
				}
			}
			
			simStats.setMaxqueuelength(queue.getSize());
			
			if(clock >= simtime && queue.getSize() == 0) {
				timer.stop();
				simulationStatisticsArea.append(simStats.statmessage(currentcustomer));
			}
			
			currentSimulationTime.setText("Current Simulation Time: " + clock);
			int size = queue.getSize();
			currentQueueLength.setText("Current Queue Length:" + size);
			totalServiceTime.setText("Total Service Time " + totservtime);
	}

	public int randomArrivalTime(int arrvtime) {
		return rand.nextInt(arrvtime)+1;

	}
	public int randomServiceTime(int servtime) {
		return rand.nextInt(servtime)+1;

	}
}
}

