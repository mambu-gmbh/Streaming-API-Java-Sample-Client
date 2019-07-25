package streamingapi.client;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;

import streamingapi.client.exception.CommitCursorException;
import streamingapi.client.exception.SubscriptionException;
import streamingapi.client.http.HttpClient;
import streamingapi.client.http.Response;
import streamingapi.client.model.Batch;
import streamingapi.client.model.Cursor;
import streamingapi.client.model.CursorWrapper;
import streamingapi.client.model.Subscription;
import streamingapi.client.processor.EventsProcessor;

/**
 * Client in charge with streaming api operations.
 * <ul>
 * <li>
 * Create subscriptions: The subscription is needed to be able to consume events from EventTypes.
 * </li>
 * <li>
 * Consume events: Starts a new stream for reading events from this subscription. The data will be
 * automatically rebalanced between streams of one subscription.
 * </li>
 * <li>
 * Commit cursor:  After a batched is processed the cursor is committed. The commit uses the endpoint
 * for committing offsets of the subscription.
 * </li>
 * </ul>
 *
 * @author dancojocaru
 */
public class StreamingApiClient {

	String MAMBU_ENDPOINT = "http://demo_tenant.mambuonline.com:8000";
	String SUBSCRIPTION_ENDPOINT = MAMBU_ENDPOINT + "/api/v1/subscriptions";
	String EVENTS_ENDPOINT = SUBSCRIPTION_ENDPOINT + "/%s/events?batch_flush_timeout=1&batch_limit=1";
	String CURSORS_ENDPOINT = SUBSCRIPTION_ENDPOINT + "/%s/cursors";
	String CONTENT_TYPE = "Content-Type";
	String CONTENT_TYPE_VALUE = "application/json";

	String API_KEY = "apikey";
	String MAMBU_STREAM_ID_HEADER_FIELD = "X-Mambu-StreamId";

	private Gson gson;

	private HttpClient client;

	private EventsProcessor processor;

	/**
	 * @param client    Http client used to make http requests.
	 * @param gson      Gson instance used to serialize/deserialize request and responses.
	 * @param processor A processor that handles the received events when listening to a stream.
	 */
	public StreamingApiClient(HttpClient client, Gson gson, EventsProcessor processor) {

		this.client = client;
		this.gson = gson;
		this.processor = processor;
	}

	/**
	 * Create subscription
	 *
	 * @param subscription    Subscription to create.
	 * @param streamingApiKey Streaming api key used to for authentication.
	 * @return created subscription
	 */
	public Subscription createSubscription(Subscription subscription, String streamingApiKey) throws IOException {

		HttpPost httpPost = buildSubscriptionHttpPost(subscription, streamingApiKey);

		Response response = client.executeRequest(httpPost);

		return getSubscription(response);
	}


	/**
	 * Consume events of based on a subscription.
	 *
	 * @param subscriptionId  subscription id
	 * @param streamingApiKey Streaming api key used to for authentication.
	 */
	public void consumeEvents(String subscriptionId, String streamingApiKey) throws Exception {

		URLConnection connection = createConnection(subscriptionId, streamingApiKey);
		String streamId = connection.getHeaderField(MAMBU_STREAM_ID_HEADER_FIELD);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

			String inputLine;

			while ((inputLine = reader.readLine()) != null) {

				Batch batch = gson.fromJson(inputLine, Batch.class);

				if (nonNull(batch) && nonNull(batch.getEvents())) {

					processor.process(batch.getEvents());
					Response response = commitCursor(batch.getCursor(), streamId, subscriptionId, streamingApiKey);
					if (isCommitCursorSuccessful(response)) {
						throw new CommitCursorException("Error while committing cursor.");
					}
					out.println("Cursor committed. Response status code: " + response.getStatusCode());
				}
			}
		}
	}


	private URLConnection createConnection(String subscriptionId, String streamingApiKey) throws IOException {

		URL url = new URL(format(EVENTS_ENDPOINT, subscriptionId));
		URLConnection connection = url.openConnection();
		connection.setRequestProperty(API_KEY, streamingApiKey);
		return connection;
	}

	private Response commitCursor(Cursor cursor, String streamId, String subscriptionId, String streamingApiKey)
			throws Exception {

		HttpPost httpPost = createCursorsHttpPost(streamId, subscriptionId, streamingApiKey);
		String jsonBody = gson.toJson(new CursorWrapper(singletonList(cursor)));
		httpPost.setEntity(new StringEntity(jsonBody));
		return client.executeRequest(httpPost);
	}

	private HttpPost createCursorsHttpPost(String streamId, String subscriptionId, String streamingApiKey) {

		HttpPost httpPost = new HttpPost(format(CURSORS_ENDPOINT, subscriptionId));
		httpPost.setHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
		httpPost.setHeader(MAMBU_STREAM_ID_HEADER_FIELD, streamId);
		httpPost.setHeader(API_KEY, streamingApiKey);
		return httpPost;
	}


	private HttpPost buildSubscriptionHttpPost(Subscription subscription, String streamingApiKey) throws UnsupportedEncodingException {

		StringEntity subscriptionStringEntity = toStringEntity(subscription);
		HttpPost httpPost = getHttpPost(streamingApiKey);
		httpPost.setEntity(subscriptionStringEntity);
		return httpPost;
	}

	private StringEntity toStringEntity(Subscription subscription) throws UnsupportedEncodingException {

		String jsonBody = gson.toJson(subscription);
		return new StringEntity(jsonBody);
	}

	private HttpPost getHttpPost(String streamingApiKey) {

		HttpPost httpPost = new HttpPost(SUBSCRIPTION_ENDPOINT);
		httpPost.setHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
		httpPost.setHeader(API_KEY, streamingApiKey);
		return httpPost;
	}

	private Subscription getSubscription(Response response) throws SubscriptionException {

		int statusCode = response.getStatusCode();
		if (isCreateSubscriptionSuccessful(statusCode)) {
			throw new SubscriptionException(response.getResponse());
		}
		return gson.fromJson(response.getResponse(), Subscription.class);
	}

	private boolean isCommitCursorSuccessful(Response response) {

		return response.getStatusCode() != SC_NO_CONTENT && response.getStatusCode() != SC_OK;
	}

	private boolean isCreateSubscriptionSuccessful(int statusCode) {

		return statusCode != SC_OK && statusCode != SC_CREATED;
	}
}
