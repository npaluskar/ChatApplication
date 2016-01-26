
package chatapplication;

import java.io.*;

/** This class is using setters and getters of name and message from clients
 * @author Nachiket Paluskar(31375824)
 *
 */
public class ChatMessage implements Serializable {
	public String name;
	public String message;

	public ChatMessage() {
	}

	public ChatMessage(String name, String message) {
		setName(name);
		setMessage(message);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
