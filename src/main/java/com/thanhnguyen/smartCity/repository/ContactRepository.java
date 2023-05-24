package com.thanhnguyen.smartCity.repository;


import com.thanhnguyen.smartCity.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByStatus(String status);
}
