package com.web.Mlog.repository;

import com.web.Mlog.domain.Category;
import com.web.Mlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findAllByVisibleTrue();
    public List<Post> findAllByCategoryAndVisibleTrue(Category category);
    public Optional<Post> findByPostIdAndVisibleIsTrue(int id);
}
