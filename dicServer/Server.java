import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	public static void main(String[] args) {
 /*       InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
             
            String localname=ia.getHostName();
            String localip=ia.getHostAddress();
            System.out.println("本机名称是："+ localname);
            System.out.println("本机的ip是 ："+localip);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
		new Server();

	}

	public Server()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(8000);
//			int clientNo = 1;
			while(true)
			{
				Socket socket = serverSocket.accept();
				HandleAClient task = new HandleAClient(socket);
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
		
		public HandleAClient(Socket socket)
		{
			this.socket = socket;
		}
		
		public void run()
		{
			try
			{
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				//continuously serve the client
				while(true)
				{
					//handle data
					int type = inputFromClient.readInt();
					switch(type)
					{
						case 0://查询单词释义
							{
								String word = inputFromClient.readUTF();
								String []explain = SearchOnline.search(word);
								outputToClient.writeBytes(explain[0]);//baidu
								outputToClient.writeBytes(explain[1]);//youdao
								outputToClient.writeBytes(explain[2]);//bing
								break;
							}
							case 1:break;
							case 2:break;
							case 3:break;
					}
					
//					double radius = inputFromClient.readDouble();
//					double area = radius * radius * Math.PI;
//					outputToClient.writeDouble(area);
				}
			}
			catch(IOException e)
			{
				System.err.println(e);
			}
		}
	}
}
