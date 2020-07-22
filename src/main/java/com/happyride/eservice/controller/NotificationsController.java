package com.happyride.eservice.controller;

import com.happyride.eservice.entity.model.Notifications;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.PostReport;
import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.service.NotificationsService;
import com.happyride.eservice.service.ReportService;
import com.happyride.eservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/notifications")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ReportService reportService;

    @PostMapping("/saveNotifications")
    public @ResponseBody
    Map<String, String> saveNotifications(@RequestAttribute("notifications") Notifications notifications, BindingResult bindingResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (bindingResult.hasErrors()) {
            responseResult.put("response", bindingResult.getFieldError().toString());
            return responseResult;
        }
        notifications.setCreatAt(LocalDateTime.now());
        notificationsService.saveNotifications(notifications);

        responseResult.put("response", "Successful");
        return responseResult;
    }


    @GetMapping("/singleNotifications/{id}")
    public  @ResponseBody String findSingleNotifications(@PathVariable long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> users = usersService.findByEmail(auth.getName());

        Optional<Notifications> notifications = notificationsService.findNotificationById(id);
        return notifications.get().toString();

    }

    @GetMapping("/findAllNotificationByUserId")
    public @ResponseBody List<Notifications> findNotificationByUserId(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<Notifications> listOfnotification = notificationsService.findNotificationByUsers(usersService.findByEmail(auth.getName()).get());

        return  listOfnotification;
    }

    @GetMapping("/newNotification")
    public @ResponseBody List<Notifications> findNewNotification(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersService.findByEmail(authentication.getName()).get();

        List<Notifications> listOfNewNotification = notificationsService.findNewNotification(false);
         for(Notifications notifications: listOfNewNotification){
             notifications.setNotificationSeenStatus(true);
             notificationsService.saveNotifications(notifications);
         }
        return listOfNewNotification;
    }



    @PostMapping("/saveReport")
    public @ResponseBody  Map<String, String>  saveReport(@RequestAttribute PostReport postReport, BindingResult bindingResult){
        Map<String, String> responseResult = new HashMap<>();

        if (bindingResult.hasErrors()) {
            responseResult.put("response", bindingResult.getFieldError().toString());
            return responseResult;
        }
        postReport.setCreatAt(LocalDateTime.now());
        reportService.saveReport(postReport);

        responseResult.put("response", "Successful");
        return responseResult;
    }

}
