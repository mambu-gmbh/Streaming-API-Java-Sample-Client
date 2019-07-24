package streamingapi.client.processor;

import static java.lang.System.out;

import java.util.List;

import streamingapi.client.model.Event;

/**
 * Processor that can print events.
 */
public class PrintEventsProcessor implements EventsProcessor {

	@Override
	public void process(List<Event> events) {

		events.forEach(event -> {
			out.println("--------------EVENT RECEIVED-------------");
			out.println("Topic : " + event.getMetadata().getEventType());
			out.println("Template name :" + event.getTemplateName());
			out.println("Template body :" + event.getBody());
		});

	}
}
