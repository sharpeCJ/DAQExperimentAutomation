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

/**
 * A behaviour layers are stacked by priority and is how to resolve conflicting action goals in a complex environment
 * without direct communication, and without persisting state. In VMXi the layers are Sample Safety - Top priority,
 * handles any internal external error Sleeve Handling - Manage loading of Plate Sleeves Sample Handling - Manage
 * loading samples A behaviour is said to be fired if a particular environment state has been sensed. A candidate action
 * is selected from the layer to be executed. If more than one layer is fired then the highest action from the highest
 * priority is always selected.
 */
public interface BehaviourLayer {
	
	public enum BehaviourLayerStatus {
		NO_ACTION_SELECTED ,
		INHIBITED;
	}
	
	/**
	 * Inhibit the layer: discard any input signals, i.e. enable to read the environment state information.
	 */
	void inhibit();

	/**
	 * Disinhibit the layer: allow it to read the environment state information.
	 */
	void uninhibit();
	
	
	boolean inhibited();
	
	/**
	 * Fire the layer and select an action from the action set of the layer.
	 */
	Object fire(Object environmentState);
	
	void setActionSet(ActionRules as);
	

}
