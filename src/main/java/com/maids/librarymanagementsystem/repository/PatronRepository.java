package com.maids.librarymanagementsystem.repository;

import com.maids.librarymanagementsystem.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    boolean existsByPatronEmail(String patronEmail);

    boolean existsByPatronPhone(String patronPhone);

}
