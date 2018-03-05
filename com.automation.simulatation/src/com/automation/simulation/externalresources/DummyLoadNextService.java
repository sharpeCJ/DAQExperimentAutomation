/**
 * 
 */
package com.automation.simulation.externalresources;

import java.util.ArrayList;
import java.util.List;
import com.automation.simulation.externalresources.LoadNextQueue.SampleLoadQueueElement;

/**
 * @author abr01390
 *
 */
public class DummyLoadNextService implements SimpleServiceModel,LoadNextService {

	/* (non-Javadoc)
	 * @see com.automation.simulation.externalresources.InformationSource#isConnected()
	 */
	private boolean connected = true;
	private LoadNextQueue loadNext;
	
	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void setInformationSource(Object infoSource) {
		loadNext = (LoadNextQueue)infoSource;
	}

	@Override
	public Object getInformationSource() {
		return loadNext;
	}
	
	@Override
	public void setConnectionState(boolean conn) {
		connected = conn;
	}
	
	public List<SampleLoadQueueElement> getLoadNext() {
		
		ArrayList<SampleLoadQueueElement> loadnext = new ArrayList<>();
		if(loadNext.hasElements()) 
		{SampleLoadQueueElement sample = loadNext.getQueueHead();
		loadnext.add(sample);}
		
		return loadnext;
	}
	
	public void removeQueueHead() {
		
		loadNext.popQueueHead();
	}
	
	public void showQueueInformation() {
		
		loadNext.printLoadQueue();
	}
}
