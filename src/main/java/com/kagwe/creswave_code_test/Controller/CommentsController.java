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
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/new_comment")
    public ResponseEntity<BlogResponse> postComment(@RequestBody Comment comment){
        return commentsService.createComment(comment);
    }

    @GetMapping("/get_comments")
    public ResponseEntity<BlogResponse> getAllComments(@RequestParam Long id){
        return commentsService.getCommentsForBlog(id);
    }

    @PutMapping("/edit_blog")
    public ResponseEntity<BlogResponse> editComment(@RequestBody @NonNull Comment comment){
        return commentsService.editComment(comment);
    }

    @PutMapping("/delete_blog")
    public ResponseEntity<BlogResponse> deleteComment(@RequestParam @NonNull Long id){
        return commentsService.deleteComment(id);
    }
}
