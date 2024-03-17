package com.kagwe.creswave_code_test.Controller;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.DTO.CommentRequest;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Entities.Comment;
import com.kagwe.creswave_code_test.Service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/new_comment")
    public ResponseEntity<BlogResponse> postComment(@RequestBody CommentRequest comment){
        return commentsService.createComment(comment);
    }

    @GetMapping("/get_comments")
    public ResponseEntity<BlogResponse> getAllCommentsForABlog(@RequestParam Long id,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "createdAt") String sortBy,
                                                       @RequestParam(defaultValue = "DESC") String sortOrder){
        return commentsService.getCommentsForBlog(id, page, size, sortBy, sortOrder);
    }

    @PutMapping("/edit_blog")
    public ResponseEntity<BlogResponse> editComment(@RequestBody @NonNull Comment comment){
        return commentsService.editComment(comment);
    }

    @PutMapping("/delete_blog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogResponse> deleteComment(@RequestParam @NonNull Long id){
        return commentsService.deleteComment(id);
    }
}
