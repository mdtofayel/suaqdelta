package com.happyride.eservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.happyride.eservice.model.Condition;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Vehicles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String manufacturer;

    private Condition condition;

    @NotEmpty
    private String model;

    @NotEmpty
    private String manufacturYear;

    private String bodyType;

    private String numberOfDoor;

    private String color;

    private String transmission;

    private String fuelType;

    private String serviceHistory;

    private String mileage;

    private String engineSize;

    @OneToOne
    @JsonIgnore
    private Post post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturYear() {
        return manufacturYear;
    }

    public void setManufacturYear(String manufacturYear) {
        this.manufacturYear = manufacturYear;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getNumberOfDoor() {
        return numberOfDoor;
    }

    public void setNumberOfDoor(String numberOfDoor) {
        this.numberOfDoor = numberOfDoor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(String serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(String engineSize) {
        this.engineSize = engineSize;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
/*        1. Title`
        2. Description`
        3. Price`
        4. Latitude`
        5. Longitude`
        6. Photos`
        7. Videos`
        8. Post Type`
        9. Sub-category`
        10. Category`
        11. Manufacturer`
        12. Condition`
        13. Model`
        14. Manufacturing Year
        15. Body Type
        16. Number Of Doors
        17. Color`
        18. Transmission
        19. Fuel Type
        20. Service History
        21. Mileage
        22. Engine Size
        23. Contact Number
        24. createAt
        25. updateAt
        26. disableAt*/