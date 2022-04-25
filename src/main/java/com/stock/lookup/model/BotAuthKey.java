package com.stock.lookup.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class BotAuthKey implements Serializable {
    protected String groupName;
    protected String userName;

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof BotAuthKey)) return false;
        BotAuthKey stockKey = (BotAuthKey) o;
        return Objects.equals(this.groupName, stockKey.groupName)
                && Objects.equals(this.userName, stockKey.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.groupName, this.userName);
    }
}
