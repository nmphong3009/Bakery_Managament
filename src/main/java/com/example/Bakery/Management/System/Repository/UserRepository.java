package com.example.Bakery.Management.System.Repository;

import com.example.Bakery.Management.System.Entity.User;
import com.example.Bakery.Management.System.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByIdCard(String idCard);

    List<User> findByRole(Role role);

    User findByPhoneNumber(String phoneNumber);
}

