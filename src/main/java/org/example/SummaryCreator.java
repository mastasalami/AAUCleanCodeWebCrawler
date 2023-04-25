package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SummaryCreator {
    public static final String FILE_NAME = "report.md";

    private String summaryString;
    private File outputFile;

    private final boolean OVERWRITE_SUMMARY = false;
    private final boolean APPEND_TO_SUMMARY = true;

    public SummaryCreator(String summaryString) throws IOException {
        this.summaryString = summaryString;
        outputFile = new File(FILE_NAME);
        createOrClearSummaryFile();
    }

    public void writeSummaryToFile() throws IOException {
        writeContentToSummary(summaryString, APPEND_TO_SUMMARY);
    }

    private void createOrClearSummaryFile() throws IOException {
        if (outputFile.exists())
            clearSummaryfile();
        else
            outputFile.createNewFile();

    }

    private void clearSummaryfile() throws IOException {
        writeContentToSummary("", OVERWRITE_SUMMARY);
    }

    private void writeContentToSummary(String content, boolean appendContent) throws IOException {
        FileWriter writer = new FileWriter(FILE_NAME, appendContent);
        writer.write(content);
        writer.close();
    }
}
