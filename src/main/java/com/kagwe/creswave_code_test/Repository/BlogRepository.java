package com.kagwe.creswave_code_test.Repository;

import com.kagwe.creswave_code_test.Entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
