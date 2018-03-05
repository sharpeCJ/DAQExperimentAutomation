/*-
 *******************************************************************************
 * Copyright (c) Chris Sharpe 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Chris Sharpe - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.automation.simulation.action;
/**
* @author cjsharpe
*
*/
/**
 * Any action that depends on another hardware/software/network resource needs to check up front 
 * before attempting the action.
 */
public interface DependentAction extends Action {
	
	/**
	 * @return the result of checks of availability of all dependent resources.
	 */
	boolean checkAllDependentResources();
	
}
