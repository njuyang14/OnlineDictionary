import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class SendMsg extends JFrame{
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private User user;
	private String toFriend;
	private JTextArea text = new JTextArea();
	private JScrollPane p;
	private JPanel psend = new JPanel();
	private JButton send = new JButton("发送");
	
	public SendMsg(User u, ObjectOutputStream o, ObjectInputStream i, String to){
		p = new JScrollPane(text);
		text.setFont(new Font("Serif", 0, 22));
		psend.add(send);
		setLayout(new BorderLayout());
		add(p,BorderLayout.CENTER);
		add(psend,BorderLayout.SOUTH);
		send.addActionListener(new SendListener());
		
		user = u;
		os = o;
		is = i;
		toFriend = to;
		
		this.setTitle("Send Message");
		this.setSize(400, 300);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	class SendListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			if(user.getLogInfo()){
			String str = text.getText();
			OrderType num = new OrderType(3);
			try {
				os.writeObject(num);
				try {
					num = (OrderType)is.readObject();
					if(num.getRecv()){
						String[] msg = {" "," "," "};
						msg[0] = user.getName();
						msg[1] = toFriend;
						msg[2] = str;
						os.writeObject(msg);
						os.flush();
						msg = (String[])is.readObject();
					}
				} catch (ClassNotFoundException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			}
			SendMsg.this.dispose();
		}
		
	}
}
