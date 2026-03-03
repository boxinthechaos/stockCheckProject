package com.example.stock.repository;

import com.example.stock.dto.StockDTO;
import com.example.stock.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<StockEntity, String> {
    List<StockEntity> findBySymbolOrderByDate(String Symbol);
}

