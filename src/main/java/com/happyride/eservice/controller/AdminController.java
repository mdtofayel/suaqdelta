package com.happyride.eservice.controller;

import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("admin/admin_index");
        return modelAndView;
    }

    @PostMapping("/addUser")
    public @ResponseBody
    Map<String, String> addUser(@RequestParam("role") String[] role, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
        Map<String, String> responseMap = new HashMap<>();
        if (username.length() < 2) {
            responseMap.put("response", "Username length in not ok.");
            return responseMap;
        }
        if (!password.equals(confirmPassword)) {
            responseMap.put("response", "Password not match.");
            return responseMap;
        }
        if (password.length() < 6) {
            responseMap.put("response", "Password length must be getter than 5.");
            return responseMap;
        }
        if (role.length < 1) {
            responseMap.put("response", "Invalid role");
            return responseMap;
        }

        Optional<Users> optionalUsers = usersService.findByEmail(username);

        if (optionalUsers.isPresent()) {
            responseMap.put("response", "User already exist");
            return responseMap;
        }

        Set<Role> roles1 = new HashSet<>();

        for (String s : role) {
            Optional<Role> role1 = roleService.getRoleByRole(s);
            role1.ifPresent(roles1::add);
        }

        if (roles1.size() > 0) {
            Users users = new Users();
            users.setEmail(username);
            users.setPassword(password);
            users.setConfirmPassword(confirmPassword);
            users.setRoles(roles1);
            users.setActive(1);
            users.setCreatedDate(LocalDateTime.now());

            usersService.saveUser(users);

            responseMap.put("response", "Success");
            return responseMap;

        } else {
            responseMap.put("response", "Invalid role");
            return responseMap;
        }
    }

    @PostMapping("/editUser")
    public @ResponseBody
    Map<String, String> editUser(@RequestParam("userId") Long userId, @RequestParam("role") String[] role, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("active") int active) {
        Map<String, String> responseMap = new HashMap<>();
        if (username.length() < 2) {
            responseMap.put("response", "Username length in not ok.");
            return responseMap;
        }
        if (!password.isEmpty() || !confirmPassword.isEmpty()) {
            if (!password.equals(confirmPassword)) {
                responseMap.put("response", "Password not match.");
                return responseMap;
            }
            if (password.length() < 6) {
                responseMap.put("response", "Password length must be getter than 5.");
                return responseMap;
            }
        }
        if (role.length < 1) {
            responseMap.put("response", "Invalid role");
            return responseMap;
        }

        Optional<Users> optionalUsers1 = usersService.findUserById(userId);

        if (!optionalUsers1.isPresent()) {
            responseMap.put("response", "Invalid Request");
            return responseMap;
        }

        Optional<Users> optionalUsers = usersService.findByEmail(username);

        if (optionalUsers.isPresent() && !optionalUsers.equals(optionalUsers1)) {
            responseMap.put("response", "User already exist");
            return responseMap;
        }

        Set<Role> roles1 = new HashSet<>();

        for (String s : role) {
            Optional<Role> role1 = roleService.getRoleByRole(s);
            role1.ifPresent(roles1::add);
        }

        if (roles1.size() > 0) {
            Users users = optionalUsers1.get();
            users.setEmail(username);
            users.setConfirmPassword(optionalUsers1.get().getPassword());
            users.setRoles(roles1);
            users.setActive(active);
            users.setUpdatedDate(LocalDateTime.now());
            if (password.length() > 5) {
                users.setPassword(password);
                users.setConfirmPassword(confirmPassword);

                usersService.saveUser(users);

                responseMap.put("response", "Success");
                return responseMap;
            }
            usersService.updateUser(users);

            responseMap.put("response", "Success");
            return responseMap;

        } else {
            responseMap.put("response", "Invalid role");
            return responseMap;
        }
    }

    @GetMapping("/viewAllUsers")
    public ModelAndView viewUser() {
        ModelAndView modelAndView = new ModelAndView("admin/user_management");
        return modelAndView;
    }

    @GetMapping("/viewRestUsers")
    public @ResponseBody
    List<Users> viewRestUser() {
        return usersService.getUserList();
    }

    @GetMapping("/getUserById/{id}")
    public @ResponseBody
    Users getUserById(@PathVariable("id") Long id) {
        Optional<Users> optionalUsers = usersService.findUserById(id);
        return optionalUsers.orElse(null);
    }


    //------------********Access of admin to view user********-------------

    @GetMapping("/viewclientuser/{id}")
    public String singleUserView(Model model,
                                 @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> a = usersService.findByEmail(auth.getName());

        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));


        return "";
    }


    // ------------**********Access of admin to Edit users**********---------------

    @GetMapping("/edit/clientuser/{id}")
    public String getuserEditPage(Model model,
                                  @PathVariable("id") Long id) {
        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));
        return " ";
    }

    @PostMapping("/save/edited/user")
    public String saveEditedUser(@ModelAttribute("user") @Validated Users users,
                                 BindingResult userBindResult,
                                 Model model) {
        if (userBindResult.hasErrors()) {
            return " ";
        }
        users.setUpdatedDate(LocalDateTime.now());
        usersService.saveUser(users);

        return " ";
    }

    @GetMapping("/edit/clientProfile/{id}")
    public String getuserProfileEditPage(Model model,
                                         @PathVariable("id") Long id) {
        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));
        return " ";
    }

    @PostMapping("/save/edited/userprofile")
    public String saveEditedUserProfile(@ModelAttribute("profile") @Validated Profile profile,
                                        BindingResult profileBindResult,
                                        Model model) {
        if (profileBindResult.hasErrors()) {
            return " ";
        }

        profile.setUpdatedDate(LocalDateTime.now());
        profileService.saveProfile(profile);

        return " ";
    }

    /*------------disable userProfile by manager----------*/

    @GetMapping("/disable/userProfilebyadmin/{id}")
    public @ResponseBody
    String disableProfileUser(Model model,
                              @PathVariable("id") Long id) {
        Optional<Users> users = usersService.findUserById(id);
        Profile profile = users.get().getProfile();
        profile.setStatus(false);
        profile.setUpdatedDate(LocalDateTime.now());

        profileService.saveProfile(profile);

        List<Users> listOfuser = usersService.getUserList();
        model.addAttribute("listOfUser", listOfuser);
        return listOfuser.toString();

    }

    //----------Disable user By admin--------------//

    @GetMapping("/disableuserByadmin/{id}")
    public @ResponseBody
    String disbleUserByAdmin(@PathVariable("id") Long id, Model model) {

        Optional<Users> users = usersService.findUserById(id);
        users.get().setActive(0);
        users.get().setUpdatedDate(LocalDateTime.now());
        List<Users> usersList = usersService.getUserList();
        model.addAttribute("listOfUser", usersList);
        return usersList.toString();
    }

    // --------// To Change manager Profile info and user information---------------------//

    //------------******** Access of admin to view manager information ********-------------

    @GetMapping("/managerview/{id}")
    public String singleManagerView(Model model,
                                    @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> a = usersService.findByEmail(auth.getName());

        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));

        return "";
    }

    // ------------**********Access of admin to Edit manger**********---------------

    @GetMapping("/manager/edit/{id}")
    public String getManagerEditPage(Model model,
                                     @PathVariable("id") Long id) {
        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));
        return " ";
    }

    @PostMapping("/manager/edited/save")
    public String saveEditedmanager(@ModelAttribute("user") @Validated Users users,
                                    BindingResult userBindResult,
                                    Model model) {
        if (userBindResult.hasErrors()) {
            return " ";
        }
        users.setUpdatedDate(LocalDateTime.now());
        usersService.saveUser(users);

        return " ";
    }

    @GetMapping("/managerProfile/edit/{id}")
    public String getManagerProfileEditPage(Model model,
                                            @PathVariable("id") Long id) {
        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));
        return " ";
    }

    @PostMapping("/managerprofile/save/edited")
    public String saveEditedManagerProfile(@ModelAttribute("profile") @Validated Profile profile,
                                           BindingResult profileBindResult,
                                           Model model) {
        if (profileBindResult.hasErrors()) {
            return " ";
        }

        profile.setUpdatedDate(LocalDateTime.now());
        profileService.saveProfile(profile);

        return " ";
    }

    //-----------view admin profile--------

    @GetMapping("/adminView/{id}")
    public String singleAdminView(Model model,
                                  @PathVariable("id") Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> a = usersService.findByEmail(auth.getName());

        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));

        return "";
    }

    // ------------**********Access of admin to Edit manger**********---------------

    @GetMapping("/admin/edit/{id}")
    public String getAdminEditPage(Model model,
                                   @PathVariable("id") Long id) {
        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));
        return " ";
    }

    @PostMapping("/admin/edited/save")
    public String saveEditedAdmin(@ModelAttribute("user") @Validated Users users,
                                  BindingResult userBindResult,
                                  Model model) {
        if (userBindResult.hasErrors()) {
            return " ";
        }
        users.setUpdatedDate(LocalDateTime.now());
        usersService.saveUser(users);

        return " ";
    }

    @GetMapping("/adminprofile/edit/{id}")
    public String getadminProfileEditPage(Model model,
                                          @PathVariable("id") Long id) {
        Optional<Users> usersInfo = usersService.findUserById(id);
        usersInfo.ifPresent(users -> model.addAttribute("users", users));
        return " ";
    }

    @PostMapping("/adminprofile/edited/save")
    public String saveEditedAdminProfile(@ModelAttribute("profile") @Validated Profile profile,
                                         BindingResult profileBindResult,
                                         Model model) {
        if (profileBindResult.hasErrors()) {
            return " ";
        }

        profile.setUpdatedDate(LocalDateTime.now());
        profileService.saveProfile(profile);

        return " ";
    }
    //-----------********Comments approbation*********--------------//

    @PostMapping("/approvecomment")
    public String approvedComment(@RequestParam("commentId") Long commentId) {
        Optional<Comments> comments = commentService.findComment(commentId);
        comments.ifPresent(comments1 -> {
            comments1.setStatus(false);
            commentService.saveComments(comments1);
        });
        return "";
    }

    /*@GetMapping("/allAds")
    public @ResponseBody
    List<Post> allPost() {
        return postService.getAllPost();
    }*/

    @GetMapping("/allAds")
    public ModelAndView allPost() {
        List<Post> listOfAllAdds = postService.getAllPost();
        List<Post> listListOfPendingAds = postService.getAllAccordingStatus("PENDING");
        List<Post> listListOfApprovedAds = postService.getAllAccordingStatus("APPROVED");
        List<Post> listListOfRejectedAds = postService.getAllAccordingStatus("REJECTED");
        List<Post> listListOfSoldAds = postService.getAllAccordingStatus("SOLD");
        List<Post> listListOfBlockedAdds = postService.getAllAccordingStatus("BLOCKED");

        ModelAndView modelAndView = new ModelAndView("admin/admin_all_ads");
        modelAndView.addObject("listOfAllAdds", listOfAllAdds);
        modelAndView.addObject("listListOfPendingAds", listListOfPendingAds);
        modelAndView.addObject("listListOfApprovedAds", listListOfApprovedAds);
        modelAndView.addObject("listListOfRejectedAds", listListOfRejectedAds);
        modelAndView.addObject("listListOfSoldAds", listListOfSoldAds);
        modelAndView.addObject("listListOfBlockedAdds", listListOfBlockedAdds);

        return modelAndView;
    }

    @GetMapping("/allJobAds")
    public ModelAndView allJobPost() {
        List<Post> listOfAllAdds = postService.getAllPost();
        List<Post> listListOfPendingAds = postService.getAllAccordingStatus("PENDING");
        List<Post> listListOfApprovedAds = postService.getAllAccordingStatus("APPROVED");
        List<Post> listListOfRejectedAds = postService.getAllAccordingStatus("REJECTED");
        List<Post> listListOfSoldAds = postService.getAllAccordingStatus("SOLD");
        List<Post> listListOfBlockedAdds = postService.getAllAccordingStatus("BLOCKED");

        ModelAndView modelAndView = new ModelAndView("admin/admin_job_Ads");
        modelAndView.addObject("listOfAllAdds", listOfAllAdds);
        modelAndView.addObject("listListOfPendingAds", listListOfPendingAds);
        modelAndView.addObject("listListOfApprovedAds", listListOfApprovedAds);
        modelAndView.addObject("listListOfRejectedAds", listListOfRejectedAds);
        modelAndView.addObject("listListOfSoldAds", listListOfSoldAds);
        modelAndView.addObject("listListOfBlockedAdds", listListOfBlockedAdds);

        return modelAndView;
    }

    @GetMapping("/allSaleAds")
    public ModelAndView allSaleAds() {
        List<Post> listOfAllAdds = postService.getAllPost();
        List<Post> listListOfPendingAds = postService.getAllAccordingStatus("PENDING");
        List<Post> listListOfApprovedAds = postService.getAllAccordingStatus("APPROVED");
        List<Post> listListOfRejectedAds = postService.getAllAccordingStatus("REJECTED");
        List<Post> listListOfSoldAds = postService.getAllAccordingStatus("SOLD");
        List<Post> listListOfBlockedAdds = postService.getAllAccordingStatus("BLOCKED");

        ModelAndView modelAndView = new ModelAndView("admin/admin_sale_ads");
        modelAndView.addObject("listOfAllAdds", listOfAllAdds);
        modelAndView.addObject("listListOfPendingAds", listListOfPendingAds);
        modelAndView.addObject("listListOfApprovedAds", listListOfApprovedAds);
        modelAndView.addObject("listListOfRejectedAds", listListOfRejectedAds);
        modelAndView.addObject("listListOfSoldAds", listListOfSoldAds);
        modelAndView.addObject("listListOfBlockedAdds", listListOfBlockedAdds);

        return modelAndView;
    }

    @GetMapping("/allRentAds")
    public ModelAndView allRentAds() {
        List<Post> listOfAllAdds = postService.getAllPost();
        List<Post> listListOfPendingAds = postService.getAllAccordingStatus("PENDING");
        List<Post> listListOfApprovedAds = postService.getAllAccordingStatus("APPROVED");
        List<Post> listListOfRejectedAds = postService.getAllAccordingStatus("REJECTED");
        List<Post> listListOfSoldAds = postService.getAllAccordingStatus("SOLD");
        List<Post> listListOfBlockedAdds = postService.getAllAccordingStatus("BLOCKED");

        ModelAndView modelAndView = new ModelAndView("admin/admin_rent_ads");
        modelAndView.addObject("listOfAllAdds", listOfAllAdds);
        modelAndView.addObject("listListOfPendingAds", listListOfPendingAds);
        modelAndView.addObject("listListOfApprovedAds", listListOfApprovedAds);
        modelAndView.addObject("listListOfRejectedAds", listListOfRejectedAds);
        modelAndView.addObject("listListOfSoldAds", listListOfSoldAds);
        modelAndView.addObject("listListOfBlockedAdds", listListOfBlockedAdds);
        return modelAndView;
    }

    @GetMapping("/allOtherAds")
    public ModelAndView allOtherAds() {
        List<Post> listOfAllAdds = postService.getAllPost();
        List<Post> listListOfPendingAds = postService.getAllAccordingStatus("PENDING");
        List<Post> listListOfApprovedAds = postService.getAllAccordingStatus("APPROVED");
        List<Post> listListOfRejectedAds = postService.getAllAccordingStatus("REJECTED");
        List<Post> listListOfSoldAds = postService.getAllAccordingStatus("SOLD");
        List<Post> listListOfBlockedAdds = postService.getAllAccordingStatus("BLOCKED");

        ModelAndView modelAndView = new ModelAndView("admin/admin_other_ads");
        modelAndView.addObject("listOfAllAdds", listOfAllAdds);
        modelAndView.addObject("listListOfPendingAds", listListOfPendingAds);
        modelAndView.addObject("listListOfApprovedAds", listListOfApprovedAds);
        modelAndView.addObject("listListOfRejectedAds", listListOfRejectedAds);
        modelAndView.addObject("listListOfSoldAds", listListOfSoldAds);
        modelAndView.addObject("listListOfBlockedAdds", listListOfBlockedAdds);

        return modelAndView;
    }

    @PostMapping("/action/status/")
    public @ResponseBody
    String changeStatus(@RequestParam("actionIs") String actionIs, @RequestParam("pId") Long pId) {
        Optional<Post> post = postService.findPostById(pId);

        if (post.isPresent()) {
            Post post1 = post.get();
            PostApproved postApproved = post.get().getApproved();
            postApproved.setApproved(actionIs);
            postApproved.setApprovedBy(usersService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get());
            postApproved.setApprovedAt(LocalDateTime.now());
            post1.setApproved(postApproved);

            if (actionIs.equalsIgnoreCase("APPROVED")) {
                PostStatus postStatus = post.get().getPostStatus();
                postStatus.setActive(true);
                post1.setPostStatus(postStatus);
            }

            postService.savePost(post1);
        }

        return actionIs;
    }


    @PostMapping("/postByUser")
    public @ResponseBody
    Page<Post> allPost(@RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size) {
        return postService.getAllPost(PageRequest.of(page.orElse(0), size.orElse(20)));
    }

    @GetMapping("/getAllAds")
    public @ResponseBody
    Page<Post> getAllPost(@RequestParam("page") Optional<Integer> page,
                          @RequestParam("size") Optional<Integer> size) {
        return postService.getAllPost(PageRequest.of(page.orElse(0), size.orElse(20)));
    }
}
