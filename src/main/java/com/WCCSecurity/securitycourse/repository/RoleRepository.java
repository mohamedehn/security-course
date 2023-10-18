package com.WCCSecurity.securitycourse.repository;

import com.WCCSecurity.securitycourse.entity.Role;
import com.WCCSecurity.securitycourse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(String string);
    boolean existsByRole(String role);
}
