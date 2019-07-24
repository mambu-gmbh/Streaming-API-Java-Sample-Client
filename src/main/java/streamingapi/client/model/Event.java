package streamingapi.client.model;

/**
 * Model for streaming events.
 */
public class Event {

	private Metadata metadata;
	private String templateName;
	private String body;

	public Event(Metadata metadata, String templateName, String body) {
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

	public String getBody() {

		return body;
	}

	public void setBody(String body) {

		this.body = body;
	}


}
