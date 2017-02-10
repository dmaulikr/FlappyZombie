package ve.com.edgaralexanderfr.net;

import java.io.IOException;
import java.net.SocketException;
import java.net.DatagramSocket;

public class UDPServer extends UDPConnection {
	public UDPServer (UDPEvents udpEvents) {
		super(udpEvents);
	}

	public UDPServer () {
		this(null);
	}

	public synchronized void listen (int port) throws IOException, SocketException {
		this.close();
		this.datagramSocket = new DatagramSocket(port);
		this.startThreads();
	}

	@Override
	protected void onConnect (UDPHost udpHost) {
		if (this.udpEvents != null) {
			this.udpEvents.onConnect(udpHost);
		}
	}

	@Override
	protected void onContentReceived (UDPHost udpHost, byte[] content) {
		if (this.udpEvents != null) {
			this.udpEvents.onContentReceived(udpHost, content);
		}
	}

	@Override
	protected void onPong (UDPHost udpHost) {
		
	}

	@Override
	protected void onDisconnect (UDPHost udpHost) {
		if (this.udpEvents != null) {
			this.udpEvents.onDisconnect(udpHost);
		}
	}
}
