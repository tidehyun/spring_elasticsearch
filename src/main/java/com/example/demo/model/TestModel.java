package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TestModel {

    private String place;
    private String address;
    private String keyword;
    private String reporter;
    private String question;
    private LocationModel location;
}
