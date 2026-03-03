package com.example.stock.service;

import com.example.stock.model.NewsItem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RssService {

    public List<NewsItem> fetchNews() {
        List<NewsItem> newsList = new ArrayList<>();

        try {
            // URL을 제공해주신 https://www.hankyung.com/feed/it 로 변경합니다.
            String rssUrl = "https://www.cnbc.com/id/15839135/device/rss/rss.html";

            Document doc = Jsoup.connect(rssUrl).parser(Parser.xmlParser()).get();
            Elements items = doc.select("item");

            if(items.isEmpty()){
                System.out.println("데이터가 없습니다 (RSS 피드에서 item 태그를 찾을 수 없음)");
            } else{
                for (int i = 0; i < Math.min(10, items.size()); i++) {
                    Element item = items.get(i);
                    String title = item.selectFirst("title").text();
                    String link = item.selectFirst("link").text();
                    String description = item.selectFirst("description") != null ? item.selectFirst("description").text() : "";

                    newsList.add(new NewsItem(title, description, link));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("RssService Exception: " + e.getMessage());
        }

        return newsList;
    }

}