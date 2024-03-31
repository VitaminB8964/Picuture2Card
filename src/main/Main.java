package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String p = "https://steamuserimages-a.akamaihd.net/ugc/2444962572783166573/5F46C546653A986C8EDD31EC1C4CD43AE19521C4/?imw=512&&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=false",//图片地址
                t = "骷髅打金",//标题
                st = "嘻嘻",//副标题
                yx = "骷髅打金";//图片外显示
        String a = result(p,t,st,yx);
        System.out.println(a);
    }
    public static String result(String pictureUrl,String title,String subtitle,String yx){
        String requestUrl = "http://api.mrgnb.cn/API/qq_ark37.php";
        Map parms = new HashMap<>();
        parms.put("url",pictureUrl);
        parms.put("title",title);
        parms.put("subtitle",subtitle);
        parms.put("yx",yx);
        String string = httpRequest(requestUrl,parms);
        return string;
    }
    private static String httpRequest(String requestUrl,Map params){
        //buffer用于接受返回的字符
        StringBuilder buffer = new StringBuilder();
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            URL url = new URL(requestUrl+"?"+ urlencoded(params));
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            //获得输入
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //将bufferReader的值给放到buffer里
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //关闭bufferReader和输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            //断开连接
            httpUrlConn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回字符串
        return buffer.toString();

    }
    public static String urlencoded(Map<String,Object>data) {
        //将map里的参数变成像 showapi_appid=###&showapi_sign=###&的样子
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
