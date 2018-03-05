package com.automation.simulation.samplehandling.conveyance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.automation.simulation.externalresources.DataMapping;
import com.automation.simulation.externalresources.LoadNextQueue.SampleLoadQueueElement;
import com.automation.simulation.subsumption.architecture.Sensor;
import com.automation.simulation.externalresources.LoadNextService;
import com.automation.simulation.externalresources.SimpleServiceModel;

public class SenseLoadNext implements Sensor {
	
	private LoadNextService loadNextservice;
	private Map<String,Object> sensedInformation  = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean doSense(){
		
		boolean sensed = false;
		
		sensedInformation.clear();
		sensedInformation.put(DataMapping.LOAD_NEXT,null);
		
		Object sensedData = loadNextservice.getLoadNext();
		
		List<SampleLoadQueueElement> sensedRawInformation = (List<SampleLoadQueueElement>)sensedData;
		
		sensedInformation.put(DataMapping.LOAD_NEXT, sensedRawInformation);
		
		if (!sensedRawInformation.isEmpty()) {
			sensed = true;
		}
		
		return sensed;
	}

	@Override
	public Map<String, Object> getSensedState() {
		return sensedInformation;
	}

	@Override
	public void setInformationSource(SimpleServiceModel source) {
		loadNextservice = (LoadNextService)source;
	}

}
