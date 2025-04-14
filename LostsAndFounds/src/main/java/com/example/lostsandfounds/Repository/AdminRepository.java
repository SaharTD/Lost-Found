package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findAdminById(Integer id);






}
