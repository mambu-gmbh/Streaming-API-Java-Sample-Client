package streamingapi.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for streaming events.
 */
public class Event {

	private Metadata metadata;
	@JsonProperty("template_name")
	private String templateName;

	/**
	 * For sample purpose declared he body as object to cover also the case when the content type is of type JSON
	 * and the body is deserialized into a Map of <key, value> pairs.
	 * If the content type is different from JSON the body it will be deserialized into a String.
	 */
	private String body;

	public Event() {
	}

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
