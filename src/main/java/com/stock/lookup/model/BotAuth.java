package com.stock.lookup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BOTAUTH")
@Getter
@Setter
@NoArgsConstructor
@IdClass(BotAuthKey.class)
public class BotAuth {
    @Id
    @Column(name = "GROUP_NAME", nullable = false)
    @JsonProperty("group_name")
    private String groupName;

    @Id
    @Column(name = "USER_NAME", nullable = false)
    @JsonProperty("stock_name")
    private String userName;

    @Column(name = "GROUP_TITLE")
    @JsonProperty("groupTitle")
    private String groupTitle;

    public BotAuth(String groupName, String userName, String groupTitle) {
        this.groupName = groupName;
        this.userName = userName;
        this.groupTitle = groupTitle;
    }
}

