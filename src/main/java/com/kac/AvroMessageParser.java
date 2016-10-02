/*******************************************************************************************************
 * 
 * Copyright (C) TrendSutra Platform Services Private Limited,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.json.JSONObject;

/**
* TODO : insert comment here
* 
* @author vipul
* @date 02-Oct-2016
*/

public class AvroMessageParser implements MessageParser
{
	private Schema schema;
	
	public AvroMessageParser(Schema schema)
	{
		this.schema = schema;
	}
	public JSONObject parse(byte[] receivedMessage) throws ParseException
	{
        try 
        {
			DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
			Decoder decoder = DecoderFactory.get().binaryDecoder(receivedMessage, null);
			GenericRecord payload2 = null;
			payload2 = reader.read(null, decoder);
			JSONObject jsonObj = new JSONObject(payload2.toString());
			return jsonObj;
		} 
        catch (IOException e) 
        {
			throw new ParseException(e);
		}
	}
}
