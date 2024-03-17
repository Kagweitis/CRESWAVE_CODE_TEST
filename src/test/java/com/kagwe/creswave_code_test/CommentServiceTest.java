package com.kagwe.creswave_code_test;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.DTO.CommentRequest;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Entities.Comment;
import com.kagwe.creswave_code_test.Repository.BlogRepository;
import com.kagwe.creswave_code_test.Repository.CommentRepository;
import com.kagwe.creswave_code_test.Service.BlogService;
import com.kagwe.creswave_code_test.Service.CommentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private CommentsService commentsService;

    @Mock
    private BlogService blogService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {

        // Mock data
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setBlogId(1L);
        commentRequest.setComment("Test comment");
        commentRequest.setCommentorName("John Doe");
        commentRequest.setDeleted(false);
        commentRequest.setBlogId(1L);
        commentRequest.setCreatedAt(Instant.now());

        Blog blog = new Blog();

        blog.setDeleted(false);
        blog.setBlogPost("post");
        blog.setAuthor("me");
        blog.setId(1L);

        blogService.createBlog(blog);

        Comment savedComment = new Comment();
        savedComment.setId(1L);
        savedComment.setComment("Test comment");
        savedComment.setCommentorName("John Doe");
        savedComment.setDeleted(false);
        savedComment.setBlogId(commentRequest.getBlogId());
        savedComment.setCreatedAt(commentRequest.getCreatedAt());

        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // Call the createComment method
        ResponseEntity<BlogResponse> response = commentsService.createComment(commentRequest);

        // Assert the response
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getComments().size());
    }

    @Test
    public void testEditComment() {
        // Mock data
        Comment existingComment = new Comment();
        existingComment.setId(1L);
        existingComment.setComment("Old Comment");

        Comment updatedComment = new Comment();
        updatedComment.setId(1L);
        updatedComment.setComment("Updated Comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedComment);

        // Call the editComment method
        ResponseEntity<BlogResponse> response = commentsService.editComment(updatedComment);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comment edited successfully", response.getBody().getMessage());
        assertEquals(1, response.getBody().getComments().size());
        assertEquals("Updated Comment", response.getBody().getComments().get(0).getComment());
    }

    @Test
    public void testDeleteComment() {
        // Mock data
        Comment existingComment = new Comment();
        existingComment.setId(1L);
        existingComment.setDeleted(false);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(existingComment);

        // Call the deleteComment method
        ResponseEntity<BlogResponse> response = commentsService.deleteComment(1L);

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Comment deleted successfully", response.getBody().getMessage());
        assertTrue(response.getBody().getComments().get(0).getDeleted());
    }

    @Test
    public void testGetCommentsForBlog() {
        // Mock data
        Long blogId = 1L;
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());

        Page<Comment> commentsPage = new PageImpl<>(comments);

        when(commentRepository.findPageByBlogId(blogId, PageRequest.of(0, 10)))
                .thenReturn(commentsPage);

        // Call the getCommentsForBlog method
        ResponseEntity<BlogResponse> response = commentsService.getCommentsForBlog(blogId, 0, 10, "createdAt", "asc");

        // Assert the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("comments found", response.getBody().getMessage());
        assertEquals(2, response.getBody().getComments().size());
    }


}
