package com.ravi.semester.repository;


import com.ravi.semester.model.Faculty;
import com.ravi.semester.model.IndsPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndsPartnerRepo extends JpaRepository<IndsPartner,String> {
    Optional<IndsPartner> findByEmail(String email);

    boolean existsByEmail(String email);
}
