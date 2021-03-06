package org.osate.analysis.flows.model;

import org.osate.aadl2.instance.ConnectionInstance;
import org.osate.analysis.flows.FlowLatencyUtil;

/**
 * A latency Contributor represents something in the flow
 * that can contribute to increase/decrease the latency.
 * 
 * This class contains the result for a latency contributor
 * with min/max latency.
 * 
 * @author julien
 *
 */
public class LatencyContributorConnection extends LatencyContributor {

	public LatencyContributorConnection(ConnectionInstance ci) {
		super();
		this.relatedElement = ci;
	}

	protected String getContributorType() {
		if (FlowLatencyUtil.getConnectionType((ConnectionInstance) this.relatedElement) == ConnectionType.DELAYED) {
			return "Delayed Connection";
		}

		if (FlowLatencyUtil.getConnectionType((ConnectionInstance) this.relatedElement) == ConnectionType.IMMEDIATE) {
			return "Immediate Connection";
		}

		if (FlowLatencyUtil.getConnectionType((ConnectionInstance) this.relatedElement) == ConnectionType.SAMPLED) {
			return "Connection";
		}

		return "Connection";
	}

}
