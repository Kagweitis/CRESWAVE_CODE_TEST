package com.kagwe.creswave_code_test;

import com.kagwe.creswave_code_test.DTO.BlogResponse;
import com.kagwe.creswave_code_test.Entities.Blog;
import com.kagwe.creswave_code_test.Repository.BlogRepository;
import com.kagwe.creswave_code_test.Service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBlog_ValidBlog_ReturnsResponseEntityWithCreatedBlog() {
        // Arrange
        Blog newBlog = new Blog();
        when(blogRepository.save(any(Blog.class))).thenReturn(newBlog);

        // Act
        BlogResponse response = blogService.createBlog(newBlog).getBody();

        // Assert
        assertEquals(Collections.singletonList(newBlog), response.getBlogs());
        assertEquals(201, response.getCode());
        assertEquals("Blog posted successfully", response.getMessage());
    }

    @Test
    void getAllBlogs_ValidRequest_ReturnsResponseEntityWithBlogs() {
        // Arrange
        Page<Blog> blogPage = new PageImpl<>(Collections.singletonList(new Blog()));
        when(blogRepository.findAllByDeletedFalse(any())).thenReturn(blogPage);

        // Act
        BlogResponse response = blogService.getAllBlogs(0, 10, "title", "asc").getBody();

        // Assert
        assertEquals(blogPage.getContent(), response.getBlogs());
        assertEquals(200, response.getCode());
        assertEquals("Blogs found!", response.getMessage());
    }

    @Test
    void editBlog_ValidBlog_ReturnsResponseEntityWithEditedBlog() {
        // Arrange
        Blog existingBlog = new Blog();
        existingBlog.setId(1L);
        existingBlog.setTitle("Existing Title");
        existingBlog.setBlogPost("Existing Content");
        existingBlog.setAuthor("Existing Author");

        Blog editedBlog = new Blog();
        editedBlog.setId(1L);
        editedBlog.setTitle("Edited Title");
        editedBlog.setBlogPost("Edited Content");
        editedBlog.setAuthor("Edited Author");
        editedBlog.setDeleted(false);

        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(existingBlog));
        when(blogRepository.save(any(Blog.class))).thenReturn(editedBlog);

        // Act
        BlogResponse response = blogService.editBlog(editedBlog).getBody();

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("Blog edited successfully", response.getMessage());
        assertEquals(Collections.singletonList(editedBlog), response.getBlogs());
    }

    @Test
    void deleteBlog_ValidBlogId_ReturnsResponseEntityWithDeletedBlog() {
        // Arrange
        Blog existingBlog = new Blog();
        existingBlog.setId(1L);
        existingBlog.setDeleted(false);

        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(existingBlog));
        when(blogRepository.save(any(Blog.class))).thenReturn(existingBlog);

        // Act
        BlogResponse response = blogService.deleteBlog(1L).getBody();

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("Blog deleted successfully", response.getMessage());
        assertEquals(Collections.singletonList(existingBlog), response.getBlogs());
    }

    @Test
    void searchBlogByTitleOrContent_ValidSearchCriteria_ReturnsResponseEntityWithMatchingBlogs() {
        // Arrange
        Page<Blog> searchResultPage = new PageImpl<>(Collections.singletonList(new Blog()));
        when(blogRepository.findByTitleContainingOrBlogPostContaining(anyString(), anyString(), any())).thenReturn(searchResultPage);

        // Act
        BlogResponse response = blogService.searchBlogByTitleOrContent("keyword1", "keyword2", 0, 10).getBody();

        // Assert
        assertEquals(200, response.getCode());
        assertEquals("Blogs found", response.getMessage());
        assertEquals(searchResultPage.getContent(), response.getBlogs());
    }
}
