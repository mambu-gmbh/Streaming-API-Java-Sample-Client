package streamingapi.client.model;

import java.util.List;

/**
 * Model holding information needed for creating a subscription.
 *
 * @author dancojocaru
 */
public class Subscription {

	private String id;
	private String owningApplication;
	private List<String> eventTypes;
	private String readFrom = "end";

	public Subscription(List<String> eventTypes, String owningApplication) {

		this.eventTypes = eventTypes;
		this.owningApplication = owningApplication;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getOwningApplication() {

		return owningApplication;
	}

	public void setOwningApplication(String owningApplication) {

		this.owningApplication = owningApplication;
	}

	public List<String> getEventTypes() {

		return eventTypes;
	}

	public void setEventTypes(List<String> eventTypes) {

		this.eventTypes = eventTypes;
	}

	public String getReadFrom() {

		return readFrom;
	}

	public void setReadFrom(String readFrom) {

		this.readFrom = readFrom;
	}
}
