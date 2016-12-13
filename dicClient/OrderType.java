import java.io.Serializable;


public class OrderType implements Serializable{
	private int type;
	/*
	 * 0 ·­Òë
	 * 1µÇÂ½
	 * 4×¢²á
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
