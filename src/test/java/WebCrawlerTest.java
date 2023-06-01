import org.example.WebCrawler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WebCrawlerTest {
    private WebCrawler crawler;
    private static final String testUrl = "https://www.aau.at/";
    private static final String invalidUrl = "fffffff";

    private static final int testDepth  = 0;
    private static final String firstHeadingText = "## UNIVERSITÄT KLAGENFURT";
    private static final String workingLinkText = "link to <a>https://www.aau.at/</a>\n";

    @BeforeEach
    void setUp() {
        crawler = new WebCrawler(testDepth);
        crawler.crawl(testUrl);
    }
    @Test
    @DisplayName("Test resulting headings")
    void testHeadingsText() {
        assertTrue(crawler.getHeadingsText().contains(firstHeadingText));
    }

    @Test
    @DisplayName("Test resulting headings")
    void testLinksText() {
        assertTrue(crawler.getLinksText().contains(workingLinkText));
    }

    @Test
    void testCrawlForInvalidURL() {
        crawler = new WebCrawler(testDepth);
        assertThrows(IllegalArgumentException.class, () -> crawler.crawl(invalidUrl));
    }

}
