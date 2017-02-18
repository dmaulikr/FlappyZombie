package ve.com.edgaralexanderfr.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import ve.com.edgaralexanderfr.net.UDPPacketPart;
import ve.com.edgaralexanderfr.util.Count;
import ve.com.edgaralexanderfr.util.Timer;
import ve.com.edgaralexanderfr.util.TimerEvent;

public abstract class UDPConnection implements Runnable, TimerEvent {
	public static final long MONITOR_TIMER_TIME         = 1000;
	public static final long HOST_TIMEOUT_TIME          = 10000;
	public static final short UDP_CONTENT_SIZE          = 768;
	public static final long PACKET_PART_RECYCLING_TIME = 5000;

	protected UDPEvents udpEvents                       = null;
	protected DatagramSocket datagramSocket             = null;
	protected List<UDPHost> connectedHosts              = new ArrayList<UDPHost>();
	protected Thread networkingThread                   = null;
	protected UDPHost remoteHost                        = null;
	protected Timer monitorTimer                        = null;
	protected long hostTimeoutTime                      = HOST_TIMEOUT_TIME;
	protected List<Count> timeouts                      = new ArrayList<Count>();
	protected short udpContentSize                      = UDP_CONTENT_SIZE;
	protected List<UDPPacketPart> packetsParts          = new ArrayList<UDPPacketPart>();
	protected long packetPartRecyclingTime              = PACKET_PART_RECYCLING_TIME;

	public UDPHost getRemoteHost () {
		return this.remoteHost;
	}

	public synchronized boolean isDatagramSocketClosed () {
		return this.datagramSocket == null || this.datagramSocket.isClosed();
	}

	public boolean hasRemoteHost () {
		return this.remoteHost != null;
	}

	public long getHostTimeoutTime () {
		return this.hostTimeoutTime;
	}

	public short getUDPContentSize () {
		return this.udpContentSize;
	}

	public long getPacketPartRecyclingTime () {
		return this.packetPartRecyclingTime;
	}

	public void setUDPEvents (UDPEvents udpEvents) {
		this.udpEvents = udpEvents;
	}

	public void setRemoteHost (UDPHost remoteHost) {
		this.remoteHost = remoteHost;
	}

	public void setHostTimeoutTime (long hostTimeoutTime) {
		if (hostTimeoutTime > 0) {
			this.hostTimeoutTime = hostTimeoutTime;
		}
	}

	public void setUDPContentSize (short udpContentSize) {
		if (udpContentSize > 0) {
			this.udpContentSize = udpContentSize;
		}
	}

	public void setPacketPartRecyclingTime (long packetPartRecyclingTime) {
		if (packetPartRecyclingTime > 0) {
			this.packetPartRecyclingTime = packetPartRecyclingTime;
		}
	}

	public UDPConnection (UDPEvents udpEvents) {
		this.setUDPEvents(udpEvents);
	}

	public synchronized UDPHost findHostAndReplace (UDPHost udpHost) {
		int size = this.connectedHosts.size();
		int i;

		for (i = 0; i < size; i++) {
			if (this.connectedHosts.get(i).compare(udpHost)) {
				return this.connectedHosts.get(i);
			}
		}

		return udpHost;
	}

	public synchronized boolean isHostConnected (UDPHost udpHost) {
		int size = this.connectedHosts.size();
		int i;

		for (i = 0; i < size; i++) {
			if (this.connectedHosts.get(i).compare(udpHost)) {
				return true;
			}
		}

		return false;
	}

	public synchronized boolean findPacketParts (UDPPacketPart udpPacketPart, List<UDPPacketPart> foundParts) {
		int size      = this.packetsParts.size();
		boolean valid = true;
		int i;
		UDPPacketPart part;

		for (i = 0; i < size; i++) {
			part = this.packetsParts.get(i);

			if (!udpPacketPart.getUDPHost().compare(part.getUDPHost()) || udpPacketPart.getTime() != part.getTime()) {
				continue;
			}

			if (udpPacketPart.getNumber() == part.getNumber() || udpPacketPart.getTotal() != part.getTotal()) {
				valid = false;
			}

			foundParts.add(part);
		}

		return valid;
	}

	public synchronized void sendSingle (byte[] content, UDPHost udpHost) throws IOException {
		if (this.datagramSocket != null) {
			DatagramPacket packetToSend = new DatagramPacket(content, content.length, udpHost.getInetAddress(), udpHost.getPort());
			this.datagramSocket.send(packetToSend);
		}
	}

	public void sendSingle (String content, UDPHost udpHost) throws IOException {
		this.sendSingle(content.getBytes(), udpHost);
	}

	public synchronized void broadcastSingle (byte[] content, UDPHost udpHost) throws IOException {
		int size = this.connectedHosts.size();
		int i;

		for (i = 0; i < size; i++) {
			if (!this.connectedHosts.get(i).compare(udpHost)) {
				this.connectedHosts.get(i).sendSingle(content);
			}
		}
	}

	public void broadcastSingle (String content, UDPHost udpHost) throws IOException {
		this.broadcastSingle(content.getBytes(), udpHost);
	}

	public void broadcastSingle (byte[] content) throws IOException {
		this.broadcastSingle(content, null);
	}

	public void broadcastSingle (String content) throws IOException {
		this.broadcastSingle(content.getBytes());
	}

	public synchronized void send (byte[] content, UDPHost udpHost) throws IOException {
		UDPPacketPart[] udpPacketParts = UDPPacketPart.partitionPacket(udpHost, this.udpContentSize, content);
		int length                     = udpPacketParts.length;
		int i;

		for (i = 0; i < length; i++) {
			udpPacketParts[ i ].sendSingle(udpPacketParts[ i ].serialize());
		}
	}

	public void send (String content, UDPHost udpHost) throws IOException {
		this.send(content.getBytes(), udpHost);
	}

	public synchronized void broadcast (byte[] content, UDPHost udpHost) throws IOException {
		int size = this.connectedHosts.size();
		int i;

		for (i = 0; i < size; i++) {
			if (!this.connectedHosts.get(i).compare(udpHost)) {
				this.connectedHosts.get(i).send(content);
			}
		}
	}

	public void broadcast (String content, UDPHost udpHost) throws IOException {
		this.broadcast(content.getBytes(), udpHost);
	}

	public void broadcast (byte[] content) throws IOException {
		this.broadcast(content, null);
	}

	public void broadcast (String content) throws IOException {
		this.broadcast(content.getBytes());
	}

	public synchronized void disconnect (UDPHost udpHost) {
		this.connectedHosts.remove(udpHost);
		this.onDisconnect(udpHost);
	}

	public synchronized void setTimeout (Count count) {
		this.timeouts.add(count);
	}

	public synchronized void close () throws IOException {
		this.broadcastSingle("UDP_CONNECTION_CLOSE");
		this.connectedHosts.clear();
		this.timeouts.clear();
		
		if (this.monitorTimer != null) {
			this.monitorTimer.setCancelled(true);
			this.monitorTimer = null;
		}
		
		if (this.datagramSocket != null) {
			this.datagramSocket.close();
			this.datagramSocket = null;
		}

		this.remoteHost = null;
	}

	@Override
	public void run () {
		this.managePackets();
	}

	@Override
	public void onTick (Timer timer) {
		this.monitorConnections();
		this.updateTimeouts();
		this.recycleOldPacketsParts();
	}

	protected void managePackets () {
		byte[] receivedData = new byte[1024];
		DatagramPacket receivedPacket;
		UDPHost udpHost;
		long now;
		String contentString;
		byte[] content;
		UDPPacketPart udpPacketPart;

		while (!this.isDatagramSocketClosed()) {
			try {
				receivedPacket = new DatagramPacket(receivedData, receivedData.length);
				this.datagramSocket.receive(receivedPacket);

				synchronized (this) {
					if (this.isDatagramSocketClosed()) {
						break;
					}

					udpHost = this.findHostAndReplace(new UDPHost(this, receivedPacket));

					if (this.hasRemoteHost() && !udpHost.compare(this.remoteHost)) {
						continue;
					}

					now           = Timer.now();
					contentString = new String(receivedPacket.getData(), receivedPacket.getOffset(), receivedPacket.getLength());
					udpHost.setLastPacketTime(now);

					if (udpHost.isConnected()) {
						if (contentString.equals("UDP_CONNECTION_PING")) {
							udpHost.sendSingle("UDP_CONNECTION_PONG");
						} else 
						if (contentString.equals("UDP_CONNECTION_PONG")) {
							this.onPong(udpHost);
						} else 
						if (contentString.equals("UDP_CONNECTION_CLOSE")) {
							udpHost.disconnect();
						} else {
							content = new byte[ receivedPacket.getLength() ];
							System.arraycopy(receivedPacket.getData(), 0, content, 0, receivedPacket.getLength());

							try {
								udpPacketPart = new UDPPacketPart(udpHost, content);
								udpPacketPart.setReceptionTime(now);

								if (udpPacketPart.isValid()) {
									this.buildPacket(udpPacketPart);
								}
							} catch (Exception exception) {
								
							}
						}
					} else 
					if (contentString.equals("UDP_CONNECTION_REQUEST")) {
						this.connectedHosts.add(udpHost);
						this.sendSingle("UDP_CONNECTION_OK", udpHost);
						this.onConnect(udpHost);
					} else 
					if (contentString.equals("UDP_CONNECTION_OK")) {
						this.connectedHosts.add(udpHost);
						this.onConnect(udpHost);
					}
				}
			} catch (Exception exception) {
				
			}
		}
	}

	protected void startThreads () {
		this.networkingThread = new Thread(this);
		this.networkingThread.start();
		this.monitorTimer     = Timer.setInterval(MONITOR_TIMER_TIME, this);
	}

	protected abstract void onConnect (UDPHost udpHost);
	protected abstract void onContentReceived (UDPHost udpHost, byte[] content);
	protected abstract void onPong (UDPHost udpHost);
	protected abstract void onDisconnect (UDPHost udpHost);

	private synchronized void monitorConnections () {
		long now                 = Timer.now();
		UDPHost[] connectedHosts = new UDPHost[ this.connectedHosts.size() ];
		this.connectedHosts.toArray(connectedHosts);
		int length               = connectedHosts.length;
		int i;
		UDPHost udpHost;
		long lastPacketElapsedTime;

		for (i = 0; i < length; i++) {
			udpHost               = connectedHosts[ i ];
			lastPacketElapsedTime = now - udpHost.getLastPacketTime();

			if (lastPacketElapsedTime >= this.hostTimeoutTime) {
				udpHost.disconnect();
			}
		}
	}

	private synchronized void updateTimeouts () {
		Count[] timeouts = new Count[ this.timeouts.size() ];
		this.timeouts.toArray(timeouts);
		int length = timeouts.length;
		int i;
		Count count;

		for (i = 0; i < length; i++) {
			count = timeouts[ i ];
			count.count();

			if (count.isCompleted()) {
				this.timeouts.remove(count);
			}
		}
	}

	private synchronized void buildPacket (UDPPacketPart udpPacketPart) {
		List<UDPPacketPart> parts = new ArrayList<UDPPacketPart>();

		if (!this.findPacketParts(udpPacketPart, parts)) {
			return;
		}

		this.packetsParts.add(udpPacketPart);
		parts.add(udpPacketPart);
		int number          = 1;
		int total           = udpPacketPart.getTotal();
		int size            = parts.size();
		boolean missingPart = false;
		byte[] packet       = new byte[0];
		byte[] content      = new byte[0];
		int i;
		UDPPacketPart part;
		byte[] aux;

		for (number = 1; number <= total; number++) {
			missingPart = true;

			for (i = 0; i < size; i++) {
				part = parts.get(i);

				if (part.getNumber() == number) {
					missingPart = false;
					content     = part.getContent();
					aux         = packet;
					packet      = new byte[ aux.length + content.length ];
					System.arraycopy(aux, 0, packet, 0, aux.length);
					System.arraycopy(content, 0, packet, aux.length, content.length);

					break;
				}
			}

			if (missingPart) {
				return;
			}
		}

		for (i = 0; i < size; i++) {
			this.packetsParts.remove(parts.get(i));
		}

		this.onContentReceived(udpPacketPart.getUDPHost(), packet);
	}

	private synchronized void recycleOldPacketsParts () {
		long now                     = Timer.now();
		UDPPacketPart[] packetsParts = new UDPPacketPart[ this.packetsParts.size() ];
		this.packetsParts.toArray(packetsParts);
		int length                   = packetsParts.length;
		int i;
		UDPPacketPart part;

		for (i = 0; i < length; i++) {
			part = packetsParts[ i ];

			if ((now - part.getReceptionTime()) >= this.packetPartRecyclingTime) {
				this.packetsParts.remove(part);
			}
		}
	}
}
