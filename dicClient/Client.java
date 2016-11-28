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
	private JCheckBox gold = new JCheckBox("��ɽ");
	
	private JPanel south = new JPanel();//�·����
	
	private JPanel meaning3 = new JPanel();//��ʾ����ƽ̨���ʽ���
	private JTextArea text0 = new JTextArea(5,34);
	private JTextArea text1 = new JTextArea(5,34);
	private JTextArea text2 = new JTextArea(5,34);
	
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
	public Client(User u){
		user = u;
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
		web.add(baidu);
		web.add(youdao);
		web.add(gold);
		
		meaning3.setLayout(new GridLayout(3,1,3,3));
		text0.setFont(new Font("Serif", 0, 25));
		text1.setFont(new Font("Serif", 0, 25));
		text2.setFont(new Font("Serif", 0, 25));
		text0.setLineWrap(true);
		text1.setLineWrap(true);//�Զ�����
		text2.setLineWrap(true);
		meaning3.add(web);
		meaning3.add(text0);
		meaning3.add(text1);
		meaning3.add(text2);
		
		meanAndCheck.setLayout(new BorderLayout());
		meanAndCheck.add(web,BorderLayout.NORTH);
		meanAndCheck.add(meaning3,BorderLayout.CENTER);
		
		/*�������*/
		south.add(sharePanel,BorderLayout.CENTER);//
		sharePanel.setBorder(lineBorder);
		
		/*���ñ������*/
		//south.add(recitePanel);
		recitePanel.setLayout(new GridLayout(6,1,10,20));
		recitePanel.setBorder(lineBorder);
		//recitePanel.setSize(10, 700);
			
        friendList = new JList<String>(friendOnline);//init list
		friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
		friendList.setFont(new Font("Arial", Font.PLAIN, 19));
		listPanel = new JScrollPane(friendList);
		listPanel.setPreferredSize(new Dimension(280,630));
		south.add(listPanel,BorderLayout.EAST);//!!!!!!!! ScrollPane�������ú������
		
		this.setTitle("Online Dictionary");
		this.setSize(1050, 830);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/*����socket�ӿ�*/
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
			// TODO �Զ����ɵķ������
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
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
	}
}
