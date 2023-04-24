import org.example.SummaryCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SummaryCreatorTest {
    private SummaryCreator creator;
    private static final String testText = "TEST";

    @BeforeEach
    void setUp() {
        try {
            creator = new SummaryCreator(testText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("For initialisation a empty file should be created.")
    void testSummaryCreation() {
        assertEquals(checkIfFileExists(), true);
        assertEquals(checkIfFileIsEmpty(), true);
    }

    boolean checkIfFileExists() {
        File summaryFile = new File(SummaryCreator.FILE_NAME);
        return summaryFile.exists();
    }

    boolean checkIfFileIsEmpty() {
        File summaryFile = new File(SummaryCreator.FILE_NAME);
        return summaryFile.length() == 0;
    }

    @Test
    @DisplayName("The text after the call should be TEST")
    void testWriteSummaryToFile() throws IOException {
        creator.writeSummaryToFile();
        assertEquals(getTextOfFile(), testText);
        assertEquals(getTextOfFile().equals("FALSE"), false);
    }

    String getTextOfFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(SummaryCreator.FILE_NAME));
        String line = br.readLine();
        return line;
    }

}
