package com.ravi.semester.controller;

import com.ravi.semester.dto.UserDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
    @GetMapping("pic/getuser")
    UserDto getUser(){
//        return new UserDto("student","7376211AE132");
        return new UserDto("faculty","MC007");
//        return new UserDto("admin","1");
//        return new UserDto("indsPartner","DJ555");
    }
}
