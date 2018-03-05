package com.automation.simulation.samplehandling.agent;

import java.util.Map;
import com.automation.simulation.action.Action;
import com.automation.simulation.externalresources.DummyLoadNextService;
import com.automation.simulation.externalresources.DummySampleInformationService;
import com.automation.simulation.externalresources.LoadNextQueue;
import com.automation.simulation.externalresources.LoadNextService;
import com.automation.simulation.externalresources.SampleInformationService;
import com.automation.simulation.externalresources.SampleState.SampleLocationState;
import com.automation.simulation.externalresources.SimpleServiceModel;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.samplehandling.action.ActionTypes;
import com.automation.simulation.samplehandling.conveyance.ConveyanceActionRules;
import com.automation.simulation.samplehandling.conveyance.ConveyanceBehaviourLayer;
import com.automation.simulation.samplehandling.conveyance.ConveyanceSense;
import com.automation.simulation.samplehandling.conveyance.StorageSlots;
import com.automation.simulation.simulations.ParameterSetup;
import com.automation.simulation.subsumption.architecture.ActionStates;
import com.automation.simulation.subsumption.architecture.Controller;

public final class SampleHandlingReactiveController implements Controller{
	
	private boolean startProcess = true; 
	private int dutyCycleDefault = 1000;
	private ConveyanceSense conveyanceSensor = null; 
	private ConveyanceBehaviourLayer conveyanceBehaviour = null;
	private SampleHandlingActionExecutorController actionExe = new SampleHandlingActionExecutorController();
	private SampleInformationService sampleinfoService = null; 
	private LoadNextService loadNextService = null;
	boolean circularQueue = false;
	Object preActionState = null;
	Object postActionState = null;
	
	@Override
	public void start() {
		
		while(startProcess) {
			
			System.out.println("Start");
			System.out.println("Pre");
			((DummySampleInformationService)sampleinfoService).showAllSampleInformation();
			((DummyLoadNextService)loadNextService).showQueueInformation();
			senseAct();
			System.out.println("Post");
			((DummySampleInformationService)sampleinfoService).showAllSampleInformation();
			((DummyLoadNextService)loadNextService).showQueueInformation();
			dutyCycle();
		}
		
	}
	
	private void dutyCycle() {
		
		System.out.println("Duty Cycle");

		try {
			Thread.sleep(dutyCycleDefault);
		} catch (InterruptedException e) {
			e.printStackTrace();}
	}
	
	public void senseAct() 
	{
		
		System.out.println("SenseAct");
		
		
		Action action = null;
		
		if(conveyanceSensor.doSense()) 
		{
			Map<String, Object> sensedState = conveyanceSensor.getSensedState();
			Map<String, Object> potentialAction = conveyanceBehaviour.fire(sensedState);
			
			if(!conveyanceBehaviour.inhibited() && !potentialAction.containsKey(ActionStates.NO_ACTION_SELECTED))
			{
				Map.Entry<String, Object> actionAndSensedState =  potentialAction.entrySet().iterator().next();
				action =  conveyanceBehaviour.getAction(actionAndSensedState.getKey());
				Object sample =  actionAndSensedState.getValue();
				preActionState = action.setPreActionState(sample);
			}
		}	
			
		if(action !=null)
		{
			if(action.getActionType() == ActionTypes.CONVEYANCE_ACTION.name()) 
			{updateSampleState(preActionState);}
			
			conveyanceBehaviour.inhibit();
			actionExe.executeAction(action);
			System.out.println("Exxture");
		}
		
		if(actionExe.getPerformingActionState())
		{
			if(actionExe.doneAction()){
				postActionState = actionExe.getActionExecuted().setPostActionState(preActionState);
				
				actionExe.setPerformingActionState(false);
				
				if(actionExe.getActionExecuted().getActionType() == ActionTypes.CONVEYANCE_ACTION.name()) 
				{
				updateSampleState(postActionState);
				conveyanceBehaviour.uninhibit();
				}
				
				
				}
		}
	}
	
	@Override
	public void stop() {
		startProcess = false;
		
	}
	
	private void updateSampleState(Object sampleInformation) 
	
	{
		SampleLocationState samplelocation = ((SampleInformationElement)sampleInformation).location;
		
		if(samplelocation == SampleLocationState.LOADING_TO_LOCAL_STORAGE)
		{	
			((DummyLoadNextService)loadNextService).removeQueueHead();
		}
		
		sampleinfoService.updateSampleState(sampleInformation);
	}
	
	@Override
	public void inbibitAllLayers() {
		conveyanceBehaviour.inhibit();
		
	}

	@Override
	public void uninbitAllLayers() {
		conveyanceBehaviour.uninhibit();
		
	}
	public static void main(String args)
	{
		SampleHandlingReactiveController rc = createReactiveController();
		
		LoadNextQueue lnq = ParameterSetup.createLoadNextQueue();
		
		SimpleServiceModel loadNextService = new DummyLoadNextService();
		loadNextService.setInformationSource(lnq);
		SimpleServiceModel sampleinfoService = new DummySampleInformationService();
		ConveyanceSense conveyanceSense = new ConveyanceSense();
		
		conveyanceSense.setInformationSource(loadNextService);
		conveyanceSense.setInformationSource(sampleinfoService);
		conveyanceSense.setInformationSource(sampleinfoService);
		
		rc.setConveyance(conveyanceSense);
		rc.setInformationSource(sampleinfoService);
		rc.start();
		
	}
	
	public void setConveyance(ConveyanceSense conveyance) 
	{
		conveyanceSensor = conveyance;
		conveyanceBehaviour = new ConveyanceBehaviourLayer();
		ConveyanceActionRules car = new ConveyanceActionRules();
		conveyanceBehaviour.setActionSet(car);
		StorageSlots storage = new StorageSlots();
		storage.setStorageSlots(10);
		car.setStorageSlots(storage);
	}
	
	public void setInformationSource(SimpleServiceModel infoSource) 
	{
		
		sampleinfoService = (SampleInformationService)infoSource;
	}
	
	public void setLoadNextQueue(SimpleServiceModel infoSource) 
	{
		
		loadNextService = (LoadNextService)infoSource;
	}
	
	public static SampleHandlingReactiveController createReactiveController() {
		return new SampleHandlingReactiveController();
	}
}
