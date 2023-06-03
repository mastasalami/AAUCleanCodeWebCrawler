import org.example.DOMDocument;
import org.example.WebPage;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WebPageTest {
    private WebPage emptyWebPage;
    private WebPage webPageWithDocument;

    private static final String testUrl = "https://www.aau.at/";
    private static final int testDepth  = 0;
    private static final int deeperTestDepth  = 2;
    private static final String workingLinkText = "link to <a>https://www.aau.at/</a>\n";
    private static final String brokenLinkText = "broken link <a>https://www.aau.at/</a>\n";
    private static final String firstHeadingText = "## UNIVERSITÄT KLAGENFURT";

    private static final String workingLinkTextDepth2 = "----> link to <a>https://www.aau.at/</a>\n";
    private static final String firstHeadingTextDepth2 = "## ----> UNIVERSITÄT KLAGENFURT";

    @BeforeEach
    void setUp() {
        try {
            initEmptyWebPage();
            initWebPageWithDocument(testDepth);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("Test web page url")
    void testUrlWebPage() {
        assertEquals(emptyWebPage.getUrl(), testUrl);
        assertEquals(webPageWithDocument.getUrl(), testUrl);
    }


    @Test
    @DisplayName("Test if the selected headings are correct")
    void testWebPageHeadings() {
        assertTrue(emptyWebPage.getHeadingsToString().isEmpty());
        assertFalse(webPageWithDocument.getHeadingsToString().isEmpty());

        String firstHeading = webPageWithDocument.getHeadingsToString();
        assertTrue(firstHeading.contains(firstHeadingText));
    }

    @Test
    @DisplayName("Test if the selected links are correct")
    void testWebPageLinks() {
        assertEquals(emptyWebPage.getLinkText(), brokenLinkText);
        assertEquals(webPageWithDocument.getLinkText(), workingLinkText);
    }

    @Test
    @DisplayName("Test if the selected links are correct for deeper level")
    void testWebPageLinksForDepth2() throws Exception {
        initWebPageWithDocument(deeperTestDepth);

        assertEquals(webPageWithDocument.getLinkText(), workingLinkTextDepth2);
    }

    @Test
    @DisplayName("Test if the selected headings are correct for deeper level")
    void testWebPageHeadingsForDepth2() throws Exception {
        initWebPageWithDocument(deeperTestDepth);

        assertFalse(webPageWithDocument.getHeadingsToString().isEmpty());

        String firstHeading = webPageWithDocument.getHeadingsToString();
        assertTrue(firstHeading.contains(firstHeadingTextDepth2));
    }
    private void initEmptyWebPage() {
        emptyWebPage = new WebPage(null, testUrl, testDepth);
        emptyWebPage.loadElementsFromDocument();
    }

    private void initWebPageWithDocument(int depth) throws Exception {
        Connection connection = Jsoup.connect(testUrl);
        DOMDocument document = new DOMDocument(connection.execute().parse());

        webPageWithDocument = new WebPage(document, testUrl, depth);
        webPageWithDocument.loadElementsFromDocument();
    }
}
