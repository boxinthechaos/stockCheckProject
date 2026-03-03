package com.example.stock.service;

import com.example.stock.dto.StockDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ChartService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Set<String> validRanges = Set.of(
            "1d", "5d", "1mo", "3mo", "6mo", "1y", "2y", "5y", "10y", "ytd", "max"
    );

    public List<StockDTO> fetchChartData(String symbol, String range, String interval) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("symbol은 필수입니다.");
        }

        if (!validRanges.contains(range)) {
            throw new IllegalArgumentException("잘못된 기간입니다: " + range);
        }

        String url = String.format(
                "https://query1.finance.yahoo.com/v8/finance/chart/%s?interval=%s&range=%s",
                symbol, interval, range
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Mozilla/5.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map body = response.getBody();

        if (body == null || !body.containsKey("chart")) {
            throw new RuntimeException("Yahoo 응답에 chart 데이터가 없습니다.");
        }

        Map chart = (Map) body.get("chart");
        List result = (List) chart.get("result");

        if (result == null || result.isEmpty()) {
            Object error = chart.get("error");
            throw new RuntimeException("API 결과가 없습니다. 오류: " + (error != null ? error.toString() : "알 수 없음"));
        }

        Map resultData = (Map) result.get(0);
        List<Long> timestamps = (List<Long>) resultData.get("timestamp");

        if (timestamps == null || timestamps.isEmpty()) {
            System.out.println("Yahoo 응답 전체: " + body);
            throw new RuntimeException("timestamp 정보가 없습니다.");
        }

        Map indicators = (Map) resultData.get("indicators");
        List<Map> quotes = (List<Map>) indicators.get("quote");
        Map quote = quotes.get(0);

        List<Double> opens = (List<Double>) quote.get("open");
        List<Double> closes = (List<Double>) quote.get("close");
        List<Double> highs = (List<Double>) quote.get("high");
        List<Double> lows = (List<Double>) quote.get("low");

        List<StockDTO> responseList = new ArrayList<>();
        for (int i = 0; i < timestamps.size(); i++) {
            if (opens.get(i) == null || closes.get(i) == null || highs.get(i) == null || lows.get(i) == null) {
                continue;
            }

            Long timestamp = ((Number) timestamps.get(i)).longValue();
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
                    .format(new Date(timestamp * 1000));

            StockDTO dto = new StockDTO(
                    formattedDate,
                    opens.get(i),
                    closes.get(i),
                    highs.get(i),
                    lows.get(i)
            );
            responseList.add(dto);
        }

        return responseList;
    }
}
