/*******************************************************************************************************
 * 
 * Copyright (C) TrendSutra Platform Services Private Limited,
 * All Rights Reserved Unauthorized copying of this file, 
 * via any medium is strictly prohibited Proprietary and confidential.
 * 
 *******************************************************************************************************/

package com.kac;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
* TODO : insert comment here
* 
* @author vipul
* @date 02-Oct-2016
*/

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class ProducerTest {

    public void producer(Schema schema) throws IOException {

        Properties props = new Properties();
        props.put("bootstrap.servers", "0:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("request.required.acks", "1");
        
        Producer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
        
        GenericRecord payload1 = new GenericData.Record(schema);
        //Step2 : Put data in that genericrecord object
        payload1.put("desc", "'testdata'");
        payload1.put("name", "dbevent1");
        payload1.put("id", 111);
        
        System.out.println("Original Message : "+ payload1);
        
        //Step3 : Serialize the object to a bytearray
        DatumWriter<GenericRecord>writer = new SpecificDatumWriter<GenericRecord>(schema);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(payload1, encoder);
        encoder.flush();
        out.close();

        byte[] serializedBytes = out.toByteArray();
        System.out.println("Sending message in bytes : " + serializedBytes);
        ProducerRecord<String, byte[]> message = new ProducerRecord<String, byte[]>("test", serializedBytes);
        producer.send(message);
        producer.close();

    }

    public static void main(String[] args) throws IOException {
        ProducerTest test = new ProducerTest();
        Schema schema = new Schema.Parser().parse(new File("src/test/resources/payload.avsc"));
        test.producer(schema);
    }
}

