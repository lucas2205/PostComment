
package com.project2.project2.services;

import com.project2.project2.dto.PostDto;
import com.project2.project2.dto.PostResponse;


public interface PostService {
    
    public PostDto crearPost(PostDto postDto);
    
    public PostResponse getAllPosts(int numeroDePagina,int medidaDePagina,String ordenarPor,String sortDir);
    
    public PostDto getById(long id);
    
    public PostDto updatePost(PostDto postDto, long id);
    
    public void deletePost(long id);
}
