package com.example.motorbikebooking.controller;

import com.example.motorbikebooking.entity.Motorbike;
import com.example.motorbikebooking.entity.RentalOrder;
import com.example.motorbikebooking.repository.MotorbikeRepository;
import com.example.motorbikebooking.repository.RentalOrderRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class MotorbikeController {

    @Autowired
    private MotorbikeRepository motorbikeRepo;

    @Autowired
    private RentalOrderRepository orderRepo;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("motorbikes", motorbikeRepo.findAll());
        return "motorbike_list";
    }

    @GetMapping("/rent/form")
    public String showForm(@RequestParam Long bikeId, Model model) {
        RentalOrder order = new RentalOrder();
        order.setMotorbike(motorbikeRepo.findById(bikeId).orElseThrow());

        Motorbike bike = motorbikeRepo.findById(bikeId).orElseThrow();

        model.addAttribute("bike", bike);
        model.addAttribute("rentalOrder", order);
        return "rent_form";
    }

    @PostMapping("/rent/submit")
    public String submit(@ModelAttribute RentalOrder rentalOrder) {
        orderRepo.save(rentalOrder);
        return "redirect:/";
    }

    @GetMapping("/admin/orders")
    public String viewOrders(Model model) {

        long totalOrders = orderRepo.count();
        long totalSuccess = orderRepo.countByStatus("Đã xác nhận");
        long totalPending = orderRepo.countByStatus("Chờ xác nhận");
        long totalCancelled = orderRepo.countByStatus("Đã hủy");

        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalSuccess", totalSuccess);
        model.addAttribute("totalPending", totalPending);
        model.addAttribute("totalCancelled", totalCancelled);
        model.addAttribute("orders", orderRepo.findAll());
        return "orders";
    }

    @PostMapping("/admin/confirm")
    public String confirmOrder(@RequestParam Long id) {
        RentalOrder order = orderRepo.findById(id).orElseThrow();
        order.setStatus("Đã xác nhận");
        orderRepo.save(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/admin/cancel")
    public String cancelOrder(@RequestParam Long id) {
        RentalOrder order = orderRepo.findById(id).orElseThrow();
        order.setStatus("Đã hủy");
        orderRepo.save(order);
        return "redirect:/admin/orders";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // trỏ tới templates/login.html
    }

    @GetMapping("/admin/motorbikes")
    public String motorbikesPage(Model model) {
        model.addAttribute("motorbikes", motorbikeRepo.findAll());
        model.addAttribute("motorbikeDTO", new Motorbike());
        return "motorbikes";
    }

    @GetMapping("admin/motorbikes/add")
    public String showAddForm(Model model) {
        model.addAttribute("motorbikeDTO", new Motorbike());
        return "admin/motorbike_form";
    }

    @PostMapping("/add")
    public String saveMotorbike(@ModelAttribute("motorbikeDTO") Motorbike motorbikeDTO) {
        Motorbike motorbike = new Motorbike();
        motorbike.setName(motorbikeDTO.getName());
        motorbike.setPricePerDay(motorbikeDTO.getPricePerDay());
        motorbike.setType(motorbikeDTO.getType());
        motorbike.setStatus(motorbikeDTO.getStatus());
        motorbike.setImageUrl(motorbikeDTO.getImageUrl());

        motorbikeRepo.save(motorbike);
        return "redirect:/admin/motorbikes";
    }

    @PostMapping("/motorbikes/delete/{id}")
    public String deleteMotorbike(@PathVariable Long id) {
        motorbikeRepo.deleteById(id);
        return "redirect:/admin/motorbikes";
    }

    @PostMapping("/motorbikes/update")
    public String updateMotorbike(@ModelAttribute Motorbike motorbike) {
        motorbikeRepo.save(motorbike);
        return "redirect:/admin/motorbikes";
    }
}