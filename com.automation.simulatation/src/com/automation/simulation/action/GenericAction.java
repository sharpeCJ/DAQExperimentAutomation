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
/**
 * The Generic Action class that defines the name of the action and, crucially,
 * contains the doneAction flag.
 * 
 *  It is a critical flag to indicate that an action has/has not been completed. 
 *  other threads may be monitoring its state. Therefore it must be ensured that is 
 *  not left in in an indeterminate state. To make absolutely sure of this
 *	any method calling the method getDoneAction() must be synchronised.
 *	
 * @author cjsharpe
 */

public abstract class GenericAction implements Action {
	
	protected volatile boolean doneAction = false;
	
	protected String actionName = "";
	protected String actionType = "";
	
	@Override
	public String getActionName() {
		return actionName;
	}
	
	@Override
	public String getActionType() {
		return actionType;
	}
	
	@Override
	public boolean getDoneAction() {
		return this.doneAction;
	}
	
}
