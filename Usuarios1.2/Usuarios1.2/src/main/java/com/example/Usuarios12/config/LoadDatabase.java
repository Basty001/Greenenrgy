package com.example.Usuarios12.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Usuarios12.model.Rol;
import com.example.Usuarios12.model.Usuario;
import com.example.Usuarios12.repository.RoleRepository;
import com.example.Usuarios12.repository.UsuarioRepository;



/**
 * Configuración para cargar datos iniciales en la base de datos.
 * Se ejecuta al iniciar la aplicación si la base de datos está vacía.
 */
@Configuration
public class LoadDatabase {

    /**
     * Crea roles y usuarios iniciales si la base de datos está vacía.
     * @param roleRepo Repositorio de roles
     * @param usuarioRepo Repositorio de usuarios
     * @param encoder Codificador de contraseñas
     * @return CommandLineRunner que ejecuta la carga inicial
     */
    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepo, UsuarioRepository usuarioRepo, 
                                 BCryptPasswordEncoder encoder) {
        return args -> {
            // Solo carga datos si no existen registros
            if (roleRepo.count() == 0 && usuarioRepo.count() == 0) {
                // Crear roles iniciales
                Rol admin = new Rol();
                admin.setNombre("Administrador");
                roleRepo.save(admin);

                Rol cliente = new Rol();
                cliente.setNombre("Cliente");
                roleRepo.save(cliente);

                Rol tecnico = new Rol();
                tecnico.setNombre("Tecnico");
                roleRepo.save(tecnico);

                // Crear usuarios iniciales con contraseñas encriptadas
                usuarioRepo.save(new Usuario(null, "victor", encoder.encode("12345"), admin));
                usuarioRepo.save(new Usuario(null, "Joselito", encoder.encode("12345"), cliente));
                usuarioRepo.save(new Usuario(null, "Pacherco", encoder.encode("12345"), tecnico));

                System.out.println("Datos iniciales cargados:");
                System.out.println("- 3 roles creados: Administrador, Cliente, Tecnico");
                System.out.println("- 3 usuarios creados con contraseñas encriptadas");
            }
        };
    }
}