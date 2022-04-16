
package com.project2.project2.dao;

import com.project2.project2.entity.Rol;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface RolDao extends JpaRepository<Rol, Long>{

	public Optional<Rol> findByNombre(String nombre);
	
}