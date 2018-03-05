package com.automation.simulation.samplehandling.conveyance;

import java.util.HashMap;
import java.util.Map;

import com.automation.simulation.externalresources.LoadNextService;
import com.automation.simulation.externalresources.SampleInformationService;
import com.automation.simulation.externalresources.SimpleServiceModel;
import com.automation.simulation.subsumption.architecture.Sensor;

public class ConveyanceSense implements Sensor {
	
	private SenseLoadNext senseLoadNext = null;
	private SenseLocalStorage senseLocalStorage = null;
	private SenseSampleMounted senseSampleMounted = null;
	private SenseInTransit senseInTransit = null;
	Map<String,Object> sensedInformation  = new HashMap<>();
	
	public ConveyanceSense() 
	{
		senseLoadNext = new SenseLoadNext();
		senseLocalStorage = new SenseLocalStorage();
		senseSampleMounted = new SenseSampleMounted();	
		senseInTransit = new SenseInTransit();
		
	}
	
	@Override
	public boolean doSense() {
		boolean sensed = false;
		
		boolean sensedLoadNext = senseLoadNext.doSense(); 
		boolean sensedLocalStorage = senseLocalStorage.doSense(); 
		boolean sensedSampleStage = senseSampleMounted.doSense(); 
		boolean sensedInTransit = senseInTransit.doSense();
		
		if(sensedLoadNext || sensedLocalStorage || sensedSampleStage || sensedInTransit) 
		{
			sensed = true;
		}
		
		return sensed;
	}

	@Override
	public Map<String, Object> getSensedState() {
		
		sensedInformation.clear();
		
		sensedInformation.putAll((senseLoadNext.getSensedState()));
		sensedInformation.putAll(senseLocalStorage.getSensedState());
		sensedInformation.putAll(senseSampleMounted.getSensedState());
		sensedInformation.putAll(senseInTransit.getSensedState());
		
		return sensedInformation;
	}

	@Override
	public void setInformationSource(SimpleServiceModel source) {
		if(source instanceof LoadNextService) {
			senseLoadNext.setInformationSource(source);
		}
		else if(source instanceof SampleInformationService) 
		{
			senseLocalStorage.setInformationSource(source);
			senseSampleMounted.setInformationSource(source);
			senseInTransit.setInformationSource(source);
		}
	}

}
