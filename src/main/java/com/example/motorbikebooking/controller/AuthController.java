package com.example.motorbikebooking.controller;

import com.example.motorbikebooking.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // Bỏ qua xác thực thực tế, bạn có thể thêm xác thực từ DB
        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername());
            return new LoginResponse(token); // Frontend sẽ lưu vào localStorage
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
