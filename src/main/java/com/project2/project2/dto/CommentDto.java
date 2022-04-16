

package com.project2.project2.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class CommentDto {
 
    private long id;
     
    @NotEmpty
    @Size(min=2, message = "El nombre debe tener al menos 2 caracteres...")
    private String nombre;
     
    @NotEmpty(message = "El titulo debe tener al menos 2 caracteres...")
    @Email
    private String email;
     
    @NotEmpty
    @Size(min=2, message = "El cuerpo debe tener al menos 5 caracteres...")
    private String cuerpo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

}
