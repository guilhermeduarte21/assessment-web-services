package com.guilherme.duarte.usuario.repository;

import com.guilherme.duarte.usuario.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioRepositoryMemoria implements IUsuarioRepository {
    private Map<Long, Usuario> usuarios;
    private long proximoId;

    public UsuarioRepositoryMemoria() {
        this.usuarios = new HashMap<>();
        this.proximoId = 1;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(proximoId++);
        }
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarios.get(id);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public boolean deletar(Long id) {
        return usuarios.remove(id) != null;
    }

    @Override
    public boolean existePorEmail(String email) {
        return usuarios.values().stream()
                .anyMatch(u -> u.getEmail().equals(email));
    }

    public void limpar() {
        usuarios.clear();
        proximoId = 1;
    }
}
