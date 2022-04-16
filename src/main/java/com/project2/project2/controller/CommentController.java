

package com.project2.project2.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.project2.project2.dto.CommentDto;
import com.project2.project2.services.CommentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
    
@Autowired
    private CommentService commentService;


//GUARDA COMENTARIO
     @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> guardarComment(@PathVariable (name="postId") long postId,
                                                     @Valid  @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.crearComment(postId,commentDto), HttpStatus.CREATED);
    }
    
    
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> commentsByPost(@PathVariable(name = "postId") long postId) {
        
        return commentService.getCommentByPostId(postId);
    }
    
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") long postId,
            @PathVariable(name = "commentId") long commentId) {
        
        return new ResponseEntity<>(commentService.getCommentDtoById(postId, commentId), HttpStatus.OK);
    }
    
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(name = "postId") long postId,
            @PathVariable(name = "commentId") long commentId,
            @Valid  @RequestBody CommentDto commentDto) {
        
        CommentDto commentUpdated = commentService.updateComment(postId, commentId, commentDto);
    
        return new ResponseEntity<>(commentUpdated, HttpStatus.OK);
        
    }
    
    
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") long postId,
            @PathVariable(name = "commentId") long commentId) {
        
        commentService.deleteComment(postId, commentId);
        
        return new ResponseEntity<>("Comment Eliminado...",HttpStatus.OK);
    }
}