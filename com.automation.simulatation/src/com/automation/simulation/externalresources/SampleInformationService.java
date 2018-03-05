/**
 * 
 */
package com.automation.simulation.externalresources;

/**
 * @author abr01390
 *
 */
public interface SampleInformationService {

	public void updateSampleState(Object sample);
	
	public Object getAllInternalStorageSamples();
	
	public Object getSamplesInTransitSamples();
	
	public Object getOnSampleStageSample();
	
}
