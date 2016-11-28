import java.io.*;
import java.net.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{
	private Socket socket; 
	//private DataOutputStream toServer;
	//private DataInputStream fromServer;
	private ObjectOutputStream os;
    private ObjectInputStream is;  
	private User user = new User();//
	
	private JPanel pInput = new JPanel();//最上方面板
	//private JButton login = new JButton("login");
	private JButton search = new JButton("search");
	private JTextArea in = new JTextArea(1,28);
	//private JButton changeMode = new JButton("中/英");
	private String[] mode = {"汉译英","英译汉"};
	private JComboBox changeMode = new JComboBox(mode);
	//private JButton register = new JButton("register");
	private JButton logout = new JButton("logout");
	
	private JPanel web = new JPanel();//选择查词网站
	private JCheckBox baidu = new JCheckBox("百度");
	private JCheckBox youdao = new JCheckBox("有道");
	private JCheckBox gold = new JCheckBox("金山");
	
	private JPanel south = new JPanel();//下方面板
	
	private JPanel meaning3 = new JPanel();//显示三方平台单词解释
	private JTextArea text0 = new JTextArea(5,34);
	private JTextArea text1 = new JTextArea(5,34);
	private JTextArea text2 = new JTextArea(5,34);
	
	private JPanel meanAndCheck = new JPanel();//选择平台和词意显示
	
	private JPanel sharePanel = new JPanel();
	private JButton shareToFriend = new JButton();//分享
	
	private JPanel recitePanel = new JPanel();//背单词面板
	private JList reciteList = new JList();//单词本显示
	private JButton nextRecite = new JButton();//下一个单词
	private JButton prevRecite = new JButton();//上一个单词
	private JButton removeRecite = new JButton();//删除单词表中的单词
	
	private JPanel friendPanel = new JPanel();//好友面板
	private JList<String> friendList = new JList<String>();//好友显示
	private JScrollPane listPanel = new JScrollPane(friendList);
	
	private JButton sendMsg = new JButton();//给好友发信息 
	
	private Border lineBorder = new LineBorder(Color.GRAY, 1);//全局边界线
	/*Client构造函数*/
	public Client(User u){
		user = u;
		System.out.println(user.getName()+":"+user.getpswd());
		String[] friendOnline = { "black", "blue", "green", "yellow", "white", "black", "blue", "green", "yellow", "white" };
		
		setLayout(new BorderLayout());//Frame Layout 
		/*设置最上方输入以及用户登陆界面*/
		add(pInput,BorderLayout.NORTH);
		pInput.setLayout(new FlowLayout());
		pInput.add(search);
		search.setFont(new Font("Serif", 0, 23));
		pInput.add(in);
		in.setFont(new Font("Serif", 0, 23));
		changeMode.setFont(new Font("Serif", 0, 23));
		pInput.add(changeMode);
		logout.setFont(new Font("Serif", 0, 23));
		pInput.add(logout);
		search.addActionListener(new SearchListener());
		
		/*下方面板*/
		add(south,BorderLayout.CENTER);
		south.setLayout(new BorderLayout());
		south.setBorder(lineBorder);
		
		/*设置单词解释面板*/
		south.add(meanAndCheck,BorderLayout.WEST);//    
		
		/*词典选择*/
		web.setLayout(new GridLayout(1,3,1,1));
		//baidu.setSize(100,100);
		baidu.setFont(new Font("Serif", 0, 25));
		youdao.setFont(new Font("Serif", 0, 25));
		gold.setFont(new Font("Serif", 0, 25));
		web.add(baidu);
		web.add(youdao);
		web.add(gold);
		
		meaning3.setLayout(new GridLayout(3,1,3,3));
		text0.setFont(new Font("Serif", 0, 25));
		text1.setFont(new Font("Serif", 0, 25));
		text2.setFont(new Font("Serif", 0, 25));
		text0.setLineWrap(true);
		text1.setLineWrap(true);//自动换行
		text2.setLineWrap(true);
		meaning3.add(web);
		meaning3.add(text0);
		meaning3.add(text1);
		meaning3.add(text2);
		
		meanAndCheck.setLayout(new BorderLayout());
		meanAndCheck.add(web,BorderLayout.NORTH);
		meanAndCheck.add(meaning3,BorderLayout.CENTER);
		
		/*分享面板*/
		south.add(sharePanel,BorderLayout.CENTER);//
		sharePanel.setBorder(lineBorder);
		
		/*设置背诵面板*/
		//south.add(recitePanel);
		recitePanel.setLayout(new GridLayout(6,1,10,20));
		recitePanel.setBorder(lineBorder);
		//recitePanel.setSize(10, 700);
			
        friendList = new JList<String>(friendOnline);//init list
		friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		friendList.setFont(new Font("Arial", Font.PLAIN, 19));
		listPanel = new JScrollPane(friendList);
		listPanel.setPreferredSize(new Dimension(280,630));
		south.add(listPanel,BorderLayout.EAST);//!!!!!!!! ScrollPane必须设置好再添加
		
		this.setTitle("Online Dictionary");
		this.setSize(1050, 830);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/*建立socket接口*/
		try
		{
			socket = new Socket("10.0.1.7", 8000);
			is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			os = new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException ex)
		{
			text0.append(ex.toString() + "\n");
		}
	}
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			try
			{
				/*socket = new Socket("10.0.1.7", 8000);
				os = new ObjectOutputStream(socket.getOutputStream());
				is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));*/
				
				TranslateInfo t = new TranslateInfo();
				t.setType(0);
				t.setWord(in.getText());
				
				os.writeObject(t);
				os.flush();
				
				t = (TranslateInfo)is.readObject();
				text0.setText(t.getMean0());
				text1.setText(t.getMean1());
				text2.setText(t.getMean2());
				socket.close();
			}
			catch(IOException ex)
			{
				text1.append(ex.toString() + "\n");
			} 
			catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
