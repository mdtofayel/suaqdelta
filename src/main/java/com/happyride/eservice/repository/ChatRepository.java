package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.Chat;
import com.happyride.eservice.entity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySendTo(Users users);

    List<Chat> findBySendFrom(Users users);

    List<Chat> findBySendToAndSendFromOrderByTime(Users sendTo, Users sendFrom);

    List<Chat> findBySendFromAndSendTo(Users sendTo, Users sendFrom);

    List<Chat> findBySendFromAndSendToOrSendToAndSendFromOrderByTime(Users sendTo1, Users sendFrom1, Users sendTo2, Users sendFrom2);

    List<Chat> findBySendFromAndSendToAndSeen(Users sendTo, Users sendFrom, boolean seen);

    List<Chat> findBySendFromAndSeenAndSendTo(Users sendFrom, boolean seen, Users sendTo);

    //List<Chat> findDistinctBySendToAndSendToAndSendFromOrderByTimeDesc(Users users, Users users1);


    @Query(
            value = "Select Distinct * from (SELECT * from chat ch ORDER BY ch.time DESC) c where c.send_to_id =:users or c.send_from_id =:users",
            nativeQuery = true
    )
    List<Chat> findAllFriendChat(Long users);

    List<Chat> findDistinctBySendToAndSendFromAndSendToOrderByTimeDesc(Users distinctUser, Users sendFrom, Users sendTo);

   /* @Query(
            value ="Select * from chat c Where(( c.send_to_id =:sendTo And c.send_from_id =:sendFrom) or (c.send_from_id =:sendTo and c.send_to_id =:sendFrom))",
            nativeQuery = true
    )
    List<Chat> findAllchat(@Param("sendTo") int sendTo, @Param("sendFrom") int sendFrom);*/


}
