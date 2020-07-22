package com.happyride.eservice.service;


import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.entity.model.UsersLogInfo;
import com.happyride.eservice.repository.UsersLogInofRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersLogInofService {
     private UsersLogInofRepository usersLogInofRepository;

    @Autowired
    public  UsersLogInofService(UsersLogInofRepository usersLogInofRepository){
        this.usersLogInofRepository = usersLogInofRepository;
    }

    public UsersLogInfo saveLogIinfo(UsersLogInfo usersLogInfo){
        return usersLogInofRepository.save(usersLogInfo);
    }
}
