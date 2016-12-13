import java.io.*;
import java.net.*;

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
	private JCheckBox gold = new JCheckBox("必应");
	private boolean[] checkboxState = {false,false,false};//
	
	private JPanel south = new JPanel();//下方面板
	
	private JPanel meaning3 = new JPanel();//显示三方平台单词解释
	/*翻译图标*/
	private ImageIcon iconBaidu = new ImageIcon("./src/百度.png");
	private ImageIcon iconYoudao = new ImageIcon("./src/有道.png");
	private ImageIcon iconBing = new ImageIcon("./src/必应.png");
	private JLabel labelBaidu = new JLabel(iconBaidu);
	private JLabel labelYoudao = new JLabel(iconYoudao);
	private JLabel labelBing = new JLabel(iconBing);
	/*点赞按钮*/
	private JButton good0 = new JButton("赞");
	private JButton good1 = new JButton("赞");
	private JButton good2 = new JButton("赞");
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
	public Client(User u,ObjectOutputStream o,ObjectInputStream i){
		user = u;
		os = o;
		is = i;
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
		baidu.addItemListener(new myItemListener());//复选框添加监听
		youdao.addItemListener(new myItemListener());
		gold.addItemListener(new myItemListener());
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
		good0.setBorderPainted(false);
		good1.setBorderPainted(false);
		good2.setBorderPainted(false);
		good0.addActionListener(new GoodListener(0));
		good1.addActionListener(new GoodListener(1));
		good2.addActionListener(new GoodListener(2));
		meaning3.add(web);
		meaning3.add(m0);
		meaning3.add(m1);
		meaning3.add(m2);
		
		meanAndCheck.setLayout(new BorderLayout());
		meanAndCheck.add(web,BorderLayout.NORTH);
		meanAndCheck.add(meaning3,BorderLayout.CENTER);
			
        friendList = new JList<String>(friendOnline);//init list
		friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		friendList.setFont(new Font("Arial", Font.PLAIN, 19));
		if(user.getLogInfo()==false)
		    listPanel = new JScrollPane(friendList);
		listPanel.setPreferredSize(new Dimension(280,630));
		south.add(listPanel,BorderLayout.EAST);//!!!!!!!! ScrollPane必须设置好再添加
		
		
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
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
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
}
