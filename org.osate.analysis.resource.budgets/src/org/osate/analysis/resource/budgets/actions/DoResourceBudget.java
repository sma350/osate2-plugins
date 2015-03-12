/*
 *
 * <copyright>
 * Copyright  2004 by Carnegie Mellon University, all rights reserved.
 *
 * Use of the Open Source AADL Tool Environment (OSATE) is subject to the terms of the license set forth
 * at http://www.eclipse.org/legal/cpl-v10.html.
 *
 * NO WARRANTY
 *
 * ANY INFORMATION, MATERIALS, SERVICES, INTELLECTUAL PROPERTY OR OTHER PROPERTY OR RIGHTS GRANTED OR PROVIDED BY
 * CARNEGIE MELLON UNIVERSITY PURSUANT TO THIS LICENSE (HEREINAFTER THE "DELIVERABLES") ARE ON AN "AS-IS" BASIS.
 * CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED AS TO ANY MATTER INCLUDING,
 * BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR A PARTICULAR PURPOSE, MERCHANTABILITY, INFORMATIONAL CONTENT,
 * NONINFRINGEMENT, OR ERROR-FREE OPERATION. CARNEGIE MELLON UNIVERSITY SHALL NOT BE LIABLE FOR INDIRECT, SPECIAL OR
 * CONSEQUENTIAL DAMAGES, SUCH AS LOSS OF PROFITS OR INABILITY TO USE SAID INTELLECTUAL PROPERTY, UNDER THIS LICENSE,
 * REGARDLESS OF WHETHER SUCH PARTY WAS AWARE OF THE POSSIBILITY OF SUCH DAMAGES. LICENSEE AGREES THAT IT WILL NOT
 * MAKE ANY WARRANTY ON BEHALF OF CARNEGIE MELLON UNIVERSITY, EXPRESS OR IMPLIED, TO ANY PERSON CONCERNING THE
 * APPLICATION OF OR THE RESULTS TO BE OBTAINED WITH THE DELIVERABLES UNDER THIS LICENSE.
 *
 * Licensee hereby agrees to defend, indemnify, and hold harmless Carnegie Mellon University, its trustees, officers,
 * employees, and agents from all claims or demands made against them (and any related losses, expenses, or
 * attorney's fees) arising out of, or relating to Licensee's and/or its sub licensees' negligent use or willful
 * misuse of or negligent conduct or willful misconduct regarding the Software, facilities, or other rights or
 * assistance granted by Carnegie Mellon University under this License, including, but not limited to, any claims of
 * product liability, personal injury, death, damage to property, or violation of any laws or regulations.
 *
 * Carnegie Mellon University Software Engineering Institute authored documents are sponsored by the U.S. Department
 * of Defense under Contract F19628-00-C-0003. Carnegie Mellon University retains copyrights in all material produced
 * under this contract. The U.S. Government retains a non-exclusive, royalty-free license to publish or reproduce these
 * documents, or allow others to do so, for U.S. Government purposes only pursuant to the copyright license
 * under the contract clause at 252.227.7013.
 *
 * </copyright>
 *
 *
 * %W%
 * @version %I% %H%
 */
package org.osate.analysis.resource.budgets.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.osate.aadl2.Element;
import org.osate.aadl2.NamedElement;
import org.osate.aadl2.instance.InstanceObject;
import org.osate.aadl2.instance.SystemInstance;
import org.osate.analysis.architecture.InstanceValidation;
import org.osate.analysis.resource.budgets.ResourceBudgetPlugin;
import org.osate.analysis.resource.budgets.logic.DoResourceBudgetLogic;
import org.osate.ui.actions.AaxlReadOnlyActionAsJob;
import org.osate.ui.dialogs.Dialog;
import org.osgi.framework.Bundle;

public class DoResourceBudget extends AaxlReadOnlyActionAsJob {

	protected Bundle getBundle() {
		return ResourceBudgetPlugin.getDefault().getBundle();
	}

	protected String getActionName() {
		return "Resource Budget Analysis";
	}

	public String getMarkerType() {
		return "org.osate.analysis.resource.budgets.ResourceAnalysisMarker";
	}

	protected void initPropertyReferences() {
	}

	@Override
	protected boolean initializeAction(NamedElement obj) {
		setCSVLog("ResourceBudgets", obj);
		return true;
	}

	protected void doAaxlAction(IProgressMonitor monitor, Element obj) {

		// Get the system instance (if any)
		final SystemInstance si = (obj instanceof InstanceObject) ? ((InstanceObject) obj).getSystemInstance() : null;

		if (si != null) {
			monitor.beginTask(getActionName(), IProgressMonitor.UNKNOWN);
			DoResourceBudgetLogic logic = null;
//			final SOMIterator soms = new SOMIterator(si);
//			while (soms.hasNext())
//			{
//				final SystemOperationMode som = soms.nextSOM();
//				final String somName = som.getName();
			// final String somName = null;
			InstanceValidation iv = new InstanceValidation(this);
			if (!iv.checkReferenceProcessor(si)) {
				Dialog.showWarning("Resource Budget Analysis",
						"Model contains thread execution times without reference processor.");
				return;
			}

			logic = new DoResourceBudgetLogic(this);
			logic.analyzeResourceBudget(si, null);
//			}
			monitor.done();

			if (si.getSystemOperationModes().size() == 1) {
				// Also report the results using a message dialog
				Dialog.showInfo("Resource Budget Statistics", getResultsMessages());
			}
		}
	}

	public void invoke(IProgressMonitor monitor, SystemInstance root) {
		actionBody(monitor, root);
	}

}