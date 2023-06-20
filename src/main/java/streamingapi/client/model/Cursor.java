package streamingapi.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for streaming cursor that contains data about the offset of the events.
 */
public class Cursor {

	@JsonProperty("event_type")
	private String eventType;
	@JsonProperty("cursor_token")
	private String cursorToken;
	private String offset;
	private String partition;

	public String getEventType() {

		return eventType;
	}

	public void setEventType(String eventType) {

		this.eventType = eventType;
	}

	public String getCursorToken() {

		return cursorToken;
	}

	public void setCursorToken(String cursorToken) {

		this.cursorToken = cursorToken;
	}

	public String getOffset() {

		return offset;
	}

	public void setOffset(String offset) {

		this.offset = offset;
	}

	public String getPartition() {

		return partition;
	}

	public void setPartition(String partition) {

		this.partition = partition;
	}
}
