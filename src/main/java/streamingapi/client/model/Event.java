package streamingapi.client.model;

/**
 * Model for streaming events.
 *
 * @author dancojocaru
 */
public class Event {

	private Metadata metadata;
	private String templateName;

	/**
	 * For sample purpose declared he body as object to cover also the case when the content type is of type JSON
	 * and the body is deserialized into a Map of <key, value> pairs.
	 * If the content type is different than JSON the body it will be deserialized into a String.
	 */
	private Object body;

	public Event(Metadata metadata, String templateName, Object body) {

		this.metadata = metadata;
		this.templateName = templateName;
		this.body = body;
	}

	public Metadata getMetadata() {

		return metadata;
	}

	public void setMetadata(Metadata metadata) {

		this.metadata = metadata;
	}

	public String getTemplateName() {

		return templateName;
	}

	public void setTemplateName(String templateName) {

		this.templateName = templateName;
	}

	public Object getBody() {

		return body;
	}

	public void setBody(Object body) {

		this.body = body;
	}

}
