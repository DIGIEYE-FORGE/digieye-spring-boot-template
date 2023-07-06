package org.digieye.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;



@EnableKafka
@Configuration
public class KafkaConfig {

	@Value("${kafka.consumer.bootstrap-servers}")
	private String bootstrapAddress;

	@Value("${kafka.consumer.group-id}")
	private String groupId;

	@Value("${kafka.consumer.auto-commit}")
	private String isAutoCommit;

	@Value("${kafka.consumer.offset-reset}")
	private String offsetReset;

	protected KafkaConfig() {

	}

	@Bean
	protected ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, isAutoCommit);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
		props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false");

		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	protected ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		factory.getContainerProperties().getKafkaConsumerProperties().entrySet()
				.forEach(consumer -> System.out.printf("%s=%s \n", consumer.getKey(), consumer.getValue()));
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

}