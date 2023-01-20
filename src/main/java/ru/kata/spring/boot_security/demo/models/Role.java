package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {}

    public Role(Long roleId, ERole name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public ERole getName() {
        return name;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName().toString();
    }
}
