package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.model.Condition;
import com.happyride.eservice.model.Gender;
import com.happyride.eservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Optional<Post> findTopPrice() {
        return postRepository.findTopByOrderByPriceDesc();
    }

    public Optional<Post> findTopPriceBySubCategory(SubCategory subCategory) {
        return postRepository.findTopBySubCategoryOrderByPriceDesc(subCategory);
    }

    public Optional<Post> findTopPriceByCategory(Category category) {
        return postRepository.findTopBySubCategory_CategoryOrderByPriceDesc(category);
    }

    public Optional<Post> findTopPriceByPostType(PostType postType) {
        return postRepository.findTopBySubCategory_Category_PostTypeOrderByPriceDesc(postType);
    }

    public Optional<Post> findPostById(Long id) {
        return postRepository.findById(id);
    }

    public Page<Post> findPostByGeneric(Generic generic, Pageable pageRequest) {
        return postRepository.findPostByGeneric(generic, pageRequest);
    }

    public Page<Post> findByTitle(String title, Pageable pageRequest) {
        return postRepository.findByTitleContainingIgnoreCase(title, pageRequest);
    }

    public Page<Post> findPostByPostedBy(Users users, Pageable pageRequest) {
        return postRepository.findPostByPostedBy(users, pageRequest);
    }

    public Page<Post> findAllBySubCategory(SubCategory subCategory, Pageable pageable) {
        return postRepository.findAllBySubCategory(subCategory, pageable);
    }

    public Page<Post> findAllBySubCategoryIn(List<SubCategory> subCategory, Pageable pageable) {
        return postRepository.findAllBySubCategoryIn(subCategory, pageable);
    }

    public Page<Post> getAllPost(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public List<Post> getAllAccordingStatus(String approvedStatus) {

        return postRepository.findByApprovedContaining(approvedStatus);
    }

    public Page<Post> findAllBySubCategoryInAndPriceBetween(List<SubCategory> subCategory, Double priceFrom, Double PriceTo, Pageable pageable) {
        return postRepository.findAllBySubCategoryInAndPriceBetween(subCategory, priceFrom, PriceTo, pageable);
    }

    public Page<Post> findAllByPriceBetween(Double priceFrom, Double PriceTo, Pageable pageable) {
        return postRepository.findAllByPriceBetween(priceFrom, PriceTo, pageable);
    }

    public Page<Post> findByTitleContainingIgnoreCaseAndSubCategoryIn(String title, List<SubCategory> subCategory, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCaseAndSubCategoryIn(title, subCategory, pageable);
    }

    public Page<Post> findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(String title, List<SubCategory> subCategory, Double priceFrom, Double PriceTo, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(title, subCategory, priceFrom, PriceTo, pageable);
    }

    public Page<Post> findAllPetAnimalByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, Double ageFrom, Double ageTo, Integer quantityFrom, Integer quantityTo, List<Gender> gender, String color, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndPetAnimal_AgeBetweenAndPetAnimal_QuantityBetweenAndPetAnimal_GenderInAndPetAnimal_ColorContainingIgnoreCase(title, address, subCategory, priceFrom, PriceTo, ageFrom, ageTo, quantityFrom, quantityTo, gender, color, pageable);
    }

    public Page<Post> findAllGenericByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturerName, List<Condition> Condition, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndGeneric_ManufacturerNameContainingIgnoreCaseAndGeneric_ConditionIn(title, address, subCategory, priceFrom, PriceTo, manufacturerName, Condition, pageable);
    }

    public Page<Post> findAllHireByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String employmentType, String position, String jobLevel, String type, String nationality, List<Gender> gender, String experiences, String language, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndHire_EmploymentTypeContainingIgnoreCaseAndHire_PositionContainingIgnoreCaseAndHire_JobLevelContainingIgnoreCaseAndHire_TypeContainingIgnoreCaseAndHire_NationalityContainingIgnoreCaseAndHire_genderInAndHire_ExperiencesContainingIgnoreCaseAndHire_LanguageContainingIgnoreCase(title, address, subCategory, priceFrom, PriceTo, employmentType, position, jobLevel, type, nationality, gender, experiences, language, pageable);
    }

    public Page<Post> findAllLaptopByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturerName, String ram, String processor, String Storage, String Display, List<Condition> Condition, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndLaptop_ManufacturerContainingIgnoreCaseAndLaptop_RamContainingIgnoreCaseAndLaptop_ProcessorContainingIgnoreCaseAndLaptop_StorageContainingIgnoreCaseAndLaptop_DisplayContainingIgnoreCaseAndLaptop_ConditionIn(title, address, subCategory, priceFrom, PriceTo, manufacturerName, ram, processor, Storage, Display, Condition, pageable);
    }

    public Page<Post> findAllPhoneByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturerName, String model, int ramFrom, int ramTo, String processor, String color, String Storage, String networkType, String simType, String fontCamera, String backCamera, String Display, List<Condition> Condition, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndSmartPhones_ManufacturerContainingIgnoreCaseAndSmartPhones_ModelContainingIgnoreCaseAndSmartPhones_RamBetweenAndSmartPhones_ProcessorContainingIgnoreCaseAndSmartPhones_ColorContainingIgnoreCaseAndSmartPhones_StorageContainingIgnoreCaseAndSmartPhones_NetworkTypeContainingIgnoreCaseAndSmartPhones_SimTypeContainingIgnoreCaseAndSmartPhones_FrontCameraContainingIgnoreCaseAndSmartPhones_BackCameraContainingIgnoreCaseAndSmartPhones_DisplayContainingIgnoreCaseAndSmartPhones_ConditionIn(title, address, subCategory, priceFrom, PriceTo, manufacturerName, model, ramFrom, ramTo, processor, color, Storage, networkType, simType, fontCamera, backCamera, Display, Condition, pageable);
    }

    public Page<Post> findAllPropertiesByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetween(title, address, subCategory, priceFrom, PriceTo, pageable);
    }

    public Page<Post> findAllJobSeekerByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String employmentType, String position, String nationality, List<Gender> gender, String experiences, String language, String type, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndJobSeeker_EmploymentTypeContainingIgnoreCaseAndJobSeeker_PositionContainingIgnoreCaseAndJobSeeker_NationalityContainingIgnoreCaseAndJobSeeker_GenderInAndJobSeeker_ExperiencesContainingIgnoreCaseAndJobSeeker_LanguageContainingIgnoreCaseAndJobSeeker_TypeContainingIgnoreCase(title, address, subCategory, priceFrom, PriceTo, employmentType, position, nationality, gender, experiences, language, type, pageable);
    }

    public Page<Post> findAllVehicleByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturer, List<Condition> conditions, String model, String manufactureYear, String numberOfDoor, String color, String transmission, String fuelType, String mileage, String engineSize, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndVehicles_ManufacturerContainingIgnoreCaseAndVehicles_ConditionInAndVehicles_ModelContainingIgnoreCaseAndVehicles_ManufacturYearContainingIgnoreCaseAndVehicles_NumberOfDoorContainingIgnoreCaseAndVehicles_ColorContainingIgnoreCaseAndVehicles_TransmissionContainingIgnoreCaseAndVehicles_FuelTypeContainingIgnoreCaseAndVehicles_MileageContainingIgnoreCaseAndVehicles_EngineSizeContainingIgnoreCase(title, address, subCategory, priceFrom, PriceTo, manufacturer, conditions, model, manufactureYear, numberOfDoor, color, transmission, fuelType, mileage, engineSize, pageable);
    }

    public Page<Post> findAllVillaByProperties(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String furnished, String numberOfRoom, String numberOfBathroom, String kitchenType, String parking, String deposit, Pageable pageable) {
        return postRepository.findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndVilla_FurnishedContainingIgnoreCaseAndVilla_NumberOfRoomContainingIgnoreCaseAndVilla_NumberOfBathroomContainingIgnoreCaseAndVilla_KitchenTypeContainingIgnoreCaseAndVilla_ParkingContainingIgnoreCaseAndVilla_DepositContainingIgnoreCase(title, address, subCategory, priceFrom, PriceTo, furnished, numberOfRoom, numberOfBathroom, kitchenType, parking, deposit, pageable);
    }

}
