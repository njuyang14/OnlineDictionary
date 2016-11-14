package dicServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	public static void main(String[] args) {
		new Server();
	}

	public Server()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(8000);
			int clientNo = 1;
			while(true)
			{
				Socket socket = serverSocket.accept();
				HandleAClient task = new HandleAClient(socket);
				new Thread(task).start();
				clientNo++;
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
					double radius = inputFromClient.readDouble();
					
					double area = radius * radius * Math.PI;
					
					outputToClient.writeDouble(area);
					
					System.out.print("send.");
				}
			}
			catch(IOException e)
			{
				System.err.println(e);
			}
		}
	}
}

