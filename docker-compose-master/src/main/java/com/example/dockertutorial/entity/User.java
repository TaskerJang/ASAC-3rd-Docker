package com.example.dockertutorial.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "\"User\"")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "age")
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}