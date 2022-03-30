package mq.manager.main;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import mq.manager.sender.MqSender;
import mq.manager.receiver.MqReceiver;

import mq.manager.util.MiMQConnectionFactory;

public class App {
	public static void main(String[] args) throws IOException, TimeoutException {
		
		// 기준 정보
		String mqServerIP = "127.0.0.1";
		String mqName = "MessageTest";
		
		// Add Factory
		MiMQConnectionFactory miFactory = new MiMQConnectionFactory();
		miFactory.addHost(mqServerIP);
		miFactory.getConnection();
		Channel channel = miFactory.getChannel();
		miFactory.addQueue(channel, mqName);
		
		String messageBase = "Test Message";	
		String message = String.format("%s", messageBase);
		
		MqSender.SendMessage(channel, mqName, message);
		
		//channel.close();
		//connection.close();	

		MqReceiver.WaitMessage(channel, mqName);		
	}
}
