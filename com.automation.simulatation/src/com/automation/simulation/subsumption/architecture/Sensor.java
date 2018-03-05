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

import com.automation.simulation.externalresources.SimpleServiceModel;

/**
 * This describes the basic behaviour of any sensor. Any implementation is designed to sense specific states - a
 * stimulus - in the sample handling environment. It is passive.
 */
public interface Sensor {

	/**
	 * Sense the required state in the sample handling environment.
	 * 
	 * @return true if a behaviour layer is fired.
	 */
	public boolean doSense();

	/**
	 * Converts what has been sensed into a object that converts the data from the sensor in to state information that
	 * determines what action to take.
	 * 
	 * For example, in sample handling layer: {"Location":0->Formulatrix,"ScheduledState":0->Not Scheduled}
	 * 
	 * @return state of environment
	 */
	public Map<String, Object> getSensedState();
	
	
	public void setInformationSource(SimpleServiceModel source);

}
