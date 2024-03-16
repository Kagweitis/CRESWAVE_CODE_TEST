package com.kagwe.creswave_code_test.DTO;

import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponse {
    private int code;
    private String message;
    private List<Blog> blogs;
    private List<Comment> comments;
}
