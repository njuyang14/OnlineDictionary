
public class User {
	private int logInfo;
	private int searchMode;
	private String userName;
	private String password;
	private String[] friendOnlineCurrent;
	
	public int getLogInfo(){return logInfo;}
	public int getSearchMode(){return searchMode;}
	public String getName(){return userName;}
	public String getpswd(){return password;}
	public String[] getFriendList(){
		return friendOnlineCurrent;
	}
	public void setLogInfo(int info){logInfo = info;}
	public void setSearchMode(int mode){searchMode = mode;}
	public void setName(String name){userName = name;}
	public void setpswd(String pwsd){password = pwsd;}
	public void setFriendList(String[] str){
		friendOnlineCurrent = str;
	}
	
}
