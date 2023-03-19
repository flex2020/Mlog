package com.web.Mlog.repository;

import com.web.Mlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findAllByVisibleIsTrue();
    public Optional<Post> findByPostIdAndVisibleIsTrue(long id);
}
