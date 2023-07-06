package org.digieye.application;

import org.digieye.kafka.KafkaChannel;
import org.digieye.kafka.KafkaMessage;
import org.digieye.kafka.KafkaTopicSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.digieye")
public class Application implements CommandLineRunner {

	@Autowired
	private KafkaTopicSubject kafkaTopicSubject;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		KafkaChannel ch = new KafkaChannel() {

			@Override
			public void onReceive(KafkaMessage message) {
				System.out.println(message.toString());
				message.ackMessage();
			}
		};

		this.kafkaTopicSubject.subscribe(ch);
	}

}
