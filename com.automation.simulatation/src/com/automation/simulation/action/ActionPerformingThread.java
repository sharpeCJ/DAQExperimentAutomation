/*- *******************************************************************************
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
/**
 * Simulate the time it takes to perform a sample action by executing
 * and then pausing a thread for t seconds before it terminates. 
 * 
 * @author cjsharpe
 */
public class ActionPerformingThread implements Runnable {

	private long actionExecutionTime = 0; 
	boolean done = false;
	public static final long ONE_SEC = 1000;
	@Override	
	public void run() {
		
		try {
			done = false;
			Thread.sleep(this.actionExecutionTime * ONE_SEC);
			done = true;
			
		} catch (InterruptedException e) {
			done = false;
			e.printStackTrace();
		}
		
	}
	public void setExecutionTime(long actionExecutionTime)
	{
		this.actionExecutionTime = actionExecutionTime;
	}
	
	public synchronized boolean getDone(){
		
		return done;
	} 
}
