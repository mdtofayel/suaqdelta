package com.happyride.eservice.controller;

import com.happyride.eservice.configuration.GlobalUtils;
import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.model.AddManufacturer;
import com.happyride.eservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    private final CategoryService categoryService;

    private final SubCategoryService subCategoryService;

    private final PostTypeService postTypeService;

    private final PostModelService postModelService;

    private final SliderImageService sliderImageService;

    private ServletContext servletContext;

    private ManufacturerService manufacturerService;

    private ManufacturerModelService manufacturerModelService;

    @Autowired
    public ManagerController(CategoryService categoryService,
                             SubCategoryService subCategoryService,
                             PostTypeService postTypeService, PostModelService postModelService, SliderImageService sliderImageService, ServletContext servletContext, ManufacturerService manufacturerService, ManufacturerModelService manufacturerModelService) {
        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;
        this.postTypeService = postTypeService;
        this.postModelService = postModelService;
        this.sliderImageService = sliderImageService;
        this.servletContext = servletContext;
        this.manufacturerService = manufacturerService;
        this.manufacturerModelService = manufacturerModelService;
    }

    @GetMapping
    public ModelAndView managerDashboard() {
        return new ModelAndView("admin/manger_profile");
    }

    @GetMapping(value = "/viewCategory")
    public ModelAndView viewCategoryCategoryPage() {
        return new ModelAndView("admin/category");
    }

    @GetMapping("/viewManufacturer")
    public ModelAndView viewManufacturer() {
        return new ModelAndView("admin/manufacturer");
    }

    @GetMapping("/viewProductModelList")
    public ModelAndView getProductModelList() {
        return new ModelAndView("admin/manufacturerModel");
    }

    @GetMapping("/productModelList")
    public @ResponseBody
    List<ManufacturerModel> sendProductModelList() {
        return manufacturerModelService.findAll();
    }

    @PostMapping("/saveModel")
    public @ResponseBody
    Map<String, String> saveModel(@ModelAttribute ManufacturerModel manufacturerModel) {
        Map<String, String> map = new HashMap<>();
        Optional<ManufacturerModel> findingDuplicateManufaturerModel = manufacturerModelService.findByNameAndManufacturerId(manufacturerModel.getName(), manufacturerModel.getManufacturer());


        if (findingDuplicateManufaturerModel.isPresent()) {
            if (findingDuplicateManufaturerModel.get().getName().equals(manufacturerModel.getName()) && findingDuplicateManufaturerModel.get().getManufacturer().getId().equals(manufacturerModel.getManufacturer().getId())) {
                map.put("response", "Product model already exist.");
                return map;
            }
            ManufacturerModel newManufacturerModel = new ManufacturerModel();
            newManufacturerModel.setManufacturer(manufacturerModel.getManufacturer());
            newManufacturerModel.setCreatAt(LocalDateTime.now());
            newManufacturerModel.setName(manufacturerModel.getName());
            manufacturerModelService.saveManufacturerModel(newManufacturerModel);
            map.put("response", "Model has successfully saved.");
            return map;
        }

        ManufacturerModel newManufacturerModel = new ManufacturerModel();
        newManufacturerModel.setManufacturer(manufacturerModel.getManufacturer());
        newManufacturerModel.setCreatAt(LocalDateTime.now());
        newManufacturerModel.setName(manufacturerModel.getName());
        manufacturerModelService.saveManufacturerModel(newManufacturerModel);
        map.put("response", "Model has successfully saved.");
        return map;
    }

    @GetMapping("/editManufacturerModel/{modelId}")
    public @ResponseBody
    ManufacturerModel getManufacturerModel(@PathVariable("modelId") Long id) {
        return manufacturerModelService.findById(id).orElse(null);
    }

    @PostMapping("/editSaveModel")
    public @ResponseBody
    Map<String, String> editModel(@ModelAttribute ManufacturerModel manufacturerModel) {
        Map<String, String> map = new HashMap<>();
        Long id = manufacturerModel.getId();
        Optional<ManufacturerModel> findingDuplicateManufaturerModel = manufacturerModelService.findByNameAndManufacturerId(manufacturerModel.getName(), manufacturerModel.getManufacturer());


        if (findingDuplicateManufaturerModel.isPresent()) {
            map.put("response", "Product model already exist.");
            return map;
        }

        ManufacturerModel newManufacturerModel = new ManufacturerModel();
        newManufacturerModel.setCreatAt(manufacturerModelService.findById(manufacturerModel.getId()).get().getCreatAt());
        newManufacturerModel.setId(manufacturerModel.getId());
        newManufacturerModel.setManufacturer(manufacturerModel.getManufacturer());
        newManufacturerModel.setUpdateAt(LocalDateTime.now());
        newManufacturerModel.setName(manufacturerModel.getName());
        manufacturerModelService.saveManufacturerModel(newManufacturerModel);
        map.put("response", "Model has successfully updated.");

        return map;
    }

    //this mehod for add Manufacturer for subcategory.

    @PostMapping("/updateSubcategoryWithManufaturerList")
    public @ResponseBody
    Map<String, String> updateSubcategoryWthManufacturer(AddManufacturer addManufacturer) {

        Map<String, String> map = new HashMap<>();

        List<Long> manufacturerId = Arrays.asList(addManufacturer.getListOfManufacturer());

        SubCategory subCategory = subCategoryService.getSubCategoryById(addManufacturer.getSubcategoryId()).get();

        subCategory.setManufacturerList(new ArrayList<>());

        List<Manufacturer> manufacturers = new ArrayList<>();

        for (Long manuId : manufacturerId) {
            Optional<Manufacturer> manufacturerOptional = manufacturerService.findManufacturerById(manuId);
            if (manufacturerOptional.isPresent()) {
                Manufacturer manufacturer = manufacturerOptional.get();
                if (!manufacturers.contains(manufacturer)) {
                    manufacturers.add(manufacturer);
                }

            }
        }

        subCategory.setManufacturerList(manufacturers);
        subCategoryService.saveSubCategories(subCategory);
        return map;
    }

    @GetMapping(value = "/categoryList")
    public @ResponseBody
    List<Category> getCategoryList() {
        return categoryService.findAll();
    }

    @GetMapping(value = "/categoryList/{typeId}")
    public @ResponseBody
    List<Category> getCategoryList(@PathVariable("typeId") Long typeId) {
        Optional<PostType> postType = postTypeService.getPostTypebyId(typeId);
        return postType.map(categoryService::findByPostType).orElse(null);
    }

    @RequestMapping(value = "/saveCategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> saveCategories(@ModelAttribute Category category) {
        Map<String, String> map = new HashMap<>();
        Optional<PostType> postType = postTypeService.getPostTypebyId(category.getPostType().getId());
        if (category.getName().length() > 1) {
            Optional<Category> category2 = categoryService.findCategoryByName(category.getName());
            if (category2.isPresent() && postType.isPresent() && category2.get().getPostType() == postType.get()) {
                map.put("response", "Category already Exist");
                return map;
            }
            Category category1 = new Category();
            category1.setPostType(postTypeService.getPostTypebyId(category.getPostType().getId()).get());
            category1.setName(category.getName());
            category1.setIconClass(category.getIconClass());
            category1.setCreatAt(LocalDateTime.now());
            categoryService.saveCategories(category1);

            if (category.getCategoryImage() != null) {
                String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                Path imagePath = Paths.get(path + "/images/category");
                String file = imagePath.toString() + "/" + category1.getId() + ".png";
                category1.setImageLink(file);
                try {
                    category.getCategoryImage().transferTo(new File(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                categoryService.saveCategories(category1);
            }

            map.put("response", "success");
            return map;
        }
        map.put("response", "failed");
        return map;
    }

    @RequestMapping(value = "/saveManufacturer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> saveManufacturer(@ModelAttribute Manufacturer manufacturer) {
        Map<String, String> map = new HashMap<>();
        if (manufacturer.getName().length() > 1) {
            Optional<Manufacturer> manufacturer1 = manufacturerService.findManufacturerByName(manufacturer.getName());
            if (manufacturer1.isPresent()) {
                map.put("response", "Manufacturer already exist");
                return map;
            }

            Manufacturer tempManufacturer = new Manufacturer();
            tempManufacturer.setName(manufacturer.getName());
            tempManufacturer.setCreatAt(LocalDateTime.now());


            if (manufacturer.getManufacturerImage() != null) {
                manufacturerService.saveManufacturer(tempManufacturer);
                String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                Path imagePath = Paths.get(path + "/images/manufacturer");
                String file = imagePath.toString() + "/" + tempManufacturer.getId() + ".png";
                tempManufacturer.setImageLink(file);
                try {
                    manufacturer.getManufacturerImage().transferTo(new File(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                manufacturerService.saveManufacturer(tempManufacturer);
                map.put("response", "success");
                return map;
            }

            map.put("response", "Please Select Manufacturer Image.");
        }
        map.put("response", "Please give a valid name.");
        return map;
    }

    @RequestMapping(value = "/editSaveManufacturer", method = RequestMethod.POST)

    public @ResponseBody
    Map<String, String> geteditSaveManufacturer(@ModelAttribute Manufacturer manufacturer) {
        Map<String, String> map = new HashMap<>();

        if (manufacturer.getName().length() > 1) {
            Optional<Manufacturer> manufacturer1 = manufacturerService.findManufacturerByName(manufacturer.getName());
            if (manufacturer1.isPresent()) {
                if (!manufacturer.getId().equals(manufacturer1.get().getId())) {
                    map.put("response", "Manufacturer already exist");
                    return map;
                }
            }

            Optional<Manufacturer> manufacturer2 = manufacturerService.findManufacturerById(manufacturer.getId());

            if (!manufacturer2.isPresent()) {
                map.put("response", "Invalid Manufacturer");
                return map;
            }

            Manufacturer tempManufacturer = manufacturer2.get();
            tempManufacturer.setName(manufacturer.getName());
            tempManufacturer.setUpdateAt(LocalDateTime.now());


            if (manufacturer.getManufacturerImage() != null) {
                manufacturerService.saveManufacturer(tempManufacturer);
                String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                Path imagePath = Paths.get(path + "/images/manufacturer");
                String file = imagePath.toString() + "/" + tempManufacturer.getId() + ".png";
                tempManufacturer.setImageLink(file);
                try {
                    manufacturer.getManufacturerImage().transferTo(new File(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            manufacturerService.saveManufacturer(tempManufacturer);
            map.put("response", "success");
            return map;
        }
        map.put("response", "Please give a valid name.");
        return map;
    }


    @GetMapping(value = "/editManufacturer/{id}")
    public @ResponseBody
    Manufacturer getManufacturer(@PathVariable("id") Long id) {
        Optional<Manufacturer> manufacturer = manufacturerService.findManufacturerById(id);
        return manufacturer.orElse(null);
    }

    @GetMapping(value = "/getCategory/{categoryId}")
    public @ResponseBody
    Category getCategory(@PathVariable("categoryId") Long id) {
        Optional<Category> category = categoryService.categoryById(id);
        return category.orElse(null);
    }

    @PostMapping(value = "/editCategory")
    public @ResponseBody
    Map<String, String> editCategory(@ModelAttribute Category category3) {
        Map<String, String> map = new HashMap<>();
        Optional<Category> category = categoryService.categoryById(category3.getId());
        Optional<Category> category1 = categoryService.findCategoryByNameAndPost(category3.getName(), category3.getPostType());
        if (category.isPresent() && category1.isPresent()) {
            if (category.get().getId() != category1.get().getId()) {
                map.put("response", "Category already Exist");
                return map;
            }
            if (category.get().getIconClass() == null || !category.get().getIconClass().equals(category3.getIconClass()) && category3.getIconClass() != null) {
                Category category2 = category.get();
                category2.setIconClass(category3.getIconClass());

                if (category3.getCategoryImage() != null) {
                    String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                    Path imagePath = Paths.get(path + "/images/category");
                    String file = imagePath.toString() + "/" + category2.getId() + ".png";
                    category2.setImageLink(file);
                    try {
                        category3.getCategoryImage().transferTo(new File(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                categoryService.saveCategories(category2);
                map.put("response", "Category Updated");
                return map;
            }

            if (category3.getCategoryImage() != null) {
                Category category2 = category.get();
                String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                Path imagePath = Paths.get(path + "/images/category");
                String file = imagePath.toString() + "/" + category2.getId() + ".png";
                category2.setImageLink(file);
                try {
                    category3.getCategoryImage().transferTo(new File(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                categoryService.saveCategories(category2);
                map.put("response", "Category Updated");
                return map;
            }

            map.put("response", "Category Updated");
            return map;
        }
        if (category.isPresent()) {
            Category category2 = category.get();
            category2.setName(category3.getName());
            category2.setIconClass(category3.getIconClass());
            category2.setUpdateAt(LocalDateTime.now());
            if (category3.getCategoryImage() != null) {
                String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                Path imagePath = Paths.get(path + "/images/category");
                String file = imagePath.toString() + "/" + category2.getId() + ".png";
                category2.setImageLink(file);
                try {
                    category3.getCategoryImage().transferTo(new File(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            categoryService.saveCategories(category2);
            map.put("response", "Category Updated");
            return map;
        }

        map.put("response", "No changes");
        return map;
    }

    @GetMapping(value = "/viewSubCategory")
    public ModelAndView viewSubCategory() {
        return new ModelAndView("admin/subcategory");
    }

    @GetMapping(value = "/subcategoryList")
    public @ResponseBody
    List<SubCategory> getSubcategoryList() {
        return subCategoryService.findAll();
    }

    @GetMapping("/manufacturerList")
    public @ResponseBody
    List<Manufacturer> getListOfManufacturer() {
        return manufacturerService.findAllManufacturer();
    }

    @GetMapping(value = "/subcategoryList/{categoriesId}")
    public @ResponseBody
    List<SubCategory> getSubcategoryListByCategoriesId(@PathVariable("categoriesId") Long id) {
        Category category = categoryService.categoryById(id).get();
        return subCategoryService.findSubCategoriesByCategoryId(category);
    }

    @GetMapping(value = "/getPostModel")
    public @ResponseBody
    List<PostModel> getResourceForSubcategory() {
        return postModelService.findAll();
    }

    @PostMapping(value = "/addSubcategory")
    public @ResponseBody
    Map<String, String> getAddSubcategory(@RequestParam("categoryId") Long categoryId, @RequestParam("modelId") Long postModelId, @RequestParam("subcategory") String subcategoryName, @RequestParam("iconClass") String iconClass) {
        Map<String, String> map = new HashMap<>();
        if (categoryId > -1 && postModelId > -1 && subcategoryName.length() > 1) {
            Optional<Category> category = categoryService.categoryById(categoryId);
            Optional<PostModel> postModel = postModelService.findById(postModelId);
            Optional<SubCategory> subCategory = subCategoryService.findByName(subcategoryName);

            if (!category.isPresent()) {
                map.put("response", "Invalid Request");
                return map;
            }

            if (!postModel.isPresent()) {
                map.put("response", "Invalid Request");
                return map;
            }
            if (subCategory.isPresent()) {
                if (subCategory.get().getCategory().getPostType().equals(category.get().getPostType()) && subCategory.get().getCategory().equals(category.get())) {
                    map.put("response", "Subcategory already exist");
                    return map;
                }
            }


            SubCategory subCategory1 = new SubCategory();
            subCategory1.setName(subcategoryName);
            subCategory1.setPostModel(postModel.get());
            subCategory1.setCategory(category.get());
            subCategory1.setCreatAt(LocalDateTime.now());
            subCategory1.setIconClass(iconClass);
            subCategoryService.saveSubCategories(subCategory1);

            map.put("response", "Subcategory successfully added");
            return map;

        } else {
            map.put("response", "Invalid Subcategory");
            return map;
        }
    }

    @GetMapping(value = "/getSubcategory/{subcategoryId}")
    public @ResponseBody
    SubCategory getSubcategory(@PathVariable("subcategoryId") Long id) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(id);
        return subCategory.orElse(null);
    }

    @PostMapping(value = "/editSubcategory")
    public @ResponseBody
    Map<String, String> editSubcategory(@RequestParam("subCategoryId") Long id, @RequestParam("subcategoryName") String name, @RequestParam("iconClass") String iconClass) {
        Map<String, String> map = new HashMap<>();
        if (id > -1 && name.length() > 1) {
            Optional<SubCategory> subCategory = subCategoryService.findByName(name);
            Optional<SubCategory> subCategory1 = subCategoryService.getSubCategoryById(id);

            if (!subCategory1.isPresent()) {
                map.put("response", "Invalid Request");
                return map;
            }

            if (subCategory.isPresent()) {
                if (subCategory1.get().equals(subCategory.get())) {
                    map.put("response", "Updated");
                    return map;
                }
                if (subCategory1.get().getCategory().equals(subCategory.get().getCategory())) {
                    map.put("response", "Subcategory already exist");
                    return map;
                }
            }


            SubCategory subCategory2 = subCategory1.get();
            subCategory2.setName(name);
            subCategory2.setIconClass(iconClass);
            subCategory2.setUpdateAt(LocalDateTime.now());
            subCategoryService.saveSubCategories(subCategory2);

            map.put("response", "Subcategory successfully added");
            return map;

        } else {
            map.put("response", "Invalid Subcategory");
            return map;
        }
    }

    @GetMapping(value = "/viewPostType")
    public ModelAndView viewPostType() {
        return new ModelAndView("admin/post_type");
    }

    @GetMapping(value = "/getPostTypeList")
    public @ResponseBody
    List<PostType> getPostTypeList() {
        return postTypeService.findAll();
    }

    @PostMapping(value = "/addPostType")
    public @ResponseBody
    Map<String, String> getAddPostType(@ModelAttribute PostType postType) {
        Map<String, String> map = new HashMap<>();
        if (postType.getName().length() > 1) {

            Optional<PostType> postType1 = postTypeService.findPostTypeByName(postType.getName());

            if (postType1.isPresent()) {
                map.put("response", "Post type already exist");
                return map;
            }

            PostType postType2 = new PostType();
            postType2.setName(postType.getName());
            postType2.setCreatAt(LocalDateTime.now());
            postTypeService.savePostType(postType2);

            map.put("response", "Successful");
            return map;

        } else {
            map.put("response", "Invalid Subcategory");
            return map;
        }
    }

    @GetMapping(value = "/getPostType/{postTypeId}")
    public @ResponseBody
    PostType getPostType(@PathVariable("postTypeId") Long id) {
        Optional<PostType> postType = postTypeService.getPostTypebyId(id);
        return postType.orElse(null);
    }

    @PostMapping(value = "/editPostType")
    public @ResponseBody
    Map<String, String> editPostType(@ModelAttribute PostType postType) {
        Map<String, String> map = new HashMap<>();
        Optional<PostType> postType1 = postTypeService.getPostTypebyId(postType.getId());
        Optional<PostType> postType2 = postTypeService.findPostTypeByName(postType.getName());
        if (postType1.isPresent() && postType2.isPresent()) {
            if (postType1.get().getId() != postType2.get().getId()) {
                map.put("response", "Post Type already Exist");
                return map;
            }
            map.put("response", "Post Type Updated");
            return map;
        }
        if (postType1.isPresent()) {
            PostType postType3 = postType1.get();
            postType3.setName(postType.getName());
            postType3.setUpdateAt(LocalDateTime.now());
            postTypeService.savePostType(postType3);
            map.put("response", "Post Type Updated");
            return map;
        }

        map.put("response", "No changes");
        return map;
    }

    @GetMapping(value = "/sliderImage")
    public ModelAndView sliderImage() {
        ModelAndView modelAndView = new ModelAndView("admin/admin_images");
        return modelAndView;
    }

    @GetMapping(value = "/sliderImageList")
    public @ResponseBody
    List<SliderImage> sliderImageList() {
        return sliderImageService.sliderImageList();
    }

    @RequestMapping(value = "/saveSliderImage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> saveSliderImage(@ModelAttribute SliderImage sliderImage) {
        Map<String, String> map = new HashMap<>();

        if (sliderImage.getMultipartFiles() != null) {
            try {
                for (MultipartFile image : sliderImage.getMultipartFiles()) {
                    SliderImage sliderImage1 = new SliderImage();
                    sliderImage1.setPostLink(sliderImage.getPostLink());
                    sliderImageService.save(sliderImage1);
                    String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
                    String file = path + "/images/sliderImage/" + sliderImage1.getId() + ".png";
                    image.transferTo(new File(file));
                    sliderImage1.setImageLink(file);
                    sliderImageService.save(sliderImage1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Product images saving failed", e);
            }
            map.put("response", "Successful");
            return map;
        }

        map.put("response", "failed");
        return map;
    }


    @RequestMapping(value = "/deleteSliderImage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> deleteSliderImage(@RequestParam("imageId") Long sliderImageId) {
        Map<String, String> map = new HashMap<>();

        Optional<SliderImage> sliderImage = sliderImageService.getSliderImageById(sliderImageId);

        if (sliderImage.isPresent()) {
            sliderImageService.delete(sliderImage.get());
            String path = GlobalUtils.tomcatContextPathParent(servletContext.getRealPath("/")) + "/Eservice_Files";
            String file = path + "/images/sliderImage/" + sliderImage.get().getId() + ".png";
            File file1 = new File(file);
            if (file1.exists()) {
                if (file1.delete()) {
                    map.put("response", "Successful");
                    return map;
                }
            }
            map.put("response", "Link deleted but file not deleted");
            return map;
        }

        map.put("response", "failed");
        return map;
    }

}
