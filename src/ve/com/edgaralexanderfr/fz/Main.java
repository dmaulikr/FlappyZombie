package ve.com.edgaralexanderfr.fz;

import java.util.Scanner;
import ve.com.edgaralexanderfr.net.UDPClient;
import ve.com.edgaralexanderfr.net.UDPEvents;
import ve.com.edgaralexanderfr.net.UDPHost;
import ve.com.edgaralexanderfr.net.UDPServer;

import ve.com.edgaralexanderfr.game.Game;         //
import ve.com.edgaralexanderfr.game.GameObject;   //
import ve.com.edgaralexanderfr.game.Renderer;     //

public class Main implements UDPEvents {
	private Scanner scanner = new Scanner(System.in);

	public static void main (String[] args) throws Exception {
		new Main();
	}

	public Main () throws Exception {
		System.out.println("Type y for UDP server: ");
		String type = this.scanner.next();

		if (type.equals("y")) {
			this.startUDPServer();
		} else {
			this.startUDPClient();
		}
	}

	@Override
	public void onConnect (UDPHost udpHost) {
		System.out.println("Connected to " + udpHost.getInetAddress().getHostAddress() + ":" + udpHost.getPort() + ".");
	}

	@Override
	public void onConnectionTimeout () {
		System.out.println("Failed to connect to remote host.");
	}

	@Override
	public void onContentReceived (UDPHost udpHost, byte[] content) {
		System.out.println(udpHost.getInetAddress().getHostAddress() + ":" + udpHost.getPort() + ": " + new String(content));
	}

	@Override
	public void onPong (UDPHost udpHost, long ping) {
		System.out.println(udpHost.getInetAddress().getHostAddress() + ":" + udpHost.getPort() + " PING: " + ping);
	}

	@Override
	public void onDisconnect (UDPHost udpHost) {
		System.out.println(udpHost.getInetAddress().getHostAddress() + ":" + udpHost.getPort() + " has been disconnected.");
	}

	private void startUDPServer () throws Exception {
		try {
			System.out.println("Port: ");
			int port         = Integer.parseInt(this.scanner.next());
			UDPServer server = new UDPServer(this);
			server.listen(port);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void startUDPClient () throws Exception {
		try {
			System.out.println("Remote IP Address: ");
			String remoteIPAddress = this.scanner.next();
			System.out.println("Remote port: ");
			int remotePort         = Integer.parseInt(this.scanner.next());
			UDPClient client       = new UDPClient(this);
			client.connect(remoteIPAddress, remotePort);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
