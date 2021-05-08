package com.stock.lookup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockLookUpDTO {
    @JsonProperty("group_name")
    public String groupName;

    @JsonProperty("stock_name")
    public String stockName;

    @JsonProperty("weight_age")
    public Integer weightAge;

    @JsonProperty("buy_start_range")
    public Float buyStartRange;

    @JsonProperty("buy_end_range")
    public Float buyEndRange;

    public StockLookUpDTO(
            String groupName,
            String stockName,
            Integer weightAge,
            Float buyStartRange,
            Float buyEndRange) {
        this.groupName = groupName;
        this.stockName = stockName;
        this.weightAge = weightAge;
        this.buyStartRange = buyStartRange;
        this.buyEndRange = buyEndRange;
    }
}
