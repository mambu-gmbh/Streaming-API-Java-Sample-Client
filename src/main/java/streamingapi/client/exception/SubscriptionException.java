package streamingapi.client.exception;

import java.io.IOException;

/**
 * Occurs while creating a subscription.
 *
 * @author dancojocaru
 */
public class SubscriptionException extends IOException {

	public SubscriptionException(String message) {

		super(message);
	}
}
