package com.WCCSecurity.securitycourse.repository;

import com.WCCSecurity.securitycourse.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
