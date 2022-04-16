package com.project2.project2.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project2.project2.dao.PostDao;
import com.project2.project2.dto.PostDto;
import com.project2.project2.dto.PostResponse;
import com.project2.project2.entity.Post;
import com.project2.project2.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;


@Service
public class PostServiceImplement implements PostService {
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PostDao postDao;
    
    @Override
    public PostDto crearPost(PostDto postDto) {
        
        Post post = mapearEntidad(postDto);
        
        Post newPost = postDao.save(post);
        
        PostDto postResponse = mapearDto(newPost);        
        
        return postResponse;
    }
    
    @Override
    public PostResponse getAllPosts(int numeroDePagina, int medidaDePagina, String ordenarPor,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                                            ? Sort.by(ordenarPor).ascending()
                                            : Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

        Page<Post> posts = postDao.findAll(pageable);

		List<Post> listaDePosts = posts.getContent();
		List<PostDto> contenido = listaDePosts.stream().map(post -> mapearDto(post))
				.collect(Collectors.toList());

		PostResponse publicacionRespuesta = new PostResponse();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(posts.getNumber());
		publicacionRespuesta.setMedidaPagina(posts.getSize());
		publicacionRespuesta.setTotalElementos(posts.getTotalElements());
		publicacionRespuesta.setTotalPaginas(posts.getTotalPages());
		publicacionRespuesta.setUltima(posts.isLast());

		return publicacionRespuesta;
    }
    
    @Override
    public PostDto getById(long id) {
        Post post = postDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        
        return mapearDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        post.setTitulo(postDto.getTitulo());
        post.setDescripcion(postDto.getDescripcion());
        post.setContenido(postDto.getContenido());

        Post postUpdated = postDao.save(post);
        
        return mapearDto(postUpdated);
    }

    @Override
    public void deletePost(long id) {
        Post post = postDao.findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        
    postDao.delete(post);
    }
    
    
      //Convierte Entidad a DTO
    private PostDto mapearDto(Post post) {
        
        PostDto postDto = modelMapper.map(post, PostDto.class);
        
        return postDto;
    }

    //Convierte DTO a Entidad
    private Post mapearEntidad(PostDto postDto) {
        
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }
    
}
    