
package com.project2.project2.services;

import com.project2.project2.dto.CommentDto;
import java.util.List;


public interface CommentService {
    
    public CommentDto crearComment(long postId, CommentDto commentDto);
    
    public List<CommentDto> getCommentByPostId (long postId);
    
    public CommentDto getCommentDtoById (long postId,long commentId);
    
    public CommentDto updateComment(long postId,long commentId, CommentDto solicitudComment);
    
    public void deleteComment (long postId,long commentId);
}
