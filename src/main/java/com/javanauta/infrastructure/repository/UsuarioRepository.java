package com.javanauta.infrastructure.repository;

import com.javanauta.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> { // passar qual a tabela que este repository representa e o tipo do id

    boolean existsByEmail(String email); // esse metodo verifica se já existe um email no banco de dados e retorna um boolean (se existe "true", se não "false")

    Optional<Usuario> findByEmail(String email); // Optional é para evitar o quebrar a aplicação caso não tenha o email buscado no banco, ele chama uma exceção

    @Transactional
        // anotação obrigatória para o delete que ajuda a não causar nenhum erro na hora de deletar
    void deleteByEmail(String email);
}


// Essa interface serve para que o Spring gere automaticamente o código de acesso ao banco de dados para a tabela usuário
// Sem isso, teria que escrever muito código repetitivo (CRUD) na mão
