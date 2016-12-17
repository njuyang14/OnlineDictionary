import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Login extends JFrame{
	private ObjectOutputStream os;
    private ObjectInputStream is;
	private User user = new User();
	private JLabel l1 = new JLabel("�û���");
	private JLabel l2 = new JLabel("   ���� ");
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel p3 = new JPanel();
	private JTextArea area1 = new JTextArea(1,10);
	private JTextArea area2 = new JTextArea(1,10);
	//private JTextArea area3 = new JTextArea(1,28);
	
	private JButton y = new JButton("��½");
	private JButton q = new JButton("�˳�");
	private JButton r = new JButton("ע��");
	private JButton travel = new JButton("�ο͵�½");
	
	public Login(ObjectOutputStream o, ObjectInputStream i){

		os = o;
		is = i;
		l1.setFont(new Font("Serif", 0, 25));
		l2.setFont(new Font("Serif", 0, 25));
		area1.setFont(new Font("Serif", 0, 25));
		area2.setFont(new Font("Serif", 0, 25));
		y.setFont(new Font("Serif", 0, 20));
		q.setFont(new Font("Serif", 0, 20));
		r.setFont(new Font("Serif", 0, 20));
		travel.setFont(new Font("Serif", 0, 20));
		
		p1.setLayout(new FlowLayout());
		p2.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		p1.add(l1);
		p1.add(area1);
		p2.add(l2);
		p2.add(area2);
		p3.add(y);
		p3.add(r);
		p3.add(q);
		p3.add(travel);
		setLayout(new GridLayout(3,1,1,1));
		add(p1);
		add(p2);
		add(p3);
		
		
		
		y.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				String name = area1.getText();
				String pswd = area2.getText();
				NameLogin temp = new NameLogin(name,pswd);
				
				try {
					OrderType order = new OrderType(1);
					os.writeObject(order);
					os.flush();
					order = (OrderType)is.readObject();
					
					if(order.getRecv()){
					os.writeObject(temp);
					os.flush();
					temp = (NameLogin)is.readObject();
					}

				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				
				if(temp.getConn()){
					user.setName(name);
					user.setpswd(pswd);
					user.setLogInfo(true);
					user.setFriendList(temp.getFriendList());//��ú����б�
					JFrame frame = new Client(user,os,is);
				}	
				Login.this.dispose();//���ص�½����
			}
			
		});
		
		travel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������

				user.setLogInfo(false);
				JFrame frame = new Client(user,os,is);
				Login.this.dispose();//���ص�½����
			}
			
		});
		
		r.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				Login.this.dispose();//���ص�½����
				JFrame frame = new Register(os,is);
			}
			
		});
		
		this.setTitle("Login");
		this.setSize(400, 300);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	

}
