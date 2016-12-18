import java.io.Serializable;


public class OrderType implements Serializable{
	private int type;
	/*
	 * 0 翻译
	 * 1登陆
	 * 2点赞
	 * 3分享
	 * 4注册
	 * 5退出
	 * 6查看
	 */
	private boolean recv = false;
	
	public OrderType(){}
	public OrderType(int order){
		type = order;
	}
	public int getType(){return type;}
	public boolean getRecv(){return recv;}
	public void setType(int t){type = t;}
	public void setRecv(boolean r){recv = r;}
}
