package streamingapi.client.model;

/**
 * Model for streaming cursor.
 */
public class Cursor {

	private String eventType;
	private String cursorToken;
	private String offset;
	private String partition;

	public Cursor(String eventType, String cursorToken, String offset, String partition) {

		this.eventType = eventType;
		this.cursorToken = cursorToken;
		this.offset = offset;
		this.partition = partition;
	}

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
