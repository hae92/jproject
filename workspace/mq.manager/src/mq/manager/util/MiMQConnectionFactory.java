package mq.manager.util;

import java.util.HashMap;
import java.util.ArrayList;

import java.util.HashMap;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.Queue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MiMQConnectionFactory {
	
	private ConnectionFactory _connectionFactory;
	private Connection _connection;
	private Channel _channel;
	private HashMap<Number, Channel> _channelList;
	
	public MiMQConnectionFactory() {
		_channelList = new HashMap<Number, Channel>();
		_connectionFactory = new ConnectionFactory();
	}
	
	public ConnectionFactory getConnectionFactory() {
		return _connectionFactory;
	}
	
	public Boolean addHost(String ip) {
		String logText = "";
		Boolean result = false;
		// Connection Factory 당 Host는 1개만 존재할 수 있음
		_connectionFactory.setHost(ip);
	
		logText = String.format("Add Host IP : %s", ip);
		System.out.println(logText);
		
		return result;
	}
	
	public void getFactoryProperties() {
		for(String key : _connectionFactory.getClientProperties().keySet()) {
			var propsValue = _connectionFactory.getClientProperties().get(key);
			String logText = "";
			logText = String.format("%s : %s", key, propsValue.toString());
			System.out.println(logText);
		}
	}
	
	public void Test() { 
		
	}
	
	
	public void getConnectionFactoryHostName() {
		String logText = "";
		String hostName = _connectionFactory.getHost();
		
		logText = String.format("Host Name : %s", hostName);
		System.out.println(logText);
	}
	
	public Connection getConnection() throws IOException, TimeoutException {
		String logText = "";
		_connection = _connectionFactory.newConnection();
		
		logText = String.format("Add Connection : %s", _connection.getAddress());
		System.out.println(logText);
		
		return _connection;
	}
	
	public Channel getChannel() throws IOException, TimeoutException {
		String logText = "";
		_channel = _connection.createChannel();
		
		logText = String.format("Get Channel : %s", _channel.getChannelNumber());
		addChannel(_channel);
		System.out.println(logText);
		
		return _channelList.get(_channel.getChannelNumber());
	}
	
	public Boolean searchQueue(Channel channel, String queueName) throws IOException {
		String logText = "";
		Boolean result = false;
		
		try {
			Queue.DeclareOk validQueue =  channel.queueDeclarePassive(queueName);
			result = (validQueue == null) ? false : true;	
		}
		catch (Exception ex) {
			result = false;
		}
		
		logText = String.format("[Channel Number %d] Search Queue : %s, Result : %s ", channel.getChannelNumber(), queueName, result);
		System.out.println(logText);
		return result;
	}
	
	public void addQueue(Channel channel, String queueName) throws IOException {
		
		System.out.print("Add Queue     \r\n");
		String logText = "";
		channel.queueDeclare(queueName, false, false, false, null);
		
		logText = String.format("[Channel Number %d] Add Queue : %s", channel.getChannelNumber(), queueName);
		System.out.println(logText);
	}
	
	public void getQueue(Channel channel, String queueName) {
		// None Implement
	}
	
	public void EnqueueMessage(Channel channel, String queueName, String message) throws IOException {
		String logText = "";
		channel.basicPublish("", queueName, null, message.getBytes());
		
		logText = String.format("[Channel Number %d] EnQueue Message, Content : %s ", channel.getChannelNumber(), message);
		System.out.println(logText);
	}
	
	private void addChannel(Channel channel) {
		if(!_channelList.containsKey(channel.getChannelNumber())) {
			_channelList.put(channel.getChannelNumber(), channel);
		}
	}
	
	public void dispose() throws IOException, TimeoutException {
		_channel.close();
		_connection.close();
	}
}
