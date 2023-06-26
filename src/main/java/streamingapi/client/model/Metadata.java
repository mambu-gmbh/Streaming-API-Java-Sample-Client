package streamingapi.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for metadata of an event.
 *
 * @author dancojocaru
 */
public class Metadata {

	private String eid;
	@JsonProperty("occurred_at")
	private String occurredAt;
	@JsonProperty("content_type")
	private String contentType;
	private String category;
	@JsonProperty("event_type")
	private String eventType;

	public String getEid() {

		return eid;
	}

	public void setEid(String eid) {

		this.eid = eid;
	}

	public String getOccurredAt() {

		return occurredAt;
	}

	public void setOccurredAt(String occurredAt) {

		this.occurredAt = occurredAt;
	}

	public String getContentType() {

		return contentType;
	}

	public void setContentType(String contentType) {

		this.contentType = contentType;
	}

	public String getCategory() {

		return category;
	}

	public void setCategory(String category) {

		this.category = category;
	}

	public String getEventType() {

		return eventType;
	}

	public void setEventType(String eventType) {

		this.eventType = eventType;
	}
}
