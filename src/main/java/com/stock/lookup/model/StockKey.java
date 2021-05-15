package com.stock.lookup.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class StockKey implements Serializable {
  protected String groupName;
  protected String stockName;

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (!(o instanceof StockKey)) return false;
    StockKey stockKey = (StockKey) o;
    return Objects.equals(this.groupName, stockKey.groupName)
        && Objects.equals(this.stockName, stockKey.stockName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.groupName, this.stockName);
  }
}
