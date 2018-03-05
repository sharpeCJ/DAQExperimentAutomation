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

package com.automation.simulation.subsumption.architecture;

import java.util.Map;


import com.automation.simulation.action.Action;

/**
 * The Action Set determines which action to select given what has been sensed in the sample handling environment. The
 * environment state maybe matched to more than one action, however one and only one action is subsequently executed.
 */
public interface ActionRules {

	/**
	 * Select a candidate action given what has been sensed in the environment. For example, in sample handling layer,
	 * environmentsStates={ "Location":0->Formulatrix, "ScheduledState":0->Not Scheduled }
	 * 
	 * @param environmentState
	 *            state of environment
	 */
	Map<String,Object> selectPotentialActions(Object environmentState);

	/**
	 * @return actionID of action selected to execute
	 */
	void setActionRules(Map<String,Action> actions);

	Action getExecutableAction(String actionID);
	
}
