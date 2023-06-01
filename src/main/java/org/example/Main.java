package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    private static Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        try {
            ConsoleReader console = new ConsoleReader();
            console.writeInitialMessages();

            String[] urls     = console.readUrlFromConsole().split(",");
            int depth   = console.readSearchDepthsFromConsole();
            String language = console.readTargetLanguageFromConsole();
            StringBuilder summary = new StringBuilder();

            summary.append(createSummaryConcurrent(urls, depth, language));
            summary.append(logger.getLoggedMessageSummary());
            createSummaryFile(summary.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String createSummaryConcurrent(String[] urls, int depth, String language) throws ExecutionException, InterruptedException {
        StringBuilder summary = new StringBuilder();

        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(Math.min(urls.length, 10));                           // 10 Threads is maximum is enough. If there are only X urls we don`t need more
        CompletionService<String> taskCompletionService = new ExecutorCompletionService<>(executor);
        ArrayList<Future<String>> futures = new ArrayList<>();

        for (String url : urls) {
            WebCrawlerThread webCrawlerThread = new WebCrawlerThread(url, depth, language);
            Future<String> summaryResult = taskCompletionService.submit(webCrawlerThread);
            futures.add(summaryResult);
        }
        executor.shutdown();

        for (Future<String> summaryFuture : futures) {
            summary.append(summaryFuture.get());
        }

        return summary.toString();
    }

    private static void createSummaryFile(String summary) {
        try {
            SummaryCreator summaryCreator = new SummaryCreator(summary);
            summaryCreator.writeSummaryToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}