package ve.com.edgaralexanderfr.net;

public interface UDPEvents {
	public void onConnect (UDPHost udpHost);
	public void onConnectionTimeout ();
	public void onContentReceived (UDPHost udpHost, byte[] content);
	public void onPong (UDPHost udpHost, long ping);
	public void onDisconnect (UDPHost udpHost);
}
