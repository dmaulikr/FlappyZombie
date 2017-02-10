package ve.com.edgaralexanderfr.net;

import java.io.IOException;
import java.net.SocketException;
import java.net.DatagramSocket;
import ve.com.edgaralexanderfr.util.Count;
import ve.com.edgaralexanderfr.util.CountEvent;
import ve.com.edgaralexanderfr.util.Timer;

public class UDPClient extends UDPConnection implements CountEvent {
	public static final short CONNECTION_TIMEOUT_TIME = 10;

	private short connectionTimeoutTime               = CONNECTION_TIMEOUT_TIME;
	private long pingLastTime                         = Timer.now();

	public short getConnectionTimeoutTime () {
		return this.connectionTimeoutTime;
	}

	public long getPingLastTime () {
		return this.pingLastTime;
	}

	public long getPing () {
		return Timer.now() - this.pingLastTime;
	}

	public void setConnectionTimeoutTime (short connectionTimeoutTime) {
		if (connectionTimeoutTime > 0) {
			this.connectionTimeoutTime = connectionTimeoutTime;
		}
	}

	public UDPClient (UDPEvents udpEvents) throws SocketException {
		super(udpEvents);
	}

	public UDPClient () throws SocketException {
		this(null);
	}

	public synchronized void connect (String remoteIPAddress, int remotePort) throws IOException, SocketException {
		this.close();
		this.datagramSocket = new DatagramSocket();
		this.setRemoteHost(new UDPHost(this, remoteIPAddress, remotePort));
		this.startThreads();
		this.remoteHost.sendSingle("UDP_CONNECTION_REQUEST");
		this.setTimeout(new Count(this, (short) 0, this.connectionTimeoutTime, "UDP_CONNECTION_TIMEOUT"));
	}

	@Override
	public void onTick (Timer timer) {
		super.onTick(timer);

		if (this.remoteHost != null) {
			try {
				this.remoteHost.sendSingle("UDP_CONNECTION_PING");
				this.pingLastTime = Timer.now();
			} catch (Exception exception) {
				
			}
		}
	}

	@Override
	public void onCount (Count count) {
		if (count.getTag().equals("UDP_CONNECTION_TIMEOUT") && !this.remoteHost.isConnected() && this.udpEvents != null) {
			this.udpEvents.onConnectionTimeout();

			try {
				this.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
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
		if (this.udpEvents != null) {
			this.udpEvents.onPong(udpHost, this.getPing());
		}
	}

	@Override
	protected void onDisconnect (UDPHost udpHost) {
		if (this.udpEvents != null) {
			this.udpEvents.onDisconnect(udpHost);
		}

		try {
			this.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
