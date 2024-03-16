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
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
            resp.setCode(201);
            resp.setMessage("Blog posted successfully");
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while posting your blog: "+e.getMessage());
            resp.setMessage("an error occured while posting your blog");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    public ResponseEntity<BlogResponse> getAllBlogs(){
        BlogResponse resp = new BlogResponse();

        try {
            List<Blog> blogs = blogRepository.findAllByDeletedFalse();
            if (blogs.isEmpty()){
                resp.setMessage("No blogs available right now");
                resp.setCode(204);
            } else {
                resp.setCode(200);
                resp.setMessage("Blogs found!");
                resp.setBlogs(blogs);
            }
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while getting blogs: "+e.getMessage());
            resp.setMessage("an error occured while getting blogs. Try again later");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    public ResponseEntity<BlogResponse> editBlog(Blog blogEdit){
        BlogResponse resp = new BlogResponse();

        try {
            AtomicReference<BlogResponse> res = new AtomicReference<>(new BlogResponse());
            Optional<Blog> existingBlog = blogRepository.findById(blogEdit.getId());
            existingBlog.ifPresentOrElse(existingBlog1 -> {
                BlogResponse response = res.get();
                existingBlog1.setBlogPost(blogEdit.getBlogPost() != null ? blogEdit.getBlogPost() : existingBlog.get().getBlogPost());
                existingBlog1.setAuthor(blogEdit.getAuthor() != null ? blogEdit.getAuthor() : existingBlog.get().getAuthor());
                existingBlog1.setTitle(blogEdit.getTitle() != null ? blogEdit.getTitle() : existingBlog.get().getTitle());
                blogRepository.save(existingBlog1);
                response.setCode(200);
                response.setMessage("Blog edited successfully");
                response.setBlogs(Collections.singletonList(existingBlog1));
            }, () -> {
                BlogResponse response = res.get();
                response.setCode(404);
                response.setMessage("No blog with that Id found");
            });
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while editing blog: "+e.getMessage());
            resp.setMessage("an error occured while editing blog. Try again later");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    public ResponseEntity<BlogResponse> deleteBlog(Long id) {
        BlogResponse resp = new BlogResponse();

        try {
            AtomicReference<BlogResponse> res = new AtomicReference<>(new BlogResponse());
            Optional<Blog> existingBlog = blogRepository.findById(id);
            existingBlog.ifPresentOrElse(existingBlog1 -> {
                BlogResponse response = res.get();
                existingBlog1.setDeleted(true);
                blogRepository.save(existingBlog1);
                response.setCode(200);
                response.setMessage("Blog deleted successfully");
                response.setBlogs(Collections.singletonList(existingBlog1));
            }, () -> {
                BlogResponse response = res.get();
                response.setCode(404);
                response.setMessage("No blog with that Id found");
            });
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while deleting blog: "+e.getMessage());
            resp.setMessage("an error occured while deleting blog. Try again later");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }
}
