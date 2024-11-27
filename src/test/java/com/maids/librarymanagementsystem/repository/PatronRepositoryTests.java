package com.maids.librarymanagementsystem.repository;

import com.maids.librarymanagementsystem.model.Patron;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PatronRepositoryTests {

    @Autowired
    private PatronRepository patronRepository;

    @Test
    public void PatronRepository_ExistsByPatronEmail_ReturnsFalse() {

        String patronEmail = "patron@email.com";

        boolean exists = patronRepository.existsByPatronEmail(patronEmail);

        Assertions.assertFalse(exists);

    }

    @Test
    public void PatronRepository_ExistsByPatronEmail_ReturnsTrue() {

        String patronEmail = "patron@email.com";

        Patron patron = Patron.builder()
                .patronName("PatronName")
                .patronEmail(patronEmail)
                .patronPhone("+1 843-749-3820")
                .build();

        patronRepository.save(patron);

        boolean exists = patronRepository.existsByPatronEmail(patronEmail);

        Assertions.assertTrue(exists);

    }

    @Test
    public void PatronRepository_ExistsByPatronPhone_ReturnsFalse() {

        String patronPhone = "+1 843-749-3820";

        boolean exists = patronRepository.existsByPatronPhone(patronPhone);

        Assertions.assertFalse(exists);

    }

    @Test
    public void PatronRepository_ExistsByPatronPhone_ReturnsTrue() {

        String patronPhone = "+1 843-749-3820";

        Patron patron = Patron.builder()
                .patronName("PatronName")
                .patronEmail("patron@email.com")
                .patronPhone(patronPhone)
                .build();

        patronRepository.save(patron);

        boolean exists = patronRepository.existsByPatronPhone(patronPhone);

        Assertions.assertTrue(exists);

    }


}
