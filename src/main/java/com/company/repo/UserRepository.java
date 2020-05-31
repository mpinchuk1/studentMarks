package com.company.repo;

import com.company.entities.CustomUser;
import com.company.utils.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<CustomUser, Long> {

    @Query("SELECT u FROM CustomUser u where u.login = :login")
    CustomUser findByLogin(@Param("login") String login);

    @Query("select u from CustomUser u where u.role = :role")
    List<CustomUser> findCustomUsersByRole(@Param("role") UserRole role);

}
