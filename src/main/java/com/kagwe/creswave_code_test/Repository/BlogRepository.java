package com.kagwe.creswave_code_test.Repository;

import com.kagwe.creswave_code_test.Entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query(nativeQuery = true, value = "select * from blog where deleted = false")
    List<Blog> findAllByDeletedFalse();

    @Query(nativeQuery = true, value = "select * from blog where id = :id and deleted = false")
    Optional<Blog> findById(Long id);


    Page<Blog> findAllByDeletedFalse(Pageable pageable);
}
