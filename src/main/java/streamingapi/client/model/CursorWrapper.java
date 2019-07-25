package streamingapi.client.model;

import java.util.List;

/**
 * Model for streaming cursor wrapper.
 *
 * @author dancojocaru
 */
public class CursorWrapper {

	private List<Cursor> items;

	public CursorWrapper(List<Cursor> items) {

		this.items = items;
	}

	public List<Cursor> getItems() {

		return items;
	}

	public void setItems(List<Cursor> items) {

		this.items = items;
	}
}
