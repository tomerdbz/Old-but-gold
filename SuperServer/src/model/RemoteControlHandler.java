package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import presenter.ServerProperties;

/** Remote control TCP/IP handler - didn't use. UDP handler is better. but saved it.
 * @author Tomer
 *
 */
public class RemoteControlHandler extends Observable implements ClientHandler,Observer {
	MazeClientHandler handler;
	PrintWriter outputToGUI;
	MazeServer server;
	@Override
	public void handleClient(Socket client)
	{
		try {		
			InputStream inFromClient=client.getInputStream();
			OutputStream outToClient=client.getOutputStream();
			BufferedReader inputFromClient=new BufferedReader(new InputStreamReader(inFromClient));
			outputToGUI=new PrintWriter(new OutputStreamWriter(outToClient));
			if(server==null)
				if((inputFromClient.readLine()).equals("start server"))
				{
					ObjectInputStream propertiesLoader=new ObjectInputStream(inFromClient);
					//handler=new MazeClientHandler(this);
					handler.addObserver(this);
					//new GUIUDPServer(handler,IP,PORT);
					this.addObserver(handler);
					server=new MazeServer((ServerProperties)propertiesLoader.readObject(), handler);
					handler.setServer(server);
					new Thread(server).start();
				}
			String input;
			while(!((input=inputFromClient.readLine()).equals("stop server")))
			{
				if(input.equals("exit"))
				{
					inFromClient.close();
					outToClient.close();
					return;
				}
				else if(input.contains("disconnect"))
				{
					setChanged();
					notifyObservers(input);
				}
			}
			server.stoppedServer();
			server=null;
			outputToGUI=null;
			inFromClient.close();
			outToClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o==handler)
		{
			for(String message: handler.messages)
			{
				outputToGUI.println(message);
			}
			outputToGUI.flush();
		}
	}

}
