package edu.eci.dosw.segundo_parcial.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.eci.dosw.segundo_parcial.entity.Role;
import edu.eci.dosw.segundo_parcial.entity.UserEntity;
import edu.eci.dosw.segundo_parcial.repository.UserRepository;

/**
 * Carga usuarios de ejemplo al iniciar la aplicacion.
 *
 * Si quieres cambiar los datos iniciales, modifica los valores del metodo run.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Si ya hay datos, no volvemos a insertar ejemplos.
        if (userRepository.count() > 0) {
            return;
        }

        // Usuario administrador inicial.
        userRepository.save(new UserEntity("Administrador", "admin@segundoparcial.com",
                passwordEncoder.encode("admin123"), Role.ADMIN));
        // Usuario normal inicial.
        userRepository.save(new UserEntity("Usuario Demo", "user@segundoparcial.com",
                passwordEncoder.encode("user123"), Role.USER));
    }
}
