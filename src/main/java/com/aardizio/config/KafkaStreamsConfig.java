package com.aardizio.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Config class to build kafka streams.
 * @author alessandropioardizio
 *
 */
@Configuration
@Slf4j
public class KafkaStreamsConfig {

	@Autowired
	private KafkaProperties kafkaProperties;
	
	
	/* @Bean("simpleStream")
	public KafkaStreams simpleStream() {
		
		Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "simplestreamid");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        StreamsConfig streamsConfig = new StreamsConfig(props);
        Serde<String> stringSerde = Serdes.String();
        // Building streams
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> simpleFirstStream = builder.stream("src-topic", Consumed.with(stringSerde, stringSerde));
        KStream<String, String> upperCasedStream = simpleFirstStream.mapValues(String::toUpperCase);
        upperCasedStream.to( "out-topic", Produced.with(stringSerde, stringSerde));
        upperCasedStream.print(Printed.<String, String>toSysOut().withLabel("Yelling App"));
        log.info("Requesting a simple kafka stream");
		return new KafkaStreams(builder.build(),streamsConfig);
	} */
	
}
