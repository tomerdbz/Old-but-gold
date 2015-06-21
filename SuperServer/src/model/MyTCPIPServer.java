package model;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;

import presenter.ServerProperties;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

/** This class creates a generic TCP/IP Server. 
 * @author Tomer
 *
 */
public class MyTCPIPServer {
	/**	Server Properties - how many clients should the server handle simultaneously and port to handle them.
	 * 
	 */
	ServerProperties serverProperties;
	/**	The client handler that will be injected to this field will change how clients will be handled.
	 * 
	 */
	ClientHandler clientHandler;
	/** a flag to know when the server was signaled to stop.
	 * 
	 */
	private volatile boolean stopped;

	public MyTCPIPServer(ServerProperties serverProperties,ClientHandler clientHandler) {
		this.serverProperties=serverProperties;
		stopped=false;
		this.clientHandler=clientHandler;
		
		// TODO Auto-generated constructor stub
	}
	/**	This method will start the TCP/IP Server.
	 * Please Inject your desired client handler first.
	 * 
	 */
	public void startServer()
	{
		ServerSocket server;
		try {
			server = new ServerSocket(serverProperties.getPort());
			ListeningExecutorService threadPool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(serverProperties.getNumOfClients()));
			server.setSoTimeout(500);
			while(!stopped)
			{
				try {
					final Socket someClient=server.accept();
					threadPool.execute(new Runnable() {
						
						@Override
						public void run() {
							try {
								//InputStream inputFromClient=someClient.getInputStream();
								//OutputStream outputToClient=someClient.getOutputStream();
								clientHandler.handleClient(someClient);
								//inputFromClient.close();
								//outputToClient.close();
								someClient.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					
				} catch (SocketTimeoutException e) {
				}
			}
			threadPool.shutdownNow();
			server.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}
	public void stoppedServer()
	{
		stopped=true;
	}
}
