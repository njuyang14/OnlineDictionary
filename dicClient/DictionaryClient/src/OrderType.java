import java.io.Serializable;


public class OrderType implements Serializable{
	private int type;
	/*
	 * 0 ����
	 * 1��½
	 * 2����
	 * 3����
	 * 4ע��
	 * 5�˳�
	 * 6�鿴
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
