package com.happyride.eservice.controller;

import com.happyride.eservice.configuration.GlobalUtils;
import com.happyride.eservice.entity.model.Properties;
import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.model.MessageResponseModel;
import com.happyride.eservice.model.PasswordReset;
import com.happyride.eservice.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
public class HomeController {

    private static final String URL = "http://suqqatar.com";

    private final RoleService roleService;

    private final UsersService usersService;

    private final MailSendingService mailSendingService;

    private final PasswordResetTokenService passwordResetTokenService;

    private final PostService postService;

    private final GenericService genericService;

    private final CategoryService categoryService;

    private final SubCategoryService subCategoryService;

    private final PostTypeService postTypeService;

    private final SliderImageService sliderImageService;

    private final PostViewService postViewService;

    private final ServletContext servletContext;

    private final FavoritePostService favoritePostService;

    private final PhoneNumberVerificationTokenService phoneNumberVerificationTokenService;

    private final EmailVerificationTokenService emailVerificationTokenService;

    private final OtpSendCounterService otpSendCounterService;

    private final VerificationService verificationService;

    private final LikePostService likePostService;

    private final ManufacturerService manufacturerService;

    private final ManufacturerModelService manufacturerModelService;

    private final NotificationsService notificationsService;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HomeController(CategoryService categoryService, RoleService roleService, UsersService usersService,
                          MailSendingService mailSendingService, PasswordResetTokenService passwordResetTokenService,
                          PostService postService, GenericService genericService, FavoritePostService favoritePostService,
                          SubCategoryService subCategoryService, ServletContext servletContext, LikePostService likePostService,
                          PostTypeService postTypeService, PhoneNumberVerificationTokenService phoneNumberVerificationTokenService,
                          SliderImageService sliderImageService, PostViewService postViewService, EmailVerificationTokenService emailVerificationTokenService,
                          VerificationService verificationService, OtpSendCounterService otpSendCounterService, ManufacturerService manufacturerService,
                          ManufacturerModelService manufacturerModelService, NotificationsService notificationsService) {
        this.categoryService = categoryService;
        this.roleService = roleService;
        this.usersService = usersService;
        this.mailSendingService = mailSendingService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.postService = postService;
        this.genericService = genericService;
        this.favoritePostService = favoritePostService;
        this.subCategoryService = subCategoryService;
        this.servletContext = servletContext;
        this.likePostService = likePostService;
        this.postTypeService = postTypeService;
        this.phoneNumberVerificationTokenService = phoneNumberVerificationTokenService;
        this.sliderImageService = sliderImageService;
        this.postViewService = postViewService;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.otpSendCounterService = otpSendCounterService;
        this.verificationService = verificationService;
        this.manufacturerService = manufacturerService;
        this.manufacturerModelService = manufacturerModelService;
        this.notificationsService = notificationsService;
    }

    @GetMapping("/")
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("imageSlider", sliderImageService.sliderImageList());
        modelAndView.addObject("listOfNewProduct", postService.getAllPost());
        return modelAndView;
    }

    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/access-denied");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest servletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(servletRequest.getScheme());
        urlBuilder.append("://");
        urlBuilder.append(servletRequest.getServerName());
        if (servletRequest.getServerPort() != 80) {
            urlBuilder.append(":");
            urlBuilder.append(servletRequest.getServerPort());
        }

        /*String refererURI = "/";

        try {
            refererURI = new URI(servletRequest.getHeader("referer")).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }*/

        if (servletRequest.getHeader("referer") != null && !servletRequest.getHeader("referer").equals(servletRequest.getRequestURL().toString()) && !servletRequest.getHeader("referer").equals(urlBuilder.toString() + "/")) {
            /*if (refererURI.equals("/css/style.css")) {
                return "redirect:/usercheck";
            }*/
            servletRequest.getSession().setAttribute("referer_url", servletRequest.getHeader("referer"));
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/usercheck";
        }
        return "login";
    }

    @GetMapping("/usercheck")
    public String checkUser(HttpServletRequest request, Authentication authentication) {
        boolean isAdmin = false;
        boolean isManager = false;
        boolean isSupporter = false;
        boolean isUser = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_ADMIN":
                    isAdmin = true;
                    break;
                case "ROLE_MANAGER":
                    isManager = true;
                    break;
                case "ROLE_SUPPORTER":
                    isSupporter = true;
                    break;
                case "ROLE_USER":
                    isUser = true;
                    break;
            }
        }

        if (isAdmin) {
            return "redirect:/admin";
        } else if (isManager) {
            return "redirect:/manager";
        } else if (isSupporter) {
            return "redirect:/supporter";
        } else if (isUser) {
            return "redirect:/user";
        } else {
            throw new IllegalStateException();
        }
    }

    @GetMapping("/registration")
    public ModelAndView getRegistration(@RequestParam(required = false) boolean success) {
        ModelAndView modelAndView = new ModelAndView("user_register");
        modelAndView.addObject("user", new Users());
        modelAndView.addObject("profile", new Profile());
        if (success) {
            modelAndView.addObject("successMessage", "Registration Successful");
        }
        return modelAndView;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") @Validated Users users, BindingResult userBindResult, @ModelAttribute("profile") @Validated Profile profile, BindingResult profileBindResult, Model model) {
        if (userBindResult.hasErrors()) {
            return "user_register";
        }

        if (profileBindResult.hasErrors()) {
            return "user_register";
        }

        if (!users.getPassword().equals(users.getConfirmPassword())) {
            model.addAttribute("passwordNotMatch", "Password not matched");
            return "user_register";
        }

        Optional<Users> users1 = usersService.findByEmail(users.getEmail());

        if (users1.isPresent()) {
            userBindResult.rejectValue("email", "error.user", "An account already exists for this email or phone number.");
            return "user_register";
        }

        boolean isNumeric = false;

        if (isNumeric(users.getEmail())) {
            isNumeric = true;
            if (users.getEmail().length() < 8 || users.getEmail().length() > 8) {
                userBindResult.rejectValue("email", "error.user", "Invalid phone number. Number must be 8 digit.");
                return "user_register";
            }
        } else if (!isValidEmailAddress(users.getEmail())) {
            userBindResult.rejectValue("email", "error.user", "Invalid Email address.");
            return "user_register";
        }

        users.setActive(1);
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = roleService.getRoleByRole("USER");
        if (!role.isPresent()) {
            return "user_register";
        }
        Verification verification = new Verification();
        roles.add(role.get());
        users.setRoles(roles);
        users.setCreatedDate(LocalDateTime.now());
        users.setProfile(profile);
        verification.setUsers(users);
        users.setVerification(verification);
        profile.setUsers(users);
        profile.setStatus(true);
        usersService.saveUser(users);

        OtpSendCounter otpSendCounter1 = new OtpSendCounter();
        otpSendCounter1.setUsers(users);
        otpSendCounter1.setCount(otpSendCounter1.getCount() + 1);
        otpSendCounter1.setTokenCreateTimeAndDate(LocalDateTime.now());
        otpSendCounterService.addOtpCount(otpSendCounter1);

        if (isNumeric) {
            String token = generateRandomChars(
                    "1234567890", 6);
            new Thread(() -> {
                try {
                    MessageResponseModel messageResponseModel = sendOTP(token, users.getEmail());
                    PhoneNumberVerificationToken phoneNumberVerificationToken = new PhoneNumberVerificationToken();
                    phoneNumberVerificationToken.setMessageId(messageResponseModel.getMessage_id());
                    phoneNumberVerificationToken.setToken(token);
                    phoneNumberVerificationToken.setUsers(users);
                    phoneNumberVerificationToken.setCreateTime(LocalDateTime.now());
                    phoneNumberVerificationToken.setExpireTime(LocalDateTime.now().plusHours(3));
                    phoneNumberVerificationTokenService.savePhoneNumberVerificationToken(phoneNumberVerificationToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            emailVerificationLinkSend(users);
        }

        notificationsService.saveNotifications(new Notifications("#", usersService.findUserById(0L).get(), usersService.findUserById(users.getId()).get(), "Welcome from Admin of souqqatar.com", false, LocalDateTime.now(), false));

        return "redirect:/registration?success=true";
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    private MessageResponseModel sendOTP(String otpToken, String phoneNumber) throws IOException {
        String nPhoneNumber = "+974";
        nPhoneNumber = nPhoneNumber.concat(phoneNumber);
        final String uri = "http://api.smsala.com/api/SendSMS?api_id=API85740667631&api_password=1iiBZonfL0&sms_type=T&encoding=T&sender_id=SUQ QATAR&phonenumber=" + nPhoneNumber + "&textmessage=" + "Your Security Code is: " + otpToken;

        RestTemplate restTemplate = new RestTemplate();
        MessageResponseModel messageResponseModel = restTemplate.getForObject(uri, MessageResponseModel.class);

        assert messageResponseModel != null;
        return messageResponseModel;
    }

    private void emailVerificationLinkSend(Users users) {
        String token = generateRandomChars(
                "1234567890", 6);

        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        Optional<EmailVerificationToken> emailVerificationToken1 = emailVerificationTokenService.findByUsers(users);
        emailVerificationToken1.ifPresent(emailVerificationToken2 -> emailVerificationToken.setId(emailVerificationToken2.getId()));
        emailVerificationToken.setUsers(users);
        emailVerificationToken.setToken(token);
        emailVerificationToken.setCreateTime(LocalDateTime.now());
        emailVerificationToken.setExpireTime(LocalDateTime.now().plusHours(72));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(users.getEmail());
        message.setSubject("Email Verification");
        message.setText("Verify your email by clicking this link: " + URL + "/emailVerificationRequest?token=" + token + "\n" + "Your Security Code is: " + token);

        new Thread(() -> mailSendingService.sendMail(message)).start();
        emailVerificationTokenService.saveEmailVerificationToken(emailVerificationToken);
    }

    @GetMapping("/emailVerificationRequest")
    public String emailVerificationRequest(@RequestParam("token") String token) {
        Optional<EmailVerificationToken> emailVerificationToken = emailVerificationTokenService.getEmailVerificationTokenByToken(token);
        if (!emailVerificationToken.isPresent()) {
            return "invalid_url";
        }
        if (ChronoUnit.HOURS.between(emailVerificationToken.get().getExpireTime(), LocalDateTime.now()) > 72) {
            return "time_expired";
        }
        Optional<Verification> verification = verificationService.getVerificationByUser(emailVerificationToken.get().getUsers());
        if (!verification.isPresent()) {
            return "invalid_url";
        }
        Verification verification1 = verification.get();
        verification1.setMailVerified(true);
        verificationService.saveVerification(verification1);

        Users users = verification1.getUsers();

        notificationsService.saveNotifications(new Notifications("#", usersService.findUserById(0L).get(), usersService.findUserById(users.getId()).get(), "Thanks for verifying your email.", false, LocalDateTime.now(), false));
        return "redirect:/login";
    }

    @GetMapping("/forgotPassword")
    public ModelAndView forgotPassword(@RequestParam(required = false) boolean success) {
        ModelAndView modelAndView = new ModelAndView("forgotpassword");
        if (success) {
            modelAndView.addObject("success", "Password reset link sent to your registered email");
        }
        return modelAndView;
    }

    @PostMapping("/forgotPassword")
    public String forgotPasswordPost(@RequestParam("email") String email, Model model) {

        Optional<Users> users = usersService.findByEmail(email);

        if (!users.isPresent()) {
            model.addAttribute("success", "Invalid Email address or Phone Number.");
            return "forgotpassword";
        }

        Optional<OtpSendCounter> otpSendCounter = otpSendCounterService.getOtpCounterByUser(users.get());
        boolean otpCounterPresent = false;
        boolean otpCountFromStart = false;
        if (otpSendCounter.isPresent()) {
            otpCounterPresent = true;
        }
        OtpSendCounter otpSendCounter1 = new OtpSendCounter();
        otpSendCounter1.setUsers(users.get());
        if (otpCounterPresent) {
            if (otpSendCounter.get().getCount() >= 5) {
                if (dtf.format(otpSendCounter.get().getTokenCreateTimeAndDate()).equals(dtf.format(LocalDateTime.now()))) {
                    model.addAttribute("success", "You have exceed your otp limit try again later");
                    return "forgotpassword";
                }

                otpCountFromStart = true;
            }

            otpSendCounter1 = otpSendCounter.get();
        }

        if (otpCountFromStart) {
            otpSendCounter1.setCount(0);
        }

        otpSendCounter1.setCount(otpSendCounter1.getCount() + 1);
        otpSendCounter1.setTokenCreateTimeAndDate(LocalDateTime.now());
        otpSendCounterService.addOtpCount(otpSendCounter1);


        if (!isValidEmailAddress(email)) {
            if (isNumeric(email)) {
                String token = generateRandomChars("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 12);
                try {
                    MessageResponseModel messageResponseModel = sendOTP(token, users.get().getEmail());
                    PhoneNumberVerificationToken phoneNumberVerificationToken = new PhoneNumberVerificationToken();
                    Optional<PhoneNumberVerificationToken> phoneNumberVerificationToken1 = phoneNumberVerificationTokenService.findByUsers(users.get());
                    phoneNumberVerificationToken1.ifPresent(phoneNumberVerificationToken2 -> phoneNumberVerificationToken.setId(phoneNumberVerificationToken2.getId()));
                    phoneNumberVerificationToken.setMessageId(messageResponseModel.getMessage_id());
                    phoneNumberVerificationToken.setToken(token);
                    phoneNumberVerificationToken.setUsers(users.get());
                    phoneNumberVerificationToken.setCreateTime(LocalDateTime.now());
                    phoneNumberVerificationToken.setExpireTime(LocalDateTime.now().plusHours(4));
                    phoneNumberVerificationTokenService.savePhoneNumberVerificationToken(phoneNumberVerificationToken);
                    model.addAttribute("m", "p");
                    model.addAttribute("guid", users.get().getId());
                    return "submitTokenForForgotPasswordMPhone";
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                model.addAttribute("success", "Invalid Email address or Phone Number.");
                return "forgotpassword";
            }
        }

        String token = generateRandomChars(
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890", 20);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        Optional<PasswordResetToken> passwordReset2 = passwordResetTokenService.findByUsers(users.get());
        passwordReset2.ifPresent(passwordResetToken1 -> passwordResetToken.setPasswordResetId(passwordResetToken1.getPasswordResetId()));
        passwordResetToken.setUsers(users.get());
        passwordResetToken.setToken(token);
        passwordResetToken.setCreateTime(LocalDateTime.now());
        passwordResetToken.setExpireTime(LocalDateTime.now().plusHours(4));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Password Reset Link: " + URL + "/passwordResetRequest?m=e&guid=" + users.get().getId() + "&" + "token=" + token + "\n" + "Your Security code is: " + token);

        new Thread(() -> mailSendingService.sendMail(message)).start();
        passwordResetTokenService.savePasswordReset(passwordResetToken);

        model.addAttribute("m", "e");
        model.addAttribute("guid", users.get().getId());
        return "submitTokenForForgotPasswordMEmail";
    }

    private static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }

    @GetMapping("/passwordResetRequest")
    public String passwordResetRequest(@RequestParam("m") String method, @RequestParam("guid") Long guid, @RequestParam("token") String token, Model model) {
        Optional<Users> users = usersService.findUserById(guid);
        if (!users.isPresent()) {
            model.addAttribute("success", "Invalid request.");
            return "invalid_url";
        }

        if (method.equals("p")) {
            Optional<PhoneNumberVerificationToken> phoneNumberVerificationToken = phoneNumberVerificationTokenService.findByUsers(users.get());
            if (!phoneNumberVerificationToken.isPresent()) {
                model.addAttribute("success", "Invalid request.");
                return "invalid_url";
            }
            if (ChronoUnit.HOURS.between(phoneNumberVerificationToken.get().getExpireTime(), LocalDateTime.now()) > 4) {
                model.addAttribute("success", "Token validity time expired.");
                return "invalid_url";
            }
            if (!phoneNumberVerificationToken.get().getToken().equals(token)) {
                model.addAttribute("success", "Invalid request.");
                return "invalid_url";
            }
            PasswordReset passwordReset = new PasswordReset();
            passwordReset.setToken(token);
            passwordReset.setMethod("p");
            passwordReset.setGuid(users.get().getId());
            model.addAttribute("passwordReset", passwordReset);
            return "change_password";
        }

        if (method.equals("e")) {
            Optional<PasswordResetToken> passwordReset = passwordResetTokenService.findByUsers(users.get());
            if (!passwordReset.isPresent()) {
                model.addAttribute("success", "Invalid request.");
                return "invalid_url";
            }
            if (ChronoUnit.HOURS.between(passwordReset.get().getExpireTime(), LocalDateTime.now()) > 4) {
                model.addAttribute("success", "Token validity time expired.");
                return "invalid_url";
            }
            if (!passwordReset.get().getToken().equals(token)) {
                model.addAttribute("success", "Invalid request.");
                return "invalid_url";
            }
            PasswordReset passwordReset1 = new PasswordReset();
            passwordReset1.setToken(token);
            passwordReset1.setMethod("e");
            passwordReset1.setGuid(users.get().getId());
            model.addAttribute("passwordReset", passwordReset1);
            return "change_password";
        }
        model.addAttribute("success", "Invalid request.");
        return "invalid_url";
    }

    @PostMapping("/changeForGottenPassword")
    public String passwordResetRequestPost(@ModelAttribute("passwordReset") @Validated PasswordReset passwordReset, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "change_password";
        }
        if (!passwordReset.getNewPassword().equals(passwordReset.getConfirmPassword())) {
            model.addAttribute("passwordNotMatched", "Confirm password do not match");
            return "change_password";
        }

        Optional<Users> users = usersService.findUserById(passwordReset.getGuid());
        if (!users.isPresent()) {
            return "change_password";
        }

        if (passwordReset.getMethod().equals("p")) {
            Optional<PhoneNumberVerificationToken> phoneNumberVerificationToken = phoneNumberVerificationTokenService.findByUsers(users.get());
            if (!phoneNumberVerificationToken.isPresent()) {
                return "change_password";
            }
            if (ChronoUnit.HOURS.between(phoneNumberVerificationToken.get().getExpireTime(), LocalDateTime.now()) > 4) {
                return "change_password";
            }
            if (!phoneNumberVerificationToken.get().getToken().equals(passwordReset.getToken())) {
                return "change_password";
            }
            Users users1 = users.get();
            users1.setPassword(passwordReset.getNewPassword());
            users1.setConfirmPassword(passwordReset.getConfirmPassword());
            users1.setPasswordResetToken(null);
            usersService.saveUser(users1);
            model.addAttribute("success", "Password Reset Successful");
            return "redirect:/";
        } else if (passwordReset.getMethod().equals("e")) {
            Optional<PasswordResetToken> passwordReset1 = passwordResetTokenService.findByUsers(users.get());
            if (!passwordReset1.isPresent()) {
                return "change_password";
            }
            if (ChronoUnit.HOURS.between(passwordReset1.get().getExpireTime(), LocalDateTime.now()) > 4) {
                return "change_password";
            }
            if (!passwordReset1.get().getToken().equals(passwordReset.getToken())) {
                return "change_password";
            }
            Users users1 = users.get();
            users1.setPassword(passwordReset.getNewPassword());
            users1.setConfirmPassword(passwordReset.getConfirmPassword());
            users1.setPasswordResetToken(null);
            usersService.saveUser(users1);
            model.addAttribute("success", "Password Reset Successful");
            return "redirect:/";
        }
        return "change_password";
    }

    @GetMapping("/allPost")
    public @ResponseBody
    Page<Post> allPost(@RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        return postService.getAllPost(PageRequest.of(page.orElse(0), size.orElse(20)));
    }

    @PostMapping("/trendingads")
    public @ResponseBody
    Page<Post> trendingads(@RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        return postService.getAllPost(PageRequest.of(page.orElse(0), size.orElse(20)));
    }

    @PostMapping("/searchAllPost")
    public @ResponseBody
    Page<Post> searchAllPost(@RequestParam("title") Optional<String> title,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        return postService.findByTitle(title.orElse("_"), PageRequest.of(page.orElse(0), size.orElse(20)));
    }

    @GetMapping("/postById/{id}")
    public @ResponseBody
    Optional<Post> postById(@PathVariable("id") Long id) {
        return postService.findPostById(id);
    }

    @PostMapping("/postByUser")
    public @ResponseBody
    Page<Post> postById(@RequestParam("userId") Long userId,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        Optional<Users> users = usersService.findUserById(userId);
        return users.map(users1 -> postService.findPostByPostedBy(users1, PageRequest.of(page.orElse(0), size.orElse(20)))).orElse(null);
    }

    @PostMapping("/userFavoriteItem")
    public @ResponseBody
    Page<FavoritePost> userFavoriteItemById(@RequestParam("userId") Long userId,
                                            @RequestParam("page") Optional<Integer> page,
                                            @RequestParam("size") Optional<Integer> size) {
        Optional<Users> users = usersService.findUserById(userId);
        Optional<FavoritePost> favoritePost = Optional.of(new FavoritePost());

        return favoritePost.map(users1 -> favoritePostService.findPostByFavouriteItem(users.get(), PageRequest.of(page.orElse(0), size.orElse(20)))).orElse(null);
    }

    @PostMapping("/userLikeItem")
    public @ResponseBody
    Page<LikePost> userLikeItemById(@RequestParam("userId") Long userId,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        Optional<Users> users = usersService.findUserById(userId);
        Optional<LikePost> likePost = Optional.of(new LikePost());

        return likePost.map(users1 -> likePostService.findPostByLikeItem(users.get(), PageRequest.of(page.orElse(0), size.orElse(20)))).orElse(null);
    }

    @PostMapping("/allPostByGeneric")
    public @ResponseBody
    Page<Post> allPostByGeneric(@RequestParam("genericId") Long genericId,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        Optional<Generic> generic = genericService.findById(genericId);
        return generic.map(generic1 -> postService.findPostByGeneric(generic1, PageRequest.of(page.orElse(0), size.orElse(20)))).orElse(null);
    }

    @GetMapping("/allPostTypes")
    public @ResponseBody
    List<PostType> allPostTypes() {
        return postTypeService.findAll();
    }

    @PostMapping("/allPostByCategoryId")
    public @ResponseBody
    Page<Post> allPostByCategoryId(@RequestParam("categoryId") Long categoryId,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        Optional<Category> category = categoryService.categoryById(categoryId);
        return category.map(category1 -> postService.findAllBySubCategoryIn(category1.getSubCategoryList(), PageRequest.of(page.orElse(0), size.orElse(1)))).orElse(null);
    }

    @PostMapping("/allPostByCategoryNameAndType")
    public @ResponseBody
    Page<Post> allPostByCategoryId(@RequestParam("categoryName") String categoryName,
                                   @RequestParam("typeId") Long typeId,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        Optional<Category> category = categoryService.findCategoryByName(categoryName);
        return category.map(category1 -> postService.findAllBySubCategoryIn(category1.getSubCategoryList(), PageRequest.of(page.orElse(0), size.orElse(1)))).orElse(null);
    }

    @PostMapping("/allPostBySubCategoryId")
    public @ResponseBody
    Page<Post> allPostBySubCategoryId(@RequestParam("subCategoriesId") Long subCategoryId,
                                      @RequestParam("page") Optional<Integer> page,
                                      @RequestParam("size") Optional<Integer> size) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);

        return subCategory.map(subCategory1 -> postService.findAllBySubCategory(subCategory1, PageRequest.of(page.orElse(0), size.orElse(1)))).orElse(null);
    }

    @PostMapping("/allPostByPostTypeId")
    public @ResponseBody
    Page<Post> allPostByPostTypeId(@RequestParam("postType") Long postTypeId,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        Optional<PostType> postType1 = postTypeService.getPostTypebyId(postTypeId);

        if (!postType1.isPresent()) {
            return null;
        }

        List<Category> categoryList = postType1.get().getCategoryList();

        if (categoryList.size() < 1) {
            return null;
        }

        List<SubCategory> subCategoryList = new ArrayList<>();

        for (Category category : categoryList) {
            subCategoryList.addAll(category.getSubCategoryList());
        }

        if (subCategoryList.size() < 1) {
            return null;
        }

        return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(1)));
    }

    /*@PostMapping("/allPostByCategoryName")
    public @ResponseBody
    Page<Post> allPostByCategoryName(@RequestParam("categoryName") String categoryName,
                                     @RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size) {
        Optional<Category> category = categoryService.findCategoryByName(categoryName);
        if (!category.isPresent()) {
            return null;
        } else {
            List<PostModel> postModels = new ArrayList<>();
            for (SubCategory subCategory : category.get().getSubCategoryList()) {
                postModels.add(subCategory.getPostModel());
            }
            return postService.findPostByPostModelIn(postModels, PageRequest.of(page.orElse(0), size.orElse(1)));
        }
    }*/

    @GetMapping("/allAdsByCategory")
    public ModelAndView allAddsByCategory(@RequestParam("categoryId") Long categoryId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("categoryWise_search");
        Optional<Category> category = categoryService.categoryById(categoryId);
        if (!category.isPresent()) {
            return null;
        } else {
            modelAndView.addObject("category", category.get());
        }
        return modelAndView;
    }

    @GetMapping("/listOfPostByPostTypeId")
    public ModelAndView listOfPostByPostTypeId(@RequestParam("postTypeId") Long postTypeId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("postTypeWise_search");
        Optional<PostType> postType = postTypeService.getPostTypebyId(postTypeId);
        if (!postType.isPresent()) {
            return null;
        } else {
            modelAndView.addObject("postType", postType.get());
        }
        return modelAndView;
    }

    @GetMapping("/allAdsBySubcategory")
    public ModelAndView allAddsBySubCategory(@RequestParam("subCategoryId") Long subCategories) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategories);
        if (!subCategory.isPresent()) {
            return null;
        } else {

            if (subCategory.get().getPostModel().getModelClass().isInstance(new Generic())) {
                modelAndView.setViewName("ads-general");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new SmartPhones())) {
                modelAndView.setViewName("ads-mobile");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new PetAnimal())) {
                modelAndView.setViewName("ads-animal");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new Hire())) {
                modelAndView.setViewName("ads-hire");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new Properties())) {
                modelAndView.setViewName("ads-property");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new Laptop())) {
                modelAndView.setViewName("ads-lap");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new Villa())) {
                modelAndView.setViewName("ads-villa");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new JobSeeker())) {
                modelAndView.setViewName("ads-seek");
                modelAndView.addObject("subCategories", subCategory.get());
            } else if (subCategory.get().getPostModel().getModelClass().isInstance(new Vehicles())) {
                modelAndView.setViewName("ads-vehicle");
                modelAndView.addObject("subCategories", subCategory.get());
            }

        }
        return modelAndView;
    }

    /*@RequestMapping("/categories/{id}")
    public ModelAndView allPostByCategories(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("categories");

        Optional<Category> optionalCategory = categoryService.categoryById(id);

        List<SubCategory> subCategoryList = new ArrayList<>();

        if (optionalCategory.isPresent()) {
            List<Category> categoriesList = categoryService.findCategoryListByName(optionalCategory.get().getName());
            List<PostType> postTypesList = new ArrayList<>();
            for (Category category : categoriesList) {
                postTypesList.add(category.getPostType());
                subCategoryList.addAll(category.getSubCategoryList());
            }
            modelAndView.addObject("postTypesList", postTypesList);
            modelAndView.addObject("categoriesList", categoriesList);
            modelAndView.addObject("subCategoryList", subCategoryList);
            modelAndView.addObject("categoriesById", optionalCategory.get());
        }

        return modelAndView;
    }*/

    @RequestMapping("/categories/{id}")
    public ModelAndView allPostByCategories(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("categerie_view");

        Optional<Category> optionalCategory = categoryService.categoryById(id);

        List<SubCategory> subCategoryList = new ArrayList<>();

        if (optionalCategory.isPresent()) {
            List<Category> categoriesList = categoryService.findCategoryListByName(optionalCategory.get().getName());
            List<PostType> postTypesList = new ArrayList<>();
            for (Category category : categoriesList) {
                postTypesList.add(category.getPostType());
                subCategoryList.addAll(category.getSubCategoryList());
            }
            modelAndView.addObject("postTypesList", postTypesList);
            modelAndView.addObject("categoriesList", categoriesList);
            modelAndView.addObject("subCategoryList", subCategoryList);
            modelAndView.addObject("categoriesById", optionalCategory.get());
        }

        return modelAndView;
    }


    @GetMapping("/guest/singlePrduct/view/{id}")
    public String showProductDetails(@PathVariable("id") Long id, HttpServletRequest request) {
        Long xyzSlugid1 = id;
        request.getSession().setAttribute("postId", String.valueOf(xyzSlugid1));
        Optional<Post> post = postService.findPostById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/userPost/singlePrduct/" + GlobalUtils.nameToSlug(post.get().getTitle()) + id;
        }
        return "redirect:/guest/singlePrduct/" + GlobalUtils.nameToSlug(post.get().getTitle()) + id;
    }

    @GetMapping("/guest/singlePrduct/{slug}")
    public String showProductDetailswithSlug(@PathVariable("slug") String slug, HttpServletRequest request, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        int len = slug.length();
        String postIds = String.valueOf(request.getSession().getAttribute("postId"));
        Long postId = Long.valueOf(postIds);
        Optional<Post> post = postService.findPostById(postId);
        /*Class modelClass = post.get().getPostModel().getModelClass();
        if(modelClass.isInstance(new Generic())){

        }*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/userPost/singlePrduct/" + GlobalUtils.nameToSlug(post.get().getTitle()) + postId;
        }
        Optional<PostView> postView = postViewService.findViewCountByPost(post.get());
        if (postView.isPresent()) {
            postView.get().setCount(postView.get().getCount() + 1);
            postViewService.savePostView(postView.get());
        } else {
            PostView postView1 = new PostView();
            postView1.setCount(1);
            postView1.setPost(post.get());
            postViewService.savePostView(postView1);
        }
        model.addAttribute("post", post);
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Generic())) {
            // modelAndView.setViewName("single-general");
            return "single-general";
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Hire())) {
            //modelAndView.setViewName("single-hire");
            return "single-hire";
        }

        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new JobSeeker())) {
            //modelAndView.setViewName("single-seek");
            return "single-seek";
        }

        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Properties())) {
            // modelAndView.setViewName("single-property");
            return "single-property";
        }

        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Laptop())) {
            // modelAndView.setViewName("single-lap");
            return "single-lap";
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new SmartPhones())) {
            //modelAndView.setViewName("single-mobile");
            return "single-mobile";
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Villa())) {
            //modelAndView.setViewName("single-villa");
            return "single-villa";
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new PetAnimal())) {
            // modelAndView.setViewName("single-animal");
            return "single-animal";
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Vehicles())) {
            // modelAndView.setViewName("single-vehicle");
            return "single-vehicle";
        } else {
            return "index";
        }


    }

    @RequestMapping(value = "/images/sliderImage/{imageId}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getSliderImage(@PathVariable("imageId") int imageId) throws IOException {
        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/images/sliderImage";
        File file = new File(IMAGE_FOLDER + "/" + imageId + ".png");
        return Files.readAllBytes(file.toPath());
    }

    @RequestMapping(value = "/images/post/{imageId}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getPostImage(@PathVariable("imageId") String imageId) throws IOException {
        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/images/post";
        File file = new File(IMAGE_FOLDER + "/" + imageId + ".png");
        return Files.readAllBytes(file.toPath());
    }

    @RequestMapping(value = "/videos/post/{videoId}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getvideos(@PathVariable("videoId") String videoId) throws IOException {
        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/videos/post";
        File file = new File(IMAGE_FOLDER + "/" + videoId + ".webm");
        return Files.readAllBytes(file.toPath());
    }

    @RequestMapping(value = "/typeList", method = RequestMethod.GET)
    public @ResponseBody
    List<PostType> getTypeList() {
        return postTypeService.findAll();
    }

    @RequestMapping(value = "/categoryListByPostType/{typeId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> categoryListByPostType(@PathVariable("typeId") Long typeId) {
        Optional<PostType> postType = postTypeService.getPostTypebyId(typeId);
        return postType.map(postType1 -> categoryService.findByPostType(postType1)).orElse(null);
    }

    @RequestMapping(value = "/categoryListByPostType", method = RequestMethod.GET)
    public @ResponseBody
    List<Category> categoryListByPostType() {
        List<Category> listOfCateGories = categoryService.findAll();
        Set<String> reDucplicateCategories = new HashSet<>();
        List<Category> uniqueCatLsit = new ArrayList<>();
        for (Category category : listOfCateGories) {
            reDucplicateCategories.add(category.getName().toLowerCase());
        }
        for (String name : reDucplicateCategories) {

            for (Category category1 : listOfCateGories) {
                if (name.toLowerCase().equals(category1.getName().toLowerCase())) {

                    uniqueCatLsit.add(category1);
                    break;
                }
            }
        }
        return uniqueCatLsit;
    }

    @RequestMapping(value = "/subcategoryListByCategory/{categoryId}", method = RequestMethod.GET)
    public @ResponseBody
    List<SubCategory> SubcategoryListByCategory(@PathVariable("categoryId") Long categoryId) {
        Optional<Category> category = categoryService.categoryById(categoryId);
        return category.map(category1 -> subCategoryService.findSubCategoriesByCategoryId(category1)).orElse(null);
    }

    @RequestMapping(value = "/images/profileImage/{imageId}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getProfileImage(@PathVariable("imageId") int imageId) throws IOException {
        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/images/profileImage";
        File file = new File(IMAGE_FOLDER + "/" + imageId + ".png");
        return Files.readAllBytes(file.toPath());
    }

    @RequestMapping(value = "/images/category/{categoryId}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getCategoryImage(@PathVariable("categoryId") int categoryId) throws IOException {
        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/images/category";
        File file = new File(IMAGE_FOLDER + "/" + categoryId + ".png");
        return Files.readAllBytes(file.toPath());
    }

    @RequestMapping("/SubcategoryWisePostSearch")
    public ModelAndView SubcategorySearch() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("category_search");
        return modelAndView;
    }

    @RequestMapping("/categoryWisePostSearch")
    public ModelAndView categorySearch() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("categoryWise_search");
        return modelAndView;
    }

    @GetMapping("/allAdsByGlobalSearch")
    public ModelAndView allAdsByGlobalSearch(@RequestParam("searchString") String searchString) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("global_search");
        modelAndView.addObject("searchString", searchString);
        return modelAndView;
    }

    @PostMapping("/getMaxPriceBySubcategoryId")
    public @ResponseBody
    Double allAdsByGlobalSearch(@RequestParam("subcategoryId") Long subcategoryId) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);
        if (subCategory.isPresent()) {
            Optional<Post> post = postService.findTopPriceBySubCategory(subCategory.get());
            if (post.isPresent()) return post.get().getPrice();
        }

        return 1000.0;
    }

    @PostMapping("/getMaxPriceByCategoryId")
    public @ResponseBody
    Double getMaxPriceByCategoryId(@RequestParam("categoryId") Long categoryId) {
        Optional<Category> category = categoryService.categoryById(categoryId);
        if (category.isPresent()) {
            Optional<Post> post = postService.findTopPriceByCategory(category.get());
            if (post.isPresent()) return post.get().getPrice();
        }

        return 1000.0;
    }

    @PostMapping("/getMaxPriceByTypeId")
    public @ResponseBody
    Double getMaxPriceByTypeId(@RequestParam("typeId") Long typeId) {
        Optional<PostType> postType = postTypeService.getPostTypebyId(typeId);
        if (postType.isPresent()) {
            Optional<Post> post = postService.findTopPriceByPostType(postType.get());
            if (post.isPresent()) return post.get().getPrice();
        }

        return 1000.0;
    }

    @GetMapping("/getMaxPrice")
    public @ResponseBody
    Double getMaxPrice() {
        Optional<Post> post = postService.findTopPrice();
        return post.map(Post::getPrice).orElse(1000.0);
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profileFind(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Users users = usersService.findUserById(id).get();
        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @GetMapping("/contactus")
    public ModelAndView profileFind() {
        return new ModelAndView("contact_us");
    }

    @PostMapping("/getPostModel")
    public @ResponseBody
    PostModel getPostModel(@RequestParam("subcategoryId") Long subcategoryId) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);
        return subCategory.map(SubCategory::getPostModel).orElse(null);
    }

    @PostMapping("/getTypeListByCategoryName")
    public @ResponseBody
    List<PostType> getTypeListByCategoryName(@RequestParam("categoryName") String categoryName) {
        List<Category> categoryList = categoryService.findCategoryListByName(categoryName);

        List<PostType> postTypeList = new ArrayList<>();

        for (Category category : categoryList) {
            if (!postTypeList.contains(category.getPostType())) {
                postTypeList.add(category.getPostType());
            }
        }

        return postTypeList;
    }

    @PostMapping("/getManufacturerList")
    public @ResponseBody
    List<Manufacturer> getManufacturerList(@RequestParam("subcategoryId") Long subcategoryId) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);

        if (subCategory.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            subCategoryList.add(subCategory.get());
            return manufacturerService.findAllBySubCategory(subCategoryList);
        }
        return null;
    }

    @GetMapping("/images/manufacturerImage/{manufacturerId}")
    public @ResponseBody
    byte[] getManufacturerImage(@PathVariable("manufacturerId") Long manufacturerId) throws IOException {

        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/images/manufacturer";
        File file = new File(IMAGE_FOLDER + "/" + manufacturerId + ".png");
        return Files.readAllBytes(file.toPath());
    }

    @PostMapping("/getManufacturerModelList")
    public @ResponseBody
    List<ManufacturerModel> getManufacturerModelList(@RequestParam("manufacturerName") String manufacturerName) {

        Optional<Manufacturer> manufacturer = manufacturerService.findManufacturerByName(manufacturerName);

        return manufacturer.map(manufacturerModelService::findAllByManufacturer).orElse(null);
    }
}
