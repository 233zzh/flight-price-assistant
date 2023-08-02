package com.zihao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author zhangzhihao06 <zhangzhihao06@kuaishou.com>
 * Created on 2023-08-01
 */
public class JsoupTest {
    private static List<LinkEntity> stores = new ArrayList<>();

    public static void main(String[] args) {
        //1、与目标服务建立连接
        Connection connection = Jsoup.connect("https://flight.tuniu.com/domestic/list/BJS_SHA_ST_1_0_0/?start=2023-08-02");
        connection.header("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
        try {
            // 2、执行请求、解析结果
            Document document = connection.get();
            System.out.println(document);
            Element element = document.getElementById("coolsites_wrapper");
            // 3、定位到->"酷站列表"
            Elements elements = element.getElementsByClass("cool-row");
            Iterator iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element ulElement = (Element) iterator.next();
                // 4、获取当前盒子模型下<a>标签列表
                Elements links = ulElement.getElementsByTag("a");
                Iterator iteratorLinks = links.iterator();
                // 5、迭代遍历超链接列表
                while (iteratorLinks.hasNext()) {
                    Element items = (Element) iteratorLinks.next();
                    String text = items.text();
                    String attribute = items.select("a").attr("href");
                    if (StringUtil.isBlank(attribute) || StringUtil.isBlank(text)) {
                        continue;
                    }
                    stores.add(new LinkEntity(text, attribute));
                }
            }
            // 6、遍历爬取的结果（此处只做演示，不做任何存储操作）
            stores.parallelStream().forEach(item -> {
                System.out.println(item.getName() + " -> " + item.getUrl());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

class LinkEntity {
    private String name;
    private String url;

    public LinkEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
