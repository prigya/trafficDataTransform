package com.accenture.trafficDataTransform;


import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(Processor.class)
@EnableConfigurationProperties(TrafficDataTransformProperties.class)
public class TrafficDataTransformConfiguration {
	
	@Autowired
	private TrafficDataTransformProperties properties;

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Message<String> transform(String payload) throws Exception {
		
		//convert xml payload string to json
		JSONObject msgObj = XML.toJSONObject(payload);
		// now read that json object and get useful info 
		System.out.println("msgObj :"+msgObj);
		
		String message = "{" +
	            "   \"origin\":\""+ msgObj.getJSONObject("DistanceMatrixResponse").getString("origin_address") + "\"," +
	            "   \"destination\":\"" +  msgObj.getJSONObject("DistanceMatrixResponse").getString("destination_address") +"\"," +
	            "   \"duration\":\""+msgObj.getJSONObject("DistanceMatrixResponse").getJSONObject("row")
	            .getJSONObject("element").getJSONObject("duration").getString("text") +"\","+
	            "   \"durationInTraffic\":\""+ msgObj.getJSONObject("DistanceMatrixResponse").getJSONObject("row")
	            .getJSONObject("element").getJSONObject("duration_in_traffic").getString("text") +"\","+
	            "   \"distance\":\"" +msgObj.getJSONObject("DistanceMatrixResponse").getJSONObject("row")
	            .getJSONObject("element").getJSONObject("distance").getString("text")  +"\","+
	            "   \"username\":\""+this.properties.userName.toLowerCase()+"\","+
	            "   \"travellingto\":\""+this.properties.travellingTo.toLowerCase()+
	            "\"}";

			//print result
			System.out.println("\n ********"+message+"********\n");
			//return () -> MessageBuilder.withPayload(response.toString()).build();
			
			
		return MessageBuilder.withPayload(message).build();
		    }

}
