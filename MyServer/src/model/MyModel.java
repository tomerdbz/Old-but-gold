package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import presenter.RemoteControlProperties;
/**
 * 
 * @author Alon
 *	This class is an implementation of the interface model
 *	to be used in a MVP architecture  
 */
public class MyModel extends Observable implements Model {
	/**
	 * Properties of the server
	 */
	RemoteControlProperties serverProperties;
	/**
	 * A socket which will alow us to send and recieve data
	 */
	DatagramSocket socket;
	/**
	 * representation of the address to which we are sending
	 */
	InetAddress address;
	/**
	 * a hash map that works safely with threads and can easily allow us to get a staus of a connected cliend
	 */
	ConcurrentHashMap<String,String> clientStatus = new ConcurrentHashMap<String, String>();
	/**
	 * data that is created during some methods in the model
	 */
	String modelData=null;
	/**
	 * a thread that will recieve data all the time from server
	 */
	int RemoteServerToServerPort;
	ExecutorService Exec=null;
	boolean shutdown = true;
	public MyModel(RemoteControlProperties serverProperties){
		try {
			this.serverProperties = serverProperties; //intializing the properties data member
			socket = new DatagramSocket(serverProperties.getRemoteControlPortListener()); //getting the port from the user to which we will listen to
			address=InetAddress.getByName("127.0.0.1"); //address of use
			this.RemoteServerToServerPort=serverProperties.getPortOnWhichServerListens();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param client- represents a client in the server
	 * This function will return the status of this
	 * specific client on the server
	 */
	@Override
	public void getStatusClient(String client) {
	
		//client comes in a format of Client Ip: THEIP Port: THEPORT
		//therefore needs to be changed a bit in order to get data from hash map
		modelData=this.clientStatus.get(client.split(" ")[2]+","+client.split(" ")[4]); //we return the status of the client from the hash map
		setChanged(); //Hash map example looks like 127.0.0.1,2222,"connected"
		notifyObservers();
		
		
		
	}

	

	
	/**
	 * This function will allow us to disconnect the server in a remote way
	 */
	@Override
	public void DisconnectServer() {
		String message="stop server"; //sending the server a message to stop server
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, RemoteServerToServerPort);
		try {
			socket.send(sendPacket); //sending the packet
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.modelData="msg Server Got The Signal and Stopped Serving"; //notifying the view
		setChanged();
		notifyObservers();
	}
	/**
	 * This functon will allow us to start the server in a remote way
	 */
	@Override
	public void StartServer() {
		String message="start server"; 
		byte[] data=message.getBytes(); //creating the message to be sent
		DatagramPacket sendPacket = new DatagramPacket(data,data.length, address, RemoteServerToServerPort);
		try { 
			socket.send(sendPacket); //sending the message
		} catch (IOException e) {
			e.printStackTrace();
		}
		//sending the properties needed for the server
		message =this.serverProperties.getNumOfClients() + "," +(this.serverProperties.getPortServerClients());
		data = message.getBytes();
		sendPacket = new DatagramPacket(data,data.length, address, RemoteServerToServerPort);
		try {
			socket.send(sendPacket); //send Properties
		} catch (IOException e) {
			e.printStackTrace();
		}
		//creating a thread that will read data from the socket
		//like status updates new clients and so on
		 Exec = Executors.newSingleThreadExecutor();
				Exec.execute((new Runnable(){

			@Override
			public void run() {
				while(true && shutdown){
				byte info[]=new byte[1000];
				DatagramPacket receivedPacket=new DatagramPacket(info,info.length);
				try {
					
					System.out.println("chunk");
					socket.receive(receivedPacket); //recieving data
					String line=new String(receivedPacket.getData(), 0,receivedPacket.getLength());
					String [] lines =line.split("\n"); 
					for(int i =0 ;i<lines.length;i++){//understanding the data and using it
						System.out.println(lines[i]);
					if(lines[i].split(",").length==3 ){  //if new user
						if(lines[i].split(",")[2].equals("connected")){
						clientStatus.put(lines[i].split(",")[0]+","+ lines[i].split(",")[1],"connected");
						setChanged();
						modelData="add " +lines[i];
						notifyObservers();
						}
						else
						if(lines[i].split(",")[2].equals("disconnected")) //if user disconnected
						{
							clientStatus.remove(lines[i]);
							modelData="remove "+"Client IP: " + lines[i].split(",")[0]+" Port: "+ lines[i].split(",")[1];
							setChanged();
							notifyObservers();
						}
						else
						{ //or just updating user status
							clientStatus.put(lines[i].split(",")[0]+","+ lines[i].split(",")[1],lines[i].split(",")[2]);
						}
					}
					}
				} catch (IOException e) {
					
				}
			}
			}
		}));
		//t.start(); //starting the thread 
		this.modelData="msg Server Got The Signal and Starting to Operate";
		setChanged(); 
		notifyObservers(); //notifying the view that server has started
	
		
	}
	/**
	 * 
	 * @param client - represents a client in the server
	 * This function will alow us to disconnect the client 
	 * from the server
	 */
	@Override
	public void DisconnectClient(String client) {
		String message=client.split(" ")[2]+","+ client.split(" ")[4]+",disconnect"; //creating a message such as IP,PORT,disconnect 
		byte[] data=message.getBytes(); // in order to disconnect certain client
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, RemoteServerToServerPort);
		try {
			socket.send(sendPacket); //sending the request to disconnect
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.clientStatus.remove(message); //removing the client from the views list
		modelData="remove "+client;
		setChanged();
		notifyObservers(); //telling the view to do the commands
		
	}
	/**
	 * this function will close the everyting properly
	 */
	@Override
	public void exit() {
		//if(t!=null){
		//t.interrupt(); //stopping the thread reading if it does exist
		//}
		shutdown =false;
		if(Exec!=null)
		Exec.shutdownNow();
		String message="exit"; //creating a message to exit properly
		byte[] data=message.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(data,
		data.length, address, RemoteServerToServerPort);
		try {
			socket.send(sendPacket); //sending the exit message
		} catch (IOException e) {
			e.printStackTrace();
		}
		socket.close();
		this.modelData="msg Exiting window now.";
		setChanged();
		notifyObservers(); //telling the view he should exit
	}
	/**
	 * 
	 * @return this function returns a string that was created by some actions in the presenter later to be used by the view through the presneter
	 */
	@Override
	public String takedata() {
		return this.modelData;
	}
	
}
