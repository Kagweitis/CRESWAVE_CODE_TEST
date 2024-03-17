package com.kagwe.creswave_code_test.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String blogPost;
    private String author;
    private Boolean deleted = false;

//    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "blogId", referencedColumnName = "id")
    private List<Comment> comments;

    @CreationTimestamp
    private Instant createdAt;
}
