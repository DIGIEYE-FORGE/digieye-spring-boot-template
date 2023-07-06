package org.digieye.kafka;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class KafkaTopicSubject extends KafkaSubject {
	private List<KafkaChannel> channels;
	private final Object MUTEX = new Object();

	protected KafkaTopicSubject() {
		this.channels = new ArrayList<>();
	}

	@Override
	protected void register(KafkaChannel ch) {
		if (ch == null)
			throw new NullPointerException("Null Observer");
		synchronized (MUTEX) {
			if (!channels.contains(ch))
				channels.add(ch);
		}
	}

	@Override
	protected void unregister(KafkaChannel ch) {
		synchronized (MUTEX) {
			channels.remove(ch);
		}
	}

	@Override
	protected void notifyChannels(KafkaMessage message) {
		for (KafkaChannel ch : channels) {
			ch.onReceive(message);
		}
	}

	// method to post message to the topic
	protected void emitMessage(KafkaMessage message) {
		notifyChannels(message);
	}

	public void subscribe(KafkaChannel ch) {
		register(ch);
	}

	public void unsubscribe(KafkaChannel ch) {
		unregister(ch);
	}

}
