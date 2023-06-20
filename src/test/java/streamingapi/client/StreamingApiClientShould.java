package streamingapi.client;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import streamingapi.client.helper.EventModelObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import streamingapi.client.exception.SubscriptionException;
import streamingapi.client.http.HttpClient;
import streamingapi.client.http.Response;
import streamingapi.client.model.Subscription;
import streamingapi.client.processor.PrintEventsProcessor;

public class StreamingApiClientShould {

	private static final String TEST_APPLICATION_NAME = "testApplication";
	private static final String STREAMING_API_KEY = "PwI5LSrNiMjHMNM5Fhl3FkOU3FOeZLrp";
	private static final String CLIENT_CREATED_TOPIC = "mrn.event.demo_tenant.streamingapi.client_created";
	private static final String DATA_ACCESS_STATE_CHANGED_TOPIC = "mrn.event.demo_tenant.streamingapi.data_access_state_changed";
	private static final int HTTP_NO_CONTENT_SUCCESS = 204;
	private static final int HTTP_NOT_FOUND = 404;

	private HttpClient httpClient;

	private StreamingApiClient client;

	private PrintEventsProcessor processor;

	private StreamMonitor monitor;

	@BeforeEach
	public void setup() {

		httpClient = new HttpClient();
		processor = new PrintEventsProcessor();
		monitor = new StreamMonitor();
		client = new StreamingApiClient(httpClient, new EventModelObjectMapper(), processor, monitor);
	}

	@Test
	public void create_subscription() throws IOException {

		final Subscription subscription = buildSubscription();

		final Subscription createdSubscription = client.createSubscription(subscription, STREAMING_API_KEY);

		assertThat(createdSubscription, notNullValue());
		assertThat(createdSubscription.getId(), not(isEmptyOrNullString()));
		assertThat(createdSubscription.getOwningApplication(), is(TEST_APPLICATION_NAME));
		assertThat(createdSubscription.getReadFrom(), is(subscription.getReadFrom()));
		assertThat(createdSubscription.getEventTypes(), containsInAnyOrder(CLIENT_CREATED_TOPIC, DATA_ACCESS_STATE_CHANGED_TOPIC));
	}

	@Test
	public void throw_subscription_exception_when_creation_fails() {

		final List<String> topics = singletonList("invalid_topic");
		final Subscription subscription = new Subscription();
		subscription.setEventTypes(topics);
		subscription.setOwningApplication(TEST_APPLICATION_NAME);

		assertThrows(SubscriptionException.class, () -> client.createSubscription(subscription, STREAMING_API_KEY));
	}

	@Test
	public void get_success_status_when_delete_subscription() throws IOException {

		final Subscription subscription = buildSubscription();

		final Subscription createdSubscription = client.createSubscription(subscription, STREAMING_API_KEY);

		final Response response = client.deleteSubscription(createdSubscription.getId(), STREAMING_API_KEY);

		assertEquals(HTTP_NO_CONTENT_SUCCESS, response.getStatusCode());
	}

	@Test
	public void get_not_found_status_when_deletion_fails() {

		final String invalidId = "AAA";

		final Response response = client.deleteSubscription(invalidId, STREAMING_API_KEY);

		assertEquals(HTTP_NOT_FOUND, response.getStatusCode());
	}

	@Test
	public void consume_events() throws Exception {

		final Subscription subscription = buildSubscription();

		final Subscription createdSubscription = client.createSubscription(subscription, STREAMING_API_KEY);

		client.consumeEvents(createdSubscription.getId(), STREAMING_API_KEY);
	}

	private Subscription buildSubscription() {

		final List<String> topics = asList(CLIENT_CREATED_TOPIC, DATA_ACCESS_STATE_CHANGED_TOPIC);
		final Subscription subscription = new Subscription();
		subscription.setEventTypes(topics);
		subscription.setOwningApplication(TEST_APPLICATION_NAME);
		return subscription;
	}

}