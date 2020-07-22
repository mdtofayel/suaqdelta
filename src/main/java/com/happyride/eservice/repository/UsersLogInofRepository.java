package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.UsersLogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersLogInofRepository extends JpaRepository<UsersLogInfo, Long> {
}
