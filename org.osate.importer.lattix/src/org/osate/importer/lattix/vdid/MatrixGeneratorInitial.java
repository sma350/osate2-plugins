/*
 * Copyright 2013 Carnegie Mellon University
 * 
 * The AADL/DSM Bridge (org.osate.importer.lattix ) (the �Content� or �Material�) 
 * is based upon work funded and supported by the Department of Defense under 
 * Contract No. FA8721-05-C-0003 with Carnegie Mellon University for the operation 
 * of the Software Engineering Institute, a federally funded research and development 
 * center.

 * Any opinions, findings and conclusions or recommendations expressed in this 
 * Material are those of the author(s) and do not necessarily reflect the 
 * views of the United States Department of Defense. 

 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING 
 * INSTITUTE MATERIAL IS FURNISHED ON AN �AS-IS� BASIS. CARNEGIE MELLON 
 * UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, 
 * AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR 
 * PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF 
 * THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF 
 * ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT 
 * INFRINGEMENT.
 * 
 * This Material has been approved for public release and unlimited 
 * distribution except as restricted below. 
 * 
 * This Material is provided to you under the terms and conditions of the 
 * Eclipse Public License Version 1.0 ("EPL"). A copy of the EPL is 
 * provided with this Content and is also available at 
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Carnegie Mellon� is registered in the U.S. Patent and Trademark 
 * Office by Carnegie Mellon University. 
 * 
 * DM-0000232
 * 
 */

package org.osate.importer.lattix.vdid;

import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.emf.ecore.EClass;

import org.osate.aadl2.Aadl2Package;
import org.osate.aadl2.ComponentImplementation;
import org.osate.aadl2.ComponentType;
import org.osate.aadl2.EndToEndFlow;
import org.osate.aadl2.FlowSpecification;
import org.osate.aadl2.instance.ComponentInstance;
import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.aadl2.instance.EndToEndFlowInstance;
import org.osate.aadl2.instance.util.InstanceSwitch;
import org.osate.aadl2.util.Aadl2Switch;
import org.osate.aadl2.modelsupport.errorreporting.AnalysisErrorReporterManager;
import org.osate.aadl2.modelsupport.modeltraversal.AadlProcessingSwitchWithProgress;


public class MatrixGeneratorInitial extends AadlProcessingSwitchWithProgress {


	public MatrixGeneratorInitial(final IProgressMonitor monitor) {
		super(monitor, PROCESS_PRE_ORDER_ALL);
	}

	public MatrixGeneratorInitial(final IProgressMonitor monitor, AnalysisErrorReporterManager errmgr) {
		super(monitor, PROCESS_PRE_ORDER_ALL,errmgr);
	}

	protected final void initSwitches() {


		aadl2Switch = new Aadl2Switch<String>() {

			public String caseComponentType(ComponentType obj) {

				return DONE;
			}

			public String caseComponentImplementation(ComponentImplementation ci) {

				return DONE;
			}
			
			public String caseFlowSpecification(FlowSpecification obj) {

				return DONE;
			}

			public String caseEndToEndFlow(EndToEndFlow obj) {

				return DONE;
			}
		};


		
		instanceSwitch = new InstanceSwitch<String>() {
			public String caseComponentInstance(ComponentInstance obj) {

				switch (obj.getCategory()) {
				case THREAD:
					return DONE;
				case PROCESS:
					return DONE;
				case PROCESSOR:
					return DONE;
				case VIRTUAL_PROCESSOR:
					return DONE;
				case MEMORY:
					return DONE;
				case BUS:
					return DONE;
				case VIRTUAL_BUS:
					return DONE;
				case DEVICE:
					return DONE;
				}
				return DONE;
			}

			public String caseConnectionInstance(ConnectionInstance ci) 
			{
				System.out.println ("Connection source name=" + ci.getSource().getName() + ";destination name=" + ci.getDestination().getName() );
				System.out.println ("Connection source comp=" + ci.getSource().getContainingComponentInstance().getName() + ";destination name=" + ci.getDestination().getContainingComponentInstance().getName() );
				return DONE;
			}

			public String caseEndToEndFlowInstance(EndToEndFlowInstance ci) {
				return DONE;
			}
		};

	}
	
	public void countClassifier(EClass cl)
	{
		
	}

	public String getModelResult() 
	{
		return null;

	}

	public String getFlowResult() 
	{
		return null;

	}

	public String getApplicationResult() 
	{
		return null;

	}

	public String getExecutionPlatformResult()
	{
		return null;

	}
}
