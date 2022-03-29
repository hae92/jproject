package mq.manager.main;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import mq.manager.receiver.MqReceiver;


public class App {
	public static void main(String[] args) throws IOException, TimeoutException {
		
		System.out.println("Start");
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare("hello1", false, false, false, null);
		
//		System.out.println("Delete Start");
//		channel.queueDelete("hello1");
//		channel.queueDelete("hello2");
//		channel.queueDelete("hello3");
//		channel.queueDelete("hello4");
//		System.out.println("Delete Complete");
		
		String message = "1";
		
		int messageCount = 10;
		
		for(int count = 0; count < messageCount; count++) {
			channel.basicPublish("", "hello1", null, message.getBytes());
			System.out.println(String.format("	Send : ' %s ' Count : %d", message, (count + 1)));
		}
		
		
		channel.close();
		connection.close();
		
		System.out.println("Wait Message");
		MqReceiver.WaitMessage();
		System.out.println("End");
	}
}
