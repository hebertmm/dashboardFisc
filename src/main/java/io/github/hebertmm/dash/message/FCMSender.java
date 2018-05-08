package io.github.***REMOVED***mm.dash.message;

import org.jivesoftware.smackx.gcm.packet.GcmPacketExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FCMSender implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(FCMSender.class);
    @Override
    public void run(String... strings) throws Exception {
        log.info("teste");
        /*ApplicationContext context = new ClassPathXmlApplicationContext("integration.xml");
        MessageChannel channel = context.getBean("xmppOutbond", MessageChannel.class);
        Map<String,String> map = new HashMap<>();
        map.put("mensagem", "Esta e uma mensagem de texto");
        CcsOutMessage outMessage = new CcsOutMessage("dFm4U_YygyE:APA91bH_q2MqVDBGfDhf-BZtjCpqd8C1YjVPLqBs4d32_DfwHvTXDh7irA-9p5B3-92FtmME47lQ27npaVIg7ZMbJYsI31X6nrETeA7r9bUcNjjdobW2VPcwKtoeqgs4lSVktR7oW-hE",
                "000099",map);
        Map<String, String> notificationPayload = new HashMap<>();
        notificationPayload.put("title", "Notificação teste");
        notificationPayload.put("body", "Este é o corpo da mensagem de notificação");
        outMessage.setNotificationPayload(notificationPayload);
        org.jivesoftware.smack.packet.Message message1 = new org.jivesoftware.smack.packet.Message();
        message1.addExtension(new GcmPacketExtension(MessageMapper.toJsonString(outMessage)));
        Message<org.jivesoftware.smack.packet.Message> msgFinal = new GenericMessage<org.jivesoftware.smack.packet.Message>(message1);
        */
        // send the message to the inputChannel
        //final boolean send = channel.send(msgFinal);
    }
}
