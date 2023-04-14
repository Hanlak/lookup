package com.stock.lookup.mapper;

import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.model.StockLookUp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface IStockLookupMapper {
    @Mapping(source = "stockName", target = "stockName", qualifiedByName = "stockUpperCase")
    StockLookUp fromDto(StockLookUpDTO stockLookUpDTO);

    StockLookUpDTO toDto(StockLookUp stockLookUp);

    @Named("stockUpperCase")
    default String toUpperCaseStockName(String name) {
        return name.toUpperCase();
    }
}
