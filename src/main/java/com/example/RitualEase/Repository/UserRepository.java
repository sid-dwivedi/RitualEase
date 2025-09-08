package com.example.RitualEase.Repository;

import com.example.RitualEase.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
User findByUserName(String userName);
}
