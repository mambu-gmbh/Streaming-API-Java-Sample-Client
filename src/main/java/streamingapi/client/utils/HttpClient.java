package streamingapi.client.utils;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Http client wrapper that handles the execution of the provided request.
 */
public class HttpClient {

	private static final String EMPTY_RESPONSE = "";

	public Response executeRequest(HttpRequestBase httpRequest) {

		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response;
		try {
			response = client.execute(httpRequest);

			final String rawResponse = response.getEntity() == null ? EMPTY_RESPONSE : EntityUtils.toString(response.getEntity());

			return new Response(response.getStatusLine().getStatusCode(), rawResponse);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
