package org.digieye.kafka;

public abstract class KafkaSubject {

	// methods to register and unregister observers
	protected abstract void register(KafkaChannel ch);

	protected abstract void unregister(KafkaChannel ch);

	// method to notify observers of change
	protected abstract void notifyChannels(KafkaMessage message);

}
