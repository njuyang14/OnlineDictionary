import java.io.Serializable;

public class TranslateInfo implements Serializable{
	private int type;
	private String word;
	private String mean0;
	private String mean1;
	private String mean2;
	
	public TranslateInfo(){}
	
	public TranslateInfo(int t, String w, String m0, String m1, String m2){
		type = t;
		word = w;
		mean0 = m0;
		mean1 = m1;
		mean2 = m2;
	}

	
	public int getType(){
		return type;
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
	
	public void setType(int t){
		type = t;
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

	
}
