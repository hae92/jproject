package mq.manager.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.print.attribute.standard.DateTimeAtProcessing;

import mq.manager.util.MiMQTask;

public class MiMQWorker implements Runnable {
	
	private final MiMQTask _task;
	public MiMQWorker(final MiMQTask task) {
		_task = task;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(_task.getTimeMs());
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String logText = String.format("[%s] [Task ID : %d] Task Time MilliSecond : %d", df.format(new Date()), _task.getID(), _task.getTimeMs());
			System.out.println(logText);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
