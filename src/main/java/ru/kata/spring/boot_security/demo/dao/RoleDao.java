package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDao {

    void addRole(Role role);

    List<Role> listRoles();

    Role findRoleById(Long id);

    void deleteRole(Long id);

    void updateRole(Long id, Role role);

    Role findByRoleName(String username);
}
