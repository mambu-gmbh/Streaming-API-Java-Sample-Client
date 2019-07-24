package streamingapi.client.model;

/**
 * Model for metadata.
 */
public class Metadata {

	private String eid;
	private String occurredAt;
	private String contentType;
	private String category;
	private String eventType;

	public Metadata(String eid, String occurredAt, String contentType, String category, String eventType) {

		this.eid = eid;
		this.occurredAt = occurredAt;
		this.contentType = contentType;
		this.category = category;
		this.eventType = eventType;
	}

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
