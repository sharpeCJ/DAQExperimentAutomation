/*-
 *******************************************************************************
 * Copyright (c) Chris Sharpe 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Chris Sharpe - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.automation.simulation.action;

public class GenerateRandomError {

private double errorProbability = 0.0;
private int errorCycle = 0;
private static final int ONE_SEC = 1000;
private boolean isInError = false;
/**
 * Simulate a some sample action failing - generate a random number {0-1}
 * at some cycle of time t and check against a error threshold, throwing
 * a Device Exception if the random number is below this threshold.
 *   
 * @author cjsharpe
 */
	public void setErrorProbabilty(double px)
	{
		errorProbability = px;
		
	}
	
	public void setErrorCycle(int errorC){
		
		errorCycle = errorC * ONE_SEC;
	}
	
	public void generateError(){
		
		try {
			double random = Math.random();
			isInError = random < errorProbability;
			Thread.sleep(errorCycle);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isInError(){

		return isInError;
	}

}
