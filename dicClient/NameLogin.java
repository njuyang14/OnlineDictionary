import java.io.Serializable;

public class NameLogin implements Serializable{
	private String name;
	private String password;
	private boolean canLogin;
	
	public NameLogin(String n,String p){
		name = n;
		password = p;
		canLogin = false;
	}
	public String getName(){return name;}
	public String getPass(){return password;}
	public boolean getConn(){return canLogin;}
	public void setName(String n){name = n;}
	public void setPass(String p){password = p;}
	public void setConn(boolean b){canLogin = b;}
}
