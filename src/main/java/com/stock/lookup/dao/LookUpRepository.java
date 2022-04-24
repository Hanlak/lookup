package com.stock.lookup.dao;

import com.stock.lookup.model.StockLookUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Repository
public interface LookUpRepository extends JpaRepository<StockLookUp, Long> {
  List<StockLookUp> findByGroupName(String groupName) throws EntityNotFoundException;

  List<StockLookUp> findByGroupNameAndStockNameStartsWithIgnoreCase(String groupName, String stockName)
      throws EntityNotFoundException;

  Optional<StockLookUp> findByGroupNameAndStockName(String groupName, String stockName);

  void deleteByGroupNameAndStockName(String groupName, String stockName);
}
