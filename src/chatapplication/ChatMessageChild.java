package chatapplication;

import java.util.ArrayList;
import java.util.Vector;

/**This class implements the setters and getters for userlist and history
 * @author Nachiket Paluskar(31375824)
 *
 */
class ChatMessageChild extends ChatMessage {
	Vector<String> server_user_list = new Vector<String>();
	ArrayList<String> history = new ArrayList<String>();

	public ChatMessageChild() {
		this.message = super.getMessage();
		this.name = super.getName();
	}

	public ArrayList<String> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<String> history) {

		this.history = history;
	}

	public Vector<String> getUser_list() {
		return server_user_list;
	}

	public void setUser_list(Vector<String> user_list) {
		this.server_user_list = user_list;

	}

}
