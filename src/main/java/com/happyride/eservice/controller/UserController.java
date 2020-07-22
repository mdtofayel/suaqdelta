package com.happyride.eservice.controller;

import com.happyride.eservice.configuration.GlobalUtils;
import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.model.Condition;
import com.happyride.eservice.model.MessageResponseModel;
import com.happyride.eservice.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostService postService;

    private final GenericService genericService;

    private final UsersService usersService;

    private final ProfileService profileService;

    private final SubCategoryService subCategoryService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ChatService chatService;

    private final PostModelService postModelService;

    private final LikePostService likePostService;

    private final FavoritePostService favoritePostService;

    private final ServletContext servletContext;

    private final ReportService reportService;

    private final PhoneNumberVerificationTokenService phoneNumberVerificationTokenService;

    private final EmailVerificationTokenService emailVerificationTokenService;

    private final OtpSendCounterService otpSendCounterService;

    private final VerificationService verificationService;

    private final MailSendingService mailSendingService;

    private final NotificationsService notificationsService;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private static final String URL = "http://suqqatar.com";

    @Autowired
    public UserController(PostService postService, GenericService genericService, UsersService usersService, ProfileService profileService,
                          SubCategoryService subCategoryService, BCryptPasswordEncoder bCryptPasswordEncoder, ChatService chatService,
                          PostModelService postModelService, LikePostService likePostService, FavoritePostService favoritePostService,
                          ServletContext servletContext, ReportService reportService, PhoneNumberVerificationTokenService phoneNumberVerificationTokenService,
                          EmailVerificationTokenService emailVerificationTokenService, VerificationService verificationService, MailSendingService mailSendingService,
                          OtpSendCounterService otpSendCounterService, NotificationsService notificationsService) {
        this.postService = postService;
        this.genericService = genericService;
        this.usersService = usersService;
        this.profileService = profileService;
        this.subCategoryService = subCategoryService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.chatService = chatService;
        this.postModelService = postModelService;
        this.likePostService = likePostService;
        this.favoritePostService = favoritePostService;
        this.servletContext = servletContext;
        this.reportService = reportService;
        this.phoneNumberVerificationTokenService = phoneNumberVerificationTokenService;
        this.emailVerificationTokenService = emailVerificationTokenService;
        this.verificationService = verificationService;
        this.mailSendingService = mailSendingService;
        this.otpSendCounterService = otpSendCounterService;
        this.notificationsService = notificationsService;
    }

    @GetMapping
    public String getUserDashBoard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Optional<Users> users = usersService.findByEmail(authentication.getName());

            if (users.isPresent()) {
                Users users1 = users.get();
                boolean mailVerified = users1.getVerification().isMailVerified();
                boolean phoneNumberVerified = users1.getVerification().isPhoneNumberVerified();

                if (!mailVerified && !phoneNumberVerified) {
                    if (isNumeric(users1.getEmail())) {
                        return "redirect:/user/verifyPhoneNumber";
                    } else {
                        return "redirect:/user/verifyEmail";
                    }
                }

                model.addAttribute("users", users1);
            }
        }
        return "user_dashboard";
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    @GetMapping("/postNewAds")
    public ModelAndView postNewAds() {
        ModelAndView modelAndView = new ModelAndView("postad_category");
        return modelAndView;
    }

    @GetMapping("/createPost/{subcategoryId}")
    public String createPost(@PathVariable("subcategoryId") long subcategoryId, Principal principal, Model model) {
        model.addAttribute("post", new Post());
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);
        if (subCategory.isPresent()) {
            Class modelClass = subCategory.get().getPostModel().getModelClass();
            if (modelClass.isInstance(new Generic())) {
                model.addAttribute("postModel", new Generic());
                return "post-general";
            } else if (modelClass.isInstance(new Laptop())) {
                model.addAttribute("postModel", new Laptop());
                return "post-lap";
            }
        }

        return null;
    }

    @GetMapping("/guest/singlePrduct/view/{id}")
    public String showProductDetails(@PathVariable("id") Long id) {
        return "redirect:/userPost/singlePrduct/view/" + id;
    }

    @GetMapping("/masterPost/{subcategoryId}")
    public String masterPost(@PathVariable("subcategoryId") Long subcategoryId, Principal principal, Model model) {
        model.addAttribute("post", new Post());
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);
        if (subCategory.isPresent()) {
            Class modelClass = subCategory.get().getPostModel().getModelClass();
            if (modelClass.isInstance(new Generic())) {
                model.addAttribute("postModel", new Generic());
                return "post-master";
            } else if (modelClass.isInstance(new Laptop())) {
                model.addAttribute("postModel", new Laptop());
                return "post-lap";
            }
        }

        return null;
    }

    @GetMapping("/getAllPost")
    public @ResponseBody
    Page<Post> getAllPost(Principal principal,
                          @RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        return postService.findPostByPostedBy(usersService.findByEmail(principal.getName()).get(), PageRequest.of(page.orElse(0), size.orElse(20)));
    }


    @GetMapping("/userprofile")
    public String getUserDeshBoard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Optional<Users> users = usersService.findByEmail(authentication.getName());
            users.ifPresent(users1 -> model.addAttribute("users", users1));
        }
        return "user_dashboard";
    }

    @PostMapping("/changePassword")
    public @ResponseBody
    String changePassword(@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
        if ((currentPassword.length() > 5 || newPassword.length() > 5 || confirmPassword.length() > 5) && newPassword.equals(confirmPassword)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Optional<Users> users = usersService.findByEmail(authentication.getName());
                if (users.isPresent()) {
                    boolean result = bCryptPasswordEncoder.matches(currentPassword, users.get().getPassword());
                    if (result) {
                        Users users1 = users.get();
                        users1.setPassword(newPassword);
                        users1.setConfirmPassword(confirmPassword);
                        usersService.saveUser(users1);
                        return "success";
                    }
                }
            }
        }
        return "failed";
    }


    @GetMapping("/getCondition")
    public @ResponseBody
    List<Condition> getCondition() {
        List<Condition> conditionList = new ArrayList<>();
        conditionList.add(Condition.New);
        conditionList.add(Condition.Used);
        conditionList.add(Condition.Others);

        return conditionList;
    }

    //--------------Here I am going to do - Change Edit profile function------------//

    @PostMapping("/edituser/save")
    public String editUser(/*@ModelAttribute("users") @Validated Users users,
                           BindingResult profileBindingResult*/ @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {

        logger.info(firstName + "  " + lastName);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> users = usersService.findByEmail(auth.getName());
                    /*Profile profile = users.get().getProfile();
                    profile.setFirstName(firstName);
                    profile.setLastName(lastName);*/

        if (users.isPresent()) {
            Users users1 = users.get();
            users1.setConfirmPassword(users1.getPassword());
            users1.getProfile().setFirstName(firstName);
            users1.getProfile().setLastName(lastName);
            usersService.updateUser(users1);
        }

        /*profileService.saveProfile(profile);*/
        return "success";
    }

    //-------------------*********Changes of profile picture*********------------//
    @PostMapping("/change/images")
    public @ResponseBody
    Map<String, String> changeProfileImages(@RequestParam("profileImage") MultipartFile profileImage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> users = usersService.findByEmail(authentication.getName());

        Map<String, String> map = new HashMap<>();

        String IMAGE_FOLDER = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files/images/profileImage";
        File file = new File(IMAGE_FOLDER + "/" + users.get().getId() + ".png");
        System.out.println(IMAGE_FOLDER);
        if (profileImage != null) {
            try {
                profileImage.transferTo(file);

                map.put("response", "Success");
                return map;

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Profile image saving failed", e);
            }
        }
        map.put("response", "Failed");
        return map;
    }

    //----_______-----_____****This metod for chating****_____-----_____-----//


    @PostMapping("/chat/save")
    public @ResponseBody
    List<Chat> saveChat(@RequestParam("message") String messages, @RequestParam("sendTo") Long receiverId) {
        Optional<Users> users = usersService.findUserById(receiverId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> users1 = usersService.findByEmail(auth.getName());
        List<Chat> listOfChat = null;
        if (users.isPresent() && users1.isPresent()) {
            listOfChat = chatService.findAllMessagesBySenderAndReceiver(users.get(), users1.get(), users.get(), users1.get());
        }
        if (users.isPresent() && users1.isPresent()) {
            listOfChat = chatService.findUnseenMessages(users.get(), false, users1.get());
        }
        Chat chat = new Chat(messages, users.get(), users1.get(), true, false, LocalDateTime.now());
        chatService.saveChat(chat);
        listOfChat.add(chat);
        logger.info("Successfull");

        return listOfChat;
    }

    @PostMapping("/chat/seen")
    public @ResponseBody
    String seenChat(@RequestParam("sendTo") Long receiverId) {
        Optional<Users> users = usersService.findUserById(receiverId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> users1 = usersService.findByEmail(auth.getName());
        List<Chat> listOfChat = null;
        if (users.isPresent() && users1.isPresent()) {
            listOfChat = chatService.findAllMessagesBySenderAndReceiverSeen(users.get(), users1.get(), false);
            if (listOfChat.size() > 0) {
                for (Chat chat : listOfChat) {
                    /*chat = new Chat(chat.getMessage(), chat.getSendTo(), chat.getSendFrom(), chat.isSend(), true, chat.getTime());*/
                    chat.setSeen(true);
                    chatService.saveChat(chat);
                }

            }
        }

        //   Chat chat = new Chat(messages, users.get(), users1.get(), true, false, LocalDateTime.now());


        logger.info("Successfull");

        return "ok";
    }

    @GetMapping("/chatview")
    public ModelAndView chatView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> sender = usersService.findByEmail(auth.getName());
        List<Chat> listOfChat = null;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sender", sender.get());
        modelAndView.addObject("listOfChat", listOfChat);
        modelAndView.addObject("users", sender.get());
        /*List<Long> listOfReceiver = chatService.findFriend(sender.get(), sender.get());
        List<Users> listOfFriend = new ArrayList<>();
        for (Long k : listOfReceiver) {
            listOfFriend.add(usersService.findUserById(k).get());
        }
        modelAndView.addObject("listOfFriend", listOfFriend);
        modelAndView.setViewName("chat_view");
        if (listOfFriend.size() > 0) {
            for (Users chat : listOfFriend) {
                logger.info("From: " + chat.getEmail() + " To: " + chat.getEmail());
            }

        }*/
        List<Chat> chatForUser = chatService.findFriend(sender.get(), sender.get());
        List<String> email = new ArrayList<>();

        if (chatForUser.size() > 0) {
            for (Chat chat : chatForUser) {
                if (chat.getSendTo().getEmail() != sender.get().getEmail())
                    email.add(chat.getSendTo().getEmail());
                if (chat.getSendFrom().getEmail() != sender.get().getEmail())
                    email.add(chat.getSendFrom().getEmail());
            }

        }
        Set<String> listOfEmail = new HashSet<>(email);
        email.clear();
        Map<String, Users> listOfChatedUser = new HashMap<>();
        for (String email1 : listOfEmail) {
            String k = new String();
            Users users = new Users();
            try {

                users = usersService.findByEmail(email1).get();
                k = profileService.findByUsers(users).get().getFirstName();

            } catch (Exception e) {

            }
            listOfChatedUser.put(k, users);
        }

        modelAndView.addObject("listOfChatedUser", listOfChatedUser);
        modelAndView.setViewName("chat_view");
        return modelAndView;
    }

    //-------------Chating With Specific id-----------//

    @GetMapping("/chatview/{id}")
    public ModelAndView chatViewId(@PathVariable("id") Long receiverId) {
        Long id = receiverId;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> sender = usersService.findByEmail(auth.getName());
        Optional<Users> receiver = usersService.findUserById(id);
        logger.info(sender.get().getId() + " Receiver: " + receiver.get().getId());
        List<Chat> listOfChat = null;
        listOfChat = chatService.findAllMessagesBySenderAndReceiver(receiver.get(), sender.get(), receiver.get(), sender.get());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sender", sender.get());
        modelAndView.addObject("receiver", receiver.get());
        modelAndView.addObject("listOfChat", listOfChat);
        modelAndView.addObject("users", sender.get());
        List<Chat> chatForUser = chatService.findAllChatForUser(sender.get(), sender.get(), sender.get());

        modelAndView.addObject("chatForUser", chatForUser);
        modelAndView.setViewName("chat_view");

        return modelAndView;
    }

    //-----------------chat  between it support and guest user---------------//

    @GetMapping("/chatview/guest")
    public ModelAndView chatViewGuest() {
        Long id = Long.valueOf(3);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> sender = usersService.findByEmail(auth.getName());
        Optional<Users> receiver = usersService.findUserById(id);
        logger.info(sender.get().getId() + " Receiver: " + receiver.get().getId());
        List<Chat> listOfChat = null;
        chatService.findAllMessagesBySenderAndReceiver(receiver.get(), sender.get(), receiver.get(), sender.get());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sender", sender.get());
        modelAndView.addObject("receiver", receiver.get());
        modelAndView.addObject("listOfChat", listOfChat);
        modelAndView.addObject("users", sender.get());
        List<Chat> listOfReceiver = chatService.findFriend(sender.get(), sender.get());
        List<Users> listOfFriend = new ArrayList<>();
       /* for (Chat integer : listOfReceiver) {
            listOfFriend.add(usersService.findUserById(integer).get());
        }*/
        modelAndView.addObject("listOfFriend", listOfFriend);
        modelAndView.setViewName("chat_view");
        if (listOfFriend.size() > 0) {
            for (Users chat : listOfFriend) {
                logger.info("From: " + chat.getEmail() + " To: " + chat.getEmail());
            }

        }
        return modelAndView;
    }

  /*  @PostMapping("/categories")
    public String categories(){
        return "categories ";
    }*/

    /************Liked by register user***********/

    @PostMapping("/post/likeUnlike")
    public @ResponseBody
    String checkliked(@RequestParam("postId") Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.isAuthenticated()) {
            return "failed";
        } else {
            Optional<Users> users = usersService.findByEmail(auth.getName());
            Optional<Post> post = postService.findPostById(postId);
            Optional<LikePost> likePost = likePostService.checkLike(users.get(), post.get());
            if (likePost.isPresent()) {
                if (likePost.get().isLikes()) {
                    likePost.get().setLikes(false);
                    likePostService.saveLike(likePost.get());
                    return "false";
                } else {
                    likePost.get().setLikes(true);
                    likePostService.saveLike(likePost.get());
                    return "true";
                }
            } else {
                LikePost likePost1 = new LikePost();
                likePost1.setLikedBy(usersService.findByEmail(auth.getName()).get());
                likePost1.setLikes(true);
                likePost1.setPost(postService.findPostById(postId).get());
                likePostService.saveLike(likePost1);
                return "true";
            }
        }
    }

    @PostMapping("/post/favorite")
    public @ResponseBody
    String checkFavorite(@RequestParam("postId") Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.isAuthenticated()) {
            return "/login";
        } else {
            Optional<Users> users = usersService.findByEmail(auth.getName());
            Optional<Post> post = postService.findPostById(postId);
            Optional<FavoritePost> favoritePost = favoritePostService.checkFavorite(users.get(), post.get());
            if (favoritePost.isPresent()) {
                if (favoritePost.get().getFavorite()) {
                    favoritePost.get().setFavorite(false);
                    favoritePostService.saveFavorite(favoritePost.get());
                    return "false";
                } else {
                    favoritePost.get().setFavorite(true);
                    favoritePostService.saveFavorite(favoritePost.get());
                    return "true";
                }
            } else {
                FavoritePost favoritePost1 = new FavoritePost();
                favoritePost1.setFavoriteBy(usersService.findByEmail(auth.getName()).get());
                favoritePost1.setFavorite(true);
                favoritePost1.setPost(postService.findPostById(postId).get());
                favoritePostService.saveFavorite(favoritePost1);
                return "true";
            }
        }

    }

    @PostMapping("/checkAuthentication")
    public @ResponseBody
    Users checkAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().isEmpty())
            return null;
        else
            return usersService.findByEmail(authentication.getName()).get();

    }

    @PostMapping("/getuserForImage")
    public @ResponseBody
    Map<String, Object> getUserImagefdsf(@RequestParam("name") String name) {
        Map<String, Object> map = new HashMap<>();
        Users user = usersService.findByEmail(name).orElse(null);
        map.put("user", user);
        map.put("userProfile", user.getProfile());

        return map;

    }

    @PostMapping("/messagesBox/listMessage")
    public @ResponseBody
    List<Chat> listChatForCharBox(@RequestParam("postId") Long postId) {
        Users receiver = postService.findPostById(postId).get().getPostedBy();
        Users sender = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        List<Chat> listOfChat = chatService.findAllMessagesBySenderAndReceiver(receiver, sender, receiver, sender);
        return listOfChat;
    }

    @PostMapping("/messagesBox/listMessageByUserId")
    public @ResponseBody
    List<Chat> listChatForCharBoxByUserId(@RequestParam("userId") Long userId) {
        Users receiver = usersService.findUserById(userId).get();
        Users sender = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        List<Chat> listOfChat = chatService.findAllMessagesBySenderAndReceiver(receiver, sender, receiver, sender);
        return listOfChat;
    }

    @PostMapping("/save/report")
    public @ResponseBody
    String saveUserReportOfPost(@RequestParam("postId") Long postId, @RequestParam("reportText") String reportBody) {
        Users auth = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        if (auth == null) {
            return "false";
        }
        Post post = postService.findPostById(postId).get();

        PostReport report1 = new PostReport();
        List<PostReport> postReports = new ArrayList<>();
        // postReports.add(r)
        report1.setDescription(reportBody);
        report1.setPost(post);
        postReports.add(report1);
        //  report1.getPost().setPostReportList(postReports);
        report1.setCreatAt(LocalDateTime.now());
        report1.setReportBy(auth);
        report1.setReportStatus(false);
        reportService.saveReport(report1);
        return "success";
    }

    @PostMapping("/postDelete/delete")
    public @ResponseBody
    Map<String, String> deletePostByUsers(@RequestParam("postId") Long postId) {
        Post post = postService.findPostById(postId).get();
        Map<String, String> map = new HashMap<>();

        PostStatus postStatus = new PostStatus();
        if (post.getPostStatus() == null) {
            postStatus.setActive(true);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
            postService.savePost(post);

            map.put("response", "success");
            return map;
        }

        postStatus = post.getPostStatus();
        postStatus.setActive(true);
        post.setPostStatus(postStatus);
        postService.savePost(post);
        map.put("response", "success");
        return map;
    }

    @PostMapping("/postSold/sold")
    public @ResponseBody
    Map<String, String> soldPostByUsers(@RequestParam("postId") Long postId) {
        Post post = postService.findPostById(postId).get();
        Map<String, String> map = new HashMap<>();

        PostStatus postStatus = new PostStatus();
        if (post.getPostStatus() == null) {
            postStatus.setSold(true);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
            postService.savePost(post);

            map.put("response", "success");
            return map;
        }

        postStatus = post.getPostStatus();
        postStatus.setSold(true);
        post.setPostStatus(postStatus);
        postService.savePost(post);
        map.put("response", "success");
        return map;

    }

    @PostMapping("/chatview/listMessage")
    public @ResponseBody
    List<Chat> individiualChatList(@RequestParam("id") Long receverId) {
        Users sender = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        Users receiver = usersService.findUserById(receverId).get();
        List<Chat> listOfChat = chatService.findAllMessagesBySenderAndReceiver(receiver, sender, receiver, sender);
        return listOfChat;
    }

    @PostMapping("/chatviewUserName/userName")
    public @ResponseBody
    String individiualName(@RequestParam("id") Long receverId) {

        String receiver = usersService.findUserById(receverId).get().getProfile().getFirstName();

        return receiver;
    }


    @GetMapping("/profile/{id}")
    public String profileXXXX(@PathVariable("id") Long id) {

        return "redirect:/user/chatPf/" + id;
    }

    @GetMapping("/chatPf/{id}")
    public ModelAndView profileFind(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Users users = usersService.findUserById(id).get();
        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @GetMapping("/verifyPhoneNumber")
    public ModelAndView verifyPhoneNumber() {
        return new ModelAndView("otp_verification");
    }

    @PostMapping("/verifyPhoneNumberOTP")
    public @ResponseBody
    boolean verifyPhoneNumberOTP(@RequestParam("otpCode") String otpCode) {
        Optional<Users> users = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (users.isPresent()) {
            Optional<PhoneNumberVerificationToken> phoneNumberVerificationToken = phoneNumberVerificationTokenService.findByUsers(users.get());
            if (phoneNumberVerificationToken.isPresent()) {
                if (phoneNumberVerificationToken.get().getToken().equals(otpCode)) {
                    if (ChronoUnit.HOURS.between(phoneNumberVerificationToken.get().getExpireTime(), LocalDateTime.now()) > 3) {
                    } else {
                        Verification verification = users.get().getVerification();
                        verification.setPhoneNumberVerified(true);
                        verificationService.saveVerification(verification);
                        notificationsService.saveNotifications(new Notifications("#", usersService.findUserById(0L).get(), users.get(), "Thanks for verifying your phone number.", false, LocalDateTime.now(), false));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @GetMapping("/sendMobileOTPAgain")
    public @ResponseBody
    String sendMobileOTPAgain() {

        Optional<Users> users = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (users.isPresent()) {

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
                        return "You have exceed your otp limit try again later";
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

            String token = generateRandomChars(
                    "1234567890", 6);

            MessageResponseModel messageResponseModel = null;
            try {
                messageResponseModel = sendOTP(token, users.get().getEmail());
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert messageResponseModel != null;
            PhoneNumberVerificationToken phoneNumberVerificationToken = new PhoneNumberVerificationToken();
            Optional<PhoneNumberVerificationToken> phoneNumberVerificationToken1 = phoneNumberVerificationTokenService.findByUsers(users.get());
            phoneNumberVerificationToken1.ifPresent(phoneNumberVerificationToken2 -> phoneNumberVerificationToken.setId(phoneNumberVerificationToken2.getId()));
            phoneNumberVerificationToken.setMessageId(messageResponseModel.getMessage_id());
            phoneNumberVerificationToken.setToken(token);
            phoneNumberVerificationToken.setUsers(users.get());
            phoneNumberVerificationToken.setCreateTime(LocalDateTime.now());
            phoneNumberVerificationToken.setExpireTime(LocalDateTime.now().plusHours(3));
            phoneNumberVerificationTokenService.savePhoneNumberVerificationToken(phoneNumberVerificationToken);

        }

        return null;

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


    @GetMapping("/verifyEmail")
    public ModelAndView verifyEmail() {
        return new ModelAndView("otp_verification_email");
    }

    @PostMapping("/verifyEmailOTP")
    public @ResponseBody
    boolean verifyEmailOTP(@RequestParam("otpCode") String otpCode) {
        Optional<Users> users = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (users.isPresent()) {
            Optional<EmailVerificationToken> emailVerificationToken = emailVerificationTokenService.findByUsers(users.get());
            if (emailVerificationToken.isPresent()) {
                if (emailVerificationToken.get().getToken().equals(otpCode)) {
                    if (ChronoUnit.HOURS.between(emailVerificationToken.get().getExpireTime(), LocalDateTime.now()) > 72) {
                    } else {
                        Verification verification = users.get().getVerification();
                        verification.setMailVerified(true);
                        verificationService.saveVerification(verification);
                        notificationsService.saveNotifications(new Notifications("#", usersService.findUserById(0L).get(), users.get(), "Thanks for verifying your email.", false, LocalDateTime.now(), false));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @GetMapping("/sendEmailOTPAgain")
    private @ResponseBody
    String emailVerificationLinkSendAgain() {
        Optional<Users> users = usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (users.isPresent()) {

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
                        return "You have exceed your otp limit try again later";
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

            String token = generateRandomChars(
                    "1234567890", 6);

            EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
            Optional<EmailVerificationToken> emailVerificationToken1 = emailVerificationTokenService.findByUsers(users.get());
            emailVerificationToken1.ifPresent(emailVerificationToken2 -> emailVerificationToken.setId(emailVerificationToken2.getId()));
            emailVerificationToken.setUsers(users.get());
            emailVerificationToken.setToken(token);
            emailVerificationToken.setCreateTime(LocalDateTime.now());
            emailVerificationToken.setExpireTime(LocalDateTime.now().plusHours(72));

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(users.get().getEmail());
            message.setSubject("Email Verification");
            message.setText("Verify your email by clicking this link: " + URL + "/emailVerificationRequest?token=" + token + "\n" + "Your Security Code is: " + token);

            new Thread(() -> mailSendingService.sendMail(message)).start();
            emailVerificationTokenService.saveEmailVerificationToken(emailVerificationToken);
        }

        return null;
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

    // In this section User can delete his post

}
