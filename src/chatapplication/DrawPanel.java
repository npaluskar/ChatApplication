package chatapplication;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;


/**This class implements graphics used for whitboard
 * @author Nachiket Paluskar(31375824)
 *
 */
public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener{
	ArrayList<Line> linelist;
	int lastX, lastY;
	ClientGUI container;
	
	public DrawPanel(){}
	public DrawPanel(ClientGUI conatiner){
		setBackground(Color.WHITE);
		linelist = new ArrayList<Line>();
		addMouseMotionListener(this);
		addMouseListener(this);
		this.container=conatiner;
	}
	public void mouseMoved(MouseEvent me){
	}
	public void mouseDragged(MouseEvent me){
		int endX = me.getX();
		int endY = me.getY();
		Line line = new Line(lastX, lastY, endX, endY);
		linelist.add(line);
		lastX = endX;
		lastY = endY;
		repaint();

	}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){
		lastX = me.getX();
		lastY = me.getY();
	}
	public void mouseReleased(MouseEvent me){
		LineMessage lm = new LineMessage();
	lm.setMessage(container.dp.linelist);
	container.sendMessage(lm);
	}
	public void mouseClicked(MouseEvent me){}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Iterator<Line> it = linelist.iterator();
		while(it.hasNext()){
			Line current = it.next();
			g.drawLine(current.getStartX(), current.getStartY(),
					current.getEndX(), current.getEndY());
		}
	
	
	}
	public Dimension getPreferredSize(){
		return new Dimension(450,100);
	}
	public Dimension getMinimumSize(){
		return new Dimension(450,100);
	}
}