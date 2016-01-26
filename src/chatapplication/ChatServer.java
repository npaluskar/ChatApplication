
package chatapplication;

import java.io.*;
import java.net.*;
import java.util.*;

/**This class is implementing Chat Server of Chat application
 * @author Nachiket Paluskar(31375824)
 *
 */
public class ChatServer {
	public static void main(String[] args) {
		ArrayList<ChatHandler> AllHandlers = new ArrayList<ChatHandler>();

		try {
			ServerSocket s = new ServerSocket(3456);

			for (;;) {
				Socket incoming = s.accept();
				new ChatHandler(incoming, AllHandlers).start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class ChatHandler extends Thread {
	public ChatHandler(Socket i, ArrayList<ChatHandler> h) {
		incoming = i;
		handlers = h;
		handlers.add(this);
		try {
			in = new ObjectInputStream(incoming.getInputStream());
			out = new ObjectOutputStream(incoming.getOutputStream());
		} catch (IOException ioe) {
			System.out.println("Could not create streams.");
		}
	}

	public synchronized void broadcast() {

		ChatHandler left = null;
		for (ChatHandler handler : handlers) {
			ChatMessageChild cmc = new ChatMessageChild();
			if (!list.contains(myObject.getName().toString()) && myObject.getName().toString() != "") {
				list.add(myObject.getName().toString());
			}
			cmc.setMessage(myObject.getMessage());
			cmc.setName(myObject.getName());
			cmc.getUser_list().addAll(list);
			history.add(myObject.getName() + ": " + myObject.getMessage());
			cmc.getHistory().addAll(history);

			try {
				handler.out.writeObject(cmc);
				System.out.println("Writing to handler outputstream: " + cmc.getMessage());

			} catch (IOException ioe) {
				// one of the other handlers hung up
				left = handler; // remove that handler from the arraylist
			}
		}
		handlers.remove(left);
		System.out.println("Number of handlers: " + handlers.size());
	}

	public synchronized void broadcastWhiteBoard(Object object) throws IOException {
		for (ChatHandler handler : handlers) {
			handler.out.writeObject(object);
		}
		
	}

	public void run() {
		try {
			while (!done) {
				Object readObject = in.readObject();
				if (readObject instanceof LineMessage) {
					broadcastWhiteBoard(readObject);
				} else if (readObject instanceof ChatMessageChild) {
					myObject = (ChatMessageChild) readObject;
					System.out.println("Message read: " + myObject.getMessage());
					broadcast();
				}

			}
		} catch (IOException e) {
			if (e.getMessage().equals("Connection reset")) {
				System.out.println("A client terminated its connection.");
			} else {
				System.out.println("Problem receiving: " + e.getMessage());
			}
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			handlers.remove(this);
			if (list.size() >= 1) {
				list.remove(myObject.getName());
			}

		}
	}

	ChatMessage myObject = null;
	private Socket incoming;

	boolean done = false;
	ArrayList<ChatHandler> handlers;

	ObjectOutputStream out;
	ObjectInputStream in;
	protected ChatMessageChild cmc;
	static Vector<String> list = new Vector<String>();
	static ArrayList<String> history = new ArrayList<String>();
}
