package com.example.stock.service;

import com.example.stock.model.NewsItem;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class NewsBroadcastService {
    private final SimpMessagingTemplate messagingTemplate;
    private final RssService rssService;

    public void broadcastNews(){
        System.out.println("Fetching and broadcasting news...");
        List<NewsItem> newsItems  = rssService.fetchNews();
        if(!newsItems.isEmpty()){
            messagingTemplate.convertAndSend("/topic/news", newsItems);
            System.out.println("News broadcasted: " + newsItems.size() + " items.");
        }
        else {
            System.out.println("No new items to broadcast.");
        }
    }
}
