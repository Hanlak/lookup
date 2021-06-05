package com.stock.lookup.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MailSubDTO {
    @NotBlank(message = "mail group_name cannot be empty or null")
    @JsonProperty("mail_group_name")
    public String mailGroupName;

    @NotBlank(message = "mail name cannot be empty or null")
    @JsonProperty("mail_name")
    @Email(message = "not a valid mail")
    public String mailName;

    public MailSubDTO(String mailGroupName, String mailName) {
        this.mailGroupName = mailGroupName;
        this.mailName = mailName;
    }
}
