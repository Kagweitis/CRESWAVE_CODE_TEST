package com.kagwe.creswave_code_test.Service;


import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.DTO.CommentRequest;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Entities.Comment;
import com.kagwe.creswave_code_test.Repository.BlogRepository;
import com.kagwe.creswave_code_test.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public ResponseEntity<BlogResponse> createComment(CommentRequest comment) {

        BlogResponse response = new BlogResponse();

        try{
            // Fetch the corresponding Blog object by its ID
            Blog blog = blogRepository.findById(comment.getBlogId())
                    .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
            Comment newComment = new Comment();
            log.info("============blog id "+comment.getBlogId());
            newComment.setBlogId(comment.getBlogId());
            newComment.setCreatedAt(comment.getCreatedAt());
            newComment.setDeleted(comment.getDeleted());
            newComment.setComment(comment.getComment());
            newComment.setCommentorName(comment.getCommentorName());
            commentRepository.save(newComment);
            response.setComments(Collections.singletonList(newComment));
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

    public ResponseEntity<BlogResponse> getCommentsForBlog(Long blogId, int page, int size, String sortBy, String sortOrder ) {
        BlogResponse response = new BlogResponse();

        try {
            // Create a Pageable object for pagination and sorting
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));

            // Fetch comments for the given blog ID with pagination and sorting
            log.info("pageable "+pageable);
            Page<Comment> commentsPage = commentRepository.findPageByBlogId(blogId, pageable);
            log.info("page size "+commentsPage.getSize());
//            List<Comment> comments = commentRepository.findByBlogId(blogId);
            if(commentsPage.isEmpty()){
                response.setMessage("No comments");
                response.setCode(204);
            } else {
                response.setComments(commentsPage.getContent());
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
            AtomicReference<ResponseEntity<BlogResponse>> responseEntity = new AtomicReference<>();
            AtomicReference<BlogResponse> res = new AtomicReference<>(new BlogResponse());
            Optional<Comment> existingComment = commentRepository.findById(id);
            existingComment.ifPresentOrElse(existingComment1 -> {
                BlogResponse response = res.get();
                existingComment1.setDeleted(true);
                commentRepository.save(existingComment1);
                response.setCode(200);
                response.setMessage("Comment deleted successfully");
                response.setComments(Collections.singletonList(existingComment1));
                responseEntity.set(ResponseEntity.ok().body(response));
            }, () -> {
                BlogResponse response = res.get();
                response.setCode(404);
                response.setMessage("No Comment with that Id found");
                responseEntity.set(ResponseEntity.ok().body(response));
            });
            return responseEntity.get();
        } catch (Exception e){
            log.error("an error occured while deleting comment: "+e.getMessage());
            resp.setMessage("an error occured while deleting comment. Try again later");
            resp.setCode(500);
            return ResponseEntity.internalServerError().body(resp);
        }
    }
}
