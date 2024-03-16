package com.kagwe.creswave_code_test.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private String commentorName;
    private Long blogId;
    private Boolean deleted;

}
