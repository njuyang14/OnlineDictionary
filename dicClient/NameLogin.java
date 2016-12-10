import java.io.Serializable;

public class NameLogin implements Serializable{
	private int type;
	private String name;
	private String password;
	private boolean canLogin;
	
	public NameLogin(int t,String n,String p)
	{
		type = t;
		name = n;
		password = p;
		canLogin = false;
	}
	public int getType(){return type;}
	public String getName(){return name;}
	public String getPass(){return password;}
	public boolean getConn(){return canLogin;}
	public void setType(int t){type = t;}
	public void setName(String n){name = n;}
	public void setPass(String p){password = p;}
	public void setConn(boolean b){canLogin = b;}
}
