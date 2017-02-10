package ve.com.edgaralexanderfr.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPHost {
	private UDPConnection udpConnection = null;
	private InetAddress inetAddress     = null;
	private int port                    = 0;
	private long lastPacketTime         = 0;

	public UDPConnection getUDPConnection () {
		return this.udpConnection;
	}

	public InetAddress getInetAddress () {
		return this.inetAddress;
	}

	public int getPort () {
		return this.port;
	}

	public long getLastPacketTime () {
		return this.lastPacketTime;
	}

	public boolean isConnected () {
		if (this.udpConnection == null) {
			return false;
		}

		return this.udpConnection.isHostConnected(this);
	}

	public void setUDPConnection (UDPConnection udpConnection) {
		this.udpConnection = udpConnection;
	}

	public void setInetAddress (InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}

	public void setPort (int port) {
		this.port = port;
	}

	public void setLastPacketTime (long lastPacketTime) {
		if (lastPacketTime > 0) {
			this.lastPacketTime = lastPacketTime;
		}
	}

	public UDPHost (UDPConnection udpConnection, InetAddress inetAddress, int port) {
		this.setUDPConnection(udpConnection);
		this.setInetAddress(inetAddress);
		this.setPort(port);
	}

	public UDPHost (UDPConnection udpConnection, String ipAddress, int port) throws UnknownHostException {
		this(udpConnection, InetAddress.getByName(ipAddress), port);
	}

	public UDPHost (UDPConnection udpConnection, DatagramPacket datagramPacket) {
		this(udpConnection, datagramPacket.getAddress(), datagramPacket.getPort());
	}

	public UDPHost (InetAddress inetAddress, int port) {
		this(null, inetAddress, port);
	}

	public UDPHost (String ipAddress, int port) throws UnknownHostException {
		this(null, ipAddress, port);
	}

	public UDPHost (DatagramPacket datagramPacket) {
		this(null, datagramPacket.getAddress(), datagramPacket.getPort());
	}

	public boolean compare (UDPHost udpHost) {
		if (udpHost == null || this.inetAddress == null) {
			return false;
		}

		return udpHost.getInetAddress().getHostAddress().equals(this.inetAddress.getHostAddress()) && udpHost.getPort() == this.port;
	}

	public void sendSingle (byte[] content) throws IOException {
		if (this.udpConnection != null) {
			this.udpConnection.sendSingle(content, this);
		}
	}

	public void sendSingle (String content) throws IOException {
		this.sendSingle(content.getBytes());
	}

	public void broadcastSingle (byte[] content) throws IOException {
		if (this.udpConnection != null) {
			this.udpConnection.broadcastSingle(content, this);
		}
	}

	public void broadcastSingle (String content) throws IOException {
		this.broadcastSingle(content.getBytes());
	}

	public void send (byte[] content) throws IOException {
		if (this.udpConnection != null) {
			this.udpConnection.send(content, this);
		}
	}

	public void send (String content) throws IOException {
		this.send(content.getBytes());
	}

	public void broadcast (byte[] content) throws IOException {
		if (this.udpConnection != null) {
			this.udpConnection.broadcast(content, this);
		}
	}

	public void broadcast (String content) throws IOException {
		this.broadcast(content.getBytes());
	}

	public void disconnect () {
		if (this.udpConnection != null) {
			this.udpConnection.disconnect(this);
		}
	}
}
