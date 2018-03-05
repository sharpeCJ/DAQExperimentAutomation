package com.automation.simulation.samplehandling.conveyance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.automation.simulation.externalresources.DataMapping;
import com.automation.simulation.externalresources.LoadNextQueue.SampleLoadQueueElement;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.externalresources.SampleState.SampleShotState;
import com.automation.simulation.action.Action;
import com.automation.simulation.samplehandling.action.ActionTypes;
import com.automation.simulation.samplehandling.action.LoadSampleFromLocalStorageToSampleStage;
import com.automation.simulation.samplehandling.action.LoadSampleFromMainStorageToLocalStorage;
import com.automation.simulation.samplehandling.action.UnloadPlateFromLocalStorageToMainStorage;
import com.automation.simulation.samplehandling.action.UnloadSampleFromSampleStageToLocalStorage;
import com.automation.simulation.samplehandling.conveyance.StorageSlots.StorageSlotState;
import com.automation.simulation.subsumption.architecture.ActionRules;
import com.automation.simulation.subsumption.architecture.ActionStates;

public class ConveyanceActionRules implements ActionRules {
	
	Map<String, Action> conveyanceActions = new HashMap<>();
	Map<String, Object>candidateAction = new HashMap<>();
	StorageSlots storageSlots = null;
	
	
	public ConveyanceActionRules() {
		
		conveyanceActions.put(LoadSampleFromMainStorageToLocalStorage.SAMPLE_HANDLING_ID, new LoadSampleFromMainStorageToLocalStorage(ActionTypes.CONVEYANCE_ACTION.name()));
		conveyanceActions.put(UnloadPlateFromLocalStorageToMainStorage.SAMPLE_HANDLING_ID, new UnloadPlateFromLocalStorageToMainStorage(ActionTypes.CONVEYANCE_ACTION.name()));
		conveyanceActions.put(UnloadSampleFromSampleStageToLocalStorage.SAMPLE_HANDLING_ID, new UnloadSampleFromSampleStageToLocalStorage(ActionTypes.CONVEYANCE_ACTION.name()));
		conveyanceActions.put(LoadSampleFromLocalStorageToSampleStage.SAMPLE_HANDLING_ID, new LoadSampleFromLocalStorageToSampleStage(ActionTypes.CONVEYANCE_ACTION.name()));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> selectPotentialActions(Object environmentStates) {
		String actionSelectedID = ActionStates.NO_ACTION_SELECTED;
		
		List<SampleLoadQueueElement> loadNextSample = ((Map<String,List>)environmentStates).get(DataMapping.LOAD_NEXT);
		List<SampleInformationElement> localStorageList = ((Map<String,List>)environmentStates).get(DataMapping.LOCAL_STORAGE_SAMPLES);
		List<SampleInformationElement> onStageList = ((Map<String,List>)environmentStates).get(DataMapping.SAMPLE_ON_SAMPLE_STAGE);		
		
		candidateAction.clear();
		
		List<SampleInformationElement> allSampleElements = localStorageList;
		allSampleElements.addAll(onStageList);
		updateStorageSlotState(allSampleElements);
		 
		SampleInformationElement sample = null;

		
		if(!loadNextSample.isEmpty() && storageSlots.slotFree()!=StorageSlots.NO_FREE_SLOTS)
		{	
			
			
			sample = new SampleInformationElement((loadNextSample.get(0).sqe));
			actionSelectedID = LoadSampleFromMainStorageToLocalStorage.SAMPLE_HANDLING_ID;
		}
		
		if(!localStorageList.isEmpty())
		{	
			for(SampleInformationElement s : localStorageList) 
			{
				SampleShotState shotState = s.shot;
			
				if (shotState == SampleShotState.SHOT) 
				{
					actionSelectedID = UnloadPlateFromLocalStorageToMainStorage.SAMPLE_HANDLING_ID;
					sample =  new SampleInformationElement(s);
				}
				else if(onStageList.isEmpty() &&  shotState == SampleShotState.NOT_SHOT)
				{
					actionSelectedID = LoadSampleFromLocalStorageToSampleStage.SAMPLE_HANDLING_ID;
					sample =  new SampleInformationElement(s);
					break;
				}
			}
		}
		
		if(!onStageList.isEmpty())
		{	
			SampleShotState shotState = onStageList.get(0).shot;
			
			if (shotState == SampleShotState.SHOT)
			{
				sample =  new SampleInformationElement(onStageList.get(0));
				actionSelectedID = UnloadSampleFromSampleStageToLocalStorage.SAMPLE_HANDLING_ID;
			}
		}
		
		candidateAction.put(actionSelectedID, sample);
		
		if(actionSelectedID == LoadSampleFromMainStorageToLocalStorage.SAMPLE_HANDLING_ID) 
		{	int slot = storageSlots.getRandomFreeSlot();
			storageSlots.setSlotStorageState(slot, StorageSlotState.ALLOCATED);
			sample.localStorageSlot = slot;
		}
		
		return candidateAction;
	}
	private void updateStorageSlotState(List<SampleInformationElement> allSampleElements) {
		
		storageSlots.clearLocalStorageState();
		
		for(SampleInformationElement s : allSampleElements) 
		{
			int slot = s.localStorageSlot;
			storageSlots.setSlotStorageState(slot, StorageSlotState.ALLOCATED);
		}
		
	}

	@Override
	public Action getExecutableAction(String actionSelectedID) 
	{
		return conveyanceActions.get(actionSelectedID);
	
	}
	@Override
	public void setActionRules(Map<String, Action> actions) {
		conveyanceActions = actions;
	}
	
	public void setStorageSlots(StorageSlots slots) 
	{
		storageSlots = slots;
	}

	
}
