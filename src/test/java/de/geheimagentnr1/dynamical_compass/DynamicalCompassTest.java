package de.geheimagentnr1.dynamical_compass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DynamicalCompassTest {

    @Test
    void modIdIsValid() {

        String modId = "dynamical_compass";
        assertTrue( modId.matches( "[a-z][a-z0-9_]{1,63}" ) );
    }
}
