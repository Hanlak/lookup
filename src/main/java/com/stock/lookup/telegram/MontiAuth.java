package com.stock.lookup.telegram;

import com.stock.lookup.dao.BotAuthRepository;
import com.stock.lookup.model.BotAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class MontiAuth {
    @Autowired
    BotAuthRepository botAuthRepository;

    public boolean registerUser(String groupName, String userName,String name) {
        try {
            BotAuth botAuth = new BotAuth(groupName, userName,name);
            BotAuth botAuthSave = botAuthRepository.save(botAuth);
            if (ObjectUtils.isEmpty(botAuthSave)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkValidUser(String accessedUser, String groupName) {
        try {
            BotAuth botAuth = botAuthRepository.findDistinctByGroupName(groupName);
            return accessedUser.equalsIgnoreCase(botAuth.getUserName());
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean updateUser(String newUser, String groupName) {
        try {
            int updateCheck = botAuthRepository.updateNewUserAsGroupAdmin(groupName, newUser);
            return updateCheck == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public List<BotAuth> getGroupIds(String userName) {
        try {
            return botAuthRepository.findByUserName(userName);
        } catch (Exception e) {
            return null;
        }
    }

}
