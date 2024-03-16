package com.kagwe.creswave_code_test.Service;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Repository.BlogRepository;
import com.kagwe.creswave_code_test.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<BlogResponse> createBlog(Blog newBlog){
        BlogResponse resp = new BlogResponse();

        try {
            blogRepository.save(newBlog);
            resp.setBlogs(Collections.singletonList(newBlog));
            resp.setCode(200);
            resp.setMessage("Blog posted successfully");
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while posting your blog: "+e.getMessage());
            resp.setMessage("an error occured while posting your blog");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }
}
