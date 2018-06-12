package com.skydianshi.util;

/**
 * Created by 张海逢 on 2017/6/13.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class JsoupUtil {

    public static void main(String args[]){
        new JsoupUtil().getDocFromBaike("发动机");

    }

    /**得到搜索到的关键信息*/
    public String[] getMessage(Document document, int index){
        String[] message = new String[3];
        String titleUrl = document.select("div[class=c-tools]").get(index).attr("data-tools");
        //网页标题下面的简单介绍正文
        String text = document.select("div[class=c-abstract]").get(index).text();
        //网页所对应的标题
        String title = titleUrl.split("\",\"")[0].substring(10, titleUrl.split("\",\"")[0].length());
        //网页所对应的url
        String url = titleUrl.split("\",\"")[1].substring(6, titleUrl.split("\",\"")[1].length()-2);
        message[0] = title;
        message[1] = text;
        message[2] = url;
        return message;
    }

    /**得到网页*/
    public Document getDocument(String keyword){
        Document document = null;
        try {
            keyword = URLEncoder.encode(keyword, "gb2312");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            //因为手机端默认获取手机网页的源码，而那样资料太少，所以加上请求头（header），这样可以获取电脑端网页的源码，如果在电脑端运行，则不需要加上.header()   ps:这个问题困扰了我半天
            document = Jsoup.connect("http://www.baidu.com.cn/s?wd="+keyword+"&cl=3")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0").get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return document;
    }

    /**从百科中得到正文信息*/
    public void getDocFromBaike(String keyword){
        Document doc;
        try {
            doc = Jsoup.connect("http://baike.baidu.com/search/word?word="+keyword).get();

            try{
                //根据标签和属性得到标签的文本信息
                String content = doc.select("div[class=para]").get(0).text();
                System.out.println(content);
                //根据标签和属性得到标签下其他的属性值
                String description = doc.select("meta[name=description]").get(0).attr("content");

            }catch(IndexOutOfBoundsException e){
                System.out.println("百度百科中没有收录此词条");
                //当百度百科中没有词条时通过百度查找网页
                doc = getDocument(keyword);
                getMessage(doc,0);
                getMessage(doc,1);
                getMessage(doc,2);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
