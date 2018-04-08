package com.automation.simulation.simulations;
import com.automation.simulation.externalresources.LoadNextQueue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.automation.simulation.datacollection.action.CollectData;
import com.automation.simulation.datacollection.agent.DataCollectionReactiveController;
import com.automation.simulation.exceptions.ActionExecutionException;
import com.automation.simulation.externalresources.DummyLoadNextService;
import com.automation.simulation.externalresources.DummySampleInformationService;
import com.automation.simulation.externalresources.SampleInformation;
import com.automation.simulation.samplehandling.agent.SampleHandlingReactiveController;
import com.automation.simulation.samplehandling.conveyance.ConveyanceSense;
import com.automation.simulation.samplehandling.conveyance.SenseSampleMounted;
import com.automation.simulation.simulations.ParameterSetup;


public class SingleSimulatonRun {


	
public static void main(String args[]) {
	SampleHandlingReactiveController sample_handling_agent = new SampleHandlingReactiveController();
	SingleSimulatonRun r1 = new SingleSimulatonRun();
	
	SampleInformation si = ParameterSetup.createSampleInformationSource(ParameterSetup.DEFAULT_SAMPLE_SIZE,ParameterSetup.DEFAULT_SCHEDULED);
	DummySampleInformationService sisSamplehandling = ParameterSetup.createDummySampleInformationService();
	
	sisSamplehandling.setInformationSource(si);
	
	LoadNextQueue lnq = ParameterSetup.createLoadNextQueue(si);
	DummyLoadNextService lns = ParameterSetup.createDummyLoadNextService(); 

	lns.setInformationSource(lnq);
	
	((LoadNextQueue)lns.getInformationSource()).printLoadQueue();
	
	
	ConveyanceSense cs = new ConveyanceSense();
	
	cs.setInformationSource(sisSamplehandling);
	cs.setInformationSource(lns);
	
	//sample_handling_agent.
	
	//boolean hasSensed = cs.doSense();
	
	//logger.info(hasSensed);//+ " " + ((List)(sensed.get(DataMapping.LOCAL_STORAGE_SAMPLES))).size());
	sample_handling_agent.setConveyance(cs);
	sample_handling_agent.setInformationSource(sisSamplehandling);
	sample_handling_agent.setLoadNextQueue(lns);
	//sample_handling_agent.start();
	
	DataCollectionReactiveController data_collection_agent = new DataCollectionReactiveController();
	CollectData dca = new CollectData(true,15,10);
	SenseSampleMounted ss = new SenseSampleMounted();
	ss.setInformationSource(sisSamplehandling);
	data_collection_agent.setSenseSampleStage(ss);
	data_collection_agent.setDutyCylcePeriod(1);
	data_collection_agent.setDataCollectionAlgorithm(dca);
	data_collection_agent.setInformationSource(sisSamplehandling);
	
	//data_collection_agent.start();
	
	SimulationActionExecutor se = new SimulationActionExecutor();
	se.setDataCollectionAgent(data_collection_agent);
	se.sampleHandlingAgent(sample_handling_agent);
	
	se.startSimulationThreads();
	//se.stopAllSimulationAgents();
	
}





}
