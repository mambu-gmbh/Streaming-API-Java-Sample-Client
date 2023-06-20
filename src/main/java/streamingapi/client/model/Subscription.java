package streamingapi.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

/**
 * Model holding information needed for creating a subscription.
 *
 * @author dancojocaru
 */
public class Subscription {

	private String id;
	@JsonProperty("owning_application")
	private String owningApplication;
	@JsonProperty("event_types")
	private List<String> eventTypes;
	@JsonProperty("consumer_group")
	private String consumerGroup;
	@JsonProperty("read_from")
	private String readFrom = "end";
	@JsonProperty("created_at")
	private Instant createdAt;

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

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
