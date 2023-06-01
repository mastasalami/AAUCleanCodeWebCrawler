import org.example.DOMDocument;
import org.example.DOMElement;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DOMDocumentTest {
    private static final String testUrl = "https://www.aau.at/";
    private static final String LINK_HTML_ELEMENT_NAME              = "a";

    private DOMDocument emptyDocument;
    private DOMDocument aauDocument;
    @BeforeEach
    void setUp() {
        try {
            emptyDocument = null;

            Connection connection = Jsoup.connect(testUrl);
            aauDocument = new DOMDocument(connection.execute().parse());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testEmptyDocument() throws Exception {
        assertEquals(emptyDocument, null);
    }

    @Test
    void testLoadAAUDocument() throws Exception {
        assertNotEquals(aauDocument, null);
    }

    @Test
    void testLinksForAAUDocument() {
        List<DOMElement> linkList = aauDocument.loadElementsByTagName(LINK_HTML_ELEMENT_NAME);
        assertTrue(linkList.size() > 0);
    }
}
