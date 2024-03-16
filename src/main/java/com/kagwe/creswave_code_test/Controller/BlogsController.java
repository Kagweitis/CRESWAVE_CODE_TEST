package com.kagwe.creswave_code_test.Controller;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/blog")
@RequiredArgsConstructor
public class BlogsController {

    private final BlogService blogService;


    @PostMapping("/new_blog")
    public ResponseEntity<BlogResponse> postBlog(@RequestBody Blog blog){
        return blogService.createBlog(blog);
    }

}
