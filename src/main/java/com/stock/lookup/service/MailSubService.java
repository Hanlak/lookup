package com.stock.lookup.service;

import com.stock.lookup.dao.MailSubRepository;
import com.stock.lookup.dto.MailSubDTO;
import com.stock.lookup.mapper.IMailSubMapper;
import com.stock.lookup.model.MailSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MailSubService {

    private final MailSubRepository mailSubRepository;
    @Autowired
    IMailSubMapper mailSubMapper;

    public MailSubService(MailSubRepository mailSubRepository) {
        this.mailSubRepository = mailSubRepository;
    }

    //subscribe
    public MailSubDTO subscribe(MailSubDTO mailSubDTO) {
        MailSub mailSub = mailSubMapper.fromDto(mailSubDTO);
        MailSub mailSubSave = mailSubRepository.save(mailSub);
        return mailSubMapper.toDto(mailSubSave);
    }

    //unsubscribe
    @Transactional
    public void unSubscribe(MailSubDTO mailSubDTO) {
        MailSub mailSub = mailSubMapper.fromDto(mailSubDTO);
        mailSubRepository.deleteByMailGroupNameAndMailName(mailSub.getMailGroupName(), mailSub.getMailName());
    }

    //getALL
    public Set<String> getMailsList(String mailGroupName) {
        List<MailSub> mailSubs = mailSubRepository.findByMailGroupName(mailGroupName);
        return mailSubs.parallelStream().map(mailSubMapper::toDto).map(dto -> dto.mailName).collect(Collectors.toSet());
    }
}
