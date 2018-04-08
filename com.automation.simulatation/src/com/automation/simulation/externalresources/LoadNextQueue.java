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

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;

public class LoadNextQueue  {

public static final int PRIORTIY_DEFAULT = 9999;
private PriorityQueue<SampleLoadQueueElement>sampleLoadQueue;
private static final Logger logger = LoggerFactory.getLogger(LoadNextQueue.class);

public LoadNextQueue()
{
	sampleLoadQueue = new PriorityQueue<>(new PriorityQueueComparator());
}
/**
 * Dummy Queue to simulate the load action plan that determines what Sample
 * is to load next.
 * 
 * @author cjsharpe
 */
public SampleLoadQueueElement getQueueHead(){
	
	return sampleLoadQueue.element();
}

public boolean hasElements(){
	
	return !sampleLoadQueue.isEmpty();
}

 public void popQueueHead(){
	 
	 sampleLoadQueue.remove();
}

public static SampleLoadQueueElement createQueueElement()
{
	return new SampleLoadQueueElement();
	

}

public void emptyLoadQueue(){
	
	sampleLoadQueue.clear();
}

public void addSampleLoadQueueElement(SampleLoadQueueElement element)
{
	sampleLoadQueue.add(element);
}

public void printLoadQueue()
{
	Iterator<SampleLoadQueueElement> it = sampleLoadQueue.iterator() ;
	
	logger.debug("Dummy Load Queue: Load Queue Contents:");
	while(it.hasNext())
	{
		SampleLoadQueueElement element = it.next();
		
		logger.info("\tSample: ID " +element.sqe.id+ " priority " +element.priorty);
	}
		
}

public void priorityLoadLogic() {;}

public static class SampleLoadQueueElement
{
		public int priorty = PRIORTIY_DEFAULT;
		public SampleInformationElement sqe;
}

class PriorityQueueComparator implements Comparator<SampleLoadQueueElement>{
	@Override
	public int compare(SampleLoadQueueElement qe1, SampleLoadQueueElement qe2) {
		if (qe1.priorty < qe2.priorty) {
			return -1;
		}
		if (qe1.priorty > qe2.priorty) {
			return 1;
		}
		return 0;
	}
}

}
