package com.stock.lookup.dao;

import com.stock.lookup.model.MailSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Repository
public interface MailSubRepository extends JpaRepository<MailSub, Long> {
    List<MailSub> findByMailGroupName(String mailGroupName) throws EntityNotFoundException;

    void deleteByMailGroupNameAndMailName(String mailGroupName, String mailName);

}
