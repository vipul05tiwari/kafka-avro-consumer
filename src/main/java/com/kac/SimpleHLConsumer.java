/*******************************************************************************************************
 * 
 * Copyright (C) Vipul Tiwari,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

/**
 * Kafka message consumer.
 * 
 * @author vipul
 * @date 02-Oct-2016
 */

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleHLConsumer 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLauncher.class);
	
	private final ConsumerConnector consumer;
	private final String topic;
	private final MessageParser avroMessageParser;

	public SimpleHLConsumer(Properties props, String topic, MessageParser avroMessageParser) 
	{
		consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
		this.topic = topic;
		this.avroMessageParser = avroMessageParser;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void startConsumer() throws ParseException 
	{
		Map<String, Integer> topicCount = new HashMap<>();
		topicCount.put(topic, 1);

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
		List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
		for (final KafkaStream stream : streams) 
		{
			ConsumerIterator<byte[], byte[]> it = stream.iterator();
			while (it.hasNext()) 
			{
				MessageAndMetadata<byte[], byte[]> messageAndMetadata = it.next();
				try
				{
					JSONObject jsonObject = avroMessageParser.parse(messageAndMetadata.message());
					LOGGER.info("Message offset : {} message : {}", messageAndMetadata.offset(), jsonObject.toString());
				}
				catch(Exception exception)
				{
					LOGGER.error("Error while parsing message with offset : {} Exception : {}", messageAndMetadata.offset(), ExceptionUtils.getStackTrace(exception));
				}
			}
		}
		if (consumer != null) 
		{
			consumer.shutdown();
		}
	}
}
