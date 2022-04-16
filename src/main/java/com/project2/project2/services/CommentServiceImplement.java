

package com.project2.project2.services;

import com.project2.project2.dao.CommentDao;
import com.project2.project2.dao.PostDao;
import com.project2.project2.dto.CommentDto;
import com.project2.project2.entity.Comment;
import com.project2.project2.entity.Post;
import com.project2.project2.exceptions.AppException;
import com.project2.project2.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImplement implements CommentService{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private CommentDao commentDao;
    
    @Autowired
    private PostDao postDao;
    
    
    @Override
    public CommentDto crearComment(long postId, CommentDto commentDto) {
        
        Comment comment = mapearEntidad(commentDto);
        
          Post post = postDao.findById(postId)
          .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        
          comment.setPost(post);
          Comment newComment = commentDao.save(comment);
          
          
        return mapearDto(newComment);
        
      }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
    
    List<Comment> comments = commentDao.findByPostId(postId);
    
    return comments.stream().map(comment -> mapearDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentDtoById(long postId, long commentId) {
        
     Post post = postDao.findById(postId)
             .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        
     Comment comment = commentDao.findById(commentId)
             .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
       
    if(!comment.getPost().getId().equals(post.getId())){
        throw new AppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
    }
    
    return mapearDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto solicitudComment) {
        
      Post post = postDao.findById(postId)
             .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        
     Comment comment = commentDao.findById(commentId)
             .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
       
    if(!comment.getPost().getId().equals(post.getId())){
        throw new AppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
    }
      
    comment.setNombre(solicitudComment.getNombre());
    comment.setEmail(solicitudComment.getEmail());
    comment.setCuerpo(solicitudComment.getCuerpo());
    
    return mapearDto(commentDao.save(comment));
    
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        
     Post post = postDao.findById(postId)
             .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        
     Comment comment = commentDao.findById(commentId)
             .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
       
    if(!comment.getPost().getId().equals(post.getId())){
        throw new AppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
    }
    
    commentDao.delete(comment);
    
    }
    
    
    
    //Convierte Entidad a DTO
    private CommentDto mapearDto(Comment comment) {

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    //Convierte DTO a Entidad
    private Comment mapearEntidad(CommentDto commentDto) {

        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
    
    
}
