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
	
	private JPanel pInput = new JPanel();//最上方面板
	private JButton login = new JButton("login");
	private JButton search = new JButton("search");
	private JTextArea in = new JTextArea(1,28);
	private JButton changeMode = new JButton("中/英");
	private JButton register = new JButton("register");
	private JButton logout = new JButton("logout");
	
	private JPanel menu = new JPanel();//单词本功能
	private JButton recite = new JButton();
	private JButton addRecite = new JButton();
	
	private JPanel south = new JPanel();//下方面板
	
	private JPanel meaning3 = new JPanel();//显示三方平台单词解释
	//todo:设置三个平台的布局
	private JTextArea text0 = new JTextArea(5,25);
	private JTextArea text1 = new JTextArea(5,25);
	private JTextArea text2 = new JTextArea(5,25);
	
	private JPanel sharePanel = new JPanel();
	private JButton shareToFriend = new JButton();//分享
	
	private JPanel recitePanel = new JPanel();//背单词面板
	private JList reciteList = new JList();//单词本显示
	private JButton nextRecite = new JButton();//下一个单词
	private JButton prevRecite = new JButton();//上一个单词
	private JButton removeRecite = new JButton();//删除单词表中的单词
	
	private JPanel friendPanel = new JPanel();//好友面板
	private JList friendList = new JList();//好友显示
	private JScrollPane listPanel = new JScrollPane();
	private JButton sendMsg = new JButton();//给好友发信息 
	
	private Border lineBorder = new LineBorder(Color.GRAY, 1);//全局边界线
	/*Client构造函数*/
	public Client(){
		
		setLayout(new BorderLayout());//Frame Layout
		/*设置最上方输入以及用户登陆界面*/
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
		
		/*下方面板*/
		add(south,BorderLayout.CENTER);
		south.setLayout(new FlowLayout());//TODO：设置格式
		south.setBorder(lineBorder);
		
		/*设置单词解释面板*/
		south.add(meaning3);
		meaning3.setLayout(new GridLayout(3,1,3,3));
		text0.setFont(new Font("Serif", 0, 25));
		text1.setFont(new Font("Serif", 0, 25));
		text2.setFont(new Font("Serif", 0, 25));
		meaning3.add(text0);
		meaning3.add(text1);
		meaning3.add(text2);
		
		/*分享面板*/
		south.add(sharePanel);
		sharePanel.setBorder(lineBorder);
		
		/*设置背诵面板*/
		south.add(recitePanel);
		recitePanel.setLayout(new GridLayout(6,1,10,20));
		recitePanel.setBorder(lineBorder);
		//recitePanel.setSize(10, 700);
		
		south.add(listPanel);
		listPanel = new JScrollPane(friendList);
		listPanel.setPreferredSize(new Dimension(180,700));
		
		/*建立socket接口*/
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
