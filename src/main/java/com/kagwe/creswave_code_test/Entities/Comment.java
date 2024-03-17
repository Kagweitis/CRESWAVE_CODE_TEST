package com.kagwe.creswave_code_test.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.CloseableThreadContext;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "blog_id", referencedColumnName = "id")
//    private Blog blog;

    private Long blogId;

    private Boolean deleted =false;

    @CreationTimestamp
    private Instant createdAt;

}
