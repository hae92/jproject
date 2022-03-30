package mq.manager.util;

import mq.manager.util.MiMQTask;

public class MQReceiveTask extends MiMQTask {
	
	private static final int TIME_PER_CUP = 100;

	public MQReceiveTask(int timeMs) {
		super(timeMs);
		// TODO Auto-generated constructor stub
		
		String logText = "";
		logText = String.format("Test Task : %d", timeMs);
		System.out.println(logText);
		
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", this.getClass().getSimpleName(), super.toString());
	}
}
