package com.stock.lookup.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"group_name", "stock_name", "weight_age", "buy_start_range", "buy_end_range"})
public class StockLookUpDTO {

  @NotBlank(message = "group_name cannot be empty or null")
  @JsonProperty("group_name")
  public String groupName;

  @NotBlank(message = "stock_name cannot be empty or null")
  @JsonProperty("stock_name")
  public String stockName;

  @NotNull(message = "weight_age cannot be empty or null")
  @JsonProperty("weight_age")
  public Float weightAge;

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
  @NotNull(message = "buy_start_range cannot be empty or null")
  @JsonProperty("buy_start_range")
  public Float buyStartRange;

  @NotNull(message = "buy_end_range cannot be empty or null")
  @JsonProperty("buy_end_range")
  public Float buyEndRange;

  public StockLookUpDTO(
          String groupName, String stockName, Float weightAge, Float buyStartRange, Float buyEndRange) {
    this.groupName = groupName;
    this.stockName = stockName;
    this.weightAge = weightAge;
    this.buyStartRange = buyStartRange;
    this.buyEndRange = buyEndRange;
  }
}
