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

import com.automation.simulation.exceptions.ActionExecutionException;
/**
* @author cjsharpe
*
*/
/**
 * Defines the basic behaviour to perform an action. Implicit to any action is that it 
 * that applying the action it changes a state. The action generates this
 * new state before and after the action.
 */
public interface Action {

	/**
	 * Perform a single action.
	 */
	void doAction() throws ActionExecutionException;

	/**
	 * @return true if the action has been executed
	 */
	boolean getDoneAction();
	/**
	 * Set state transition of some entity - "I'm doing this action now".
	 */
	Object setPreActionState(Object actionState);
	/**
	 * Set the new state of some entity after the action has been applied
	 */
	Object setPostActionState(Object actionState);
	/**
	 * @return the super set of action type to identify how it is classified
	 */
	String getActionType();
	/**
	 * @return the individual action type within the same set of action types.
	 */
	String getActionName();
}
