package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public DbInit(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void PostConstruct() {
        Role role1 = new Role(1L, "ROLE_ADMIN");
        Role role2 = new Role(2L, "ROLE_USER");
        Set<Role> roles = new HashSet<>();
        Set<Role> roles1 = new HashSet<>();

        roles.add(role1);
        roles.add(role2);
        roles1.add(role2);

        User admin = new User("admin@mail.ru", "admin", "admin", 37L, new BCryptPasswordEncoder().encode("admin"), roles);
        User user = new User("user@mail.ru", "user", "user", 30L, new BCryptPasswordEncoder().encode("user"), roles1);

        admin.setId(1L);
        user.setId(2L);

        roleRepository.save(role1);
        roleRepository.save(role2);

        userRepository.save(admin);
        userRepository.save(user);
    }
}
