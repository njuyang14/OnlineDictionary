import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	private JPanel pInput = new JPanel();//���Ϸ����
	private JButton login = new JButton("login");
	private JButton search = new JButton("search");
	private JTextArea in = new JTextArea(1,28);
	private JButton changeMode = new JButton("��/Ӣ");
	private JButton register = new JButton("register");
	private JButton logout = new JButton("logout");
	
	private JPanel menu = new JPanel();//���ʱ�����
	private JButton recite = new JButton();
	private JButton addRecite = new JButton();
	
	private JPanel south = new JPanel();//�·����
	
	private JPanel meaning3 = new JPanel();//��ʾ����ƽ̨���ʽ���
	//todo:��������ƽ̨�Ĳ���
	private JTextArea text0 = new JTextArea(5,25);
	private JTextArea text1 = new JTextArea(5,25);
	private JTextArea text2 = new JTextArea(5,25);
	
	private JPanel sharePanel = new JPanel();
	private JButton shareToFriend = new JButton();//����
	
	private JPanel recitePanel = new JPanel();//���������
	private JList reciteList = new JList();//���ʱ���ʾ
	private JButton nextRecite = new JButton();//��һ������
	private JButton prevRecite = new JButton();//��һ������
	private JButton removeRecite = new JButton();//ɾ�����ʱ��еĵ���
	
	private JPanel friendPanel = new JPanel();//�������
	private JList friendList = new JList();//������ʾ
	private JScrollPane listPanel = new JScrollPane();
	private JButton sendMsg = new JButton();//�����ѷ���Ϣ 
	
	private Border lineBorder = new LineBorder(Color.GRAY, 1);//ȫ�ֱ߽���
	/*Client���캯��*/
	public Client(){
		
		setLayout(new BorderLayout());//Frame Layout
		/*�������Ϸ������Լ��û���½����*/
		add(pInput,BorderLayout.NORTH);
		pInput.setLayout(new FlowLayout());
		pInput.add(login);
		//login.setSize(30, 25);
		pInput.add(search);
		pInput.add(in);
		in.setFont(new Font("Serif", 0, 25));
		pInput.add(changeMode);
		pInput.add(register);
		pInput.add(logout);
		
		/*�·����*/
		add(south,BorderLayout.CENTER);
		south.setLayout(new FlowLayout());//TODO�����ø�ʽ
		south.setBorder(lineBorder);
		
		/*���õ��ʽ������*/
		south.add(meaning3);
		meaning3.setLayout(new GridLayout(3,1,3,3));
		text0.setFont(new Font("Serif", 0, 25));
		text1.setFont(new Font("Serif", 0, 25));
		text2.setFont(new Font("Serif", 0, 25));
		meaning3.add(text0);
		meaning3.add(text1);
		meaning3.add(text2);
		
		/*�������*/
		south.add(sharePanel);
		sharePanel.setBorder(lineBorder);
		
		/*���ñ������*/
		south.add(recitePanel);
		recitePanel.setLayout(new GridLayout(6,1,10,20));
		recitePanel.setBorder(lineBorder);
		//recitePanel.setSize(10, 700);
		
		south.add(listPanel);
		listPanel = new JScrollPane(friendList);
		listPanel.setPreferredSize(new Dimension(180,700));
		
		/*����socket�ӿ�*/
		try
		{
			Socket socket = new Socket("localhost", 8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex)
		{
			//jta.append(ex.toString() + "\n");
		}
	}
}
