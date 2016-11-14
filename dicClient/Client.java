import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	private JPanel pInput = new JPanel();//最上方面板
	private JButton login = new JButton();
	private JTextArea in = new JTextArea();
	private JButton register = new JButton();
	private JButton logout = new JButton();
	
	private JPanel menu = new JPanel();//单词本功能
	private JButton recite = new JButton();
	private JButton addRecite = new JButton();
	
	private JPanel west = new JPanel();//下方面板
	
	private JPanel meaning3 = new JPanel();//显示三方平台单词解释
	private JButton shareToFriend = new JButton();//分享
	//todo:设置三个平台的布局
	
	private JPanel recitePanel = new JPanel();//背单词面板
	private JList reciteList = new JList();//单词本显示
	private JButton nextRecite = new JButton();//下一个单词
	private JButton prevRecite = new JButton();//上一个单词
	private JButton removeRecite = new JButton();//删除单词表中的单词
	
	private JPanel friendPanel = new JPanel();//好友面板
	private JList friend = new JList();//好友显示
	private JScrollPane listPanel = new JScrollPane();
	private JButton sendMsg = new JButton();//给好友发信息 
	
	/*Client构造函数*/
	public Client(){
		
		setLayout(new BorderLayout());//Frame Layout
		
		
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
