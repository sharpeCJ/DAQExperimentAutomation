package com.automation.simulation.samplehandling.conveyance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StorageSlots {

	public static final int DEALLOCATED_SLOT = -1;
	public static final int NO_FREE_SLOTS = -1;
	
	private  Map<Integer,StorageSlotState>localStorage = new HashMap<>();
	
	public Map<Integer,StorageSlotState>  getLocalStroageSlots()
	{
		
		return localStorage;
	}
	
	public void clearLocalStorageState() 
	{
		for (Map.Entry<Integer,StorageSlotState> pair : localStorage.entrySet()) 
		{		
			localStorage.put(pair.getKey(),StorageSlotState.FREE);
		}
		
	}
	
	
	public void setSlotStorageState(Integer slot, StorageSlotState newState) {
		
		localStorage.put(slot,newState);
	}
	
	public StorageSlotState getSlotStorageState(Integer slot) {
		
		return localStorage.get(slot);
	} 
	
	public int getRandomFreeSlot() 
	{
		ArrayList<Integer> freeSlots = new ArrayList<>();	
		
		for (Map.Entry<Integer,StorageSlotState> pair : localStorage.entrySet()) 
		{	
			
			boolean slotState = pair.getValue().getSlotAvailibilty();
			
			if(slotState) 
			{	
				freeSlots.add(pair.getKey());
			}
		}
		
		Integer randomSlot = freeSlots.get(new Random().nextInt(freeSlots.size()));
		
		return randomSlot.intValue();
		
	}
	
	public int slotFree() 
	{	
		int freeSlot  = NO_FREE_SLOTS;
		
		for (Map.Entry<Integer,StorageSlotState> pair : localStorage.entrySet()) 
		{	
			
			boolean slotState = pair.getValue().getSlotAvailibilty();
			
			if(slotState) 
			{	
				freeSlot = pair.getKey();
				break;
			}
		}
		
		return freeSlot;
		
	}
	public void setStorageSlots(int [] slotIndices) {
		
		for(int i=0; i<slotIndices.length;i++) 
		{ 
			localStorage.put(slotIndices[i],StorageSlotState.FREE);
		}
	}
	
	public void setStorageSlots(int capacity) {
		
		
		for(int i=1; i<=capacity;i++) 
		{ 
			localStorage.put(i,StorageSlotState.FREE);
		}
	}
	
	public enum  StorageSlotState{	
		
	
		FREE(true), ALLOCATED(false), CANNOT_USE(false);
		
		private boolean isAvailable;
		
		StorageSlotState(boolean isAvailable) {
			this.isAvailable = isAvailable;
		}
		
		public boolean getSlotAvailibilty() 
		{
			return this.isAvailable;
			
		}	
	}
}
