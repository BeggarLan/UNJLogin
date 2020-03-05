package org.example;


import org.example.js.ExecuteScript;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.security.auth.login.LoginContext;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class Login {

    Map<String, String> header = new HashMap<String, String>();
    String lt ;
    String execution ;
    String cookieid;


    public static void main(String[] args) {
        trustEveryone();
        String username = "";     //账号
        String password = "";      //密码
        Login o = new Login();
        o.init();
        o.start(username,password);
    }

    public void init(){

        header.put("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        header.put("accept-encoding","gzip, deflate, br");
        header.put("accept-language","zh-CN,zh;q=0.9");
        header.put("cache-control","max-age=0");
        header.put("content-type","application/x-www-form-urlencoded");
        header.put("host","sso.ujn.edu.cn");

        header.put("origin","http://sso.ujn.edu.cn");
        header.put("Proxy-Connection","keep-alive");

        header.put("referer","https://sso.ujn.edu.cn/tpass/login?service=http%3A%2F%2Fehall.ujn.edu.cn%2Ffp%2F");
        header.put("Upgrade-Insecure-Requests","1");
        header.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36");

    }

    public String start(String username,String password){



        String urlString = URLManager. loginURL;

        Connection con = Jsoup.connect(urlString);// 获取连接
        con.headers(header)
//			.proxy(proxy)
                .ignoreContentType(true)
                .method(Method.GET);

        String str = "";

        try {
            Response rs = con.execute();
//            String bodyString = rs.body();
           // System.out.println(" : "+bodyString);
            Document document = Jsoup.parse(rs.body());
            Elements elements = document.select("input[type=hidden]");

             lt = elements.get(3).attr("value");
             execution = elements.get(4).attr("value");
             String cookiestr = rs.cookies().toString();
            cookiestr = cookiestr.substring(1, cookiestr.length() - 1);
            String[] strings = cookiestr.split(",");
            cookieid = strings[0].trim()+"; "+strings[1];

            login(username,password);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }



    public String login(String username,String password) {


        header.put("cookie",cookieid);


        Map<String, String> datas = new HashMap<String, String>();

        String rsa = new ExecuteScript().executeScript(username,password);

        datas.put("rsa", rsa);
        datas.put("ul", username.length()+"");
        datas.put("pl", password.length()+"");
        datas.put("lt", lt);
        datas.put("execution", execution);
        datas.put("_eventId", "submit");

        String urlString = URLManager. loginURL;

        Connection con = Jsoup.connect(urlString);// 获取连接
        con.headers(header)
                .ignoreContentType(true)
                .method(Method.POST);

        String loginrString = "error";

        try {
            Response rs = con.data(datas).execute();
            String bodyString = rs.body();
            System.out.println("登陆结果 : "+bodyString);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return loginrString;
    }

    /**
     * 信任任何站点，实现https页面的正常访问
     *
     */

    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }


}
