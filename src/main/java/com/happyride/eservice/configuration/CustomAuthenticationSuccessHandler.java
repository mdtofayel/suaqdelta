package com.happyride.eservice.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    CustomAuthenticationSuccessHandler() throws IOException {
        setUseReferer(true);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (request.getSession().getAttribute("referer_url") != null) {
            String link = request.getSession().getAttribute("referer_url").toString();
            request.getSession().removeAttribute("referer_url");
            getRedirectStrategy().sendRedirect(request, response, link);
        } else {
            getRedirectStrategy().sendRedirect(request, response, "/usercheck");
        }
    }
}
