package com.stock.lookup.dao;

import com.stock.lookup.model.StockLookUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LookUpRepository extends JpaRepository<StockLookUp, Long> {
  List<StockLookUp> findByGroupName(String groupName);
    Optional<StockLookUp> findByGroupNameAndStockName(String groupName, String stockName);

    void deleteByGroupNameAndStockName(String groupName, String stockName);
}
