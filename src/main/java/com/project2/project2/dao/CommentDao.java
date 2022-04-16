
package com.project2.project2.dao;

import com.project2.project2.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentDao extends JpaRepository<Comment, Long>{
    
    public List<Comment> findByPostId(long postId);
    
}
