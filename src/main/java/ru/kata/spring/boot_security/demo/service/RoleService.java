package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);

    List<Role> listRoles();

    Role findRoleById(Long id);

    void deleteRole(Long id);

    void updateRole(Long id, Role role);

    Role findByRoleName(String username);
}
