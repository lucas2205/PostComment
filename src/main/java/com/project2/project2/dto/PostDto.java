

package com.project2.project2.dto;

import com.project2.project2.entity.Comment;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class PostDto {
    
    private Long id;
    
    @NotEmpty
    @Size(min=2, message = "El titulo debe tener al menos 2 caracteres...")
    private String titulo;
    
    @NotEmpty
    @Size(min=10, message = "La descripción debe tener al menos 10 caracteres...")
    private String descripcion;
    
    @NotEmpty
    @Size(min=2, message = "El contenido debe tener al menos 2 caracteres...")
    private String contenido;
    
    private Set<Comment> comments;

    public Long getId() {
        return id;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public PostDto() {
        super();
    }
    
}
