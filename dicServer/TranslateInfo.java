import java.io.Serializable;

public class TranslateInfo implements Serializable{
	private String word;
	private String mean0;
	private String mean1;
	private String mean2;
	private int[] goodNum = {0, 0, 0};//按顺序存储百度有道必应的点赞数
	private String[] friendOnline;
	
	public TranslateInfo(){}
	
	public TranslateInfo(int t, String w, String m0, String m1, String m2){
		word = w;
		mean0 = m0;
		mean1 = m1;
		mean2 = m2;
	}
	
	public String getWord(){
		return word;
	}
	
	public String getMean0(){
		return mean0;
	}
	
	public String getMean1(){
		return mean1;
	}
	
	public String getMean2(){
		return mean2;
	}
	
	public int[] getGoodNum(){
		return goodNum;
	}
	
	public void setWord(String w){
		word = w;
	}
	
	public void setMean0(String m0){
		mean0 = m0;
	}
	
	public void setMean1(String m1){
		mean1 = m1;
	}
	
	public void setMean2(String m2){
		mean2 = m2;
	}

	public void setNum(int num,int pos){
		goodNum[pos] = num;
	}
	
	public String[] getFriendList(){
		return friendOnline;
	}
	
	public void setFriendList(String[] str){
		friendOnline = str;
	}
}