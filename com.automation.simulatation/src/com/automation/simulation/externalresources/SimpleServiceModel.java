/**
 * 
 */
package com.automation.simulation.externalresources;

/**
 * @author abr01390
 *
 */
public interface SimpleServiceModel {

	public boolean isConnected();
	public void setConnectionState(boolean connected);
	public void setInformationSource(Object infoSource);
	public Object getInformationSource();
}
