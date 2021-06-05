package com.stock.lookup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MAILSUB")
@Getter
@Setter
@NoArgsConstructor
public class MailSub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "MAIL_GROUP_NAME", nullable = false)
    @JsonProperty("mail_group_name")
    private String mailGroupName;
    @Column(name = "MAIL_NAME", nullable = false)
    @JsonProperty("mail_name")
    private String mailName;

    public MailSub(String mailGroupName, String mailName) {
        this.mailGroupName = mailGroupName;
        this.mailName = mailName;
    }
}
