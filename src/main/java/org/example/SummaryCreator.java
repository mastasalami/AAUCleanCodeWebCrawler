package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SummaryCreator {
    public static final String FILE_NAME = "report.md";

    private String summaryString;
    private File outputFile;

    public SummaryCreator(String summaryString) throws IOException {
        this.summaryString = summaryString;
        outputFile = new File(FILE_NAME);
        createOrClearSummaryFile();
    }

    public void writeSummaryToFile() throws IOException {
        writeContentToSummary(summaryString, true);
    }

    private void createOrClearSummaryFile() throws IOException {
        if (!outputFile.createNewFile())
            writeContentToSummary("", false);
    }

    private void writeContentToSummary(String content, boolean appendContent) throws IOException {
        FileWriter writer = new FileWriter(FILE_NAME, appendContent);
        writer.write(content);
        writer.close();
    }
}
