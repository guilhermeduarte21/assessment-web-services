package com.guilherme.duarte.usuario.repository;

import com.guilherme.duarte.usuario.Usuario;

import java.util.List;

public interface IUsuarioRepository {
    Usuario salvar(Usuario usuario);

    Usuario buscarPorId(Long id);

    List<Usuario> buscarTodos();

    boolean deletar(Long id);

    boolean existePorEmail(String email);
}
