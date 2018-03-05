package com.automation.simulation.samplehandling.conveyance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.automation.simulation.externalresources.DataMapping;
import com.automation.simulation.externalresources.SampleInformationService;
import com.automation.simulation.externalresources.SimpleServiceModel;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.subsumption.architecture.Sensor;

public class SenseSampleMounted implements Sensor {

	private SampleInformationService sampleInformationService;
	private Map<String,Object> sensedInformation  = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean doSense(){
		
		boolean sensed = false;
		
		sensedInformation.clear();
		sensedInformation.put(DataMapping.SAMPLE_ON_SAMPLE_STAGE,null);
		
		Object sensedData = sampleInformationService.getOnSampleStageSample();
		
		List<SampleInformationElement> sensedRawInformation = (List<SampleInformationElement>)sensedData;
		
		sensedInformation.put(DataMapping.SAMPLE_ON_SAMPLE_STAGE, sensedRawInformation);
		
		if (!sensedRawInformation.isEmpty()){
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
		sampleInformationService = (SampleInformationService)source;
	}

}
