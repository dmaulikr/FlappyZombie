package ve.com.edgaralexanderfr.util;

import java.util.Date;

public class Timer implements Runnable {
	private long time         = 0;
	private TimerEvent event  = null;
	private Thread thread     = null;
	private boolean cancelled = false;

	public long getTime () {
		return this.time;
	}

	public TimerEvent getEvent () {
		return this.event;
	}

	public boolean isCancelled () {
		return this.cancelled;
	}

	public void setTime (long time) {
		this.time = time;
	}

	public void setEvent (TimerEvent event) {
		this.event = event;
	}

	public void setCancelled (boolean cancelled) {
		this.cancelled = cancelled;
	}

	public Timer (long time, TimerEvent event) {
		this.setTime(time);
		this.setEvent(event);
		this.thread = new Thread(this);
		this.thread.start();
	}

	public static Timer setInterval (long time, TimerEvent event) {
		return new Timer(time, event);
	}

	public static long now () {
		return new Date().getTime();
	}

	@Override
	public void run () {
		while (!this.cancelled) {
			try {
				Thread.sleep(this.time);

				if (!this.cancelled && this.event != null) {
					this.event.onTick(this);
				}
			} catch (Exception exception) {
				
			}
		}
	}
}
