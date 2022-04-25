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

    public boolean registerUser(String groupName, String userName, String name) {
        try {
            BotAuth botAuth = botAuthRepository.findDistinctByGroupName(groupName);
            if (ObjectUtils.isEmpty(botAuth)) {
                BotAuth botAuth1 = new BotAuth(groupName, userName, name);
                BotAuth botAuthSave = botAuthRepository.save(botAuth1);
                return !ObjectUtils.isEmpty(botAuthSave);
            }
            return false;
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

    public String unRegister(String groupId, String userName, String title) {
        try {
            botAuthRepository.delete(new BotAuth(groupId, userName, title));
            return "<b> Admin Access Removed.. AnyOne Can Use /register to Register to Gain Admin Access </b>";
        } catch (Exception e) {
            return "<b> UnRegistering Failed..  :(</b>";
        }
    }

    @Transactional
    public String purge(String groupId) {
        try {
            botAuthRepository.deleteByGroupName(groupId);
            return "<b> Purge Success </b>";
        } catch (Exception e) {
            return "<b> Purge Failed</b>";
        }
    }
}
