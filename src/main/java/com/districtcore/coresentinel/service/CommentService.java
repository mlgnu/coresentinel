package com.districtcore.coresentinel.service;

import com.districtcore.coresentinel.dto.comment.CreateCommentDto.CreateCommentRequestDto;
import com.districtcore.coresentinel.dto.comment.GetCommentsDto.GetCommentResponseDto;
import com.districtcore.coresentinel.mapper.CommentMapper;
import com.districtcore.coresentinel.model.Comment;
import com.districtcore.coresentinel.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public String addComment(CreateCommentRequestDto requestDto , Long userId) {
       return commentRepository.save(commentMapper.toEntity(requestDto, userId)).getCommentId();
    }

    public Page<GetCommentResponseDto> getCommentsByCaseId(Long caseId, Pageable pageable) {
        Page<Comment> comments = this.commentRepository.findByCrimeCaseId(caseId, pageable);
        return comments.map(commentMapper::toDto);
    }

    public void deleteComment(Long id){
        this.commentRepository.deleteById(id);
    }
}
