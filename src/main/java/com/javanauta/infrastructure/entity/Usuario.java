package com.javanauta.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", length = 100)
    private String nome;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;

    @OneToMany(cascade = CascadeType.ALL) // Em cascata quer dizer que quando excluir o usuário, o endereço e o email serão excluídos automaticamente
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") // Na tabela endereço terá uma coluna usuario_id que irá identificar o usuário na tabela usuário
    private List<Endereco> endereco; // Relaciona a tabela endereço

    @OneToMany(cascade = CascadeType.ALL) // OneToMany neste caso é um usuário para vários telefones
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") // usuario_id faz referencia a coluna id da tabela usuário
    private List<Telefone> telefone;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}


