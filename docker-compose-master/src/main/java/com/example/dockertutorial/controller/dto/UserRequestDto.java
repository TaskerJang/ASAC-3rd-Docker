package com.example.dockertutorial.controller.dto;

import com.example.dockertutorial.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String name;

    private Integer age;

    public User toEntity() {
        return new User(name, age);
    }
}
