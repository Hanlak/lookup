package com.stock.lookup.controller;


import com.stock.lookup.dto.MailSubDTO;
import com.stock.lookup.service.MailSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static com.stock.lookup.util.RequestValidator.validatePathVariables;


@RestController
public class MailSubController {

    @Autowired
    MailSubService mailSubService;

    @GetMapping("/thestocklookup/mailslist/{mailGroupName}")
    public ResponseEntity<Set<String>> getListOfMails(@PathVariable @NotBlank String mailGroupName) {
        validatePathVariables(mailGroupName, "mail_group_name");
        Set<String> mailsList = mailSubService.getMailsList(mailGroupName);
        return new ResponseEntity<>(mailsList, HttpStatus.OK);
    }

    @PostMapping("/thestocklookup/subscribe/mails")
    public ResponseEntity<MailSubDTO> subscribeMail(@Valid @RequestBody MailSubDTO mailSubDTO) {
        MailSubDTO mailSubDtoSaved = mailSubService.subscribe(mailSubDTO);
        return new ResponseEntity<>(mailSubDtoSaved, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/thestocklookup/unsubscribe/mails")
    public ResponseEntity<MailSubDTO> unSubscribe(@Valid @RequestBody MailSubDTO mailSubDto) {
        mailSubService.unSubscribe(mailSubDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
