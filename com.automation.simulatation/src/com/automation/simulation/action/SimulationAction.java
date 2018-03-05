/*-
 * Copyright © 2016 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */
package com.automation.simulation.action;

import java.util.Random;

import com.automation.simulation.action.ActionPerformingThread;
import com.automation.simulation.action.GenerateRandomError;
import com.automation.simulation.action.GenericAction;

/**
 * A dummy action simulates a live action insofar that the execution
 * of the action is paused for a given period of time - either 
 * a set value or one randomly generated within a given range. 
 * The point is to characterise sample handling   
 * as both deterministic and stochastic process. In the latter case 
 * this is to: 
 * 1. Test and capture the dynamic nature of the environment and provide proof
 * the controller is robust. 
 * 2. Measure the impact on throughput when re-configuring the order in which the Sense->Act rules 
 * are executed if running a Simulation of n runs.  
 *
 * @author cjsharpe
 */

public abstract class SimulationAction extends GenericAction {
	
	protected boolean isStochastic = false; 
	protected int actionTimeMin;
	protected int actionTimeMax;
	protected ActionPerformingThread actionThread;
	protected GenerateRandomError errorGenerator = new GenerateRandomError();
	protected double errorProbability = 0;
	
	/**
	 * Set total time to perform a simulated load or unload action
	 * @param loadTimeMax           
	 */
	public void setMaximumActionTime(int timeMax) {
		actionTimeMax = timeMax;
	}
	/**
	 * Set total time to perform a simulated load or unload action
	 * @param loadTimeMin         
	 */
	public void setMinimumActionTime(int timeMin) {
		actionTimeMin = timeMin;
	}
	/**
	 * Get the total time to perfrom an action          
	 */
	protected int getTotalActionTime() {
		int actionTime = 0;
		if(isStochastic)
			actionTime = (new Random().nextInt(1) * (actionTimeMax - actionTimeMin) + actionTimeMin);
		else 
			actionTime = actionTimeMax;
		return actionTime;
	}
	/**
	 * Flag to indicate if load or unload action is deterministic 
	 * or stochastic
	 * @param isStochastic             
	 */
	public void setIsStochastic(boolean stochastic) {
		isStochastic = stochastic;
	}
	/**
	 * Set the upper bound for an error -  
	 * @param px
	 * Probability of an error-> {0 - 1}            
	 */
	public void setFaultErrorBounds(double px){
		
		errorGenerator.setErrorProbabilty(px);
	}
	/**
	 * The period to generate a possible error 
	 * @param cycleTime
	 * In seconds            
	 */
	public void setErrorCycle(int cycleTime){
		
		errorGenerator.setErrorCycle(cycleTime);
	}
	
	/**
	 * Run action for given load time         
	 */
	public void executeAction(){
		
		actionThread = new ActionPerformingThread();
		actionThread.setExecutionTime(getTotalActionTime());
		Thread t = new Thread(actionThread);
		t.start();
		
		
	}
	
	/**
	 * Generate error at time interval t seconds. 
	 * Throw device exception at probability px.         
	 */
	public void generateDeviceFault() throws Exception{
		
		
		while(!actionThread.getDone())
		{	
			errorGenerator.generateError();
			if(errorGenerator.isInError()){
				throw new Exception("Plate Handling Error on Performing Action: ");
			}
		}
		
	}
	
}
