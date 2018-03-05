package com.automation.simulation.datacollection.action;

import com.automation.simulation.action.DependentAction;
import com.automation.simulation.action.SimulationAction;
import com.automation.simulation.exceptions.ActionExecutionException;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.externalresources.SampleState.SampleShotState;

public class CollectData extends SimulationAction implements DependentAction{
	
	public static final String COLLECT_DATE_ID = CollectData.class.getName();

	
	public CollectData() 
	{}
	
	public CollectData(boolean stochastic,int maxTime,int minTime) {
		
		super.setIsStochastic(stochastic);
		super.setMaximumActionTime(maxTime);
		super.setMinimumActionTime(minTime);
	}
	
	@Override
	public void doAction() throws ActionExecutionException {
		
		System.out.println("Collect Data Action");
		
		
		executeAction();
		
		
		try {
			setFaultErrorBounds(0.01);
			setErrorCycle(1);
			generateDeviceFault();
			
		} catch (Exception e) {
			throw new ActionExecutionException(COLLECT_DATE_ID,e.getCause());
		}	
		
		this.doneAction = true;	
		
	}

	@Override
	public Object setPreActionState(Object actionState) {
		
		SampleInformationElement preActionState = (SampleInformationElement)actionState;
		preActionState.shot = (SampleShotState) preActionState.shot.transition();
		
		return preActionState;
	}

	@Override
	public Object setPostActionState(Object actionState) {
		
		SampleInformationElement postActionState = (SampleInformationElement)actionState;
		postActionState.shot = (SampleShotState) postActionState.shot.transition();
		
		return postActionState;

	}

	@Override
	public boolean checkAllDependentResources() {
		return true;
	}

}
