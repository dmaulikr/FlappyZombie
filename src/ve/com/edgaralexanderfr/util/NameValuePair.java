package ve.com.edgaralexanderfr.util;

public class NameValuePair {
	private String name  = null;
	private Object value = null;

	public String getName () {
		return this.name;
	}

	public Object getValue () {
		return this.value;
	}

	public void setName (String name) {
		this.name = name;
	}

	public void setValue (Object value) {
		this.value = value;
	}

	public NameValuePair (String name, Object value) {
		this.setName(name);
		this.setValue(value);
	}
}
