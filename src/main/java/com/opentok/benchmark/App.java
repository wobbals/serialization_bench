package com.opentok.benchmark;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.opentok.benchmark.ProtoTestObject.*;

public class App 
{
    public static void main( String[] args ) 
    {
	try {
	long startTime = System.nanoTime();
	String sampleInputString = "{\"type\":4,\"id\":\"2_MX4xMzExMjU3MX43Mi41LjE2Ny4xNTh-VGh1IE9jdCAxOCAxNToxMzoyOCBQRFQgMjAxMn4wLjMzMjY4NDF-_35797926\",\"data\":{\"type\":3,\"action\":1,\"objectKey\":\"2_MX4xMzExMjU3MX43Mi41LjE2Ny4xNTh-VGh1IE9jdCAxOCAxNToxMzoyOCBQRFQgMjAxMn4wLjMzMjY4NDF-\",\"key\":\"connections\",\"value\":{\"connectionsCount\":2,\"currentConnection\":\"35797926\"},\"currentActionId\":77,\"replyExpected\":true}}";
	ObjectMapper mapper = new ObjectMapper();
	JsonNode node = mapper.readTree(sampleInputString);
	long readTime =  System.nanoTime() ;
       	String sampleOutputString = mapper.writeValueAsString(node);
	long writeTime = System.nanoTime();
	
	long jsonParseTime = readTime - startTime;
	long jsonWriteTime = writeTime - readTime;
	
	System.out.println("input: "+sampleInputString);
	System.out.println("output: "+sampleOutputString);
	
	TestObject testObject = 
	    TestObject.newBuilder()
	    .setType(TestObject.Type.FOUR)
	    .setId("2_MX4xMzExMjU3MX43Mi41LjE2Ny4xNTh-VGh1IE9jdCAxOCAxNToxMzoyOCBQRFQgMjAxMn4wLjMzMjY4NDF-_35797926")
	    .setData(InnerObject.newBuilder()
		     .setType(InnerObject.Type.THREE)
		     .setAction(InnerObject.Type.ONE)
		     .setObjectKey("2_MX4xMzExMjU3MX43Mi41LjE2Ny4xNTh-VGh1IE9jdCAxOCAxNToxMzoyOCBQRFQgMjAxMn4wLjMzMjY4NDF-")
		     .setCurrentActionId(77)
		     .setReplyExpected(true)
		     .setValue(InnerInnerObject.newBuilder()
			       .setConnectionsCount(2)
			       .setCurrentConnection("35797926")
			       .build())
		     .build()
		     ).build();
	
	System.out.println("testObject: "+testObject.toString());
	startTime = System.nanoTime();
	byte[] testObjectEncoded = testObject.toByteArray();
	writeTime = System.nanoTime();
	TestObject testObjectParsed = TestObject.parseFrom(testObjectEncoded);
	readTime = System.nanoTime();

	long protobufWriteTime = writeTime - startTime;
	long protobufParseTime = readTime - writeTime;

	System.out.printf("json parsing completed in %d ns\n", jsonParseTime);
	System.out.printf("json writing completed in %d ns\n", jsonWriteTime);
	System.out.printf("total json test time: %d ns\n", jsonParseTime+jsonWriteTime);

	System.out.printf("protobuf parsing completed in %d ns\n", protobufParseTime);
	System.out.printf("protobuf writing completed in %d ns\n", protobufWriteTime);
	System.out.printf("total protobuf test time: %d ns\n", protobufParseTime+protobufWriteTime);
	System.out.println("-------");
	System.out.println("json parse / protobuf parse = " + jsonParseTime / protobufParseTime);
	System.out.println("json write / protobuf write = " + jsonWriteTime / protobufWriteTime);
	System.out.println("total json / total protobuf = " + (jsonParseTime + jsonWriteTime) / (protobufWriteTime + protobufParseTime));

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
