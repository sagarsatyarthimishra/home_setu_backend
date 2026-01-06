package com.homesetu.homesetu_backend.auth.repository;

import com.homesetu.homesetu_backend.auth.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    List<UserAddress> findByUserProfile_UserId(Long userId);
}
