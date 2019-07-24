package streamingapi.client.processor;

import java.util.List;

import streamingapi.client.model.Event;

/**
 * Events processor.
 */
public interface EventsProcessor {

	/**
	 * Process a given list of events.
	 *
	 * @param events Events to process.
	 */
	void process(List<Event> events);
}
