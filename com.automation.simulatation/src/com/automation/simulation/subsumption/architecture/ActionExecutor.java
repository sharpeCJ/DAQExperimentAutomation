package com.automation.simulation.subsumption.architecture;

import com.automation.simulation.action.Action;

public interface ActionExecutor {

	/**
	 * @param action to execute
	 */
	void executeAction(Action action);

	/**
	 * @return true if the action is in the process of executing
	 */
	boolean getPerformingActionState();

	void setPerformingActionState(boolean state);

	/**
	 * @return true if the action has been executed
	 */
	boolean doneAction();

	boolean isError();

	void clearError();

}
