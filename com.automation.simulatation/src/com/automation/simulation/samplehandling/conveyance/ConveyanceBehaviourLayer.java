/**
 * 
 */
package com.automation.simulation.samplehandling.conveyance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.automation.simulation.action.Action;
import com.automation.simulation.externalresources.DataMapping;
import com.automation.simulation.subsumption.architecture.ActionRules;
import com.automation.simulation.subsumption.architecture.ActionStates;
import com.automation.simulation.subsumption.architecture.BehaviourLayer;

/**
 * @author abr01390
 *
 */
public class ConveyanceBehaviourLayer implements BehaviourLayer {

	private boolean inhibit = false;
	private ActionRules conveyanceActionRules;
	
	@Override
	public void inhibit() {
		inhibit = true;
		
	}

	@Override
	public void uninhibit() {
		inhibit = false;
		
	}
	@Override
	public boolean inhibited()
	{
		return inhibit;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,Object> fire(Object environmentState) {
		
		Map<String,Object>potentialAction = new HashMap<>();
		
		if(!(((Map<String,List>)(environmentState)).get(DataMapping.IN_TRANSIT)).isEmpty()){
			potentialAction.put(ActionStates.NO_ACTION_SELECTED,null);
			inhibit();
		}
		
		else {
			
			potentialAction.putAll(conveyanceActionRules.selectPotentialActions(environmentState));
		}
		return potentialAction;
	}

	@Override
	public void setActionSet(ActionRules ars) {
		conveyanceActionRules = ars;
	}
	
	public Action  getAction(String actionID) 
	{
		return conveyanceActionRules.getExecutableAction(actionID);
		
	}

}
