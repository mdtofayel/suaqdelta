package com.happyride.eservice.controller;

import com.google.gson.Gson;
import com.happyride.eservice.entity.model.Category;
import com.happyride.eservice.entity.model.Post;
import com.happyride.eservice.entity.model.PostType;
import com.happyride.eservice.entity.model.SubCategory;
import com.happyride.eservice.model.Condition;
import com.happyride.eservice.model.Gender;
import com.happyride.eservice.service.CategoryService;
import com.happyride.eservice.service.PostService;
import com.happyride.eservice.service.PostTypeService;
import com.happyride.eservice.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final PostService postService;

    private final CategoryService categoryService;

    private final SubCategoryService subCategoryService;

    private final PostTypeService postTypeService;

    @Autowired
    public SearchController(PostService postService, CategoryService categoryService, SubCategoryService subCategoryService, PostTypeService postTypeService) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.postTypeService = postTypeService;
    }

    @PostMapping("/allAdsByGlobalSearchByFilter")
    public @ResponseBody
    Page<Post> allAdsByGlobalSearchByFilter(@RequestParam(name = "typeId", required = false) Optional<Long> typeId,
                                            @RequestParam(name = "categoryId", required = false) Optional<Long> categoryId,
                                            @RequestParam(name = "subcategoryId", required = false) Optional<Long> subcategoryId,
                                            @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                            @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                            @RequestParam(name = "page") Optional<Integer> page,
                                            @RequestParam(name = "size") Optional<Integer> size) {
        boolean amount = false;

        if (priceFrom.isPresent() && priceTo.isPresent()) {
            amount = true;
        }

        if (subcategoryId.isPresent() && !amount) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (categoryId.isPresent() && !amount) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (categoryId.isPresent()) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (typeId.isPresent() && !amount) {
            Optional<PostType> postType = postTypeService.getPostTypebyId(typeId.get());
            if (postType.isPresent()) {
                List<Category> categoryList = new ArrayList<>(postType.get().getCategoryList());
                List<SubCategory> subCategoryList = new ArrayList<>();
                for (Category category : categoryList) {
                    subCategoryList.addAll(category.getSubCategoryList());
                }
                return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (typeId.isPresent()) {
            Optional<PostType> postType = postTypeService.getPostTypebyId(typeId.get());
            if (postType.isPresent()) {
                List<Category> categoryList = new ArrayList<>(postType.get().getCategoryList());
                List<SubCategory> subCategoryList = new ArrayList<>();
                for (Category category : categoryList) {
                    subCategoryList.addAll(category.getSubCategoryList());
                }
                return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (amount) {
            return postService.findAllByPriceBetween(priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        return null;
    }


    @PostMapping("/allAdsByTypeSearchByFilter")
    public @ResponseBody
    Page<Post> allAdsByTypeSearchByFilter(@RequestParam(name = "title", required = false) Optional<String> title,
                                          @RequestParam(name = "typeId", required = false) Optional<Long> typeId,
                                          @RequestParam(name = "categoryId", required = false) Optional<Long> categoryId,
                                          @RequestParam(name = "subcategoryId", required = false) Optional<Long> subcategoryId,
                                          @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                          @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                          @RequestParam(name = "page") Optional<Integer> page,
                                          @RequestParam(name = "size") Optional<Integer> size) {
        boolean amount = false;

        if (priceFrom.isPresent() && priceTo.isPresent()) {
            amount = true;
        }

        if (subcategoryId.isPresent() && !amount && !title.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent() && !amount) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findByTitleContainingIgnoreCaseAndSubCategoryIn(title.orElse("_"), subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent() && !title.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(title.orElse("_"), subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (categoryId.isPresent() && !amount && !title.isPresent()) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (categoryId.isPresent() && !amount) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findByTitleContainingIgnoreCaseAndSubCategoryIn(title.orElse("_"), subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (categoryId.isPresent() && !title.isPresent()) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (categoryId.isPresent()) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(title.orElse("_"), subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (amount) {
            Optional<PostType> postType = postTypeService.getPostTypebyId(typeId.get());
            if (postType.isPresent()) {
                List<Category> categoryList = new ArrayList<>(postType.get().getCategoryList());
                List<SubCategory> subCategoryList = new ArrayList<>();
                for (Category category : categoryList) {
                    subCategoryList.addAll(category.getSubCategoryList());
                }
                return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (title.isPresent()) {
            Optional<PostType> postType = postTypeService.getPostTypebyId(typeId.get());
            if (postType.isPresent()) {
                List<Category> categoryList = new ArrayList<>(postType.get().getCategoryList());
                List<SubCategory> subCategoryList = new ArrayList<>();
                for (Category category : categoryList) {
                    subCategoryList.addAll(category.getSubCategoryList());
                }
                return postService.findByTitleContainingIgnoreCaseAndSubCategoryIn(title.orElse("_"), subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        return null;
    }

    @PostMapping("/searchAllPostByCategory")
    public @ResponseBody
    Page<Post> searchAllPostByCategory(@RequestParam(name = "title", required = false) Optional<String> title,
                                       @RequestParam(name = "categoryId", required = false) Optional<Long> categoryId,
                                       @RequestParam(name = "subcategoryId", required = false) Optional<Long> subcategoryId,
                                       @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                       @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                       @RequestParam(name = "page") Optional<Integer> page,
                                       @RequestParam(name = "size") Optional<Integer> size) {


        boolean amount = false;

        if (priceFrom.isPresent() && priceTo.isPresent()) {
            amount = true;
        }

        if (subcategoryId.isPresent() && !amount && !title.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllBySubCategoryIn(subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent() && !amount) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findByTitleContainingIgnoreCaseAndSubCategoryIn(title.orElse("_"), subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent() && !title.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (subcategoryId.isPresent()) {
            List<SubCategory> subCategoryList = new ArrayList<>();
            Optional<SubCategory> optionalSubCategory = subCategoryService.getSubCategoryById(subcategoryId.get());
            subCategoryList.add(optionalSubCategory.orElse(null));
            return postService.findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(title.orElse("_"), subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        if (amount && categoryId.isPresent()) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findAllBySubCategoryInAndPriceBetween(subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(1000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        if (title.isPresent() && categoryId.isPresent()) {
            Optional<Category> category = categoryService.categoryById(categoryId.get());
            if (category.isPresent()) {
                List<SubCategory> subCategoryList = new ArrayList<>(category.get().getSubCategoryList());
                return postService.findByTitleContainingIgnoreCaseAndSubCategoryIn(title.orElse("_"), subCategoryList, PageRequest.of(page.orElse(0), size.orElse(20)));
            } else {
                return null;
            }
        }

        return null;
    }

    @PostMapping("/searchAllPostByAnimal")
    public @ResponseBody
    Page<Post> searchAllPostByAnimal(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                     @RequestParam(name = "title", required = false) Optional<String> title,
                                     @RequestParam(name = "address", required = false) Optional<String> address,
                                     @RequestParam(name = "ageFrom", required = false) Optional<Double> ageFrom,
                                     @RequestParam(name = "ageTo", required = false) Optional<Double> ageTo,
                                     @RequestParam(name = "quantityFrom", required = false) Optional<Integer> quantityFrom,
                                     @RequestParam(name = "quantityTo", required = false) Optional<Integer> quantityTo,
                                     @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                     @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                     @RequestParam(name = "gender", required = false) Optional<String> gender,
                                     @RequestParam(name = "color", required = false) Optional<String> color,
                                     @RequestParam(name = "page") Optional<Integer> page,
                                     @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Gender> gender1 = new ArrayList<>();
            if (gender.isPresent()) {
                if (gender.isPresent()) {
                    Gson googleJson = new Gson();
                    ArrayList<String> javaArrayListFromGSON = googleJson.fromJson(gender.get(), ArrayList.class);
                    for (String s : javaArrayListFromGSON) {
                        if (s.equals("Male")) {
                            gender1.add(Gender.Male);
                        }

                        if (s.equals("Female")) {
                            gender1.add(Gender.Female);
                        }

                        if (s.equals("Both")) {
                            gender1.add(Gender.Both);
                        }

                        if (s.equals("Others")) {
                            gender1.add(Gender.Others);
                        }
                    }
                }
            } else {
                gender1.add(Gender.Male);
                gender1.add(Gender.Female);
                gender1.add(Gender.Both);
                gender1.add(Gender.Others);
            }
            return postService.findAllPetAnimalByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(1000000.0), ageFrom.orElse(0.0), ageTo.orElse(1000.0), quantityFrom.orElse(0), quantityTo.orElse(100000), gender1, color.orElse("_"), PageRequest.of(page.orElse(0), size.orElse(20)));
        }
        return null;
    }

    @PostMapping("/searchAllPostByGeneral")
    public @ResponseBody
    Page<Post> searchAllPostByGeneral(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                      @RequestParam(name = "title", required = false) Optional<String> title,
                                      @RequestParam(name = "brandName", required = false) Optional<String> brandName,
                                      @RequestParam(name = "address", required = false) Optional<String> address,
                                      @RequestParam(name = "condition", required = false) Optional<String> condition,
                                      @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                      @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                      @RequestParam(name = "page") Optional<Integer> page,
                                      @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Condition> conditions = new ArrayList<>();
            if (condition.isPresent()) {
                if (condition.get().equals("Used")) {
                    conditions.add(Condition.Used);
                }

                if (condition.get().equals("New")) {
                    conditions.add(Condition.New);
                }

                if (condition.get().equals("Others")) {
                    conditions.add(Condition.Others);
                }
            } else {
                conditions.add(Condition.Used);
                conditions.add(Condition.New);
                conditions.add(Condition.Others);
            }
            return postService.findAllGenericByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(10000000.0), brandName.orElse("_"), conditions, PageRequest.of(page.orElse(0), size.orElse(20)));
        }

        return null;
    }

    @PostMapping("/searchAllPostByHire")
    public @ResponseBody
    Page<Post> searchAllPostByHire(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                   @RequestParam(name = "title", required = false) Optional<String> title,
                                   @RequestParam(name = "address", required = false) Optional<String> address,
                                   @RequestParam(name = "employmentType", required = false) Optional<String> employmentType,
                                   @RequestParam(name = "position", required = false) Optional<String> position,
                                   @RequestParam(name = "joblevel", required = false) Optional<String> joblevel,
                                   @RequestParam(name = "type", required = false) Optional<String> type,
                                   @RequestParam(name = "nationality", required = false) Optional<String> nationality,
                                   @RequestParam(name = "gender", required = false) Optional<String> gender,
                                   @RequestParam(name = "experiences", required = false) Optional<String> experiences,
                                   @RequestParam(name = "language", required = false) Optional<String> language,
                                   @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                   @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                   @RequestParam(name = "page") Optional<Integer> page,
                                   @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Gender> gender1 = new ArrayList<>();
            if (gender.isPresent()) {
                if (gender.isPresent()) {
                    Gson googleJson = new Gson();
                    ArrayList<String> javaArrayListFromGSON = googleJson.fromJson(gender.get(), ArrayList.class);
                    for (String s : javaArrayListFromGSON) {
                        if (s.equals("Male")) {
                            gender1.add(Gender.Male);
                        }

                        if (s.equals("Female")) {
                            gender1.add(Gender.Female);
                        }

                        if (s.equals("Both")) {
                            gender1.add(Gender.Both);
                        }

                        if (s.equals("Others")) {
                            gender1.add(Gender.Others);
                        }
                    }
                }
            } else {
                gender1.add(Gender.Male);
                gender1.add(Gender.Female);
                gender1.add(Gender.Both);
                gender1.add(Gender.Others);
            }
            return postService.findAllHireByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(1000000.0), employmentType.orElse("_"), position.orElse("_"), joblevel.orElse("_"), type.orElse("_"), nationality.orElse("_"), gender1, experiences.orElse("_"), language.orElse("_"), PageRequest.of(page.orElse(0), size.orElse(20)));
        }
        return null;
    }

    @PostMapping("/searchAllPostByLaptopModel")
    public @ResponseBody
    Page<Post> searchAllPostByLaptopModel(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                          @RequestParam(name = "title", required = false) Optional<String> title,
                                          @RequestParam(name = "address", required = false) Optional<String> address,
                                          @RequestParam(name = "brandName", required = false) Optional<String> brandName,
                                          @RequestParam(name = "condition", required = false) Optional<String> condition,
                                          @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                          @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                          @RequestParam(name = "ram", required = false) Optional<String> ram,
                                          @RequestParam(name = "processor", required = false) Optional<String> processor,
                                          @RequestParam(name = "storage", required = false) Optional<String> storage,
                                          @RequestParam(name = "display", required = false) Optional<String> display,
                                          @RequestParam(name = "page") Optional<Integer> page,
                                          @RequestParam(name = "size") Optional<Integer> size) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Condition> conditions = new ArrayList<>();
            if (condition.isPresent()) {
                if (condition.get().equals("Used")) {
                    conditions.add(Condition.Used);
                }

                if (condition.get().equals("New")) {
                    conditions.add(Condition.New);
                }

                if (condition.get().equals("Others")) {
                    conditions.add(Condition.Others);
                }
            } else {
                conditions.add(Condition.Used);
                conditions.add(Condition.New);
                conditions.add(Condition.Others);
            }
            return postService.findAllLaptopByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(10000000.0), brandName.orElse("_"), ram.orElse("_"), processor.orElse("_"), storage.orElse("_"), display.orElse("_"), conditions, PageRequest.of(page.orElse(0), size.orElse(20)));
        }
        return null;
    }

    @PostMapping("/searchAllPostByMobile")
    public @ResponseBody
    Page<Post> searchAllPostByMobile(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                     @RequestParam(name = "title", required = false) Optional<String> title,
                                     @RequestParam(name = "manufacturer", required = false) Optional<String> manufacturer,
                                     @RequestParam(name = "address", required = false) Optional<String> address,
                                     @RequestParam(name = "condition", required = false) Optional<String> condition,
                                     @RequestParam(name = "model", required = false) Optional<String> model,
                                     @RequestParam(name = "ramFrom", required = false) Optional<Integer> ramFrom,
                                     @RequestParam(name = "ramTo", required = false) Optional<Integer> ramTo,
                                     @RequestParam(name = "processor", required = false) Optional<String> processor,
                                     @RequestParam(name = "color", required = false) Optional<String> color,
                                     @RequestParam(name = "storage", required = false) Optional<String> storage,
                                     @RequestParam(name = "networkType", required = false) Optional<String> networkType,
                                     @RequestParam(name = "simType", required = false) Optional<String> simType,
                                     @RequestParam(name = "frontCamera", required = false) Optional<String> frontCamera,
                                     @RequestParam(name = "backCamera", required = false) Optional<String> backCamera,
                                     @RequestParam(name = "display", required = false) Optional<String> display,
                                     @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                     @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                     @RequestParam(name = "page") Optional<Integer> page,
                                     @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Condition> conditions = new ArrayList<>();
            if (condition.isPresent()) {
                if (condition.get().equals("Used")) {
                    conditions.add(Condition.Used);
                }

                if (condition.get().equals("New")) {
                    conditions.add(Condition.New);
                }

                if (condition.get().equals("Others")) {
                    conditions.add(Condition.Others);
                }
            } else {
                conditions.add(Condition.Used);
                conditions.add(Condition.New);
                conditions.add(Condition.Others);
            }
            return postService.findAllPhoneByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(10000000.0), manufacturer.orElse("_"), model.orElse("_"), ramFrom.orElse(0), ramTo.orElse(1000000), processor.orElse("_"), color.orElse("_"), storage.orElse("_"), networkType.orElse("_"), simType.orElse("_"), frontCamera.orElse("_"), backCamera.orElse("_"), display.orElse("_"), conditions, PageRequest.of(page.orElse(0), size.orElse(20)));
        }
        return null;
    }

    @PostMapping("/searchAllPostByProperty")
    public @ResponseBody
    Page<Post> searchAllPostByProperty(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                       @RequestParam(name = "title", required = false) Optional<String> title,
                                       @RequestParam(name = "address", required = false) Optional<String> address,
                                       @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                       @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                       @RequestParam(name = "page") Optional<Integer> page,
                                       @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        return subCategory.map(subCategory1 -> postService.findAllPropertiesByProperties(title.orElse("_"), address.orElse("_"), subCategory1, priceFrom.orElse(0.0), priceTo.orElse(10000000.0), PageRequest.of(page.orElse(0), size.orElse(20)))).orElse(null);
    }

    @PostMapping("/searchAllPostByJobSeeker")
    public @ResponseBody
    Page<Post> searchAllPostByJobSeeker(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                        @RequestParam(name = "title", required = false) Optional<String> title,
                                        @RequestParam(name = "address", required = false) Optional<String> address,
                                        @RequestParam(name = "employmentType", required = false) Optional<String> employmentType,
                                        @RequestParam(name = "position", required = false) Optional<String> position,
                                        @RequestParam(name = "nationality", required = false) Optional<String> nationality,
                                        @RequestParam(name = "gender", required = false) Optional<String> gender,
                                        @RequestParam(name = "experiences", required = false) Optional<String> experiences,
                                        @RequestParam(name = "language", required = false) Optional<String> language,
                                        @RequestParam(name = "type", required = false) Optional<String> type,
                                        @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                        @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                        @RequestParam(name = "page") Optional<Integer> page,
                                        @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Gender> gender1 = new ArrayList<>();
            if (gender.isPresent()) {
                if (gender.isPresent()) {
                    Gson googleJson = new Gson();
                    ArrayList<String> javaArrayListFromGSON = googleJson.fromJson(gender.get(), ArrayList.class);
                    for (String s : javaArrayListFromGSON) {
                        if (s.equals("Male")) {
                            gender1.add(Gender.Male);
                        }

                        if (s.equals("Female")) {
                            gender1.add(Gender.Female);
                        }

                        if (s.equals("Both")) {
                            gender1.add(Gender.Both);
                        }

                        if (s.equals("Others")) {
                            gender1.add(Gender.Others);
                        }
                    }
                }
            } else {
                gender1.add(Gender.Male);
                gender1.add(Gender.Female);
                gender1.add(Gender.Both);
                gender1.add(Gender.Others);
            }
            return postService.findAllJobSeekerByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(1000000.0), employmentType.orElse("_"), position.orElse("_"), nationality.orElse("_"), gender1, experiences.orElse("_"), language.orElse("_"), type.orElse("_"), PageRequest.of(page.orElse(0), size.orElse(20)));
        }
        return null;
    }

    @PostMapping("/searchAllPostByVehicle")
    public @ResponseBody
    Page<Post> searchAllPostByVehicle(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                      @RequestParam(name = "title", required = false) Optional<String> title,
                                      @RequestParam(name = "address", required = false) Optional<String> address,
                                      @RequestParam(name = "manufacturer", required = false) Optional<String> manufacturer,
                                      @RequestParam(name = "condition", required = false) Optional<String> condition,
                                      @RequestParam(name = "model", required = false) Optional<String> model,
                                      @RequestParam(name = "manufacturYear", required = false) Optional<String> manufacturYear,
                                      @RequestParam(name = "numberOfDoor", required = false) Optional<String> numberOfDoor,
                                      @RequestParam(name = "color", required = false) Optional<String> color,
                                      @RequestParam(name = "transmission", required = false) Optional<String> transmission,
                                      @RequestParam(name = "fuelType", required = false) Optional<String> fuelType,
                                      @RequestParam(name = "mileage", required = false) Optional<String> mileage,
                                      @RequestParam(name = "engineSize", required = false) Optional<String> engineSize,
                                      @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                      @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                      @RequestParam(name = "page") Optional<Integer> page,
                                      @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        if (subCategory.isPresent()) {
            List<Condition> conditions = new ArrayList<>();
            if (condition.isPresent()) {
                if (condition.get().equals("Used")) {
                    conditions.add(Condition.Used);
                }

                if (condition.get().equals("New")) {
                    conditions.add(Condition.New);
                }

                if (condition.get().equals("Others")) {
                    conditions.add(Condition.Others);
                }
            } else {
                conditions.add(Condition.Used);
                conditions.add(Condition.New);
                conditions.add(Condition.Others);
            }
            return postService.findAllVehicleByProperties(title.orElse("_"), address.orElse("_"), subCategory.get(), priceFrom.orElse(0.0), priceTo.orElse(10000000.0), manufacturer.orElse("_"), conditions, model.orElse("_"), manufacturYear.orElse("_"), numberOfDoor.orElse("_"), color.orElse("_"), transmission.orElse("_"), fuelType.orElse("_"), mileage.orElse("_"), engineSize.orElse("_"), PageRequest.of(page.orElse(0), size.orElse(20)));
        }
        return null;
    }

    @PostMapping("/searchAllPostByVilla")
    public @ResponseBody
    Page<Post> searchAllPostByVilla(@RequestParam(name = "subCategoryId") Long subCategoryId,
                                    @RequestParam(name = "title", required = false) Optional<String> title,
                                    @RequestParam(name = "address", required = false) Optional<String> address,
                                    @RequestParam(name = "furnished", required = false) Optional<String> furnished,
                                    @RequestParam(name = "numberOfRoom", required = false) Optional<String> numberOfRoom,
                                    @RequestParam(name = "numberOfBathroom", required = false) Optional<String> numberOfBathroom,
                                    @RequestParam(name = "kitchenType", required = false) Optional<String> kitchenType,
                                    @RequestParam(name = "parking", required = false) Optional<String> parking,
                                    @RequestParam(name = "deposit", required = false) Optional<String> deposit,
                                    @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                    @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                    @RequestParam(name = "page") Optional<Integer> page,
                                    @RequestParam(name = "size") Optional<Integer> size) {

        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(subCategoryId);
        return subCategory.map(subCategory1 -> postService.findAllVillaByProperties(title.orElse("_"), address.orElse("_"), subCategory1, priceFrom.orElse(0.0), priceTo.orElse(10000000.0), furnished.orElse("_"), numberOfRoom.orElse("_"), numberOfBathroom.orElse("_"), kitchenType.orElse("_"), parking.orElse("_"), deposit.orElse("_"), PageRequest.of(page.orElse(0), size.orElse(20)))).orElse(null);
    }

    @PostMapping("/allAdsByCategoryPreSelectFilter")
    public @ResponseBody
    Page<Post> allAdsByCategoryPreSelectFilter(@RequestParam(name = "categoryName") String categoryName,
                                               @RequestParam(name = "typeId", required = false) Optional<Long> typeId,
                                               @RequestParam(name = "title", required = false) Optional<String> title,
                                               @RequestParam(name = "priceFrom", required = false) Optional<Double> priceFrom,
                                               @RequestParam(name = "priceTo", required = false) Optional<Double> priceTo,
                                               @RequestParam(name = "page") Optional<Integer> page,
                                               @RequestParam(name = "size") Optional<Integer> size) {


        List<SubCategory> subCategoryList = new ArrayList<>();

        List<Category> categoryList = categoryService.findCategoryListByName(categoryName);

        if (typeId.isPresent()) {
            Optional<PostType> optionalPostType = postTypeService.getPostTypebyId(typeId.get());
            if (optionalPostType.isPresent()) {
                categoryList.clear();
                List<Category> categoryList1 = new ArrayList<>();
                categoryList1.addAll(optionalPostType.get().getCategoryList());
                for (Category category : categoryList1) {
                    if (category.getName().equals(categoryName)) {
                        categoryList.add(category);
                    }
                }
            } else return null;
        }

        for (Category category : categoryList) {
            subCategoryList.addAll(category.getSubCategoryList());
        }

        return postService.findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(title.orElse("_"), subCategoryList, priceFrom.orElse(0.0), priceTo.orElse(100000000.0), PageRequest.of(page.orElse(0), size.orElse(20)));
    }
}
