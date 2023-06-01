package org.example;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static Logger logger = null;
    private List<String> loggedMessages;

    private Logger() {
        loggedMessages = new ArrayList<>();
    }

    public static synchronized Logger getInstance() {                                                                   // This is a singleton so every other class can use the same error log (we just need 1 summary of all errors)
        if (logger == null)
            logger = new Logger();

        return logger;
    }

    public void log(String message) {
        loggedMessages.add(message);
    }

    public String getLoggedMessageSummary() {
        String messageSummary = "";

        for (String message : loggedMessages) {
            messageSummary += message + "\n";
        }

        return messageSummary;
    }
}
