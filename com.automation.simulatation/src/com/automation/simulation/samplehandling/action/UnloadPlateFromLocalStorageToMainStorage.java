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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.simulation.action.SimulationAction;
import com.automation.simulation.exceptions.ActionExecutionException;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.externalresources.SampleState.SampleLocationState;
import com.automation.simulation.samplehandling.conveyance.StorageSlots;

public class UnloadPlateFromLocalStorageToMainStorage extends SimulationAction
{

	private static final Logger logger = LoggerFactory.getLogger(UnloadPlateFromLocalStorageToMainStorage.class);
	private String barcode;
	private int storageSlot;
	public static final String SAMPLE_HANDLING_ID = UnloadPlateFromLocalStorageToMainStorage.class.getName();
	
	
	public UnloadPlateFromLocalStorageToMainStorage(String type) 
	{
		actionName = SAMPLE_HANDLING_ID;
		actionType = type;
	}
	
	@Override
	public void doAction() throws ActionExecutionException {
				
				this.doneAction = false;
				logger.info("\tACTION: Performing Action: " +SAMPLE_HANDLING_ID);

				logger.info("\tACTION: Insert into Automation Port at Main Storage Plate with ID " +barcode);	
				
				
				logger.info("\tACTION: Unload Plate From local storage slot " +storageSlot);	

				double loadTime = getTotalActionTime();
				logger.info("\tACTION: Unload Time " +loadTime+ " seconds");
				
				
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
		
		SampleInformationElement preActionState = (SampleInformationElement) actionState;
		
		preActionState.localStorageSlot = StorageSlots.DEALLOCATED_SLOT;
		preActionState.location = (SampleLocationState) preActionState.location.transition();
		
		barcode = preActionState.id;
		storageSlot = preActionState.localStorageSlot;
		
		return preActionState;
	}
	
	@Override
	public SampleInformationElement setPostActionState(Object preActionState) {
		
		SampleInformationElement postActionState;
		
		postActionState = (SampleInformationElement) preActionState;
		postActionState.location = (SampleLocationState) postActionState.location.transition();
		postActionState.localStorageSlot = StorageSlots.DEALLOCATED_SLOT;
		
		return postActionState;
	}



}
