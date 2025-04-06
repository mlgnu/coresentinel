package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.dto.comment.CreateCommentDto.CreateCommentRequestDto;
import com.districtcore.coresentinel.dto.comment.GetCommentsDto.GetCommentResponseDto;
import com.districtcore.coresentinel.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class, UserMapper.class })
public interface CommentMapper {

    @Mapping(target = "crimeCase", source = "requestDto.caseId")
    @Mapping(target = "createdBy", source = "userId")
    Comment toEntity(CreateCommentRequestDto requestDto, Long userId);

    GetCommentResponseDto toDto(Comment comment);

}
