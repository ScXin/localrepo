package org.phoebus.logbook;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * A service for creating log entries into the registered log clients.
 * 
 * TODO the service might be less confusing if it simply returned the registered
 * {@link LogFactory}'s and then has the calling code submit logging request to
 * the clients.
 * 
 * @author Kunal Shroff
 *
 */
public class LogService {

    /** Suggested logger for all log book related code. */
    public static final Logger logger = Logger.getLogger(LogService.class.getPackageName());

    static final java.lang.String SERVICE_NAME = "LoggingService";

    private static LogService logService;
    private ServiceLoader<LogFactory> loader;
    private Map<String, LogFactory> logFactories;

    private static ExecutorService executor = Executors.newCachedThreadPool();

    private LogService() {
        // Load available adapter factories
        logFactories = new HashMap<String, LogFactory>();
        loader = ServiceLoader.load(LogFactory.class);
        loader.stream().forEach(p -> {
            LogFactory logFactory = p.get();
            logFactories.put(logFactory.getId(), logFactory);
        });
    }

    /**
     * Returns the instance logbook service instance
     * 
     * @return
     */
    public static LogService getInstance() {
        if (logService == null) {
            logService = new LogService();
        }
        return logService;
    }

    /**
     * Get a registered log factory for creating logbook clients to the specified type of logbook service
     * @param logbook_factory A string identifying the logbook service type
     * @return logbookFactory for creating clients to logbookServiceId
     */
    public LogFactory getLogFactories(String logbookServiceId) {
        return logFactories.get(logbookServiceId);
    }

    /**
     * Get a list of all the registered logbook factories
     * @return A Map of all the logbook factories
     */
    public Map<String, LogFactory> getLogFactories() {
        return Collections.unmodifiableMap(logFactories);
    }

    /**
     * Create a log entry in all registered LogFactory
     * 
     * @param adaptedSelections
     * @param authToken
     *            - Authentication Token
     */
    public void createLogEntry(LogEntry logEntry, Object authToken) {
        executor.submit(() -> {
            logFactories.values().stream().forEach(logFactory -> {
                logFactory.getLogClient(authToken).set(logEntry);
            });
        });
    }

    /**
     * Create a log entry in all registered LogFactory TODO change to Log type
     * 
     * @param adaptedSelections
     */
    public void createLogEntry(List<LogEntry> logEntries, Object authToken) {
        executor.submit(() -> {
            logFactories.values().stream().forEach(logFactory -> {
                logEntries.forEach(logEntry -> {
                    logFactory.getLogClient(authToken).set(logEntry);
                });
            });
        });
    }

    /**
     * Create a log entry in the specified LogFactory TODO change to Log type
     * 
     * @param id
     * @param log
     */
    public void createLogEntry(String id, LogEntry logEntry, Object authToken) {
        executor.submit(() -> {
            logFactories.get(id).getLogClient(authToken).set(logEntry);
        });
    }
}
