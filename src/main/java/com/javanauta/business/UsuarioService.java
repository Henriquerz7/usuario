package com.javanauta.business;

import com.javanauta.business.converter.UsuarioConverter;
import com.javanauta.business.dto.UsuarioDTO;
import com.javanauta.infrastructure.entity.Usuario;
import com.javanauta.infrastructure.exception.ConflictException;
import com.javanauta.infrastructure.exception.ResourceNotFoundException;
import com.javanauta.infrastructure.repository.UsuarioRepository;
import com.javanauta.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificaEmailExistente(email);
            if(existe){
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);          // vai na interface usuarioRepository e chama o metodo existsByEmail passando como parâmetro email e retorna um boolean
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));             // Busca email do usuário através do token

        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null); // Criptografa a senha caso o usuário tenha passado nova senha

        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->       // Busca os dados do usuário no banco de dados
                new ResourceNotFoundException("Email não localizado"));

        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);                   // Mescla os dados recebidos na requisição (dto) com os dados do banco

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));  // Salva os dados do usuário convertido e pega o retorno e converte para UsuarioDTO
    }
}
