package com.guilherme.duarte.usuario.service;

import com.guilherme.duarte.usuario.Usuario;
import com.guilherme.duarte.usuario.ValidadorUsuario;
import com.guilherme.duarte.usuario.repository.IUsuarioRepository;

import java.util.List;

public class UsuarioService {
    private IUsuarioRepository repository;
    private ValidadorUsuario validador;

    public UsuarioService(IUsuarioRepository repository, ValidadorUsuario validador) {
        this.repository = repository;
        this.validador = validador;
    }

    public Usuario criar(Usuario usuario) {
        validarUsuario(usuario);

        if (repository.existePorEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        return repository.salvar(usuario);
    }

    public Usuario buscarPorId(Long id) {
        Usuario usuario = repository.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return usuario;
    }

    public List<Usuario> listarTodos() {
        return repository.buscarTodos();
    }

    public void deletar(Long id) {
        if (!repository.deletar(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
    }

    private void validarUsuario(Usuario usuario) {
        if (!validador.validarEmail(usuario)) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (!validador.validarNome(usuario)) {
            throw new IllegalArgumentException("Nome inválido (mínimo 3 caracteres)");
        }
    }
}
