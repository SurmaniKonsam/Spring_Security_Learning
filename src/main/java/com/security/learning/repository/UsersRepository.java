package com.security.learning.repository;

import com.security.learning.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUserByUserNameAndIsActive(String userName,boolean isActive);
}
