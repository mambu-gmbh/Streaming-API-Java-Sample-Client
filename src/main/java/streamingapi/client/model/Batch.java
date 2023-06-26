package streamingapi.client.model;

import java.util.List;

/**
 * Model for a streaming batch of events received by the client
 */
public class Batch {

	private Cursor cursor;
	private List<Event> events;
	private Info info;

	public Cursor getCursor() {

		return cursor;
	}

	public void setCursor(Cursor cursor) {

		this.cursor = cursor;
	}

	public List<Event> getEvents() {

		return events;
	}

	public void setEvents(List<Event> events) {

		this.events = events;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}
}
