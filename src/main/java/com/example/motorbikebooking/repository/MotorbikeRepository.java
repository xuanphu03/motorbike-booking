package com.example.motorbikebooking.repository;

import com.example.motorbikebooking.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorbikeRepository extends JpaRepository<Motorbike, Long> {}
