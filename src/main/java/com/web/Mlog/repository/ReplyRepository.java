package com.web.Mlog.repository;

import com.web.Mlog.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByReplyIdAndVisibleIsTrue(Long id);
    @Query("select r " +
            "from Reply r " +
            "where r.post.postId = :id " +
            "and r.visible = true")
    List<Reply> findAllByPostId(@Param("id") int id);
}
