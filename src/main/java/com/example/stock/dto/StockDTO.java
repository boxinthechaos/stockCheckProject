package com.example.stock.dto;

import com.example.stock.entity.StockEntity;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class StockDTO {
    private String date;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
}
