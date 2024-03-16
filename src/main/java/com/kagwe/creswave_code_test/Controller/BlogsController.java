package com.kagwe.creswave_code_test.Controller;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Service.BlogService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/blog")
@RequiredArgsConstructor
public class BlogsController {

    private final BlogService blogService;


    @PostMapping("/new_blog")
    public ResponseEntity<BlogResponse> postBlog(@RequestBody @NonNull  Blog blog){
        return blogService.createBlog(blog);
    }

    @GetMapping("/get_blogs")
    public ResponseEntity<BlogResponse> getAllBlogs(){
        return blogService.getAllBlogs();
    }

    @PutMapping("/edit_blog")
    public ResponseEntity<BlogResponse> editBlog(@RequestBody @NonNull Blog blog){
        return blogService.editBlog(blog);
    }

    @PutMapping("/delete_blog")
    public ResponseEntity<BlogResponse> deleteBlog(@RequestParam @NonNull Long id){
        return blogService.deleteBlog(id);
    }

}
