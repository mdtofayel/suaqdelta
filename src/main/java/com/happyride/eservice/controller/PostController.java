package com.happyride.eservice.controller;

import com.happyride.eservice.configuration.GlobalUtils;
import com.happyride.eservice.entity.model.Properties;
import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.model.Condition;
import com.happyride.eservice.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

@Controller
@RequestMapping("/userPost")
public class PostController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PostService postService;

    @Autowired
    private GenericService genericService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ChatService chatService;

    @Autowired
    private PostModelService postModelService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostTypeService postTypeService;

    @Autowired
    private PostViewService postViewService;

    @Autowired
    private CommentService commentService;


    @Autowired
    private ServletContext servletContext;

    @Autowired
    private LikePostService likePostService;

    @Autowired
    private FavoritePostService favoritePostService;

    @GetMapping("/postNewAds")
    public ModelAndView postNewAds() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("post", new Post());

        modelAndView.addObject("generic", new Generic());

        modelAndView.addObject("hire", new Hire());

        modelAndView.addObject("postModel", new JobSeeker());


        modelAndView.addObject("laptop", new Laptop());


        modelAndView.addObject("petAnimal", new PetAnimal());


        modelAndView.addObject("properties", new Properties());

        modelAndView.addObject("smartPhones", new SmartPhones());


        modelAndView.addObject("vehicles", new Vehicles());


        modelAndView.addObject("villa", new Villa());

        modelAndView.addObject("jobSeeker", new JobSeeker());

        modelAndView.setViewName("post-master");
        return modelAndView;
    }
   /* @GetMapping("/editpostNewAds")
    public ModelAndView postNewAds1() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("post", new Post());

        modelAndView.addObject("generic", new Generic());

        modelAndView.addObject("hire", new Hire());

        modelAndView.addObject("postModel", new JobSeeker());


        modelAndView.addObject("laptop", new Laptop());


        modelAndView.addObject("petAnimal", new PetAnimal());


        modelAndView.addObject("properties", new Properties());

        modelAndView.addObject("smartPhones", new SmartPhones());


        modelAndView.addObject("vehicles", new Vehicles());


        modelAndView.addObject("villa", new Villa());

        modelAndView.addObject("jobSeeker", new JobSeeker());

        modelAndView.setViewName("edit_post_master");
        return modelAndView;
    }*/

    @GetMapping("/postEditAds/{postId}")
    public ModelAndView postEditAds(@PathVariable Long postId) {

        Optional<Post> isPost = postService.findPostById(postId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("post", isPost.get());

        if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new Generic())) {

            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("smartPhones", new SmartPhones());
            modelAndView.addObject("generic", isPost.get().getGeneric());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("villa", new Villa());
            modelAndView.addObject("jobSeeker", new JobSeeker());
            modelAndView.addObject("laptop", new Laptop());

        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new Hire())) {

            modelAndView.addObject("hire", isPost.get().getHire());
            modelAndView.addObject("laptop", new Laptop());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("jobSeeker", new JobSeeker());
            modelAndView.addObject("smartPhones", new SmartPhones());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("villa", new Villa());


        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new JobSeeker())) {

            modelAndView.addObject("jobSeeker", isPost.get().getJobSeeker());
            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("smartPhones", new SmartPhones());
            modelAndView.addObject("laptop", new Laptop());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("villa", new Villa());

        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new Laptop())) {

            modelAndView.addObject("laptop", isPost.get().getLaptop());
            modelAndView.addObject("jobSeeker", new JobSeeker());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("villa", new Villa());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("smartPhones", new SmartPhones());

        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new Villa())) {

            modelAndView.addObject("postModel", new Laptop());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("jobSeeker", new JobSeeker());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("villa", isPost.get().getVilla());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("smartPhones", new SmartPhones());
        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new Properties())) {

            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("properties", isPost.get().getProperties());
            modelAndView.addObject("postModel", new Laptop());
            modelAndView.addObject("smartPhones", new SmartPhones());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("jobSeeker", new JobSeeker());
            modelAndView.addObject("villa", new Villa());
        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new PetAnimal())) {

            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("smartPhones", new SmartPhones());
            modelAndView.addObject("laptop", new Laptop());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("petAnimal", isPost.get().getPetAnimal());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("villa", new Villa());
            modelAndView.addObject("jobSeeker", new JobSeeker());
        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new Vehicles())) {

            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("laptop", new Laptop());
            modelAndView.addObject("vehicles", isPost.get().getVehicles());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("smartPhones", new SmartPhones());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("villa", new Villa());
            modelAndView.addObject("jobSeeker", new JobSeeker());
        } else if (isPost.get().getSubCategory().getPostModel().getModelClass().isInstance(new SmartPhones())) {

            modelAndView.addObject("hire", new Hire());
            modelAndView.addObject("generic", new Generic());
            modelAndView.addObject("laptop", new Laptop());
            modelAndView.addObject("smartPhones", isPost.get().getSmartPhones());
            modelAndView.addObject("vehicles", new Vehicles());
            modelAndView.addObject("petAnimal", new PetAnimal());
            modelAndView.addObject("properties", new Properties());
            modelAndView.addObject("villa", new Villa());
            modelAndView.addObject("jobSeeker", new JobSeeker());
        }


        modelAndView.setViewName("edit_post_master");
        return modelAndView;
    }


    @PostMapping("/getSubcategories")
    public @ResponseBody
    List<SubCategory> getSubCategoriesList(@RequestParam("categoryId") Long id) {
        Optional<Category> category = categoryService.categoryById(id);
        List<SubCategory> listOfSubCategories = subCategoryService.findSubCategoriesByCategoryId(category.get());
        return listOfSubCategories;
    }

    /*@GetMapping("/createPost/{subcategoryId}")
    public String createPost(@PathVariable("subcategoryId") int subcategoryId, Model model) {
        model.addAttribute("post", new Post());
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);
        if (subCategory.isPresent()) {
            Class modelClass = subCategory.get().getPostModel().getModelClass();
            if (modelClass.isInstance(new Generic())) {
                model.addAttribute("postModel", new Generic());
            } else if (modelClass.isInstance(new Hire())) {
                model.addAttribute("postModel", new Hire());
            } else if (modelClass.isInstance(new JobSeeker())) {
                model.addAttribute("postModel", new JobSeeker());

            } else if (modelClass.isInstance(new Laptop())) {
                model.addAttribute("postModel", new Laptop());

            } else if (modelClass.isInstance(new PetAnimal())) {
                model.addAttribute("postModel", new PetAnimal());

            } else if (modelClass.isInstance(new Properties())) {
                model.addAttribute("postModel", new Properties());
            } else if (modelClass.isInstance(new SmartPhones())) {
                model.addAttribute("postModel", new SmartPhones());

            } else if (modelClass.isInstance(new Vehicles())) {
                model.addAttribute("postModel", new Vehicles());

            } else if (modelClass.isInstance(new Villa())) {
                model.addAttribute("postModel", new Villa());
            }
        }
        model.addAttribute("post", new Post());

        return "post-master";
    }*/

    @PostMapping("/savePostGeneric")
    public @ResponseBody
    Map<String, String> createPostGeneric(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.getGeneric().setPost(post);
        post.setGeneric(post.getGeneric());
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }
   /* public String createPostGeneric(@ModelAttribute("post") @Validated Post post, BindingResult postBindResult, @ModelAttribute("generic") @Validated Generic generic, BindingResult genericBindResult, Principal principal) {
        if (postBindResult.hasErrors()) {
            return "post-general";
        }

        if (genericBindResult.hasErrors()) {
            return "post-general";
        }

        post.setPostedBy(usersService.findByEmail(principal.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setPostModel(postModelService.findByModelName(generic.getClass().getSimpleName()).get());
        post.setGeneric(generic);
        generic.setPost(post);
        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        Path imagePath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/images/post");
        Path videoPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Product images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Product videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);


        postService.savePost(post);

        return "redirect:/user";
    }*/

    // -----------this controller for Mobile Phone-------------//

    @PostMapping("/savePostSmartPhone")
    public @ResponseBody
    Map<String, String> createPostSmartPhone(@ModelAttribute @Validated Post post, BindingResult postBindResult) {


        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setSmartPhones(post.getSmartPhones());
        post.getSmartPhones().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    //addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }
    // -----------this controller for petAnimal-------------//

    @PostMapping("/savePostPetAnimal")
    public @ResponseBody
    Map<String, String> createPostPetAnimal(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setPetAnimal(post.getPetAnimal());
        post.getPetAnimal().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet animal images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet Animal videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    @PostMapping("/saveHire")
    public @ResponseBody
    Map<String, String> createPostHire(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setHire(post.getHire());
        post.getHire().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    /*@GetMapping("/getAllPost")
    public @ResponseBody
    List<Post> getAllPost() {
        return postService.getAllPost();
    }
*/
    @PostMapping("/saveJobSeeker")
    public @ResponseBody
    Map<String, String> createPostSeeker(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setJobSeeker(post.getJobSeeker());
        post.getJobSeeker().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Post videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    @PostMapping("/saveProperties")
    public @ResponseBody
    Map<String, String> createPostProperties(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setProperties(post.getProperties());
        post.getProperties().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet animal images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet Animal videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    @PostMapping("/saveVehicles")
    public @ResponseBody
    Map<String, String> createPostVehicles(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setVehicles(post.getVehicles());
        post.getVehicles().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet animal images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet Animal videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    @PostMapping("/saveLaptop")
    public @ResponseBody
    Map<String, String> createPostLaptop(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setLaptop(post.getLaptop());
        post.getLaptop().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet animal images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet Animal videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    @PostMapping("/saveVilla")
    public @ResponseBody
    Map<String, String> createPostVilla(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if (!subCategory.isPresent()) {
            responseResult.put("response", "Invalid Subcategory");
        }

        post.setSubCategory(subCategory.get());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        post.setVilla(post.getVilla());
        post.getVilla().setPost(post);
        PostView postView = new PostView();
        if (post.getId() == null) {
            postView.setCount(0);
            post.setPostView(postView);
        } else {
            Optional<PostView> nPostView = postViewService.findViewCountByPost(post);
            if (nPostView.isPresent()) {
                post.setPostView(nPostView.get());
            } else {
                responseResult.put("response", "Invalid");
                return responseResult;
            }
        }

        postView.setPost(post);

        if (post.getPostStatus() == null) {
            PostStatus postStatus = new PostStatus();
            postStatus.setSold(false);
            postStatus.setActive(false);
            postStatus.setPost(post);
            post.setPostStatus(postStatus);
        }

        if (post.getApproved() == null) {
            PostApproved postApproved = new PostApproved();
            postApproved.setPost(post);
            postApproved.setApproved("PENDING");
            post.setApproved(postApproved);
        }

        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    String text = "WWW.SUQQATAR.COM";
                    File input = new File(file);
                    File inputtemp = new File(file);
                    File overlay = new File(imagePath.toString() + "/" + "Suqlogo.png");
                    File output = new File(file);
                    // adding text as overlay to an image
                    addTextWatermark(text, "png", input, output);
                    addImageWatermark(overlay, "png", inputtemp, output);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet animal images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet Animal videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }

    /*@PostMapping("/saveGeneric")
    public @ResponseBody
    Map<String, String> createGeneric(@ModelAttribute @Validated Post post, BindingResult postBindResult) {

        Map<String, String> responseResult = new HashMap<>();

        if (postBindResult.hasErrors()) {
            responseResult.put("response", postBindResult.getFieldError().toString());
            return responseResult;
        }

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(post.getSubCategory().getId());

        if(!subCategory.isPresent()){

        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        post.setPostedBy(usersService.findByEmail(auth.getName()).get());
        post.setPostCreateTime(LocalDateTime.now());
        postService.savePost(post);

        MultipartFile[] postImages = post.getPhotos();
        MultipartFile[] postVideos = post.getVideos();

        String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
        Path imagePath = Paths.get(path + "/images/post");
        Path videoPath = Paths.get(path + "/videos/post");

        List<String> photoLink = new ArrayList<>();

        if (postImages != null && postImages.length > 0) {
            try {
                int count = 0;
                for (MultipartFile image : postImages) {
                    String file = imagePath.toString() + "/" + post.getId() + "_" + count + ".png";
                    photoLink.add(file);
                    image.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet animal images saving failed", e);
            }
        }

        post.setPhotosLink(photoLink);

        List<String> videoLink = new ArrayList<>();

        if (postVideos != null && postVideos.length > 0) {
            try {
                int count = 0;
                for (MultipartFile video : postVideos) {
                    String file = videoPath.toString() + "/" + post.getId() + "_" + count + ".webm";
                    videoLink.add(file);
                    video.transferTo(new File(file));
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Pet Animal videos saving failed", e);
            }
        }

        post.setVideosLink(videoLink);

        postService.savePost(post);

        responseResult.put("response", "Successful");
        return responseResult;
    }
*/

    @GetMapping("/getCondition")
    public @ResponseBody
    List<Condition> getCondition() {
        List<Condition> conditionList = new ArrayList<>();
        conditionList.add(Condition.New);
        conditionList.add(Condition.Used);
        conditionList.add(Condition.Others);

        return conditionList;
    }

    @GetMapping("/postTypeList")
    public @ResponseBody
    List<PostType> allCategories() {
        return postTypeService.findAll();
    }

    @GetMapping("/categoryListByPostType/{postTypeId}")
    public @ResponseBody
    List<Category> allCategoriesByPostType(@PathVariable("postTypeId") Long postTypeId) {
        Optional<PostType> postType = postTypeService.getPostTypebyId(postTypeId);
        return postType.map(postType1 -> categoryService.findByPostType(postType1)).orElse(null);
    }

    @GetMapping("/subCategoryListByCategory/{categoryId}")
    public @ResponseBody
    List<SubCategory> allCategoriesByCategory(@PathVariable("categoryId") Long categoryId) {
        Optional<Category> category = categoryService.categoryById(categoryId);
        return category.map(category1 -> subCategoryService.findSubCategoriesByCategoryId(category1)).orElse(null);
    }

    @GetMapping("/getModel/{subcategoryId}")
    public @ResponseBody
    Map<String, String> getModel(@PathVariable("subcategoryId") Long subcategoryId) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subcategoryId);
        Map<String, String> map = new HashMap<>();
        if (subCategory.isPresent()) {
            Class modelClass = subCategory.get().getPostModel().getModelClass();
            map.put("postModelClass", modelClass.getSimpleName());
        }
        return map;
    }

    @GetMapping("/singlePrduct/view/{id}")
    public String showProductDetails(@PathVariable("id") Long id, HttpServletRequest request) {
        request.getSession().setAttribute("postId", String.valueOf(id));
        Optional<Post> post = postService.findPostById(id);
        return "redirect:/userPost/singlePrduct/" + GlobalUtils.nameToSlug(post.get().getTitle()) + id;
    }

    @GetMapping("/singlePrduct/{slug}")
    public ModelAndView showProductDetailswithSlug(@PathVariable("slug") String slug, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String postIds = String.valueOf(request.getSession().getAttribute("postId"));
        Long postId = Long.valueOf(postIds);
        Optional<Post> post = postService.findPostById(postId);
        Optional<PostView> postView = postViewService.findViewCountByPost(post.get());
        if (postView.isPresent()) {
            postView.get().setCount(postView.get().getCount() + 1);
            postViewService.savePostView(postView.get());
            logger.info(postViewService.findViewCountByPost(post.get()).get().getCount() + "");
        } else {
            PostView postView1 = new PostView();
            postView1.setCount(1);
            postView1.setPost(post.get());
            postViewService.savePostView(postView1);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> users = usersService.findByEmail(auth.getName());
        Optional<LikePost> likePost = likePostService.findLikePostByIdAndPostId(users.get(), post.get());
        Optional<FavoritePost> favoritePost = favoritePostService.checkFavorite(users.get(), post.get());

        modelAndView.addObject("post", post);
        modelAndView.addObject("likePost", likePost.orElse(null));
        modelAndView.addObject("favoritePost", favoritePost.orElse(null));
        modelAndView.addObject("users", usersService.findByEmail(auth.getName()).get());
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Generic())) {
            modelAndView.setViewName("single-general");
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Hire())) {
            modelAndView.setViewName("single-hire");
        }

        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new JobSeeker())) {
            modelAndView.setViewName("single-seek");
        }

        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Properties())) {
            modelAndView.setViewName("single-property");
        }

        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Laptop())) {
            modelAndView.setViewName("single-lap");
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new SmartPhones())) {
            modelAndView.setViewName("single-mobile");
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Villa())) {
            modelAndView.setViewName("single-villa");
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new PetAnimal())) {
            modelAndView.setViewName("single-animal");
        }
        if (post.get().getSubCategory().getPostModel().getModelClass().isInstance(new Vehicles())) {
            modelAndView.setViewName("single-vehicle");
        }

        return modelAndView;
    }

    @PostMapping("/post/showComment")
    public @ResponseBody
    List<Comments> sendListToAjaxComments(@RequestParam("postId") Long postId) {
        Post post = postService.findPostById(postId).get();
        Map<String, Object> map = new HashMap<>();
        List<Comments> listComments = commentService.findByPostDesc(post);
        return listComments;
    }

    @GetMapping("/getProfileById/{id}")
    public @ResponseBody
    Profile sendToAjaxUserName(@PathVariable("id") Long id) {
        Profile profile = profileService.findByUsers(usersService.findUserById(id).get()).get();
        return profile;
    }

    @PostMapping("/post/comment")
    public @ResponseBody
    Map<String, Object> saveComments(@RequestParam("postId") Long postId, @RequestParam("commentBody") String commentBody) {
        Map<String, Object> map = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            map.put("respose", "Fail");
            return map;
        }
        Optional<Users> users = usersService.findByEmail(auth.getName());
        Optional<Post> post = postService.findPostById(postId);

        Comments comments = new Comments();

        comments.setStatus(true);
        comments.setComment(commentBody);
        comments.setCommentedBy(users.get());
        comments.setPost(post.get());
        comments.setLocalDateTime(LocalDateTime.now());
        commentService.saveComments(comments);

        map.put("respose", "Success");
        return map;
    }

    /*this method for Comments counts*/

    @GetMapping("/getComment/{id}")
    public @ResponseBody
    int getCommentCont(@PathVariable("id") Long id) {

        Post post = postService.findPostById(id).get();
        int count = commentService.getNumberComment(post);
        return count;
    }

    @GetMapping("/getView/{id}")
    public @ResponseBody
    Long getViewCont(@PathVariable("id") Long id) {
        Post post = postService.findPostById(id).get();
        return postViewService.getPostViewByPost(post).getCount();
    }

    @GetMapping("/getLike/{id}")
    public @ResponseBody
    int getLIkeCont(@PathVariable("id") Long id) {

        Post post = postService.findPostById(id).get();
        int count = likePostService.getNumberLike(post);
        return count;
    }

    //***************Image water marking****************

    private static void addImageWatermark(File watermark, String type, File source, File destination) throws IOException {
        BufferedImage image = ImageIO.read(source);
        BufferedImage overlay = resize(ImageIO.read(watermark), 50, 50);

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
        w.setComposite(alphaChannel);

        // calculates the coordinate where the String is painted
        int centerX = image.getWidth() / 3;
        int centerY = image.getHeight() / 3;

        // add text watermark to the image
        w.drawImage(overlay, centerX, centerY, null);
        ImageIO.write(watermarked, type, destination);
        w.dispose();
    }

    private static void addTextWatermark(String text, String type, File source, File destination) throws IOException {
        BufferedImage image = ImageIO.read(source);

        // determine image type and handle correct transparency
        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);

        // initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .40f);
        w.setComposite(alphaChannel);
        w.setColor(Color.GRAY);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
        FontMetrics fontMetrics = w.getFontMetrics();
        Rectangle2D rect = fontMetrics.getStringBounds(text, w);

        // calculate center of the image
        int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
        int centerY = image.getHeight() / 2;

        // add text overlay to the image
        w.drawString(text, centerX, centerY);
        ImageIO.write(watermarked, type, destination);
        w.dispose();
    }


    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;


    }

}
