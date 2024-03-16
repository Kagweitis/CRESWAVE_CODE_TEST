package com.kagwe.creswave_code_test.Service;


import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Entities.Comment;
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
@RequiredArgsConstructor
@Slf4j
public class CommentsService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;


    public ResponseEntity<BlogResponse> createComment(Comment comment) {

        BlogResponse response = new BlogResponse();

        try{
//            log.info("blog id "+comment.);
            commentRepository.save(comment);
            response.setComments(Collections.singletonList(comment));
            response.setMessage("comment posted");
            response.setCode(201);
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            log.error("an error occured while posting your comment: "+e.getMessage());
            response.setMessage("an error occured while posting your omment");
            response.setCode(500);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<BlogResponse> getCommentsForBlog(Long blogId) {
        BlogResponse response = new BlogResponse();

        try {
            List<Comment> comments = commentRepository.findByBlogId(blogId);
            if(comments.isEmpty()){
                response.setMessage("No comments");
                response.setCode(204);
            } else {
                response.setComments(comments);
                response.setCode(200);
                response.setMessage("comments found");
            }
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            log.error("an error occured while getting the comments: "+e.getMessage());
            response.setMessage("an error occured while getting the comments");
            response.setCode(500);
            return ResponseEntity.internalServerError().body(response);
        }

    }

    public ResponseEntity<BlogResponse> editComment(Comment comment){
        BlogResponse resp = new BlogResponse();

        try {
            AtomicReference<BlogResponse> res = new AtomicReference<>(new BlogResponse());
            Optional<Comment> existingComment = commentRepository.findById(comment.getId());
            existingComment.ifPresentOrElse(existingComment1 -> {
                BlogResponse response = res.get();
                existingComment1.setComment(comment.getComment() != null ? comment.getComment() : existingComment.get().getComment());
                existingComment1.setCommentorName(comment.getCommentorName() != null ? comment.getCommentorName() : existingComment.get().getCommentorName());
                commentRepository.save(existingComment1);
                response.setCode(200);
                response.setMessage("Comment edited successfully");
                response.setComments(Collections.singletonList(existingComment1));
            }, () -> {
                BlogResponse response = res.get();
                response.setCode(404);
                response.setMessage("No Comment with that Id found");
            });
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while editing comment: "+e.getMessage());
            resp.setMessage("an error occured while editing comment. Try again later");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }

    public ResponseEntity<BlogResponse> deleteComment(Long id){
        BlogResponse resp = new BlogResponse();

        try {
            AtomicReference<BlogResponse> res = new AtomicReference<>(new BlogResponse());
            Optional<Comment> existingComment = commentRepository.findById(id);
            existingComment.ifPresentOrElse(existingComment1 -> {
                BlogResponse response = res.get();
                existingComment1.setDeleted(true);
                commentRepository.save(existingComment1);
                response.setCode(200);
                response.setMessage("Comment deleted successfully");
                response.setComments(Collections.singletonList(existingComment1));
            }, () -> {
                BlogResponse response = res.get();
                response.setCode(404);
                response.setMessage("No Comment with that Id found");
            });
            return ResponseEntity.ok().body(resp);
        } catch (Exception e){
            log.error("an error occured while deleting comment: "+e.getMessage());
            resp.setMessage("an error occured while deleting comment. Try again later");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }
}
