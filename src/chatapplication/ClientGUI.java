
package chatapplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;

/**This class is implementing client of chat application
 * @author Nachiket Paluskar(31375824)
 *
 */
public class ClientGUI extends JFrame implements Runnable, ActionListener{

	
	/*************************************** Declaration of Variables ***************************************/
	protected Socket s;
	protected ObjectInputStream i;
	protected ObjectOutputStream o;
	protected ChatMessage cm;
	protected ChatMessageChild cmc;
	protected Thread listener;
	protected boolean first = true, kill = false, connect, name_added = true;
	protected String name = "",name_temp;
	protected JFrame client_info, namenotnull;
	private javax.swing.JTextArea History;
	private javax.swing.JTextArea User_list;
	private javax.swing.JTextField input;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTextArea output;
	private javax.swing.JButton disconnectbutton, connectbutton;
	JFrame connectionwindow;
	String host, port;
	int x,y,x1,y1;
	JTextField host_text,port_text;
	DrawPanel dp;
	public ClientGUI() {
		dp = new DrawPanel(this);
	}

	
	/*************************************** Get Host and port from the user***************************************/
	
	public void initialize() {

		connectionwindow = new JFrame("Enter Server Information");
		connectionwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panOuter = new JPanel(new BorderLayout());
		JPanel panLeft = new JPanel(new BorderLayout());
		panLeft.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel panRight = new JPanel(new BorderLayout());
		panRight.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel panBottom = new JPanel();
		panBottom.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		panOuter.add(panLeft, BorderLayout.WEST);
		panOuter.add(panRight, BorderLayout.EAST);
		panOuter.add(panBottom, BorderLayout.SOUTH);

		JLabel lblLeft = new JLabel("Host", JLabel.CENTER);
		JLabel lblRight = new JLabel("Port", JLabel.CENTER);

		 host_text = new JTextField(10);
		 port_text = new JTextField(10);

		connectbutton = new JButton("Connect");
		connectbutton.addActionListener(this);
		panLeft.add(lblLeft, BorderLayout.NORTH);
		panLeft.add(host_text, BorderLayout.CENTER);

		panRight.add(lblRight, BorderLayout.NORTH);
		panRight.add(port_text, BorderLayout.CENTER);

		panBottom.add(connectbutton);
		
		host_text.addActionListener(this);
		port_text.addActionListener(this);

		//host_text.setText("128.235.44.71");
		//port_text.setText("3456");
		host = host_text.getText();
		port = port_text.getText();
		connectionwindow.setContentPane(panOuter);
		connectionwindow.pack();
		connectionwindow.setVisible(true);

	}

	/*************************************** Initializing main Window of Chat Application **************************************/
	
	public void initComponents() {

		setVisible(true);
		setTitle("Chat Application");
		jScrollPane1 = new javax.swing.JScrollPane();
		User_list = new javax.swing.JTextArea();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		output = new javax.swing.JTextArea();
		input = new javax.swing.JTextField();
		jScrollPane3 = new javax.swing.JScrollPane();
		jScrollPane4 = new javax.swing.JScrollPane();
		History = new javax.swing.JTextArea();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		disconnectbutton = new javax.swing.JButton();
		
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		input.requestFocusInWindow();
		User_list.setEditable(false);
		User_list.setColumns(20);
		User_list.setRows(5);
		jScrollPane1.setViewportView(User_list);

		jLabel1.setText("Online Users");

		output.setEditable(false);
		output.setColumns(20);
		output.setRows(5);
		jScrollPane2.setViewportView(output);

		input.addActionListener(this);
		jScrollPane3.setViewportView(dp);
		
		History.setEditable(false);
		History.setColumns(20);
		History.setRows(5);
		jScrollPane4.setViewportView(History);

		jLabel2.setText("Enter Text to send. Enter Your Name First");

		jLabel3.setText("Chat History");

		jLabel4.setText("Be Creative !!! Draw Something");

		jLabel5.setText("Wall");

		disconnectbutton.setText("Disconnect");
		disconnectbutton.addActionListener(this);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(53, 53, 53).addComponent(jLabel1)))
				.addGap(82, 82, 82)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
						.addComponent(input, javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)).addComponent(jLabel2)
						.addComponent(jLabel3).addComponent(jLabel5))
				.addGap(45, 45, 45)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel4).addContainerGap(54,
								Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
										disconnectbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 149,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addComponent(jScrollPane3))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(25, 25, 25)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel2).addComponent(jLabel4))
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(jScrollPane1)
						.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
								.addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(jLabel5).addGap(4, 4, 4)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 228,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34,
										Short.MAX_VALUE)
								.addComponent(jLabel3)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 218,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(jScrollPane3)).addGap(5, 5, 5).addComponent(disconnectbutton)));

		pack();

	}

	/************************************** Initializing Socket Connection to server **************************************/
	
	public void initconnection(String host, String port) {
		try {
			s = new Socket(host, Integer.parseInt(port));
			o = new ObjectOutputStream(s.getOutputStream());
			i = new ObjectInputStream(s.getInputStream());
			first = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Stream creation problem.");
		}
		cmc = new ChatMessageChild();

		try {
			if (cmc.getName() != null)
				o.writeObject(cmc);
		} catch (IOException e) {
			e.printStackTrace();
		};

		listener = new Thread(this);
		listener.start();

	}


	public void stop() {
		kill = true;
		listener = null;

	}

	
	/************************************** Actions for GUI components **************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == input) {
			try {
					if (first) {
						
						if ((!input.getText().isEmpty())) {						
						name = input.getText();
						cmc.setName(input.getText());
						cmc.setMessage("HAS ENTERED");
						first = false;
					
						}else {
							/* Name is empty so return the user and get input again*/
							JOptionPane.showMessageDialog(namenotnull, "Please enter different name");
								return;
							}
					} else {
						/* Username has been entered start Chating */
					cmc.setName(name);
					cmc.setMessage(input.getText());

				}
				o.writeObject(cmc);
		

			} catch (IOException ex) {
				ex.printStackTrace();
				kill = true;
			}
			input.setText("");
			// initLabel.setText("");
			return;
		}
		if (e.getSource() == connectbutton) {

			host = host_text.getText();
			port = port_text.getText();
			System.out.println(host +"  "+ port);
			initconnection(host, port);
			connectionwindow.dispose();
			initComponents();

		}

		if (e.getSource() == disconnectbutton) {
			System.exit(0);
			
		}		
		return;
	}
	/************************************** Chat program runs in run method till we disconnect it **************************************/
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (kill) {
			return;
		}
		try {
			while (true) {
				
				Object readObject = i.readObject();
				
				if(readObject instanceof LineMessage){
					LineMessage lm = (LineMessage)readObject;
					ArrayList<Line> linelist = (ArrayList)lm.getMessage();
					dp.linelist = linelist;
					dp.repaint();
				}
				
				
				else if(readObject instanceof ChatMessageChild){
					
					cmc = (ChatMessageChild) readObject;
					
					User_list.setText(cmc.getUser_list().toString().replace("[", "").replace("]", "").replace(",","\n"));
					String line = cmc.getName() + ": " + cmc.getMessage();
					output.append(line + "\n");
					if (History.getText().isEmpty()) {
						/* If User just joined so history is empty then retrieve history for user*/
						History.setText(cmc.getHistory().toString().replace("[", "").replace("]", "").replace(",","\n"));
					}
					}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Can't find ChatMessage.");
		} finally {
			validate();
			/*try {
				o.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}*/
		}
	}
	
public void sendMessage(Object ob){
		
		if(true){
			if(ob instanceof LineMessage) System.out.println("LineMessage written to stream");
			try{
				o.writeObject(ob);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		ClientGUI gui = new ClientGUI();
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gui.initialize();
		

	}

	

}
