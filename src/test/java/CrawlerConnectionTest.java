import org.example.CrawlerConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlerConnectionTest {
    private static final String testUrl = "https://www.aau.at/";
    private static final String brokenUrl = "FFFFFF";

    private CrawlerConnection workingConnection;
    private CrawlerConnection brokenConnection;

    @BeforeEach
    void setUp() {
        try {
            workingConnection = new CrawlerConnection();
            brokenConnection = new CrawlerConnection();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testWorkingCrawlerConnect() {
        workingConnection.createNewConnection(testUrl);
        assertNotEquals(workingConnection.getConnection(), null);
    }

    @Test
    void testBrokenCrawlerConnect() {                                                                  //3rd party connection should never return null
        assertNull(brokenConnection.getConnection());
        assertThrows(IllegalArgumentException.class, () -> brokenConnection.createNewConnection(brokenUrl));
    }

    @Test
    void testLoadFullDocument() throws IOException {
        workingConnection.createNewConnection(testUrl);
        assertNotEquals(workingConnection.loadDocument(), null);
    }

    @Test
    void testBrokenConnectionLoadDocument() throws IOException {
        assertNull(brokenConnection.loadDocument());
    }
}
