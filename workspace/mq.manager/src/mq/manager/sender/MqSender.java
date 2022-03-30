package mq.manager.sender;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MqSender {
	
	public static void SendMessage(Channel channel, String mqName, String message) throws IOException, TimeoutException {
		
		try {
	        String logText = "";
			channel.basicPublish("", mqName, null, message.getBytes());
			
			logText = String.format("[Channel Number %d] EnQueue Message, Content : %s ", channel.getChannelNumber(), message);
			System.out.println(logText);		
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
