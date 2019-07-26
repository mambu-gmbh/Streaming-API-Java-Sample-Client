package streamingapi.client.exception;

import java.io.IOException;

/**
 * Occurs while committing a cursor.
 *
 * @author dancojocaru
 */
public class CommitCursorException extends IOException {

	public CommitCursorException(String message) {

		super(message);
	}
}
