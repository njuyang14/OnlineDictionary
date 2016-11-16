import java.net.*;
import java.io.*;
import baidu.TransApi;

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
	
	public static String utfToChar(String unicode)//ת��������utf-8ת���ɺ���
	{
		String re = new String();
		return re;
	}
	
	private static String baiduTran(String word)
	{
		final String APP_ID = "";
	    final String SECURITY_KEY = "";
	    
	    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String tmp=api.getTransResult(word, "en", "zh");//��ȡ��unicode���utf-8ת���ɺ�����\u4f60\u597d\u5417�������
        String result = utfToChar(tmp);
        
		return result;
	}
	
	private static String youdaoTran(String word)
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

            String para = "keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q=" + word;

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
		
	    //�ӷ���ֵ����ȡ���������ݴ���result��
		return result;
	}
	
	private static String bingTran(String word)
	{
		String result = new String();
		return result;
	}

}
