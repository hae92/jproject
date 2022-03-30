package mq.manager.receiver;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MqReceiver {
	
	public static void WaitMessage(Channel channel, String mqName) throws IOException, TimeoutException {
		
		try { 
	        channel.queueDeclare(mqName, false, false, false, null);
	        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

	        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	        	String logText = "";
	            String message = new String(delivery.getBody());
	            
	            logText = String.format("[Channel Number %d] Dequeue Object : %s", channel.getChannelNumber(), message);
	            System.out.println(logText);
	        };
	        channel.basicConsume(mqName, true, deliverCallback, consumerTag -> { });			
		}
		catch(Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
