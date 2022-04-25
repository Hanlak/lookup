package com.stock.lookup.dao;

import com.stock.lookup.model.BotAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public interface BotAuthRepository extends JpaRepository<BotAuth, Long> {
    BotAuth findDistinctByGroupName(String groupName) throws EntityNotFoundException;

    List<BotAuth> findByUserName(String userName) throws EntityNotFoundException;

    void deleteByGroupName(String groupName);

    @Modifying
    @Query(value = "UPDATE BOTAUTH b SET b.USER_NAME= :newUser WHERE b.GROUP_NAME = :groupName", nativeQuery = true)
    int updateNewUserAsGroupAdmin(@Param("newUser") String userName, @Param("groupName") String groupName);
}
