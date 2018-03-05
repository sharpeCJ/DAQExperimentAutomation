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
package com.automation.simulation.samplehandling.action;

import com.automation.simulation.action.SimulationAction;
import com.automation.simulation.exceptions.ActionExecutionException;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.externalresources.SampleState.SampleLocationState;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoadSampleFromLocalStorageToSampleStage extends SimulationAction{
		
	
		public static final String SAMPLE_HANDLING_ID = LoadSampleFromLocalStorageToSampleStage.class.getName();
		private static final Logger logger = LoggerFactory.getLogger(LoadSampleFromLocalStorageToSampleStage.class);
		private int storageSlot = 0;
		
		public LoadSampleFromLocalStorageToSampleStage(String type) 
		{
			actionName = SAMPLE_HANDLING_ID;
			actionType = type;
		}
		
		@Override
		public void doAction() throws ActionExecutionException {
					
					this.doneAction = false;
					logger.info("\tACTION: Performing Action: " +SAMPLE_HANDLING_ID);
	
					logger.info("\tACTION: Load Plate from Gonio to local storage slot " +storageSlot);	

					double loadTime = getTotalActionTime();
					logger.info("\tACTION: Load Time " +loadTime+ " seconds");
					
					executeAction();
					
					try {
						generateDeviceFault();
					} catch (Exception e) {
						throw new ActionExecutionException(SAMPLE_HANDLING_ID,e.getCause());
					}		
					logger.info("\tACTION: "+ getActionName() +" Completed");
					
					this.doneAction = true;		
		}
		
		@Override
		public SampleInformationElement setPreActionState(Object actionState) {
			
			SampleInformationElement preActionState = (SampleInformationElement)actionState;
			preActionState.location = (SampleLocationState)preActionState.location.transition();
			storageSlot = preActionState.localStorageSlot;
			
			return preActionState;
		}
		
		@Override
		public SampleInformationElement setPostActionState(Object preActionState) {
			
			SampleInformationElement postActionState;
			postActionState = (SampleInformationElement)preActionState;
			postActionState.location = (SampleLocationState)postActionState.location.transition();
			
			return postActionState;
		}

}
