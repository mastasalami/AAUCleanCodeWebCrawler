import org.example.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {
    private Logger logger = Logger.getInstance();
    @BeforeEach
    void setUp() {
        logger = Logger.getInstance();
    }
    @Test
    void testSingletonConcept() {
        Logger logger2 = Logger.getInstance();
        assertSame(logger, logger2);
    }

    @Test
    void testSingletonConceptWithMultipleMessages() {
        logger.log("Test");

        Logger logger2 = Logger.getInstance();
        logger2.log("Test2");

        assertEquals("Test\nTest2\n", logger.getLoggedMessageSummary());
    }
}
