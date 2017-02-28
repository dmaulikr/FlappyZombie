package ve.com.edgaralexanderfr.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import ve.com.edgaralexanderfr.util.NameValuePair;
import ve.com.edgaralexanderfr.util.PropertiesReader;

public class Resources {
	private List<NameValuePair> resources = new ArrayList<NameValuePair>();

	public Resources () {

	}

	public Resources (String uri) {
		this.loadFromFile(uri);
	}

	public void loadImage (String name, String uri) {
		this.resources.add(new NameValuePair(name, new ImageIcon(getClass().getResource(uri)).getImage()));
	}

	public void loadProperties (String name, String uri) {
		this.resources.add(new NameValuePair(name, new PropertiesReader(uri)));
	}

	public void loadFromFile (String uri) {
		boolean error                       = false;
		InputStream inputStream             = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader       = null;

		try {
			inputStream       = getClass().getResourceAsStream(uri);
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader    = new BufferedReader(inputStreamReader);
			String line;
			String[] data;

			while ((line = bufferedReader.readLine()) != null) {
				data = line.split("\\|");

				if (data.length != 3) {
					throw new Exception("Invalid resource info in line: " + line);
				}

				switch (data[0].toLowerCase()) {
					case "image"      : this.loadImage(data[1], data[2])                    ; break;
					case "properties" : this.loadProperties(data[1], data[2])               ; break;
					default      : throw new Exception("Invalid resource type: " + data[0]) ;
				}
			}
		} catch (Exception exception) {
			error = true;
			exception.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}

				if (inputStreamReader != null) {
					inputStreamReader.close();
				}

				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ioException) {
				error = true;
				ioException.printStackTrace();
			}
		}

		if (error) {
			System.exit(1);
		}
	}

	public <T extends Object> T get (String name, Class<T> type) {
		int size = this.resources.size();
		int i;
		NameValuePair resource;

		for (i = 0; i < size; i++) {
			resource = this.resources.get(i);

			if (resource.getName().equals(name)) {
				return type.cast(resource.getValue());
			}
		}

		return null;
	}
}
