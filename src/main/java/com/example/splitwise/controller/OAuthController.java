package com.example.splitwise.controller;

import com.example.splitwise.entity.UserEntity;
import com.example.splitwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OAuthController {

    private final UserRepository userRepository;

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model) {
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        UserEntity user = userRepository.findByEmail(email).orElseGet(() -> {
            UserEntity newUser = UserEntity.builder()
                    .userName(name)
                    .email(email)
                    .oauthId(email)
                    .build();
            return userRepository.save(newUser);
        });

        model.addAttribute("userName", user.getUserName());
        model.addAttribute("email", user.getEmail());
        return "loginSuccess"; // Thymeleaf template name without .html
    }
}

