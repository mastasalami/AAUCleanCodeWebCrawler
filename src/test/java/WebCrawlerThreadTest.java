import org.example.WebCrawlerThread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebCrawlerThreadTest {
    private static final String workingTestUrls = "https://www.aau.at/,https://www.univie.ac.at/,https://stackoverflow.com/";
    private static final String invalidUrl = "fffffff";
    private static final int numberOfThreads = 3;
    private static final int testDepth  = 0;
    private static final String testLanguage  = "it";
    private static final String firstHeadingText = "## UNIVERSITÀ DI KLAGENFURT";
    private static final String workingLinkText = "link to <a>https://www.aau.at/</a>\n";

    private ArrayList<Future<String>> futures;
    private String summaryString = "";

    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
        CompletionService<String> taskCompletionService = new ExecutorCompletionService<>(executor);
        futures = new ArrayList<>();

        for (String url : workingTestUrls.split(",")) {
            WebCrawlerThread webCrawlerThread = new WebCrawlerThread(url, testDepth, testLanguage);
            Future<String> summaryResult = taskCompletionService.submit(webCrawlerThread);
            futures.add(summaryResult);
        }

        for (Future<String> summaryFuture : futures) {
            summaryString += summaryFuture.get();
        }
        executor.shutdown();
    }
    @Test
    @DisplayName("Test resulting headings")
    void testHeadingsText() {
        assertTrue(summaryString.contains(firstHeadingText));
    }

    @Test
    @DisplayName("Test resulting headings")
    void testLinksText() {
        assertTrue(summaryString.contains(workingLinkText));
    }

    @Test
    void testCrawlForInvalidURL() {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        CompletionService<String> taskCompletionService = new ExecutorCompletionService<>(executor);

        WebCrawlerThread webCrawlerThread = new WebCrawlerThread(invalidUrl, testDepth, testLanguage);
        Future<String> summaryResult = taskCompletionService.submit(webCrawlerThread);
        assertThrows(ExecutionException.class, () -> summaryResult.get());
    }

}
