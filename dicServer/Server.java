import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.*;


public class Server {

	public static void main(String[] args) throws ReflectiveOperationException, SQLException {
 /*     InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
             
            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
            System.out.println("本机名称是："+ localname);
            System.out.println("本机的ip是 ："+localip);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
/*		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String url = "jdbc:sqlserver://localhost;databaseName=onlineDic;IntegratedSecurity=True";
		Connection connection = DriverManager.getConnection(url);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("select count from nicetran");
		Integer num[] = new Integer[3];
		for(int i=0; result.next(); i++)
		{
			num[i] = result.getInt(1);//按固定顺序返回三个int
		}
		System.out.println(num[0]+" "+num[1]+" "+num[2]);
		String str = SearchOnline.youdaoTran("hello");
		System.out.println(SearchOnline.handleChar(str));
		
		String unicode = SearchOnline.baiduTran("hello");
		System.out.println(unicode);
		Pattern pattern = Pattern.compile("(\\\\u[0-9a-f]{4})+");//("(\\\\u\\p{XDigit}{4})+");
		Matcher m = pattern.matcher(unicode);
		if(m.find())
		{
			String tmp = m.group();
			System.out.println (decode(tmp));
		}
		

		String []exp = SearchOnline.search("hello");
		System.out.println(exp[0]);*/
		new Server();

	}

	public Server()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(7900/*可以修改端口*/);
//			int clientNo = 1;
			while(true)
			{
//				System.out.println("wait");
				Socket socket = serverSocket.accept();
				HandleAClient task = new HandleAClient(socket);
//				System.out.println("linked!");
				new Thread(task).start();
//				clientNo++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	class HandleAClient implements Runnable
	{
		private Socket socket;
		private boolean login;
		
		public HandleAClient(Socket socket)
		{
			this.socket = socket;
			login = false;
		}
		
		public void run()
		{
			ObjectInputStream inputOFromClient = null;
			ObjectOutputStream outputOToClient = null;
//			String un = new String();
			try
			{
//				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
//				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				System.out.println("啊");
				inputOFromClient = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				outputOToClient = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("in!");

				//continuously serve the client
				while(true)
				{
					//handle data
					OrderType o = (OrderType)inputOFromClient.readObject();
					int type = o.getType();
					System.out.println(type);
					if(type >= 0 && type <= 6)
					{
						o.setRecv(true);
						outputOToClient.writeObject(o);
						outputOToClient.flush();
					}
					else
					{
						o.setRecv(false);
						outputOToClient.writeObject(o);
						outputOToClient.flush();
						continue;
					}
//					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//					String url = "jdbc:sqlserver://localhost;databaseName=onlineDic;IntegratedSecurity=True";
					Connection connection = Dbc.getConnection();//DriverManager.getConnection(url);
					Statement statement = connection.createStatement();
					switch(type)
					{
						case 0://查询单词释义
							{
								System.out.println("search");
								TranslateInfo search = (TranslateInfo)inputOFromClient.readObject();
								String word = search.getWord();//inputFromClient.readUTF();
								System.out.println(word);
								String []explain = SearchOnline.search(word);
								System.out.println(explain[0] + "\r\n" + explain[1] + "\r\n" + explain[2]);
								search.setMean0(explain[0]);
								search.setMean1(explain[1]);
								search.setMean2(explain[2]);
								Vector<String> st = new Vector<String>();
								synchronized(statement)
								{
									ResultSet nice = statement.executeQuery("select count from nicetran where webname = 'baidu'");
									if(nice.next())
										search.setNum(nice.getInt(1), 0);
									nice = statement.executeQuery("select count from nicetran where webname = 'youdao'");
									if(nice.next())
										search.setNum(nice.getInt(1), 1);
									nice = statement.executeQuery("select count from nicetran where webname = 'bing'");
									if(nice.next())
										search.setNum(nice.getInt(1), 2);
									ResultSet friend = statement.executeQuery("select name from userinfor where login=1");
									while(friend.next())
										st.addElement(friend.getString(1));
								}
								String []friendlist = new String[st.size()];
								st.toArray(friendlist);
								search.setFriendList(friendlist);
								for(int i=0; i<st.size(); i++)
								System.out.println(friendlist[i]);
								outputOToClient.writeObject(search);
								outputOToClient.flush();
//								outputToClient.writeBytes(explain[0]);//baidu
//								outputToClient.writeBytes(explain[1]);//youdao
//								outputToClient.writeBytes(explain[2]);//bing
								break;
							}
							case 1://登录
							{
								NameLogin l = (NameLogin)inputOFromClient.readObject();
								String username = l.getName();
//								un = username;
								String password = l.getPass();
								System.out.println("登录！ "+ username +"  " + password);
								synchronized(statement)
								{
									ResultSet result = statement.executeQuery("select password from userinfor where name='" + username + "'");
									if(result.next() == true && result.getString(1).substring(0, password.length()).equals(password))
									{
										//outputToClient.writeBoolean(true);
										login = true;
										System.out.println("Login in!");
										InetAddress ia = socket.getInetAddress();
										System.out.println(ia.getHostAddress());
										statement.execute("update userinfor set ipaddress='"+ ia.getHostAddress() +"' where name='" + username + "'");
										statement.execute("update userinfor set login=1 where name='" + username + "'");
										l.setConn(true);
									}
									else
										l.setConn(false);
									Vector<String> st = new Vector<String>();
									ResultSet friend = statement.executeQuery("select name from userinfor");
									while(friend.next())
										st.addElement(friend.getString(1));
									String []friendlist = new String[st.size()];
									st.toArray(friendlist);
									l.setFriendList(friendlist);
									for(int i=0; i<st.size(); i++)
										System.out.println(l.getFriendList()[i]);
									outputOToClient.writeObject(l);
									outputOToClient.flush();
								}
								break;
							}
							case 2://点赞
							{
								String webname = "";//youdao,bing from client
								Integer wty = (Integer)inputOFromClient.readObject();
								switch(wty)
								{
								case 0: webname = "baidu";break;
								case 1: webname = "youdao";break;
								case 2: webname = "bing";break;
								}
								System.out.println(webname);
								synchronized(statement)
								{
									statement.execute("update nicetran set count=count+1 where webname='" + webname + "'");
									ResultSet result = statement.executeQuery("select count from nicetran");
									Integer num[] = new Integer[3];
									for(int i=0; result.next(); i++)
									{
										num[i] = result.getInt(1);//按固定顺序返回三个int
									}
									//返回给客户端
									outputOToClient.writeObject(num);
								}
								break;
							}
							case 3://分享
							{
								if(login==false)
								{
//									outputToClient.writeBoolean(false);
									break;
								}
								else
								{
									String[] content = (String[])inputOFromClient.readObject();
									String name = content[0];//get from client
									String friend = content[1];//friend's name
									String message = content[2];
									System.out.println(content[1]);
									ResultSet result = statement.executeQuery("select name from userinfor where name='" + friend + "'");
									if(result.next() == true)
									{
										statement.execute("insert into share values(\'" + name + "\', \'" + friend + "\', \'" + message + "\', \'" + NowTime.getTime() + "\')");
/*										ResultSet r = statement.executeQuery("select ipaddress from userinfor where name='" + friend + "'");
										ip = r.getString(1);
										//TODO:将word发送给IP地址处的客户端
										Socket share = new Socket(ip, 8000);
										DataOutputStream stc = new DataOutputStream(share.getOutputStream());
										stc.writeBytes(word);
										share.close();*/
									}
									else;//不存在该用户
									System.out.println("完毕");
									outputOToClient.writeObject(content);
								}
								break;
							}
							case 4://注册
							{
								System.out.println("Regist!");
								NameLogin r = (NameLogin)inputOFromClient.readObject();
								String username =r.getName();
								String password = r.getPass();
								InetAddress ia = socket.getInetAddress();
								System.out.println("insert into userinfor values(\'" + username + "\', \'" + password + "\', \'" + ia.getHostAddress() + "\', 0)");
								statement.execute("insert into userinfor values(\'" + username + "\', \'" + password + "\', \'" + ia.getHostAddress() + "\', 0)");
								login = false;
								//回馈给客户端
								r.setConn(true);
								System.out.println("注册完毕");
								outputOToClient.writeObject(r);
								outputOToClient.flush();
								System.out.println("输出完毕");
								break;
							}
							case 5://退出登录
							{
								NameLogin lo = (NameLogin)inputOFromClient.readObject();//login out from client
								login = false;
								synchronized(statement)
								{
									statement.execute("update userinfor set login=0 where name='" + lo.getName() + "'");
								}
								lo.setConn(false);
								outputOToClient.writeObject(lo);
								outputOToClient.flush();
								break;
							}
							case 6://查看消息
							{
								//收一个无用
								String uname = (String)inputOFromClient.readObject();//from client
								String card = "";
								ResultSet m = statement.executeQuery("select * from share where t='"+uname+"'");
								while(m.next())
									card = card + "来自：" + m.getString(1) + "\r\n" + m.getString(3) + "\r\n" + "分享时间：" + m.getString(4) + "\r\n\r\n";
								outputOToClient.writeObject(card);
								outputOToClient.flush();
							}
					}
					synchronized(statement)
					{
						statement.close();
					}
					synchronized(connection)
					{
						connection.close();
					}
//					double radius = inputFromClient.readDouble();
//					double area = radius * radius * Math.PI;
//					outputToClient.writeDouble(area);
				}
			}
			catch(ClassNotFoundException ec)
			{
				System.err.println(ec);
			}
			catch(SQLException es)
			{
				System.err.println(es);
			}	
			catch(IOException ei)
			{
				System.err.println(ei);
			}
			finally
			{
				try {
					inputOFromClient.close();
					outputOToClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
