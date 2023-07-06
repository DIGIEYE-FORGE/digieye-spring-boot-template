package org.digieye.kafka;

import org.springframework.kafka.support.Acknowledgment;

import lombok.Getter;

public class KafkaMessage {
	@Getter
	private String key;
	@Getter
	private int partition;
	@Getter
	private String payload;

	private Acknowledgment acknowledgment;

	public KafkaMessage(String key, int partition, String payload, Acknowledgment acknowledgment) {
		super();
		this.key = key;
		this.partition = partition;
		this.payload = payload;
		this.acknowledgment = acknowledgment;
	}

	public void ackMessage() {
		this.acknowledgment.acknowledge();
	}

	@Override
	public String toString() {
		return "KafkaMessage [key=" + key + ", partition=" + partition + ", payload=" + payload + "]";
	}

}
