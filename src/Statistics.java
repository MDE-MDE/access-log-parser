import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {

    long totalTraffic = 0;
    LocalDateTime minTime = LocalDateTime.MAX;
    LocalDateTime maxTime = LocalDateTime.MIN;


    public Statistics() {
    }

    public void addEntyty(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        if (logEntry.getTime().isBefore(minTime)) {
            minTime = logEntry.getTime();
        }
        if (logEntry.getTime().isAfter(maxTime)) {
            maxTime = logEntry.getTime();
        }
    }

    public double getTrafficRate() {
        long diffHours = Duration.between(minTime, maxTime).toHours();
        return diffHours > 0 ? (double) totalTraffic / diffHours : totalTraffic;
    }
}
