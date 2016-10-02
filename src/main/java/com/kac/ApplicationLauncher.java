/*******************************************************************************************************
 * 
 * Copyright (C) TrendSutra Platform Services Private Limited,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

/**
* TODO : insert comment here
* 
* @author vipul
* @date 02-Oct-2016
*/

public class ApplicationLauncher
{

	private static final Logger LOGGER = Logger.getLogger(ApplicationLauncher.class);
	
	public static void main(String[] args)  
	{
		String topic = "test";
		
		try 
		{
			final Properties properties = new Properties();
			try (final InputStream stream = ApplicationLauncher.class.getClass().getResourceAsStream("/consumer.properties")) 
			{
			    properties.load(stream);
			}
			
			Schema schema = new Schema.Parser().parse(new File(properties.getProperty("schema.file.path")));
			MessageParser avroMessageParser = new AvroMessageParser(schema);
			
			SimpleHLConsumer simpleHLConsumer = new SimpleHLConsumer(properties, topic, avroMessageParser);
			simpleHLConsumer.startConsumer();
		} 
		catch (Exception e) 
		{
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
