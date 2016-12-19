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
	
	private JPanel pInput = new JPanel();//���Ϸ����
	//private JButton login = new JButton("login");
	private JButton search = new JButton("search");
	private JTextArea in = new JTextArea(1,28);
	//private JButton changeMode = new JButton("��/Ӣ");
	//private String[] mode = {"����Ӣ","Ӣ�뺺"};
	//private JComboBox changeMode = new JComboBox(mode);
	private JLabel myName = new JLabel();
	//private JButton register = new JButton("register");
	private JButton logout = new JButton("logout");
	
	private JPanel web = new JPanel();//ѡ������վ
	private JCheckBox baidu = new JCheckBox("�ٶ�");
	private JCheckBox youdao = new JCheckBox("�е�");
	private JCheckBox gold = new JCheckBox("��Ӧ");
	private boolean[] checkboxState = {false,false,false};//
	
	private JPanel south = new JPanel();//�·����
	
	private JPanel meaning3 = new JPanel();//��ʾ����ƽ̨���ʽ���
	/*����ͼ��*/
	private ImageIcon iconBaidu = new ImageIcon("./src/�ٶ�.png");
	private ImageIcon iconYoudao = new ImageIcon("./src/�е�.png");
	private ImageIcon iconBing = new ImageIcon("./src/��Ӧ.png");
	private ImageIcon heart = new ImageIcon("./src/heart.png");
	private ImageIcon icon = new ImageIcon("./src/background.png");
	private JLabel labelBaidu = new JLabel(iconBaidu);
	private JLabel labelYoudao = new JLabel(iconYoudao);
	private JLabel labelBing = new JLabel(iconBing);
	/*���ް�ť*/
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
	
	private JPanel meanAndCheck = new JPanel();//ѡ��ƽ̨�ʹ�����ʾ
	
	private JPanel sharePanel = new JPanel();
	private JButton readMessage = new JButton("�鿴��������");//����
	
	/*private JPanel recitePanel = new JPanel();//���������
	private JList reciteList = new JList();//���ʱ���ʾ
	private JButton nextRecite = new JButton();//��һ������
	private JButton prevRecite = new JButton();//��һ������
	private JButton removeRecite = new JButton();//ɾ�����ʱ��еĵ���
	
	private JPanel friendPanel = new JPanel();//�������*/
	private JList<String> friendList = new JList<String>();//������ʾ
	private JScrollPane listPanel = new JScrollPane(friendList);
	
	//private JButton sendMsg = new JButton();//�����ѷ���Ϣ 
	
	private Border lineBorder = new LineBorder(Color.GRAY, 1);//ȫ�ֱ߽���
	
	private boolean canGood = false;
	/*Client���캯��*/
	public Client(User u,ObjectOutputStream o,ObjectInputStream i){
		user = u;
		os = o;
		is = i;
		System.out.println(user.getName()+":"+user.getpswd());
		String[] friendOnline = {"Login to get other online user!"};
		//String[] friendOnline = user.getFriendList();
		
		setLayout(new BorderLayout());//Frame Layout 
		/*���ñ���ͼƬ����frameת����panel������ͼƬ������frame�ϸ�������Դ�ɱ���ͼƬ���õ�Ŀ��*/
		JLabel backImage = new JLabel(icon);
		backImage.setBounds(0, 0, icon.getIconWidth(),icon.getIconHeight());//����ͼƬ��С
		((JPanel)this.getContentPane()).setOpaque(false);//��ȡframe����壬����ת����JPanel
		this.getLayeredPane().setLayout(null);//�����޲��ַ�ʽ
		this.getLayeredPane().add(backImage, new Integer(Integer.MIN_VALUE));//����ͼƬ
		
		/*�ڱ���frame����ӵ�һ�����*/
		Container c = getContentPane(); //��ȡJFrame���
		c.add(bkgp,BorderLayout.CENTER);
		bkgp.setOpaque(false);//����bkgpΪ͸����
		//bkgp.add(p3);
		
		/*�������Ϸ������Լ��û���½����*/
		bkgp.add(pInput,BorderLayout.NORTH);//............
		pInput.setOpaque(false);
		pInput.add(myName);
		pInput.setLayout(new FlowLayout());
		pInput.add(search);
		search.setFont(new Font("Serif", 0, 23));
		pInput.add(in);
		in.setFont(new Font("Serif", 0, 23));
		myName.setFont(new Font("Serif", 0, 23));
		if(user.getLogInfo())myName.setText("��ǰ��"+user.getName()+" ");
		else
			myName.setText("��ǰ��"+"�ο�"+" ");
		logout.setFont(new Font("Serif", 0, 23));
		pInput.add(logout);
		search.addActionListener(new SearchListener());
		logout.addActionListener(new LogoutListener());
		
		/*�·����*/
		bkgp.add(south,BorderLayout.CENTER);//..........
		south.setLayout(new BorderLayout());
		south.setBorder(lineBorder);
		meanAndCheck.setOpaque(false);
		
		/*���õ��ʽ������*/
		south.add(meanAndCheck,BorderLayout.WEST);//    
		south.setOpaque(false);
		
		
		/*�ʵ�ѡ��*/
		web.setOpaque(false);
		web.setLayout(new GridLayout(1,3,1,1));
		//baidu.setSize(100,100);
		baidu.setFont(new Font("Serif", 0, 25));
		youdao.setFont(new Font("Serif", 0, 25));
		gold.setFont(new Font("Serif", 0, 25));
		baidu.addItemListener(new myItemListener());//��ѡ����Ӽ���
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
		text1.setLineWrap(true);//�Զ�����
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
		readMessage.setFont(new Font("����", Font.PLAIN, 22));
		readMessage.addActionListener(new MessageListener());
		sharePanel.setOpaque(false);
		listPanel.setOpaque(false);
		listPanel.getViewport().setOpaque(false);
		friendList.setOpaque(false);
		((JLabel)friendList.getCellRenderer()).setOpaque(false);
		south.add(sharePanel,BorderLayout.EAST);//!!!!!!!! ScrollPane�������ú������		
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
			// TODO �Զ����ɵķ������
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
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
				if(order.getRecv()){//�жϷ������Ƿ��յ���Ϣ
					NameLogin temp = new NameLogin(user.getName(), user.getpswd());//TODO:��ȡ�����û���
					os.writeObject(temp);
					os.flush();
					temp = (NameLogin)is.readObject();
				}
			}
			catch (IOException e)
			{
			// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			}		
			//Client.this.dispose();
			//new Login(os,is);
		}
	}
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO �Զ����ɵķ������
			Pattern p = Pattern.compile("[a-zA-Z ]+");
			Matcher m = p.matcher(in.getText());
			if(m.matches()){
			try
			{	
				
				OrderType order = new OrderType(0);
				os.writeObject(order);
				os.flush();
				order = (OrderType)is.readObject();
				
				if(order.getRecv()){//�жϷ������Ƿ��յ���Ϣ
				TranslateInfo t = new TranslateInfo();
				//t.setType(0);
				t.setWord(in.getText());
				
				os.writeObject(t);
				os.flush();
				
				t = (TranslateInfo)is.readObject();
				/*��ʼʱ���հٶ��е���Ӧ��˳����ʾ*/
				int[] goodNum = t.getGoodNum();
				/*���ս�����շ��صĵ�������������*/
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			}
			else{
				in.setText("���������룡");
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
			type = i;//�������
			goodNum = g;
			if(i==0)icon = new ImageIcon("./src/�ٶ�.png");
			else if(i==1)icon = new ImageIcon("./src/�е�.png");
			else if(i==2)icon = new ImageIcon("./src/��Ӧ.png");
			text = t;
			state = st;
		}
	}
	
	class myItemListener implements ItemListener{

		public void itemStateChanged(ItemEvent arg0) {
			// TODO �Զ����ɵķ������
			 Object source = arg0.getItemSelectable();
			 if(source == baidu){checkboxState[0] = !checkboxState[0];}
			 if(source == youdao){checkboxState[1] = !checkboxState[1];}
			 if(source == gold){checkboxState[2] = !checkboxState[2];}
		}
		
	}
	
	class MessageListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO �Զ����ɵķ������
			if(user.getLogInfo()){
			    new ReadMsg(user,os,is);
			}
		}		
	}
}
