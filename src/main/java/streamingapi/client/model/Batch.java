package streamingapi.client.model;

import java.util.List;

/**
 * Model for a streaming batch of events received by the client
 *
 * @author dancojocaru
 */
public class Batch {

	private Cursor cursor;
	private List<Event> events;

	public Batch(Cursor cursor, List<Event> events) {

		this.cursor = cursor;
		this.events = events;
	}

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
}
