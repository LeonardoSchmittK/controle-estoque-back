package com.controle_estoque_back.entity;

public class Category {
    private Long id;
    private String name;
    private String size;      
    private String packaging; 

    public Category() {
    }

    public Category(Long id, String name, String size, String packaging) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.packaging = packaging;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {   // <— ✔ THIS FIXES THE ERROR
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

}