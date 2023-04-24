import org.example.WebCrawler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebCrawlerTest {
    private WebCrawler crawler;
    private static final String testUrl = "https://www.aau.at/";
    private static final int testDepth  = 0;
    private static final String firstHeadingText = "## UNIVERSITÄT KLAGENFURT";
    private static final String workingLinkText = "link to <a>https://www.aau.at/</a>\n";

    @BeforeEach
    void setUp() throws Exception {
        crawler = new WebCrawler(testUrl, testDepth);
        crawler.crawl();
    }
    @Test
    @DisplayName("Test resulting headings")
    void testHeadingsText() throws Exception {
        assertTrue(crawler.getHeadingsText().contains(firstHeadingText));
    }

    @Test
    @DisplayName("Test resulting headings")
    void testLinksText() throws Exception {
        assertTrue(crawler.getLinksText().contains(workingLinkText));
    }

}
