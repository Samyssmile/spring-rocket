package de.abramov.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        AssertionErrors.assertTrue("Tests run...", true);
    }

}
