package com.automation.simulation.samplehandling.agent;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.simulation.action.Action;
import com.automation.simulation.exceptions.ActionExecutionException;
import com.automation.simulation.subsumption.architecture.ActionExecutor;


/**
 * The ActionExecutorController role is to execute exactly one sample handling
 * on a single non-blocking worker thread.
 * It enables a thread-safe call to be made to an action under execution to 
 * determine if the action has completed.  
 * 
 * @author cjsharpe
 */
public class SampleHandlingActionExecutorController implements ActionExecutor {
	private static final Logger logger = LoggerFactory.getLogger(SampleHandlingActionExecutorController.class);
	private volatile boolean performingAction = false;
	private ExecutorService executor = null;
	private Action action;
	private boolean error;
	@Override
	public void executeAction(Action action) {
		startExecutor();
		this.action = action;
		performingAction = false;
		setError(false);
		
		logger.info("Ready to Execute Action " +action.getActionName());
		
		Callable<Boolean> callable = new Callable<Boolean>() {
			@Override
			public Boolean call() {
				try {
					logger.info("Executing Action");
					action.doAction();
				} catch (ActionExecutionException e) 
				{
					logger.error("Error executing action", e);
					setError(true);
					setPerformingActionState(false);
				}
				return true;
			}
		};
		setPerformingActionState(true);
		executor.submit(callable);
	}

	@Override
	public synchronized boolean getPerformingActionState() {
		logger.info("Action Performing State is " +this.performingAction);
		return this.performingAction;
	}

	@Override
	public synchronized void setPerformingActionState(boolean state) {
		logger.info("Set Action Performing State to " +state);
		this.performingAction = state;
	}

	@Override
	/**
	 * Ensure thread safety on the call to read the flag to determine if an action 
	 * is completed.
	 */
	public synchronized boolean doneAction() {
		boolean done_state = action.getDoneAction();
		logger.info("Get Action Done State to " +done_state);
		return done_state;
	}
	
	public Action getActionExecuted() {
		
		return this.action;
	}
	
	@Override
	public synchronized boolean isError() {
		return error;
	}

	@Override
	public void clearError() {
		setError(false);
	}

	private synchronized void setError(boolean error) {
		this.error = error;
	}
	
	/**
	 * Close down the the thread executor - throw an exception if
	 * in the middle of perfroming an action  
	 */
	public void stopExecutor() throws InterruptedException{
		logger.info("Stop the action executor");
		executor.shutdownNow();
		if(!executor.isTerminated())
			throw new InterruptedException();
	}
	/**
	 * Create a executor to run exactly one thread at a time
	 */
	public void startExecutor() {
		logger.info("Start the action executor");
		if(executor == null)
			executor = Executors.newFixedThreadPool(1);	
	}
}