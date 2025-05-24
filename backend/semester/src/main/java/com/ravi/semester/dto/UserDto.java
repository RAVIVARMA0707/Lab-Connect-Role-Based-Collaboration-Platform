package com.ravi.semester.dto;

public class UserDto {
    private String userType;
    private String id;


    public UserDto(String userType, String id) {
        this.userType = userType;
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
