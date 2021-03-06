import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

public class Client extends JFrame{
	private Socket socket; 
	private ObjectOutputStream os;
    private ObjectInputStream is;  
	private User user = new User();//
	
	private JPanel bkgp = new JPanel();
	
	private JPanel pInput = new JPanel();//最上方面板
	//private JButton login = new JButton("login");
	private JButton search = new JButton("search");
	private JTextArea in = new JTextArea(1,28);
	//private JButton changeMode = new JButton("中/英");
	//private String[] mode = {"汉译英","英译汉"};
	//private JComboBox changeMode = new JComboBox(mode);
	private JLabel myName = new JLabel();
	//private JButton register = new JButton("register");
	private JButton logout = new JButton("logout");
	
	private JPanel web = new JPanel();//选择查词网站
	private JCheckBox baidu = new JCheckBox("百度");
	private JCheckBox youdao = new JCheckBox("有道");
	private JCheckBox gold = new JCheckBox("必应");
	private boolean[] checkboxState = {false,false,false};//
	
	private JPanel south = new JPanel();//下方面板
	
	private JPanel meaning3 = new JPanel();//显示三方平台单词解释
	/*翻译图标*/
	private ImageIcon iconBaidu = new ImageIcon("./src/百度.png");
	private ImageIcon iconYoudao = new ImageIcon("./src/有道.png");
	private ImageIcon iconBing = new ImageIcon("./src/必应.png");
	private ImageIcon heart = new ImageIcon("./src/heart.png");
	private ImageIcon icon = new ImageIcon("./src/background.png");
	private JLabel labelBaidu = new JLabel(iconBaidu);
	private JLabel labelYoudao = new JLabel(iconYoudao);
	private JLabel labelBing = new JLabel(iconBing);
	/*点赞按钮*/
	private JButton good0 = new JButton();
	private JButton good1 = new JButton();
	private JButton good2 = new JButton();
	private GoodNum g0,g1,g2;
	private GoodNum afterGoodArray[];
	private JPanel m0 = new JPanel();
	private JPanel m1 = new JPanel();
	private JPanel m2 = new JPanel();
	private JTextArea text0 = new JTextArea(5,33);
	private JTextArea text1 = new JTextArea(5,33);
	private JTextArea text2 = new JTextArea(5,33);
	
	private JPanel meanAndCheck = new JPanel();//选择平台和词意显示
	
	private JPanel sharePanel = new JPanel();
	private JButton readMessage = new JButton("查看好友留言");//分享
	
	/*private JPanel recitePanel = new JPanel();//背单词面板
	private JList reciteList = new JList();//单词本显示
	private JButton nextRecite = new JButton();//下一个单词
	private JButton prevRecite = new JButton();//上一个单词
	private JButton removeRecite = new JButton();//删除单词表中的单词
	
	private JPanel friendPanel = new JPanel();//好友面板*/
	private JList<String> friendList = new JList<String>();//好友显示
	private JScrollPane listPanel = new JScrollPane(friendList);
	
	//private JButton sendMsg = new JButton();//给好友发信息 
	
	private Border lineBorder = new LineBorder(Color.GRAY, 1);//全局边界线
	
	private boolean canGood = false;
	/*Client构造函数*/
	public Client(User u,ObjectOutputStream o,ObjectInputStream i){
		user = u;
		os = o;
		is = i;
		System.out.println(user.getName()+":"+user.getpswd());
		String[] friendOnline = {"Login to get other online user!"};
		//String[] friendOnline = user.getFriendList();
		
		setLayout(new BorderLayout());//Frame Layout 
		/*设置背景图片，将frame转换成panel，加入图片后，再在frame上覆盖面板以达成背景图片设置的目的*/
		JLabel backImage = new JLabel(icon);
		backImage.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());//设置图片大小
		((JPanel)this.getContentPane()).setOpaque(false);//获取frame的面板，将其转换成JPanel
		this.getLayeredPane().setLayout(null);//设置无布局方式
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));//加入图片
		
		/*在背景frame上添加第一层面板*/
		Container c = getContentPane(); //获取JFrame面板
		c.add(bkgp,BorderLayout.CENTER);
		bkgp.setOpaque(false);//设置bkgp为透明，
		//bkgp.add(p3);
		
		/*设置最上方输入以及用户登陆界面*/
		bkgp.add(pInput,BorderLayout.NORTH);//............
		pInput.setOpaque(false);
		pInput.add(myName);
		pInput.setLayout(new FlowLayout());
		pInput.add(search);
		search.setFont(new Font("Serif", 0, 23));
		pInput.add(in);
		in.setFont(new Font("Serif", 0, 23));
		myName.setFont(new Font("Serif", 0, 23));
		if(user.getLogInfo())myName.setText("当前："+user.getName()+" ");
		else
			myName.setText("当前："+"游客"+" ");
		logout.setFont(new Font("Serif", 0, 23));
		pInput.add(logout);
		search.addActionListener(new SearchListener());
		logout.addActionListener(new LogoutListener());
		
		/*下方面板*/
		bkgp.add(south,BorderLayout.CENTER);//..........
		south.setLayout(new BorderLayout());
		south.setBorder(lineBorder);
		meanAndCheck.setOpaque(false);
		
		/*设置单词解释面板*/
		south.add(meanAndCheck,BorderLayout.WEST);//    
		south.setOpaque(false);
		
		
		/*词典选择*/
		web.setOpaque(false);
		web.setLayout(new GridLayout(1,3,1,1));
		//baidu.setSize(100,100);
		baidu.setFont(new Font("Serif", 0, 25));
		youdao.setFont(new Font("Serif", 0, 25));
		gold.setFont(new Font("Serif", 0, 25));
		baidu.addItemListener(new myItemListener());//复选框添加监听
		youdao.addItemListener(new myItemListener());
		gold.addItemListener(new myItemListener());
		baidu.setOpaque(false);
		youdao.setOpaque(false);
		gold.setOpaque(false);
		web.add(baidu);
		web.add(youdao);
		web.add(gold);
		
		meaning3.setLayout(new GridLayout(3,1,3,3));
		text0.setFont(new Font("Serif", 0, 22));
		text1.setFont(new Font("Serif", 0, 22));
		text2.setFont(new Font("Serif", 0, 22));
		text0.setLineWrap(true);
		text1.setLineWrap(true);//自动换行
		text2.setLineWrap(true);
		m0.add(labelBaidu);
		m0.add(text0);
		m0.add(good0);
		m1.add(labelYoudao);
		m1.add(text1);
		m1.add(good1);
		m2.add(labelBing);
		m2.add(text2);
		m2.add(good2);
		m0.setOpaque(false);
		m1.setOpaque(false);
		m2.setOpaque(false);
		text0.setOpaque(false);
		text1.setOpaque(false);
		text2.setOpaque(false);
		text0.setBorder(lineBorder);
		text1.setBorder(lineBorder);
		text2.setBorder(lineBorder);
		good0.setBorderPainted(true);
		good1.setBorderPainted(true);
		good2.setBorderPainted(true);
		good0.setIcon(heart);
		good1.setIcon(heart);
		good2.setIcon(heart);
		good0.setOpaque(false);
		good1.setOpaque(false);
		good2.setOpaque(false);
		good0.addActionListener(new GoodListener(0));
		good1.addActionListener(new GoodListener(1));
		good2.addActionListener(new GoodListener(2));
		meaning3.add(web);
		meaning3.add(m0);
		meaning3.add(m1);
		meaning3.add(m2);
		meaning3.setOpaque(false);
		
		meanAndCheck.setLayout(new BorderLayout());
		meanAndCheck.add(web,BorderLayout.NORTH);
		meanAndCheck.add(meaning3,BorderLayout.CENTER);
			
        friendList = new JList<String>(friendOnline);//init list
		friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//friendList.addListSelectionListener(new listSelectionListener());
		friendList.setFont(new Font("Arial", Font.PLAIN, 25));
		
		if(user.getLogInfo()==true)friendList.setListData(user.getFriendList());
		else
			friendList.setListData(friendOnline);
		listPanel = new JScrollPane(friendList);
		listPanel.setPreferredSize(new Dimension(280,650));
		sharePanel.setLayout(new BorderLayout());		
		sharePanel.add(listPanel,BorderLayout.CENTER);
		sharePanel.add(readMessage,BorderLayout.SOUTH);
		readMessage.setFont(new Font("宋体", Font.PLAIN, 22));
		readMessage.addActionListener(new MessageListener());
		sharePanel.setOpaque(false);
		listPanel.setOpaque(false);
		listPanel.getViewport().setOpaque(false);
		friendList.setOpaque(false);
		((JLabel)friendList.getCellRenderer()).setOpaque(false);
		south.add(sharePanel,BorderLayout.EAST);//!!!!!!!! ScrollPane必须设置好再添加		
		friendList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if(arg0.getClickCount() == 2){
                	String toName = friendList.getSelectedValue();
                	new SendMsg(user,os,is,toName);
                }
            }
        });
		
		this.setTitle("Online Dictionary");
		this.setSize(1050, 830);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	class GoodListener implements ActionListener{
		private int btn;
		public GoodListener(int num)
		{
			btn = num;
		}
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			if(canGood){
			OrderType order = new OrderType(2);
			try {
				os.writeObject(order);
				os.flush();
				order = (OrderType)is.readObject();
				
				if(order.getRecv()){
					Integer goodType = 0;
					goodType = new Integer(afterGoodArray[btn].type);
					os.writeObject(goodType);
					os.flush();
					Integer[] num = (Integer[])is.readObject();
					for(int i=0;i<3;i++){
						int max=i;
						for(int j=i;j<3;j++){
							if(num[j]>num[max])max=j;
						}
						int temp = num[max];
						num[max] = num[i];
						num[i] = temp;
					}
					good0.setText(num[0].toString());
					good1.setText(num[1].toString());
					good2.setText(num[2].toString());
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			}
		}
	}
	
	class LogoutListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			if(user.getLogInfo()){
			try
			{
				OrderType order = new OrderType(5);
				os.writeObject(order);
				os.flush();
				order = (OrderType)is.readObject();
				if(order.getRecv()){//判断服务器是否收到信息
					NameLogin temp = new NameLogin(user.getName(), user.getpswd());//TODO:获取自身用户名
					os.writeObject(temp);
					os.flush();
					temp = (NameLogin)is.readObject();
				}
			}
			catch (IOException e)
			{
			// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			}		
			//Client.this.dispose();
			//new Login(os,is);
		}
	}
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			Pattern p = Pattern.compile("[a-zA-Z ]+");
			Matcher m = p.matcher(in.getText());
			if(m.matches()){
			try
			{	
				
				OrderType order = new OrderType(0);
				os.writeObject(order);
				os.flush();
				order = (OrderType)is.readObject();
				
				if(order.getRecv()){//判断服务器是否收到信息
				TranslateInfo t = new TranslateInfo();
				//t.setType(0);
				t.setWord(in.getText());
				
				os.writeObject(t);
				os.flush();
				
				t = (TranslateInfo)is.readObject();
				/*初始时按照百度有道必应的顺序显示*/
				int[] goodNum = t.getGoodNum();
				/*接收结果按照返回的点赞数进行排序*/
				g0 = new GoodNum(goodNum[0],0,t.getMean0(),checkboxState[0]);
				g1 = new GoodNum(goodNum[1],1,t.getMean1(),checkboxState[1]);
				g2 = new GoodNum(goodNum[2],2,t.getMean2(),checkboxState[2]);
				GoodNum array[] = {g0,g1,g2};
				if(checkboxState[0]==false)array[0].text="";
				if(checkboxState[1]==false)array[1].text="";
				if(checkboxState[2]==false)array[2].text="";
				for(int i=0;i<3;i++)
				{
					int max = i;
					for(int j=i;j<3;j++)
					{
						if((array[max].state==false&&array[j].state!=false)||array[j].goodNum>array[max].goodNum)
						{
							max = j;
						}
					}
					GoodNum temp = array[max];
					array[max] = array[i];
					array[i] = temp;
				}
				text0.setText(array[0].text);
				text1.setText(array[1].text);
				text2.setText(array[2].text);
				labelBaidu.setIcon(array[0].icon);
				labelYoudao.setIcon(array[1].icon);
				labelBing.setIcon(array[2].icon);
				afterGoodArray = array;
				
				if(user.getLogInfo()==true){
					friendList.setListData(user.getFriendList());
					canGood = true;
				}
				}
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
			else{
				in.setText("请重新输入！");
			}
		}
	}
	
	class GoodNum{
		public int type;//
		public int goodNum;
		public ImageIcon icon;
		public String text;
		public boolean state;
		public GoodNum(int g,int i,String t,boolean st){
			type = i;//点赞类别
			goodNum = g;
			if(i==0)icon = new ImageIcon("./src/百度.png");
			else if(i==1)icon = new ImageIcon("./src/有道.png");
			else if(i==2)icon = new ImageIcon("./src/必应.png");
			text = t;
			state = st;
		}
	}
	
	class myItemListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			// TODO 自动生成的方法存根
			 Object source = arg0.getItemSelectable();
			 if(source == baidu){checkboxState[0] = !checkboxState[0];}
			 if(source == youdao){checkboxState[1] = !checkboxState[1];}
			 if(source == gold){checkboxState[2] = !checkboxState[2];}
		}
		
	}
	
	class MessageListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			if(user.getLogInfo()){
			    new ReadMsg(user,os,is);
			}
		}		
	}
}
