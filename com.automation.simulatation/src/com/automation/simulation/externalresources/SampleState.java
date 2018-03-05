package com.automation.simulation.externalresources;

public class SampleState {

public interface State {	
	
	abstract State transition();
	
}


public enum SampleLocationState implements State{	
		
	INITIAL_LOCATION {
		@Override
		public SampleLocationState transition() {
			return MAIN_STORAGE;
		}
	},
	MAIN_STORAGE {
		@Override
		public SampleLocationState transition() {
			return LOADING_TO_LOCAL_STORAGE;
		}
	},
	LOADING_TO_LOCAL_STORAGE {
		@Override
		public SampleLocationState transition() {
			return LOCAL_STORAGE_IN;
		}
	},
	LOCAL_STORAGE_IN {
		@Override
		public SampleLocationState transition() {
			return LOADING_TO_SAMPLE_STAGE;
		}
	},
	LOADING_TO_SAMPLE_STAGE {
		@Override
		public SampleLocationState transition() {
			return SAMPLE_STAGE;
		}
	},
	SAMPLE_STAGE {
		@Override
		public SampleLocationState transition() {
			return LOADING_FROM_SAMPLE_STAGE;
		}
	},
	LOADING_FROM_SAMPLE_STAGE {
		@Override
		public SampleLocationState transition() {
			return LOCAL_STORAGE_OUT;
		}
	},
	
	LOCAL_STORAGE_OUT {
		@Override
		public SampleLocationState transition() {
			return LOADING_FROM_LOCAL_STORAGE;
		}
	},
	
	LOADING_FROM_LOCAL_STORAGE{
		@Override
		public SampleLocationState transition() {
			return MAIN_STORAGE;
		}
	}
	

}

public enum SampleShotState implements State{
	INITIAL_SHOT {
		@Override
		public SampleShotState transition() {
			return NOT_SHOT;
		}
	},
	NOT_SHOT {
		@Override
		public SampleShotState transition() {
			return SHOOTING;
			}	
		},
	
	SHOOTING {
			@Override
			public SampleShotState transition() {
				return SHOT;
				}	
			},
	SHOT {
		@Override
		public SampleShotState transition() {
			return null;
			}	
		}
	}
	
}
