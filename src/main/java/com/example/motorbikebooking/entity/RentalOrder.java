package com.example.motorbikebooking.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
public class RentalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Motorbike motorbike;

    private String customerName;
    private String phone;
    private LocalDate rentDate;
    private String place;
    private String payType;
    private int rentalDays = 1;
    private String rentDead;

    private String status = "Chờ xác nhận"; // hoặc CONFIRMED

    public String getRentDead() {
        return rentDead;
    }

    public void setRentDead(String rentDead) {
        this.rentDead = rentDead;
    }

    public String formatRentDead() {
        LocalDate endDate = rentDate.plusDays(rentalDays);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedRange = formatter.format(rentDate) + " - " + formatter.format(endDate);
        return formattedRange;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Motorbike getMotorbike() {
        return motorbike;
    }

    public void setMotorbike(Motorbike motorbike) {
        this.motorbike = motorbike;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String Payment(int rentalDays, float fee) {
        String rs = String.format("%,.0f đ",  rentalDays * fee);
        return rs;
    }
}