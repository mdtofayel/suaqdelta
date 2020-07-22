package com.happyride.eservice.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.happyride.eservice.model.Condition;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String manufacturer;

    private Condition condition;

    private String ram;

    private String processor;

    private String storage;

    private String display;

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

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}


/*        1. Title
        2. Description `
        3. Price`
        4. Latitude`
        5. Longitude`
        6. Photos`
        7. Videos`
        8. Post Type`
        9. Sub-category`
        10. Category`
        11. Manufacturer`
        12. RAM`
        13. Processor
        14. Storage
        15. Display
        16. Condition
        17. Contact Number*/