package model;

import java.net.Socket;

/**	This interface defines what every TCP/IP handler should do - handle clients based on their socket.
 * 	Thus Strategy will be implemented in MyTCPIPServer
 * @author Tomer
 *
 */
public interface ClientHandler {
	void handleClient(Socket client);
}
