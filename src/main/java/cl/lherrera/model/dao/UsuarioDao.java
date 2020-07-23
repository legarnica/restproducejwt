package cl.lherrera.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.lherrera.model.entity.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Integer>{}
