package com.blibli.future.Model;

import javax.persistence.*;

/**
 * Created by Nita on 15/10/2016.
 */

@Entity
@Table(name="product")
public class Product {
    public static final String HERBS="Herbs";
    public static final String SPICE="Spice";
    public static final String OTHER="Other";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String unit;
    private Integer firstPrice;
    private Integer secondPrice;
    private Integer firstMinQuantity;
    private Integer secondMinQuantity;
    private String category;
    private String description;
    private String status = "Available";
    private String picture;
    @ManyToOne
    private Merchant merchant;
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isHerbs(){
        return this.category.equals("Herbs");
    }
    public boolean isSpice(){
        return this.category.equals("Spice");
    }
    public boolean isOther(){
        return this.category.equals("Other");
    }

    public boolean isKilogram(){
        return this.unit.equals("kg");
    }
    public boolean isGram(){
        return this.unit.equals("gram");
    }
    public boolean isSack(){
        return this.unit.equals("sack");
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Integer getPrice(){
        return firstPrice;
    }

    public Integer getFirstPrice() {
        return firstPrice;
    }

    public void setFirstPrice(Integer firstPrice) {
        this.firstPrice = firstPrice;
    }

    public Integer getSecondPrice() {
        return secondPrice;
    }

    public void setSecondPrice(Integer secondPrice) {
        this.secondPrice = secondPrice;
    }

    public Integer getFirstMinQuantity() {
        return firstMinQuantity;
    }

    public void setFirstMinQuantity(Integer firstMinQuantity) {
        this.firstMinQuantity = firstMinQuantity;
    }

    public Integer getSecondMinQuantity() {
        return secondMinQuantity;
    }

    public void setSecondMinQuantity(Integer secondMinQuantity) {
        this.secondMinQuantity = secondMinQuantity;
    }
}
