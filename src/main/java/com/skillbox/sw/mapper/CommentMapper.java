package com.skillbox.sw.mapper;

import com.skillbox.sw.api.request.PostCommentApi;
import com.skillbox.sw.api.response.CommentApi;
import com.skillbox.sw.domain.PostComment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    uses = {
        DateMapper.class,
        PersonMapper.class
    }
)

public interface CommentMapper extends BaseMapper {

  @Mapping(target = "parentId", expression = "java(postComment.getParent().getId())")
  @Mapping(target = "postId", expression = "java(postComment.getPost().getId())")
  @Mapping(target = "authorId", expression = "java(postComment.getAuthor().getId())")
  CommentApi postCommentToCommentApi(PostComment postComment);

  @InheritInverseConfiguration
  PostComment postCommentApiToPostComment(PostCommentApi postCommentApi);
}