package org.digieye.kafka;

public interface KafkaChannel {
	public void onReceive(KafkaMessage message);
}
