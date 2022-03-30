package mq.manager.util;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class MiMQTask {
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();
	
	private final int id;
	private final int timeMs;
	
	public MiMQTask(final int timeMs) {
		this.id = ID_GENERATOR.incrementAndGet();
		this.timeMs = timeMs;
	}
	
	public int getID() {
		return id;
	}
	
	public int getTimeMs() { 
		return timeMs;
	}
	
	@Override
	public String toString() { 
		return String.format("id : %d, timeMs : %d", id, timeMs);
	}
}
