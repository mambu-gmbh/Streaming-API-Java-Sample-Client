package streamingapi.client;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import streamingapi.client.exception.SubscriptionException;
import streamingapi.client.http.HttpClient;
import streamingapi.client.model.Subscription;
import streamingapi.client.processor.PrintEventsProcessor;

public class StreamingApiClientShould {

	public static final String TEST_APPLICATION_NAME = "testApplication";
	public static final String STREAMING_API_KEY = "bmFqAxsQLUSQfOI7I2EHXtDPfeofJd3X";
	public static final String CLIENT_CREATED_TOPIC = "mrn.event.demo_tenant.streamingapi.client_created";
	public static final String DATA_ACCESS_STATE_CHANGED_TOPIC = "mrn.event.demo_tenant.streamingapi.data_access_state_changed";

	private Gson gson;

	private HttpClient httpClient;

	private StreamingApiClient client;

	private PrintEventsProcessor processor;

	@Before
	public void setup() {

		gson = new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create();
		httpClient = new HttpClient();
		processor = new PrintEventsProcessor();
		client = new StreamingApiClient(httpClient, gson, processor);
	}

	@Test
	public void create_subscription() throws IOException {

		List<String> topics = asList(CLIENT_CREATED_TOPIC, DATA_ACCESS_STATE_CHANGED_TOPIC);
		Subscription subscription = new Subscription(topics, TEST_APPLICATION_NAME);

		Subscription createdSubscription = client.createSubscription(subscription, STREAMING_API_KEY);

		assertThat(createdSubscription, notNullValue());
		assertThat(createdSubscription.getId(), not(isEmptyOrNullString()));
		assertThat(createdSubscription.getOwningApplication(), is(TEST_APPLICATION_NAME));
		assertThat(createdSubscription.getReadFrom(), is(subscription.getReadFrom()));
		assertThat(createdSubscription.getEventTypes(), containsInAnyOrder(CLIENT_CREATED_TOPIC, DATA_ACCESS_STATE_CHANGED_TOPIC));
	}


	@Test(expected = SubscriptionException.class)
	public void throw_subscription_exception_when_creation_fails() throws IOException {

		List<String> topics = singletonList("invalid_topic");
		Subscription subscription = new Subscription(topics, TEST_APPLICATION_NAME);

		client.createSubscription(subscription, STREAMING_API_KEY);
	}

	@Test
	public void consume_events() throws Exception {

		List<String> topics = asList(CLIENT_CREATED_TOPIC, DATA_ACCESS_STATE_CHANGED_TOPIC);
		Subscription subscription = new Subscription(topics, TEST_APPLICATION_NAME);

		Subscription createdSubscription = client.createSubscription(subscription, STREAMING_API_KEY);

		client.consumeEvents(createdSubscription.getId(), STREAMING_API_KEY);
	}

}