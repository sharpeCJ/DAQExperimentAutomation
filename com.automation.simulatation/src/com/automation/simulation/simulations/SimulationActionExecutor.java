package com.automation.simulation.simulations;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.automation.simulation.datacollection.agent.DataCollectionReactiveController;
import com.automation.simulation.samplehandling.agent.SampleHandlingReactiveController;
import com.automation.simulation.subsumption.architecture.Controller;

public class SimulationActionExecutor {
	
	DataCollectionReactiveController dataCollectionAgent = null;
	SampleHandlingReactiveController sampleHandlingAgent = null;
	
	private ExecutorService executor;
	
	public void setDataCollectionAgent(DataCollectionReactiveController dca) {
		dataCollectionAgent = dca;

	}

	public void sampleHandlingAgent(SampleHandlingReactiveController sha) {
		sampleHandlingAgent = sha;
	}
	
	public void startSimulationThreads() 
	
	{
		executor = Executors.newFixedThreadPool(2);
		
		ControllerThread dct = createControllerCallableThread(dataCollectionAgent);
		ControllerThread sht = createControllerCallableThread(sampleHandlingAgent);
		
		executor.submit(sht);
		executor.submit(dct);
		
	}
	
	
	public void stopAllSimulationAgents() {
		
		dataCollectionAgent.stop();
		sampleHandlingAgent.stop();
		
	
	}
	
	public ControllerThread createControllerCallableThread(Controller con) 
	{
		return new ControllerThread(con);
	}
	
	private class ControllerThread implements Callable<String> {
		
		Controller controller;
		
		public ControllerThread(Controller con) 
		{
			controller = con;
		}
		
		@Override
		public String call() throws Exception {
			
			controller.start();
			
			return Thread.currentThread().getName();
			}}

}
