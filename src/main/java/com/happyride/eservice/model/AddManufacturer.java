package com.happyride.eservice.model;

public class AddManufacturer {
    private Long subcategoryId;

    private Long[] listOfManufacturer;

    public Long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Long[] getListOfManufacturer() {
        return listOfManufacturer;
    }

    public void setListOfManufacturer(Long[] listOfManufacturer) {
        this.listOfManufacturer = listOfManufacturer;
    }
}
