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

package com.automation.simulation.externalresources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.automation.simulation.externalresources.SampleState.SampleLocationState;
import com.automation.simulation.externalresources.SampleState.SampleShotState;
import com.automation.simulation.samplehandling.conveyance.StorageSlots;


/**
 * Dummy List to simulate an information source that contains information for 
 * all registered plates.
 * 
 * @author cjsharpe
 */

public class SampleInformation {

private static final Logger logger = LoggerFactory.getLogger(SampleInformation.class);
private  Map<String,SampleInformationElement> samplesAllInformation; 
private String outputQueueString = "";	
public static final String ID_DEFAULT = "";
public static final boolean SCHEDULED_DEFAULT = false;
public static final int DATE_DEFAULT = Integer.MAX_VALUE;
public static final SampleLocationState LOCATION_DEFAULT = SampleLocationState.INITIAL_LOCATION;
public static final SampleShotState SHOT_DEFAULT = SampleShotState.INITIAL_SHOT;

public SampleInformation()
{
	samplesAllInformation = new HashMap<>();
}	

public SampleInformationElement getElementByID(String id)
{		
	return samplesAllInformation.get(id);
}

public void addInformationSampleElement(String id,SampleInformationElement element)
{	
	samplesAllInformation.put(id, element);
}

public void removeInformationSampleElement(String id)
{
	samplesAllInformation.remove(id);
}

public static SampleInformationElement createQueueElement()
{
	return new SampleInformationElement();

}
public int hasElements(){
	
	return samplesAllInformation.size();
}

public Set<String >getListKeys(){
	
	return samplesAllInformation.keySet();
}

public void emptySampleInformation(){
	
	samplesAllInformation.clear();
}
public void printSampleInformation()
{
	logger.debug("Dummy Sample Information List: Print List Contents:");
	
	for (Map.Entry<String,SampleInformationElement>entry: samplesAllInformation.entrySet())
	{
		outputQueueString = ("\tSample: id " +entry.getKey()+ " time stamp " +entry.getValue().timeStamp+
				" ready to process " + entry.getValue().scheduled +
				" location " +entry.getValue().location.name()+ " container" +entry.getValue().localStorageSlot+
				" shot_state " +entry.getValue().shot.name());
		
		System.out.println(outputQueueString);
	}
}

public String getOutputQueueString(){
	return outputQueueString;
}

public List<SampleInformationElement> getSamplesByLoadStatus(String location) {
	
	List<SampleInformationElement> sampleElements = new ArrayList<>();
	
	for(SampleInformationElement sample:samplesAllInformation.values())
	{
		
		if(sample.location.name() == location)
			sampleElements.add(sample);
	}
	return sampleElements;
}

public static SampleInformationElement createSampleInformationElement() {
	
	return new SampleInformationElement();
}
public static class SampleInformationElement
{		
	private SampleInformationElement() {}
	public SampleInformationElement (SampleInformationElement infoElement){
		
		id = infoElement.id;
		timeStamp = infoElement.timeStamp;
		scheduled = infoElement.scheduled;
		localStorageSlot = infoElement.localStorageSlot;
		location = infoElement.location;
		shot = infoElement.shot;
	}
		
		public String id = ID_DEFAULT;
		public int timeStamp = DATE_DEFAULT;
		public boolean scheduled = SCHEDULED_DEFAULT;
		public int localStorageSlot = StorageSlots.DEALLOCATED_SLOT;
		public SampleLocationState location = LOCATION_DEFAULT;
		public SampleShotState shot = SHOT_DEFAULT;
		
}

}
