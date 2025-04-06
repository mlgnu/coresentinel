package com.districtcore.coresentinel.repository;

import com.districtcore.coresentinel.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByCrimeCaseId(Long crimeCaseId, Pageable pageable);
}
