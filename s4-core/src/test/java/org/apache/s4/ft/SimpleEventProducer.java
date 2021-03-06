package org.apache.s4.ft;

import org.apache.s4.collector.EventWrapper;
import org.apache.s4.listener.EventHandler;
import org.apache.s4.listener.EventProducer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleEventProducer implements EventProducer {

	private Set<org.apache.s4.listener.EventHandler> handlers = new HashSet<org.apache.s4.listener.EventHandler>();
	private String streamName;

	LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	public void init() {
	}

	@Override
	public void addHandler(EventHandler handler) {
		handlers.add(handler);

	}

	@Override
	public boolean removeHandler(EventHandler handler) {
		return handlers.remove(handler);
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public String getStreamName() {
		return streamName;
	}

	// TODO JSON-like stuff
	public void produceEvent(String message) {
		EventWrapper ew = new EventWrapper(streamName, message, null);
		for (org.apache.s4.listener.EventHandler handler : handlers) {
			try {
				handler.processEvent(ew);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


}
