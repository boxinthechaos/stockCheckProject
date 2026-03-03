package com.example.stock.controller;
import com.example.stock.service.NewsBroadcastService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsBroadcastService newsBroadcastService;

    @PostMapping("/api/trigger-news")
    public ResponseEntity<Void> triggerNewsBroadcast(){
        newsBroadcastService.broadcastNews();
        return ResponseEntity.ok().build();
    }
}
