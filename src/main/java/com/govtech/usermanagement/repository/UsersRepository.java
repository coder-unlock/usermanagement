package com.govtech.usermanagement.repository;

import com.govtech.usermanagement.entities.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xdebugger
 */
public interface UsersRepository extends JpaRepository<UserEntity, String> {

    List<UserEntity> findBySalaryBetween(Double min, Double max, PageRequest pageReq);
}
