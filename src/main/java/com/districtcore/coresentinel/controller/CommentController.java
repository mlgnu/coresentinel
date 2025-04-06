package com.districtcore.coresentinel.controller;

import com.districtcore.coresentinel.annotation.CurrentUserId;
import com.districtcore.coresentinel.annotation.PrefixedId;
import com.districtcore.coresentinel.annotation.RateLimit;
import com.districtcore.coresentinel.dto.comment.CreateCommentDto.CreateCommentRequestDto;
import com.districtcore.coresentinel.dto.comment.CreateCommentDto.CreateCommentResponseDto;
import com.districtcore.coresentinel.dto.comment.GetCommentsDto.GetCommentResponseDto;
import com.districtcore.coresentinel.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Tag(name = "Comment", description = "Add comment to a case")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/crime-case/comments")
    @Operation(summary = "Add comment to a case",
            description = "Add comment to a case by providing the case ID and the comment, only 5 comments are allowed per minute")
    @RateLimit()
    public ResponseEntity<CreateCommentResponseDto> addComment(@RequestBody @Valid CreateCommentRequestDto requestDto, @CurrentUserId Long userId) throws URISyntaxException {
        String commentId = commentService.addComment(requestDto, userId);
        return ResponseEntity.created(new URI("/crime-cases/comments/" + commentId)).body(new CreateCommentResponseDto(commentId));
    }

    @GetMapping("/crime-case/{id}/comments")
    @Operation(summary = "Get comments of a case",
            description = "Get all comments of a case by providing the case ID, paginated")
    public ResponseEntity<Page<GetCommentResponseDto>> getComments(
            @ParameterObject Pageable pageable,
            @PrefixedId(prefix = "C") Long id)
    {
        Page<GetCommentResponseDto> page = this.commentService.getCommentsByCaseId(id, pageable);
        return ResponseEntity.ok().body(page);
    }

    @DeleteMapping("/crime-case/comments/{id}")
    @Operation(summary = "Delete comment by id", description = "Delete comment by providing the comment ID")
    public ResponseEntity<Void> deleteComment(@PrefixedId(prefix = "C") Long id){
        this.commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
