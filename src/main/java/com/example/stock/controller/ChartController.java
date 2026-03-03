package com.example.stock.controller;

import com.example.stock.dto.StockDTO;
import com.example.stock.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/chart")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ChartController {
    private final ChartService chartService;

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getChartData(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "1d") String range,
            @RequestParam(defaultValue = "1m") String interval
    ) {
        try{
            List<StockDTO> data = chartService.fetchChartData(symbol, range, interval);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Chartcontroller Exception");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error","API 호출 실패"));
        }
    }
}
