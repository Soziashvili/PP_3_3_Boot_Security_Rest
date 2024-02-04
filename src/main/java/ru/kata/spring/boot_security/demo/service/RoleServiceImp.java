package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleDao roleDao;

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleDao roleDao, RoleRepository roleRepository) {
        this.roleDao = roleDao;
        this.roleRepository = roleRepository;
    }

    @Override
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> listRoles() {
        return roleDao.listRoles();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public void deleteRole(Long id) {
        roleDao.deleteRole(id);
    }

    @Override
    public void updateRole(Long id, Role role) {
        roleDao.updateRole(id, role);
    }

    @Override
    public Role findByRoleName(String username) {
        return null;
    }
}
