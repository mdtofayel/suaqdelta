package com.happyride.eservice.configuration;

import com.happyride.eservice.repository.UsersRepository;
import com.happyride.eservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UsersRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UsersService usersService;

    private final LoggingAccessDeniedHandler loggingAccessDeniedHandler;


    @Autowired
    public SecurityConfiguration(BCryptPasswordEncoder bCryptPasswordEncoder, UsersService usersService, LoggingAccessDeniedHandler loggingAccessDeniedHandler) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersService = usersService;
        this.loggingAccessDeniedHandler = loggingAccessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(usersService)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers(
                        "/",
                        "/cate",
                        "/profile/**",
                        "/login",
                        "/registration",
                        "/forgotPassword",
                        "/passwordResetRequest",
                        "/changeForGottenPassword",
                        "/emailVerificationRequest",
                        "/allPost",
                        "/trendingads",
                        "/getPostModel",
                        "/postById",
                        "/postByUser",
                        "/allPostByGeneric",
                        "/allPostByCategory",
                        "/getTypeListByCategoryName",
                        "/allPostTypes",
                        "/categories/**",
                        "/allAdsByCategory",
                        "/allAdsBySubcategory",
                        "/allAdsBySubcategoryId",
                        "/allPostByCategoryId",
                        "/listOfPostByPostTypeId",
                        "/allPostBySubCategoryId",
                        "/allPostByPostTypeId",
                        "/userPost/post/showComment", "/userPost/getProfileById/**", "/userPost/getComment/**", "/userPost/getView/**", "/userPost/getLike/**", "/user/profile/{id}",
                        "/allAdsByGlobalSearch",
                        "/getMaxPriceBySubcategoryId",
                        "/getMaxPriceByCategoryId",
                        "/getMaxPriceByTypeId",
                        "/getManufacturerList",
                        "/getManufacturerModelList",
                        "/searchAllPost", "/guest/singlePrduct/view/**", "/guest/singlePrduct/**", "/typeList", "/categoryListByPostType/**", "/subcategoryListByCategory/**", "/search/**")
                .permitAll()
                .antMatchers("/user/**").hasAuthority("ROLE_USER")
                .antMatchers("/manager/**").hasAuthority("ROLE_MANAGER")
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout().deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(new LogoutSuccessor()).and().exceptionHandling()
                .accessDeniedHandler(loggingAccessDeniedHandler)
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/videos/**", "/fontawesome/**", "/fonts/**", "/admin_js/dist/**");
    }

    private PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return true;
            }
        };
    }
}
