package mq.manager.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import mq.manager.sender.MqSender;
import mq.manager.receiver.MqReceiver;

import mq.manager.util.MiMQTask;
import mq.manager.util.MiMQWorker;
import mq.manager.util.MQReceiveTask;
import mq.manager.util.MiMQConnectionFactory;

public class App {
	public static void main(String[] args) throws IOException, TimeoutException {
		
		log("Start App");

		List<MiMQTask> tasks = List.of(
				new MQReceiveTask(100), 
				new MQReceiveTask(200),
				new MQReceiveTask(300),
				new MQReceiveTask(400));
		
		log("Add Task");
		
		//var executor = Executors.newFixedThreadPool(3);
		var executor = Executors.newFixedThreadPool(tasks.size());
		
		tasks.stream().map(MiMQWorker::new).forEach(executor::execute);
		
		log("Execute Task");
		
		executor.shutdown();
		
		log("Shutdown Task");
		
		int loopCount = 0;
		while(!executor.isTerminated()) {
			Thread.yield();
			
			//if(loopCount++ % 1000 == 0) log(loopCount);			
		}
		
		// 기준 정보
		String mqServerIP = "127.0.0.1";
		String mqName = "MessageTest";
		
		// Add Factory
		MiMQConnectionFactory miFactory = new MiMQConnectionFactory();
		miFactory.addHost(mqServerIP);
		miFactory.getConnection();
		Channel channel = miFactory.getChannel();
		miFactory.addQueue(channel, mqName);
		
//		miFactory.addQueue(channel, "Test1");
//		miFactory.addQueue(channel, "Test2");
//		miFactory.addQueue(channel, "Test3");
//		miFactory.addQueue(channel, "Test4");
//		miFactory.addQueue(channel, "Test5");
		
		
		miFactory.getFactoryProperties();
		miFactory.getConnectionFactoryHostName();
		
		
		String messageBase = "Test Message";	
		String message = String.format("%s", messageBase);
		
		MqSender.SendMessage(channel, mqName, message);
		
//		MqSender.SendMessage(channel, "Test1", message);
//		MqSender.SendMessage(channel, "Test2", message);
//		MqSender.SendMessage(channel, "Test3", message);
//		MqSender.SendMessage(channel, "Test4", message);
//		MqSender.SendMessage(channel, "Test5", message);
		
		
		//channel.close();
		//connection.close();	

		MqReceiver.WaitMessage(channel, mqName);
		
//		MqReceiver.WaitMessage(channel, "Test1");
//		MqReceiver.WaitMessage(channel, "Test2");
//		MqReceiver.WaitMessage(channel, "Test3");
//		MqReceiver.WaitMessage(channel, "Test4");
//		MqReceiver.WaitMessage(channel, "Test5");
		
		//miFactory.dispose();
	}
	
	private static void log(String content) {
		String logText = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		logText = String.format("[%s] %s" , df.format(new Date()), content);
		System.out.println(logText);
	}
	
	private static void log(int content) {
		String logText = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		logText = String.format("[%s] %s" , df.format(new Date()), content);
		System.out.println(logText);
	}
}
