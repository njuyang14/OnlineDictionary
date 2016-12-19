import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class ReadMsg extends JFrame{
	private JTextArea text = new JTextArea();
	private JScrollPane p;
	private JPanel psend = new JPanel();
	private JButton send = new JButton("确定");
	
	public ReadMsg(User u, ObjectOutputStream os, ObjectInputStream is){
		p = new JScrollPane(text);
		text.setFont(new Font("Serif", 0, 22));
		text.setLineWrap(true);
		text.setFont(new Font("Serif", 0, 22));
		psend.add(send);
		setLayout(new BorderLayout());
		add(p,BorderLayout.CENTER);
		add(psend,BorderLayout.SOUTH);
		send.addActionListener(new SendListener());
		
		OrderType num = new OrderType(6);
		try {
			os.writeObject(num);
			os.flush();
			
			num = (OrderType)is.readObject();
			if(num.getRecv()){
				String str = u.getName();
				os.writeObject(str);
				os.flush();	
				String msg = (String)is.readObject();
				text.setText(msg);
			}
			
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		this.setTitle("Message!!!");
		this.setSize(400, 300);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	class SendListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			ReadMsg.this.dispose();
		}
		
	}
	
}
