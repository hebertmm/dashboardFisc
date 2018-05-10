package io.github.***REMOVED***mm.dash.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.***REMOVED***mm.dash.domain.MyMessage;
import io.github.***REMOVED***mm.dash.domain.MyMessageRepository;
import io.github.***REMOVED***mm.dash.domain.Team;
import io.github.***REMOVED***mm.dash.domain.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.xmpp.XmppHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;


@Component("fcmReceiver")
public class FCMReceiver {
	@Autowired
	private MyMessageRepository myMessageRepository;
	@Autowired
	private TeamRepository teamRepository;

	public void get(Message<String> message) {

		MessageHeaders headers = message.getHeaders();
		System.out.println("Number of headers: " + headers.size());
		for(String key:headers.keySet()){
			System.out.println(key);
		}
		System.out.println("MyMessage type:" + headers.get(XmppHeaders.TYPE));
		System.out.println("From: " + headers.get("xmpp_from"));
		System.out.println("To: " + headers.get("xmpp_to"));
		System.out.println("Timestamp: " + headers.getTimestamp());
		System.out.println("ID: " + headers.getId());
		System.out.println("Payload: " + message.getPayload());
		ObjectMapper om = new ObjectMapper();
		try {
			JsonNode node = om.readTree(message.getPayload());
			if(!node.has("message_type"))
				dataMessage(message);
			else {
				switch (node.get("message_type").textValue()) {
					case "ack":
						handleAck(message);
						break;
					case "nack":
						handleNack(message);
						break;
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}


	}

	public void dataMessage(Message<String> message){
		ObjectMapper om = new ObjectMapper();
		try {
			JsonNode node = om.readTree(message.getPayload());
			Team team = teamRepository.findById(node.get("data").get("team_id").asInt())
					.orElseThrow(() -> new ResourceAccessException("team"));
			MyMessage myMessage = new MyMessage();
			myMessage.setFirebaseId(node.get("message_id").asInt());
			myMessage.setText(node.get("data").get("message").asText());
			myMessage.setTimestamp(message.getHeaders().getTimestamp());
			myMessage.setType("received");
			myMessage.setTeam(team);
			myMessageRepository.save(myMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (ResourceAccessException e){
			e.printStackTrace();
		}
	}
	public void handleAck(Message<String> message){}
	public void handleNack(Message<String> message){}
}
