package ve.com.edgaralexanderfr.util;

public class Console {
	public static void clear () {
		String os = System.getProperty("os.name");

		try {
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				Runtime.getRuntime().exec("clear");
			}
		} catch (Exception exception) {

		}
	}
}
