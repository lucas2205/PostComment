package com.project2.project2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project2.project2.dto.PostDto;
import com.project2.project2.dto.PostResponse;
import com.project2.project2.services.PostService;
import com.project2.project2.util.AppConst;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /// TRAE LISTA DE PUBLICACIONES, ORDENA , FILTRA
    @GetMapping
    public PostResponse listarPosts(@RequestParam(value = "pageNo", defaultValue = AppConst.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
            @RequestParam(value = "pageSize", defaultValue = AppConst.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
            @RequestParam(value = "sortBy", defaultValue = AppConst.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
            @RequestParam(value = "order", defaultValue = AppConst.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir) {
        return postService.getAllPosts(numeroDePagina, medidaDePagina, ordenarPor, sortDir);
    }

    // TRAE PUBLICACION POR ID
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> obtenerPorId(@PathVariable(name = "id") long id) {

        return ResponseEntity.ok(postService.getById(id));
    }

    //GUARDA PUBLICACION
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> guardarPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.crearPost(postDto), HttpStatus.CREATED);
    }

    //ACTUALIZA PUBLICACION POR ID
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> actualizarPorId(@Valid @PathVariable(name = "id") long id,
            @RequestBody PostDto postDto) {
        PostDto postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //BORRA PUBLICACION POR ID
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable(name = "id") long id) {

        postService.deletePost(id);

        return new ResponseEntity<>("Post Eliminado...", HttpStatus.OK);
    }
}
