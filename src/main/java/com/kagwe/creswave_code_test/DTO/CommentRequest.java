package com.kagwe.creswave_code_test.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    private String comment;
    private String commentorName;
    private Long blogId;
    private Boolean deleted;
    private Instant createdAt;
}
