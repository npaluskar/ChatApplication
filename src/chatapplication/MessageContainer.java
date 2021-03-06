package chatapplication;
import java.io.Serializable;
import java.util.ArrayList;

/** This is abstract class which sets and gets the variables on server and client side
 * @author Nachiket Paluskar(31375824)
 *
 */
abstract public class MessageContainer{

	abstract public void setMessage(Object message);
	
	abstract public Object getMessage();

}

class StringMessage extends MessageContainer implements Serializable{

	String message;

	public void setMessage(Object message){
		this.message = (String)message;
	}
	
	public Object getMessage(){
			return message;	
	}

}
class UserMessage extends MessageContainer implements Serializable{

	String message;

	public void setMessage(Object message){
		this.message = (String)message;
	}
	
	public Object getMessage(){
			return message;	
	}

}
class LineMessage extends MessageContainer implements Serializable{

	ArrayList<Line> message;

	public void setMessage(Object message){
		this.message = (ArrayList)message;
	}
	
	public Object getMessage(){
			return message;	
	}

}
class Line implements Serializable{
	int startx, starty, endx, endy;
		
	public Line(){}
	public Line(int sx, int sy, int ex, int ey){
		setStartX(sx);
		setStartY(sy);
		setEndX(ex);
		setEndY(ey);
	}
	public void setStartX(int sx){
		startx = sx;
	}
	public void setStartY(int sy){
		starty = sy;
	}
	public void setEndX(int ex){
		endx = ex;
	}
	public void setEndY(int ey){
		endy = ey;
	}
	public int getStartX(){
		return startx;
	}
	public int getStartY(){
		return starty;
	}
	public int getEndX(){
		return endx;
	}
	public int getEndY(){
		return endy;
	}
}