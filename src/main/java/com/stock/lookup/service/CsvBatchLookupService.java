package com.stock.lookup.service;

import com.stock.lookup.dao.CsvBatchRepository;
import com.stock.lookup.dto.StockLookUpDTO;
import com.stock.lookup.exception.CsvConversionException;
import com.stock.lookup.mapper.StockLookupMapper;
import com.stock.lookup.model.StockLookUp;
import com.stock.lookup.util.CsvUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvBatchLookupService {

  private final CsvBatchRepository csvBatchRepository;
  @Autowired
  StockLookupMapper stockLookupMapper;

  public CsvBatchLookupService(CsvBatchRepository csvBatchRepository) {
    this.csvBatchRepository = csvBatchRepository;
  }

  public List<StockLookUpDTO> createNewSuggestionsBasedOnCSV(MultipartFile file) {
    // convert requestBody InputStream to CSV
    List<StockLookUpDTO> stockLookUpDTOS = null;
    try {
      stockLookUpDTOS =
              CsvUtils.read(StockLookUpDTO.class, new BufferedInputStream(file.getInputStream()));
    } catch (Exception e) {
      throw new CsvConversionException(e.getLocalizedMessage());
    }
    // END OF THAT LOGIC
    List<StockLookUp> stockLookUps =
            stockLookUpDTOS
                    .parallelStream()
                    .map(stockLookupMapper::fromDto)
                    .collect(Collectors.toList());
    return csvBatchRepository
            .saveAll(stockLookUps)
            .parallelStream()
            .map(stockLookupMapper::toDto)
            .collect(Collectors.toList());
  }
}
