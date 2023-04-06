package com.web.Mlog.repository;

import com.web.Mlog.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByReplyIdAndVisibleIsTrue(Long id);
}
