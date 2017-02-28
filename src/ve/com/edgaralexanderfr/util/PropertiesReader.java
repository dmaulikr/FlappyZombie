package ve.com.edgaralexanderfr.util;

import java.io.InputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ve.com.edgaralexanderfr.util.NameValuePair;

public class PropertiesReader {
	private Properties properties    = null;
	private List<NameValuePair> read = new ArrayList<NameValuePair>();

	public PropertiesReader (String uri) {
		boolean error           = false;
		InputStream inputStream = null;
		this.properties         = new Properties();

		try {
			inputStream = getClass().getResourceAsStream(uri);
			this.properties.load(inputStream);
		} catch (Exception exception) {
			error = true;
			exception.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException ioException) {
					error = true;
					ioException.printStackTrace();
				}
			}
		}

		if (error) {
			System.exit(1);
		}
	}

	public Object getRead (String name) {
		int size = this.read.size();
		int i;
		NameValuePair property;

		for (i = 0; i < size; i++) {
			property = this.read.get(i);

			if (property.getName().equals(name)) {
				return property.getValue();
			}
		}

		return null;
	}

	public String getString (String name) {
		Object read     = this.getRead(name);
		String property = null;

		try {
			if (read == null) {
				property = this.properties.getProperty(name);

				if (property == null) {
					throw new Exception("Property " + name + " not found.");
				}

				this.read.add(new NameValuePair(name, property));
			} else {
				property = (String) read;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.exit(1);
		}

		return property;
	}

	public int getInt (String name) {
		Object read = this.getRead(name);
		int value   = 0;
		String property;

		try {
			if (read == null) {
				property = this.properties.getProperty(name);

				if (property == null) {
					throw new Exception("Property " + name + " not found.");
				}

				value = Integer.parseInt(property);
				this.read.add(new NameValuePair(name, value));
			} else {
				value = (int) read;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.exit(1);
		}

		return value;
	}

	public float getFloat (String name) {
		Object read = this.getRead(name);
		float value = 0.0f;
		String property;

		try {
			if (read == null) {
				property = this.properties.getProperty(name);

				if (property == null) {
					throw new Exception("Property " + name + " not found.");
				}

				value = Float.parseFloat(property);
				this.read.add(new NameValuePair(name, value));
			} else {
				value = (float) read;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.exit(1);
		}

		return value;
	}

	public String s (String name) {
		return this.getString(name);
	}

	public int i (String name) {
		return this.getInt(name);
	}

	public float f (String name) {
		return this.getFloat(name);
	}
}
