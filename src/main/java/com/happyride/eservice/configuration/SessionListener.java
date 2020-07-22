package com.happyride.eservice.configuration;

import com.happyride.eservice.entity.model.Users;
import com.happyride.eservice.entity.model.UsersLogInfo;
import com.happyride.eservice.service.UsersLogInofService;
import com.happyride.eservice.service.UsersService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.time.LocalDateTime;

@Configuration
public class SessionListener implements HttpSessionListener {
   /* @Autowired
    private UsersService usersService;
    @Autowired
    private UsersLogInofService usersLogInofService;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(event.getSession()+ auth.getName());
        HttpSession session = event.getSession();
        session.setAttribute("httpReq", event);

        HttpSession session2 = event.getSession();
        HttpServletRequest      httpReq      = (HttpServletRequest) session2.getAttribute("httpReq");
        Users users = usersService.findByEmail(auth.getName()).get();
        UsersLogInfo usersLogInfo = new UsersLogInfo();
        usersLogInfo.setUsers(users);
        usersLogInfo.setLoginTime(LocalDateTime.now());
        System.out.println("session created");
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersService.findByEmail(auth.getName()).get();
        HttpSession session = event.getSession();
        session.setAttribute("httpReq", event);
        HttpSession session2 = event.getSession();
        HttpServletRequest      httpReq      = (HttpServletRequest) session2.getAttribute("httpReq");
        UsersLogInfo usersLogInfo = getDivceIfo(httpReq);
        usersLogInfo.setUsers(users);
        usersLogInfo.setLoginTime(LocalDateTime.now());
        usersLogInofService.saveLogIinfo(usersLogInfo);
        System.out.println("session destroyed");
    }

    public UsersLogInfo getDivceIfo(HttpServletRequest request) {
        ;
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        UsersLogInfo usersLogInfo = new UsersLogInfo();

        usersLogInfo.setDevice(userAgent.getOperatingSystem().getDeviceType().getName());
        usersLogInfo.setOperatingSystem(userAgent.getOperatingSystem().getName());

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        usersLogInfo.setIpAddress(ip);
        usersLogInfo.setMacAddress(getMACAddress(ip));
        return usersLogInfo;
    }

    public String getMACAddress(String ip) {
        String str = "";
        String macAddress = "";
        try {
            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1) {
                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
    }*/
}
