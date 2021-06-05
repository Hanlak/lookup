package com.stock.lookup.mapper;

import com.stock.lookup.dto.MailSubDTO;
import com.stock.lookup.model.MailSub;
import org.springframework.stereotype.Component;

@Component
public class MailSubMapper {
    public MailSub fromDto(MailSubDTO mailSubDTO) {
        return new MailSub(mailSubDTO.mailGroupName, mailSubDTO.mailName);
    }

    public MailSubDTO toDto(MailSub mailSub) {
        return new MailSubDTO(mailSub.getMailGroupName(), mailSub.getMailName());
    }
}
