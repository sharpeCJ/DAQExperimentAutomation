package com.automation.simulation.externalresources;

import java.util.ArrayList;
import java.util.List;

import com.automation.simulation.externalresources.SampleInformation;
import com.automation.simulation.externalresources.SampleState.SampleLocationState;
import com.automation.simulation.externalresources.SampleInformation.SampleInformationElement;

public class DummySampleInformationService implements SimpleServiceModel, SampleInformationService {
	
	private boolean connected = true;
	private SampleInformation sampleInformationSource;
	
	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public void setInformationSource(Object infoSource) {
		sampleInformationSource = (SampleInformation)infoSource;
	}

	@Override
	public SampleInformation getInformationSource() {
		return sampleInformationSource;
	}
	
	@Override
	public void setConnectionState(boolean conn) {
		connected = conn;
	}
	
	@Override
	public void updateSampleState(Object sample) {
		sampleInformationSource.addInformationSampleElement(((SampleInformationElement)sample).id, (SampleInformationElement)sample);
	}
	
	public void addNewSample(String id, SampleInformationElement sample) {
		
		sampleInformationSource.addInformationSampleElement(id, sample);
	}
	
	public void removeSampleInformation(String id){
		
		sampleInformationSource.removeInformationSampleElement(id);
	}
	
	public List<SampleInformationElement> getAllInternalStorageSamples()
	{	ArrayList<SampleInformationElement> localStorage = new ArrayList<>();
		localStorage.addAll(sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.LOCAL_STORAGE_IN.name()));
		localStorage.addAll(sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.LOCAL_STORAGE_OUT.name()));
		return localStorage;
		
	}
	
	public List<SampleInformationElement> getSamplesInTransitSamples(){
		
		ArrayList<SampleInformationElement> intransit = new ArrayList<>();
		intransit.addAll(sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.LOADING_FROM_LOCAL_STORAGE.name()));
		intransit.addAll(sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.LOADING_FROM_SAMPLE_STAGE.name()));
		intransit.addAll(sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.LOADING_TO_LOCAL_STORAGE.name()));
		intransit.addAll(sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.LOADING_TO_SAMPLE_STAGE.name()));
		return intransit;
	}
	
	public List<SampleInformationElement> getOnSampleStageSample() {
		return sampleInformationSource.getSamplesByLoadStatus(SampleLocationState.SAMPLE_STAGE.name());
	}
	
	public void showAllSampleInformation() {
		
		sampleInformationSource.printSampleInformation();
		
	}
	
}
