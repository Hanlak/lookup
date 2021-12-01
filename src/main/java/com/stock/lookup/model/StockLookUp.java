package com.stock.lookup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stock.lookup.util.JsonDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOOKUP")
@Getter
@Setter
@NoArgsConstructor
@IdClass(StockKey.class)
public class StockLookUp {
  @Id
  @Column(name = "GROUP_NAME", nullable = false)
  @JsonProperty("group_name")
  private String groupName;

  @Id
  @Column(name = "STOCK_NAME", nullable = false)
  @JsonProperty("stock_name")
  private String stockName;

  @Column(name = "CATEGORY", nullable = false)
  @JsonProperty("category")
  private String category;

  @Column(name = "BUY_START_RANGE", nullable = false)
  @JsonProperty("buy_start_range")
  private Float buyStartRange;

  @Column(name = "BUY_END_RANGE", nullable = false)
  @JsonProperty("buy_end_range")
  private Float buyEndRange;

  @UpdateTimestamp
  @Temporal(TemporalType.DATE)
  @Column(name = "CREATION_DATE", nullable = false)
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonProperty("creation_date")
  private Date creationDate;

  public StockLookUp(
      String groupName, String stockName, String category, Float buyStartRange, Float buyEndRange) {
    this.groupName = groupName;
    this.stockName = stockName;
    this.category = category;
    this.buyStartRange = buyStartRange;
    this.buyEndRange = buyEndRange;
  }
}
