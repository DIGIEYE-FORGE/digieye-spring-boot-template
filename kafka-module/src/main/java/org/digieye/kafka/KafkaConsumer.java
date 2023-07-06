package org.digieye.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumer {
	private KafkaTopicSubject kafkaTopicSubject;

	@KafkaListener(topics = "${kafka.consumer.topic}", groupId = "${kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
	protected void consume(ConsumerRecord<String, String> payload, Acknowledgment acknowledgment) {

		try {

			// EventDto eventDto = mapper.readValue(payload.value(), EventDto.class);
			log.info("key: {}", payload.key());
			log.info("Headers: {}", payload.headers());
			log.info("Partion: {}", payload.partition());
			log.info("Value: {}", payload.value());
			KafkaMessage message = new KafkaMessage(payload.key(), payload.partition(), payload.value(),
					acknowledgment);

			kafkaTopicSubject.emitMessage(message);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}