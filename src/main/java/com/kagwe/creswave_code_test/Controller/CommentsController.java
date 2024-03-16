package com.kagwe.creswave_code_test.Controller;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Entities.Comment;
import com.kagwe.creswave_code_test.Service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/blog")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/new_comment")
    public ResponseEntity<BlogResponse> postBlog(@RequestBody @NonNull Comment comment){
        return commentsService.createComment(comment);
    }

    @GetMapping("/get_comments")
    public ResponseEntity<BlogResponse> getAllBlogs(@RequestParam Long blogId){
        return commentsService.getCommentsForBlog(blogId);
    }

    @PutMapping("/edit_blog")
    public ResponseEntity<BlogResponse> editBlog(@RequestBody @NonNull Comment comment){
        return commentsService.editComment(comment);
    }

    @PutMapping("/delete_blog")
    public ResponseEntity<BlogResponse> deleteBlog(@RequestParam @NonNull Long id){
        return commentsService.deleteComment(id);
    }
}
