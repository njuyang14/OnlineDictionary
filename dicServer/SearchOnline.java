import java.net.*;
import java.util.regex.*;
import java.io.*;
import baidu.TransApi;
import memetix.mst.language.Language;
import memetix.mst.translate.Translate;


public class SearchOnline {
	
	public static String[] search(String word)
	{
		//search from Internet
		String[] explain = new String[3];
		explain[0] = baiduTran(word);
		explain[1] = youdaoTran(word);
		explain[2] = bingTran(word);
		return explain;
	}
	
	public static String utfToChar(String unicode)//转换函数将utf-8转换成汉字
	{
/*		int pos = unicode.length() - 4;
		for(; pos > 0 && unicode.charAt(pos - 1) != '"'; pos--);
		String re = unicode.substring(pos, unicode.length() - 4);
		unicode = ("\uaaaa");*/
		Pattern pattern = Pattern.compile("(\\\\u[0-9a-f]{4})+");
		Matcher m = pattern.matcher(unicode);
		String re = "";
		while(m.find())
		{
			re = re + decode(m.group());
			System.out.println(re);
		}
		return re;
	}
	
	public static String handleChar(String total)//提取出汉字部分
	{
		Pattern pattern = Pattern.compile("\\[\"[^\\[]+\"\\]");
		Matcher m = pattern.matcher(total);
		String str = "";
		while(m.find())
		{
			System.out.println(m.group());
			str = str + decode(m.group());
//			for(int i=2; i<=m.groupCount(); i++)
//				str=str+" "+m.group(i);
			
		}
		str = str.replaceAll("\"\\]\\[\"", "\r\n");
		str = str.replaceAll("\\[\"", "");
		str = str.replaceAll("\"\\]", "");
		str = str.replaceAll("\"", "");
		return str;
//		else return "no match";
/*		String[] re = pattern.split(total);
		if(re.length==0)
			return "No match!";
		else
		{
			String str = re[0];
			for(int i=1; i<re.length; i++)
				str = str +" " + re[i];
			return str;
		}*/
	}
	
	private static String baiduTran(String word)
	{
		final String APP_ID = "";//TODO: add appID here!
	    final String SECURITY_KEY = "";//TODO: add security key here!
	    
	    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String tmp=api.getTransResult(word, "en", "zh");
        //提取出汉字的unicode码后将utf-8转换成汉字
        String result = utfToChar(tmp);
        
		return result;
	}
	
	public static String youdaoTran(String word)//http://blog.csdn.net/nomasp/article/details/48995039
	{
		String result = new String();
	    try
	    {
            URL url = new URL("http://fanyi.youdao.com/openapi.do");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.addRequestProperty("encoding", "UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String para = "keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q=" + word;//key and id

            bw.write(para);
            bw.flush();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line;
            StringBuilder builder = new StringBuilder();
            while((line = br.readLine()) != null)
                builder.append(line);

            bw.close();
            osw.close();
            os.close();

            br.close();
            isr.close();
            is.close();

            result = builder.toString();

        }
	    catch (MalformedURLException e)
	    {
            e.printStackTrace();
        }
	    catch (IOException e)
	    {
            e.printStackTrace();
        }
		
	    //从返回值中提取出汉字内容存入result中
	    result = handleChar(result);
		return result;
	}
	
	private static String bingTran(String word)
	{
		Translate.setClientId("");		//TODO: Enter your Windows Azure Client Id here
	    Translate.setClientSecret("");	//TODO: Enter your Windows Azure Client Secret here
		String result = new String();
		try
		{
			result = Translate.execute(word, Language.ENGLISH, Language.CHINESE_SIMPLIFIED);
		}catch (Exception e)
		{
			e.printStackTrace();
		};
		return result;
	}

    public static String decode(String unicodeStr) {  
        if (unicodeStr == null) {  
            return null;  
        }  
        StringBuffer retBuf = new StringBuffer();  
        int maxLoop = unicodeStr.length();  
        for (int i = 0; i < maxLoop; i++) {  
            if (unicodeStr.charAt(i) == '\\') {  
                if ((i < maxLoop - 5)  
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr  
                                .charAt(i + 1) == 'U')))  
                    try {  
                        retBuf.append((char) Integer.parseInt(  
                                unicodeStr.substring(i + 2, i + 6), 16));  
                        i += 5;  
                    } catch (NumberFormatException localNumberFormatException) {  
                        retBuf.append(unicodeStr.charAt(i));  
                    }  
                else  
                    retBuf.append(unicodeStr.charAt(i));  
            } else {  
                retBuf.append(unicodeStr.charAt(i));  
            }  
        }  
        return retBuf.toString();  
    }
}
