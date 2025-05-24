package com.ravi.semester.controller;

import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.IndsPartner;
import com.ravi.semester.model.Student;
import com.ravi.semester.repository.FacultyRepo;
import com.ravi.semester.repository.IndsPartnerRepo;
import com.ravi.semester.repository.StudentRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController

//@CrossOrigin("https://9a82-2401-4900-1f2b-844b-65c9-d6a5-21fa-6f31.ngrok-free.app")
public class LoginController {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private FacultyRepo facultyRepo;
    @Autowired
    private IndsPartnerRepo indsPartnerRepo;

    @GetMapping("/login/success")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) {
        if (principal == null) {
                System.out.println("No session found!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
        String email = principal.getAttribute("email");

        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found.");
        }

//         Check for student, faculty, partner, or admin
        if (email.equals("ravivarma.mc21@bitsathy.ac.in")) {
            return ResponseEntity.ok(Map.of("id", 0, "userType", "admin","userName","BIT","profilePic","https://upload.wikimedia.org/wikipedia/en/7/77/Bannari_Amman_Institute_of_Technology_logo.png"));
        } else
        if (studentRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.ok(Map.of("id", studentRepo.findByEmail(email).get().getId(), "userType", "student","userName",studentRepo.findByEmail(email).get().getName(),"profilePic",principal.getAttribute("picture")));
        } else if (facultyRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.ok(Map.of("id", facultyRepo.findByEmail(email).get().getId(), "userType", "faculty","userName",facultyRepo.findByEmail(email).get().getName(),"profilePic",principal.getAttribute("picture")));
        } else if (indsPartnerRepo.findByEmail(email).isPresent()) {
            return ResponseEntity.ok(Map.of("id", indsPartnerRepo.findByEmail(email).get().getId(), "userType", "indsPartner","userName",indsPartnerRepo.findByEmail(email).get().getName(),"profilePic",principal.getAttribute("picture")));
        }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

    }
