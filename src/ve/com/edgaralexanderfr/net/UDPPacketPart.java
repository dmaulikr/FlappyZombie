package ve.com.edgaralexanderfr.net;

import java.io.IOException;
import ve.com.edgaralexanderfr.util.Timer;

public class UDPPacketPart {
	public static final char HEADER_DELIMITER = '|';

	private char headerDelimiter              = HEADER_DELIMITER;
	private long receptionTime                = 0;
	private UDPHost udpHost                   = null;
	private long time                         = 0;
	private short number                      = 0;
	private short total                       = 0;
	private byte[] content                    = null;

	public char getHeaderDelimiter () {
		return this.headerDelimiter;
	}

	public long getReceptionTime () {
		return this.receptionTime;
	}

	public UDPHost getUDPHost () {
		return this.udpHost;
	}

	public long getTime () {
		return this.time;
	}

	public short getNumber () {
		return this.number;
	}

	public short getTotal () {
		return this.total;
	}

	public byte[] getContent () {
		return this.content;
	}

	public String getContentString () {
		return (this.content == null) ? "" : new String(this.content) ;
	}

	public boolean isValid () {
		return this.number > 0 && this.total > 0 && this.number <= this.total && this.content != null;
	}

	public void setHeaderDelimiter (char headerDelimiter) {
		this.headerDelimiter = headerDelimiter;
	}

	public void setReceptionTime (long receptionTime) {
		this.receptionTime = receptionTime;
	}

	public void setContent (byte[] content) {
		this.content = content;
	}

	public void setContent (String content) {
		this.setContent(content.getBytes());
	}

	public UDPPacketPart (UDPHost udpHost, long time, short number, short total, byte[] content) {
		this.udpHost = udpHost;
		this.time    = time;
		this.number  = number;
		this.total   = total;
		this.content = content;
	}

	public UDPPacketPart (UDPHost udpHost, long time, short number, short total, String content) {
		this(udpHost, time, number, total, content.getBytes());
	}

	public UDPPacketPart (UDPHost udpHost, byte[] serialized) {
		int length           = serialized.length;
		int lastDelimiterPos = 0;
		byte step            = 0;
		long time            = 0;
		short number         = 0;
		byte[] header, content;
		int i;
		short total;

		for (i = 0; i < length; i++) {
			if (serialized[ i ] != (byte) this.headerDelimiter) {
				continue;
			}

			header = new byte[ i - lastDelimiterPos ];
			System.arraycopy(serialized, lastDelimiterPos, header, 0, header.length);
			lastDelimiterPos = i + 1;
			step++;

			if (step == 1) {
				time = Long.parseLong(new String(header, 0, header.length));
			} else 
			if (step == 2) {
				number = Short.parseShort(new String(header, 0, header.length));
			} else {
				total        = Short.parseShort(new String(header, 0, header.length));
				content      = new byte[ length - lastDelimiterPos ];
				System.arraycopy(serialized, lastDelimiterPos, content, 0, content.length);
				this.udpHost = udpHost;
				this.time    = time;
				this.number  = number;
				this.total   = total;
				this.content = content;

				break;
			}
		}
	}

	public UDPPacketPart (UDPHost udpHost, String string) {
		this(udpHost, string.getBytes());
	}

	public static UDPPacketPart[] partitionPacket (UDPHost udpHost, short partsContentSize, byte[] content) {
		if (partsContentSize < 1) {
			return new UDPPacketPart[0];
		}

		int length                     = content.length;
		short index                    = 0;
		long time                      = Timer.now();
		short total                    = (short) Math.ceil(length / (float) partsContentSize);
		UDPPacketPart[] udpPacketParts = new UDPPacketPart[ total ];
		int i;
		byte[] part;

		for (i = 0; i < length; i += partsContentSize) {
			part                    = new byte[ Math.min(partsContentSize, length - i) ];
			System.arraycopy(content, i, part, 0, part.length);
			udpPacketParts[ index ] = new UDPPacketPart(udpHost, time, (short) (index + 1), total, part);
			index++;
		}

		return udpPacketParts;
	}

	public static UDPPacketPart[] partitionPacket (UDPHost udpHost, short partsContentSize, String content) {
		return partitionPacket(udpHost, partsContentSize, content.getBytes());
	}

	public void sendSingle (byte[] content) throws IOException {
		if (this.udpHost != null) {
			this.udpHost.sendSingle(content);
		}
	}

	public void sendSingle (String content) throws IOException {
		this.sendSingle(content.getBytes());
	}

	public void broadcastSingle (byte[] content) throws IOException {
		if (this.udpHost != null) {
			this.udpHost.broadcastSingle(content);
		}
	}

	public void broadcastSingle (String content) throws IOException {
		this.broadcastSingle(content.getBytes());
	}

	public void send (byte[] content) throws IOException {
		if (this.udpHost != null) {
			this.udpHost.send(content);
		}
	}

	public void send (String content) throws IOException {
		this.send(content.getBytes());
	}

	public void broadcast (byte[] content) throws IOException {
		if (this.udpHost != null) {
			this.udpHost.broadcast(content);
		}
	}

	public void broadcast (String content) throws IOException {
		this.broadcast(content.getBytes());
	}

	public byte[] serializeHeaders () {
		return ("" + this.time + this.headerDelimiter + this.number + this.headerDelimiter + this.total + this.headerDelimiter).getBytes();
	}

	public String headersToString () {
		return new String(this.serializeHeaders());
	}

	public int getHeadersLength () {
		return this.serializeHeaders().length;
	}

	public byte[] serialize () {
		if (this.content == null) {
			return null;
		}

		byte[] headers = this.serializeHeaders();
		byte[] packet  = new byte[ headers.length + this.content.length ];
		System.arraycopy(headers, 0, packet, 0, headers.length);
		System.arraycopy(this.content, 0, packet, headers.length, this.content.length);

		return packet;
	}

	public String toString () {
		byte[] serialized = this.serialize();

		return (serialized == null) ? "" : new String(serialized);
	}
}
