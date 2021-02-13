//THIS CODE IS MY OWN WORK. I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
//I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. 
//NAME: Umur Demircioğlu

#include <stdio.h>
#include <stdlib.h>
#include "queue.h"




typedef struct Customer {
	int customerNumber;
	int arriveTimeofCustomer;
	int serviceTimeofCustomer;


}Customer;

Customer * newCustomer(int customerNumber, int arriveTimeofCustomer, int serviceTimeofCustomer) {
	struct Customer * c = malloc(sizeof(struct Customer));
	if (c) {
		c-> customerNumber = customerNumber;
		c->arriveTimeofCustomer = arriveTimeofCustomer;
		c-> serviceTimeofCustomer = serviceTimeofCustomer;
	}
	return c;
}

int randomInterval(int a , int b){
	int g = rand() % b;
	while( !(g > a && g < b) ){
		g = rand() % b;
	}
	return g;
}



int main(void) {
	int max_arrival_time;
	int max_service_time;
	int simulation_time;
	int arriveTime;
	int serviceTime;
	int time =0;
	int numberOfCustomer = 1;
	int endTime = 0;
	int serviceAvailable = 1 ;
	Customer customer;
	QueueNodePtr headPtr = NULL;
	QueueNodePtr tailPtr = NULL;
	int i = 1; 
	int currentQueueLength = 0;
	int maxQueueLength;
	int tmpForAvarageService;
	int waitTimeCurrent;
	int waitTimeAvarageTemp;
	double avarageServiceTime;
	double avarageWaitTime;
	int maxWaitTime;
	

	printf("Enter a arrival time:");
	scanf("%d",&max_arrival_time);
	arriveTime = randomInterval(1 , max_arrival_time);

	printf("Enter a service time:");
	scanf("%d",&max_service_time);
	serviceTime = randomInterval(1 , max_service_time);

	printf("Enter a simulation time:");
	scanf("%d",&simulation_time);

	arriveTime = randomInterval(1 , max_arrival_time);
	serviceTime = randomInterval(1 , max_service_time);
	tmpForAvarageService = serviceTime;
	customer = *newCustomer(numberOfCustomer, arriveTime, serviceTime);
	while(time < simulation_time){
		time++;
		if(time == arriveTime){
			arriveTime = randomInterval(1 , max_arrival_time) + time;
			printf("%d: Customer %d arrived\n", time, numberOfCustomer);
			if(numberOfCustomer == 1){
				enqueue(&headPtr,&tailPtr, &customer);
				customer = *newCustomer(numberOfCustomer, arriveTime, serviceTime);

			}else{
				customer = *newCustomer(numberOfCustomer, arriveTime, serviceTime);
				enqueue(&headPtr,&tailPtr, &customer);
			}
			numberOfCustomer++;
			
		}

		if(serviceAvailable == 0 && time == endTime){
			serviceAvailable = 1;
			printf("%d: Customer %d is served\n",endTime, i);
			i++;
			dequeue(&headPtr, &tailPtr);
		}

		if(serviceAvailable == 1 && !isEmpty(headPtr)){
			serviceTime = randomInterval(1 , max_service_time);

			tmpForAvarageService += serviceTime;

			endTime = time + serviceTime;
			
			
			printf("%d: Service starts for %d\n", time, i);
			serviceAvailable = 0;
			
			waitTimeCurrent = endTime - arriveTime;
			waitTimeAvarageTemp += waitTimeCurrent; 
			
			if(waitTimeCurrent > maxWaitTime){
				maxWaitTime = waitTimeCurrent;
			}
			currentQueueLength = numberOfCustomer - i;
			if(currentQueueLength > maxQueueLength){
				maxQueueLength = currentQueueLength;
			}
		}
	}
	printf("%d: Max Simulation Time Reached - servicing remaining customers\n",time);
	while(numberOfCustomer > i+1){

		serviceTime = randomInterval(1 , max_service_time);
		customer = *(Customer *)dequeue(&headPtr, &tailPtr);
		printf("%d: Customer %d is served\n",time + serviceTime, customer.customerNumber);
		time += serviceTime;
		i++;

	}

	avarageServiceTime = (double)tmpForAvarageService / numberOfCustomer ;
	avarageWaitTime =  (double)waitTimeAvarageTemp / numberOfCustomer;
	printf("Simulation parameters: %d %d %d \n", simulation_time, max_arrival_time, max_service_time );
	printf("Number of customers: %d \n",i);
	printf("Average Wait Time: %f \n",avarageWaitTime);
	printf("Average Service Time: %f \n",avarageServiceTime);
	printf("Maximum Wait Time: %d \n",maxWaitTime);
	printf("Maximum Queue Length: %d \n",maxQueueLength );


}