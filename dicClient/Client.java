import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	private JPanel pInput = new JPanel();//���Ϸ����
	private JButton login = new JButton();
	private JTextArea in = new JTextArea();
	private JButton register = new JButton();
	private JButton logout = new JButton();
	
	private JPanel menu = new JPanel();//���ʱ�����
	private JButton recite = new JButton();
	private JButton addRecite = new JButton();
	
	private JPanel west = new JPanel();//�·����
	
	private JPanel meaning3 = new JPanel();//��ʾ����ƽ̨���ʽ���
	private JButton shareToFriend = new JButton();//����
	//todo:��������ƽ̨�Ĳ���
	
	private JPanel recitePanel = new JPanel();//���������
	private JList reciteList = new JList();//���ʱ���ʾ
	private JButton nextRecite = new JButton();//��һ������
	private JButton prevRecite = new JButton();//��һ������
	private JButton removeRecite = new JButton();//ɾ�����ʱ��еĵ���
	
	private JPanel friendPanel = new JPanel();//�������
	private JList friend = new JList();//������ʾ
	private JScrollPane listPanel = new JScrollPane();
	private JButton sendMsg = new JButton();//�����ѷ���Ϣ 
	
	/*Client���캯��*/
	public Client(){
		
		setLayout(new BorderLayout());//Frame Layout
		
		
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
