package com.stock.lookup.mapper;

import com.stock.lookup.dto.MailSubDTO;
import com.stock.lookup.model.MailSub;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMailSubMapper {
     MailSubDTO toDto (MailSub mailSub);
     MailSub fromDto(MailSubDTO mailSubDTO);
}
