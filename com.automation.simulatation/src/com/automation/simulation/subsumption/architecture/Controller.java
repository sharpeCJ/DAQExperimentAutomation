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
 * Highest level component that starts and stops the sample handling activity.
 */
public interface Controller {

	/**
	 * Start the sample handling process. Should run continuously, even if some fault/error thrown from lower level.
	 */
	void start();

	/**
	 * Automatically stop the sample handling process.
	 */
	void stop();

	/**
	 * Mechanism by which to to disregard all inputs to the behavourial layers.
	 */
	void inbibitAllLayers();

	/**
	 * Mechanism by which to re-open all inputs to the behavourial layers. This is the means by which a user would
	 * manually re-start sample handling after some internal or external error has triggered, logged and then cleared.
	 */
	void uninbitAllLayers();

}
