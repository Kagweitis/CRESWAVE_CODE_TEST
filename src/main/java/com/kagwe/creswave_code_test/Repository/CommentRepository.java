package com.kagwe.creswave_code_test.Repository;

import com.kagwe.creswave_code_test.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(nativeQuery = true, value = "select * from comment where id = :id and deleted = false")
    List<Comment> findByBlogId(Long id);
}
