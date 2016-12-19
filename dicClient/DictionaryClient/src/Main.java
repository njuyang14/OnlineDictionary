import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;


public class Main {

	public static Socket socket;
	public static ObjectOutputStream os;
	public static ObjectInputStream is;
	public static void main(String[] args){
		/*建立socket接口*/
		try
		{
			socket = new Socket("192.168.43.197", 7900);
			os = new ObjectOutputStream(socket.getOutputStream());
		    is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		new Login(os, is);
	}
}
