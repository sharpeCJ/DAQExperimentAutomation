package com.automation.simulation.datacollection.agent;

import java.util.HashMap;
import java.util.List;

import com.automation.simulation.datacollection.action.CollectData;
import com.automation.simulation.exceptions.ActionExecutionException;
import com.automation.simulation.externalresources.DataMapping;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.externalresources.SampleInformationService;
import com.automation.simulation.externalresources.SimpleServiceModel;
import com.automation.simulation.externalresources.SampleState.SampleShotState;
import com.automation.simulation.samplehandling.conveyance.SenseSampleMounted;
import com.automation.simulation.subsumption.architecture.Controller;

public final class DataCollectionReactiveController implements Controller{
	
	private boolean startProcess = true; 
	private int dutyCycleDefault = 1000;
	private SampleInformationService sampleinfoService = null; 
	private SenseSampleMounted senseSampleMounted = null;
	private CollectData collectDataAlgorithm = null;
	boolean circularQueue = false;
	Object preActionState = null;
	Object postActionState = null;
	private boolean inhibit = false;
	
	@Override
	public void start() {
		
		while(startProcess) {
			System.out.println("Data Colection");

			if(!inhibit) 
			{
				senseAct();
			}
			dutyCycle();
		}
		
	}
	
	public void setDutyCylcePeriod(int secs) 
	
	{
		dutyCycleDefault = 2 * 1000;
		
	}
	
	private void dutyCycle() {
		
		System.out.println("Data Colection");
		try {
			Thread.sleep(dutyCycleDefault);
		} catch (InterruptedException e) {
			e.printStackTrace();}
	}
	
	public void senseAct() 
	{
		
		System.out.println("SenseAct");
		
		boolean sensed = senseSampleMounted.doSense();
		
		if(sensed) 
		{
			HashMap<String,Object>sensedInformation = new HashMap<>();
			sensedInformation.putAll(senseSampleMounted.getSensedState());
			@SuppressWarnings("unchecked")
			SampleInformationElement sample = ((List<SampleInformationElement>) sensedInformation.get(DataMapping.SAMPLE_ON_SAMPLE_STAGE)).get(0);
			
			if(sample.shot == SampleShotState.NOT_SHOT) 
			{
				
				boolean allOK = collectDataAlgorithm.checkAllDependentResources();
				
						
				if(allOK) 
				{
				 sample = (SampleInformationElement) collectDataAlgorithm.setPreActionState(sample);
				 updateSampleState(sample);
				 
				 try {
					collectDataAlgorithm.doAction();
					System.out.println("Collect Data");
					
					if(collectDataAlgorithm.getDoneAction())
					{
						sample = (SampleInformationElement) collectDataAlgorithm.setPostActionState(sample);
						updateSampleState(sample);
					}
					
				 } catch (ActionExecutionException e) {
					inbibitAllLayers();
					startProcess = false;
					System.out.println("An error occured");
				}
				 
				 
				}
				
			}
		}
		
	}
	
	@Override
	public void stop() {
		startProcess = false;
		
	}
	
	public void setDataCollectionAlgorithm(CollectData dca) 
	{
		
		collectDataAlgorithm = dca;
	}
	
	private void updateSampleState(Object sampleInformation) 
	{
		
		sampleinfoService.updateSampleState(sampleInformation);
	}
	
	
	public void setInformationSource(SimpleServiceModel infoSource) 
	{
		
		sampleinfoService = (SampleInformationService)infoSource;
	}
	
	
	public void setSenseSampleStage(SenseSampleMounted sense) 
	{
		senseSampleMounted = sense;
	
	}
	
	public static DataCollectionReactiveController createReactiveController() {
		return new DataCollectionReactiveController();
	}

	@Override
	public void inbibitAllLayers() {
		inhibit = true;
		
	}

	@Override
	public void uninbitAllLayers() {
		inhibit = false;
	}
}
