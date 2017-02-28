package ve.com.edgaralexanderfr.game;

public class ScheduledRoutine {
	ScheduledRoutineEvent scheduledRoutineEvent = null;
	private Renderer renderer                   = null;
	private String name                         = null;
	private float interval                      = 0.0f;
	private float time                          = 0.0f;
	private boolean repeat                      = false;

	public ScheduledRoutineEvent getScheduledRoutineEvent () {
		return this.scheduledRoutineEvent;
	}

	public Renderer getRenderer () {
		return this.renderer;
	}

	public String getName () {
		return this.name;
	}

	public float getInterval () {
		return this.interval;
	}

	public float getTime () {
		return this.time;
	}

	public boolean isRepeated () {
		return this.repeat;
	}

	public boolean isCompleted () {
		return this.time >= this.interval;
	}

	public void setScheduledRoutineEvent (ScheduledRoutineEvent scheduledRoutineEvent) {
		this.scheduledRoutineEvent = scheduledRoutineEvent;
	}

	public void setRenderer (Renderer renderer) {
		this.renderer = renderer;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setInterval (float interval) {
		this.interval = interval;
	}

	public void setTime (float time) {
		this.time = time;
	}

	public void setRepeat (boolean repeat) {
		this.repeat = repeat;
	}

	public ScheduledRoutine (ScheduledRoutineEvent scheduledRoutineEvent, Renderer renderer, String name, float interval, float time, boolean repeat) {
		this.setScheduledRoutineEvent(scheduledRoutineEvent);
		this.setRenderer(renderer);
		this.setName(name);
		this.setInterval(interval);
		this.setTime(time);
		this.setRepeat(repeat);
	}

	public ScheduledRoutine (ScheduledRoutineEvent scheduledRoutineEvent, Renderer renderer, String name, float interval, boolean repeat) {
		this(scheduledRoutineEvent, renderer, name, interval, 0.0f, repeat);
	}

	public void update () {
		if (this.renderer == null) {
			return;
		}

		this.time += this.renderer.deltaTime();

		if (this.isCompleted()) {
			if (this.scheduledRoutineEvent != null) {
				this.scheduledRoutineEvent.onIntervalReached(this);
			}

			if (this.repeat) {
				this.time = 0.0f;
			}
		}
	}
}
