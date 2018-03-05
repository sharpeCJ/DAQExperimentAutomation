package com.automation.simulation.simulations;

import java.util.Date;
import java.util.Set;

import com.automation.simulation.externalresources.DummyLoadNextService;
import com.automation.simulation.externalresources.DummySampleInformationService;
import com.automation.simulation.externalresources.LoadNextQueue;
import com.automation.simulation.externalresources.SampleInformation;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;
import com.automation.simulation.externalresources.SampleState.SampleLocationState;
import com.automation.simulation.externalresources.SampleState.SampleShotState;
import com.automation.simulation.externalresources.LoadNextQueue.SampleLoadQueueElement;

public final class ParameterSetup {

public static final int DEFAULT_SAMPLE_SIZE = 10;
public static final int DEFAULT_SCHEDULED = 15;

private ParameterSetup() {}

public static SampleInformation createSampleInformationSource(int samples, int scheduled) {
	
	
	SampleInformation si = new SampleInformation();
	
	for(int s = 1; s <= samples; s++)	
	{ 
		SampleInformationElement sie = SampleInformation.createSampleInformationElement();
		
		sie.location = (SampleLocationState) sie.location.transition();
		sie.shot = (SampleShotState) sie.shot.transition();
		sie.id = Integer.toString(s);
		sie.timeStamp = (int) (new Date().getTime()/1000);
		
		if(s <= scheduled) {
			sie.scheduled = true;
		}
		
		si.addInformationSampleElement(sie.id, sie);
	}
	
	return si;
	
}
public static LoadNextQueue createLoadNextQueue() {
	
	return new LoadNextQueue();
	
}


public static LoadNextQueue createLoadNextQueue(SampleInformation si) {
	
	LoadNextQueue lnq = new LoadNextQueue();

	int priority = 1;
	Set<String>ids = si.getListKeys();
	
	for(String id :ids)	
	{ 	
		SampleInformationElement sie = si.getElementByID(id);
		SampleLoadQueueElement lqe = new SampleLoadQueueElement();
		
		if(sie.scheduled)
		{	
		 	lqe.sqe = sie;
			lqe.priorty = priority;	
			lnq.addSampleLoadQueueElement(lqe);
			priority++;
		}
	}
	
	return lnq;
	
	
	
}

public static DummySampleInformationService createDummySampleInformationService() {
	
	return new DummySampleInformationService();
}


public static DummyLoadNextService createDummyLoadNextService() {
	
	return new DummyLoadNextService();
}




}
