package com.stock.lookup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.stock.lookup.util.JsonDateSerializer;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

  @Column(name = "BUY_START_RANGE", nullable = false)
  @JsonProperty("buy_start_range")
  private Integer buyStartRange;

  @Column(name = "BUY_END_RANGE", nullable = false)
  @JsonProperty("buy_end_range")
  private Integer buyEndRange;

  @UpdateTimestamp
  @Temporal(TemporalType.DATE)
  @Column(name = "CREATION_DATE", nullable = false)
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonProperty("creation_date")
  private Date creationDate;
}
