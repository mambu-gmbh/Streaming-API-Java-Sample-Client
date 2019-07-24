package streamingapi.client.utils;

/**
 * Http response wrapper.
 */
public class Response {

	private int statusCode;

	private String response;

	public Response(int statusCode, String response) {

		this.statusCode = statusCode;
		this.response = response;
	}


	public int getStatusCode() {

		return statusCode;
	}

	public void setStatusCode(int statusCode) {

		this.statusCode = statusCode;
	}

	public String getResponse() {

		return response;
	}

	public void setResponse(String response) {

		this.response = response;
	}
}
