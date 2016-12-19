import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;


public class Register extends JFrame{
	private ObjectOutputStream os;
    private ObjectInputStream is;  
    
	private JLabel l1 = new JLabel("�û���");
	private JLabel l2 = new JLabel("   ���� ");
	private JLabel l3 = new JLabel("�ٴ�����");
	private JPanel p1 = new JPanel();
	private JPanel p2 = new JPanel();
	private JPanel p4 = new JPanel();
	private JPanel p3 = new JPanel();
	private JTextArea area1 = new JTextArea(1,10);
	private JPasswordField area2 = new JPasswordField(10);
	private JPasswordField area3 = new JPasswordField(10);
	
	private JButton y = new JButton("ע��");
	private JButton q = new JButton("�˳�");
	
	public Register(ObjectOutputStream o,ObjectInputStream i){
		os = o;
		is = i;
		l1.setFont(new Font("Serif", 0, 25));
		l2.setFont(new Font("Serif", 0, 25));
		l3.setFont(new Font("Serif", 0, 25));
		area1.setFont(new Font("Serif", 0, 25));
		area2.setFont(new Font("Serif", 0, 25));
		area3.setFont(new Font("Serif", 0, 25));
		y.setFont(new Font("Serif", 0, 20));
		q.setFont(new Font("Serif", 0, 20));
		
		p1.setLayout(new FlowLayout());
		p2.setLayout(new FlowLayout());
		p4.setLayout(new FlowLayout());
		p3.setLayout(new FlowLayout());
		p1.add(l1);
		p1.add(area1);
		p2.add(l2);
		p2.add(area2);
		p4.add(l3);
		p4.add(area3);
		p3.add(y);
		p3.add(q);
		setLayout(new GridLayout(4,1,1,1));
		add(p1);
		add(p2);
		add(p4);
		add(p3);
		
		y.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				String name = area1.getText();
				String pswd = area2.getText();
				String ps = area3.getText();
				if(pswd.equals(ps)){
				NameLogin temp = new NameLogin(name,pswd);
				try {
					OrderType order = new OrderType(4);
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
				}
				else{
					area3.setText("�������벻һ��");
				}
				
				Register.this.dispose();//���ص�½����
				JFrame frame = new Login(os, is);
			}
			
		});
		
		this.setTitle("Register");
		this.setSize(400, 300);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	

}
