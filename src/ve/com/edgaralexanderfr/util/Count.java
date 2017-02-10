package ve.com.edgaralexanderfr.util;

public class Count {
	private CountEvent countEvent = null;
	private short count           = 0;
	private short maxCount        = 0;
	private String tag            = "";

	public CountEvent getCountEvent () {
		return this.countEvent;
	}

	public short getCount () {
		return this.count;
	}

	public short getMaxCount () {
		return this.maxCount;
	}

	public String getTag () {
		return this.tag;
	}

	public boolean isCompleted () {
		return this.count >= this.maxCount;
	}

	public void setCountEvent (CountEvent countEvent) {
		this.countEvent = countEvent;
	}

	public void setCount (short count) {
		this.count = count;
	}

	public void setMaxCount (short maxCount) {
		this.maxCount = maxCount;
	}

	public void setTag (String tag) {
		this.tag = tag;
	}

	public Count (CountEvent countEvent, short count, short maxCount, String tag) {
		this.setCountEvent(countEvent);
		this.setCount(count);
		this.setMaxCount(maxCount);
		this.setTag(tag);
	}

	public Count (CountEvent countEvent, short count, short maxCount) {
		this(countEvent, count, maxCount, "");
	}

	public Count (short count, short maxCount, String tag) {
		this(null, count, maxCount, tag);
	}

	public Count (short count, short maxCount) {
		this(null, count, maxCount, "");
	}

	public void count () {
		this.count++;

		if (this.isCompleted() && this.countEvent != null) {
			this.countEvent.onCount(this);
		}
	}
}
