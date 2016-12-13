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
	
	private JPanel pInput = new JPanel();//���Ϸ����
	//private JButton login = new JButton("login");
	private JButton search = new JButton("search");
	private JTextArea in = new JTextArea(1,28);
	//private JButton changeMode = new JButton("��/Ӣ");
	private String[] mode = {"����Ӣ","Ӣ�뺺"};
	private JComboBox changeMode = new JComboBox(mode);
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
	private JLabel labelBaidu = new JLabel(iconBaidu);
	private JLabel labelYoudao = new JLabel(iconYoudao);
	private JLabel labelBing = new JLabel(iconBing);
	/*���ް�ť*/
	private JButton good0 = new JButton("��");
	private JButton good1 = new JButton("��");
	private JButton good2 = new JButton("��");
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
	private JButton shareToFriend = new JButton();//����
	
	private JPanel recitePanel = new JPanel();//���������
	private JList reciteList = new JList();//���ʱ���ʾ
	private JButton nextRecite = new JButton();//��һ������
	private JButton prevRecite = new JButton();//��һ������
	private JButton removeRecite = new JButton();//ɾ�����ʱ��еĵ���
	
	private JPanel friendPanel = new JPanel();//�������
	private JList<String> friendList = new JList<String>();//������ʾ
	private JScrollPane listPanel = new JScrollPane(friendList);
	
	private JButton sendMsg = new JButton();//�����ѷ���Ϣ 
	
	private Border lineBorder = new LineBorder(Color.GRAY, 1);//ȫ�ֱ߽���
	/*Client���캯��*/
	public Client(User u,ObjectOutputStream o,ObjectInputStream i){
		user = u;
		os = o;
		is = i;
		System.out.println(user.getName()+":"+user.getpswd());
		String[] friendOnline = { "black", "blue", "green", "yellow", "white", "black", "blue", "green", "yellow", "white" };
		
		setLayout(new BorderLayout());//Frame Layout 
		/*�������Ϸ������Լ��û���½����*/
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
		
		/*�·����*/
		add(south,BorderLayout.CENTER);
		south.setLayout(new BorderLayout());
		south.setBorder(lineBorder);
		
		/*���õ��ʽ������*/
		south.add(meanAndCheck,BorderLayout.WEST);//    
		
		/*�ʵ�ѡ��*/
		web.setLayout(new GridLayout(1,3,1,1));
		//baidu.setSize(100,100);
		baidu.setFont(new Font("Serif", 0, 25));
		youdao.setFont(new Font("Serif", 0, 25));
		gold.setFont(new Font("Serif", 0, 25));
		baidu.addItemListener(new myItemListener());//��ѡ����Ӽ���
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
		south.add(listPanel,BorderLayout.EAST);//!!!!!!!! ScrollPane�������ú������
		
		
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
	
	class SearchListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			// TODO �Զ����ɵķ������
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
}
