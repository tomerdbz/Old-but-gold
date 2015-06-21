package presenter;

import java.io.Serializable;

public class RemoteControlProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int PortServerClients;
	private int portOnWhichServerListens;
	public int getPortOnWhichServerListens() {
		return portOnWhichServerListens;
	}
	public void setPortOnWhichServerListens(int portOnWhichServerListens) {
		this.portOnWhichServerListens = portOnWhichServerListens;
	}
	private int numOfClients;
	private int RemoteControlPortListener;
	/**	Default Properties.
	 * 
	 */
	public RemoteControlProperties() {
		PortServerClients=5400;
		numOfClients=32;
		RemoteControlPortListener = 1234;
	}
	public RemoteControlProperties(int port,int numOfClients,int RemoteControlPortListener) {
		this.PortServerClients=port;
		this.numOfClients=numOfClients;
		this.RemoteControlPortListener=RemoteControlPortListener;
	}
	
	public int getRemoteControlPortListener() {
		return RemoteControlPortListener;
	}
	public void setRemoteControlPortListener(int RemoteControlPortListener) {
		this.RemoteControlPortListener = RemoteControlPortListener;
	}
	public int getPortServerClients() {
		return PortServerClients;
	}
	public void setPortServerClients(int port) {
		this.PortServerClients = port;
	}
	public int getNumOfClients() {
		return numOfClients;
	}
	public void setNumOfClients(int numOfClients) {
		this.numOfClients = numOfClients;
	}
}
