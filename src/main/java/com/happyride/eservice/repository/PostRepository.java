package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.*;
import com.happyride.eservice.model.Condition;
import com.happyride.eservice.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findTopByOrderByPriceDesc();

    Optional<Post> findTopBySubCategoryOrderByPriceDesc(SubCategory subCategory);

    Optional<Post> findTopBySubCategory_CategoryOrderByPriceDesc(Category category);

    Optional<Post> findTopBySubCategory_Category_PostTypeOrderByPriceDesc(PostType postType);

    Page<Post> findPostByGeneric(Generic generic, Pageable pageable);

    Page<Post> findPostByPostedBy(Users users, Pageable pageable);

    Page<Post> findAllBySubCategory(SubCategory subCategory, Pageable pageable);

    Page<Post> findAllBySubCategoryIn(List<SubCategory> subCategory, Pageable pageable);

    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Post> findByTitleContainingIgnoreCaseAndSubCategoryIn(String title, List<SubCategory> subCategory, Pageable pageable);

    Page<Post> findAllBySubCategoryInAndPriceBetween(List<SubCategory> subCategory, Double priceFrom, Double PriceTo, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndSubCategoryInAndPriceBetween(String title, List<SubCategory> subCategory, Double priceFrom, Double PriceTo, Pageable pageable);

    Page<Post> findAllByPriceBetween(Double priceFrom, Double PriceTo, Pageable pageable);

    List<Post> findByApprovedContaining(String approvedStatus);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndPetAnimal_AgeBetweenAndPetAnimal_QuantityBetweenAndPetAnimal_GenderInAndPetAnimal_ColorContainingIgnoreCase(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, Double ageFrom, Double ageTo, Integer quantityFrom, Integer quantityTo, List<Gender> gender, String color, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndGeneric_ManufacturerNameContainingIgnoreCaseAndGeneric_ConditionIn(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturerName, List<Condition> Condition, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndHire_EmploymentTypeContainingIgnoreCaseAndHire_PositionContainingIgnoreCaseAndHire_JobLevelContainingIgnoreCaseAndHire_TypeContainingIgnoreCaseAndHire_NationalityContainingIgnoreCaseAndHire_genderInAndHire_ExperiencesContainingIgnoreCaseAndHire_LanguageContainingIgnoreCase(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String employmentType, String position, String jobLevel, String type, String nationality, List<Gender> gender, String experiences, String language, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndLaptop_ManufacturerContainingIgnoreCaseAndLaptop_RamContainingIgnoreCaseAndLaptop_ProcessorContainingIgnoreCaseAndLaptop_StorageContainingIgnoreCaseAndLaptop_DisplayContainingIgnoreCaseAndLaptop_ConditionIn(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturerName, String ram, String processor, String Storage, String Display, List<Condition> Condition, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndSmartPhones_ManufacturerContainingIgnoreCaseAndSmartPhones_ModelContainingIgnoreCaseAndSmartPhones_RamBetweenAndSmartPhones_ProcessorContainingIgnoreCaseAndSmartPhones_ColorContainingIgnoreCaseAndSmartPhones_StorageContainingIgnoreCaseAndSmartPhones_NetworkTypeContainingIgnoreCaseAndSmartPhones_SimTypeContainingIgnoreCaseAndSmartPhones_FrontCameraContainingIgnoreCaseAndSmartPhones_BackCameraContainingIgnoreCaseAndSmartPhones_DisplayContainingIgnoreCaseAndSmartPhones_ConditionIn(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturerName, String model, int ramFrom, int ramTo, String processor, String color, String Storage, String networkType, String simType, String fontCamera, String backCamera, String Display, List<Condition> Condition, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetween(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndJobSeeker_EmploymentTypeContainingIgnoreCaseAndJobSeeker_PositionContainingIgnoreCaseAndJobSeeker_NationalityContainingIgnoreCaseAndJobSeeker_GenderInAndJobSeeker_ExperiencesContainingIgnoreCaseAndJobSeeker_LanguageContainingIgnoreCaseAndJobSeeker_TypeContainingIgnoreCase(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String employmentType, String position, String nationality, List<Gender> gender, String experiences, String language, String type, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndVehicles_ManufacturerContainingIgnoreCaseAndVehicles_ConditionInAndVehicles_ModelContainingIgnoreCaseAndVehicles_ManufacturYearContainingIgnoreCaseAndVehicles_NumberOfDoorContainingIgnoreCaseAndVehicles_ColorContainingIgnoreCaseAndVehicles_TransmissionContainingIgnoreCaseAndVehicles_FuelTypeContainingIgnoreCaseAndVehicles_MileageContainingIgnoreCaseAndVehicles_EngineSizeContainingIgnoreCase(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String manufacturer, List<Condition> conditions, String model, String manufactureYear, String numberOfDoor, String color, String transmission, String fuelType, String mileage, String engineSize, Pageable pageable);

    Page<Post> findAllByTitleContainingIgnoreCaseAndAddressContainingIgnoreCaseAndSubCategoryAndPriceBetweenAndVilla_FurnishedContainingIgnoreCaseAndVilla_NumberOfRoomContainingIgnoreCaseAndVilla_NumberOfBathroomContainingIgnoreCaseAndVilla_KitchenTypeContainingIgnoreCaseAndVilla_ParkingContainingIgnoreCaseAndVilla_DepositContainingIgnoreCase(String title, String address, SubCategory subCategory, Double priceFrom, Double PriceTo, String furnished, String numberOfRoom, String numberOfBathroom, String kitchenType, String parking, String deposit, Pageable pageable);
}
