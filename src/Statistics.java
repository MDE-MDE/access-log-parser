import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Statistics {

    long totalTraffic = 0;
    LocalDateTime minTime = LocalDateTime.MAX;
    LocalDateTime maxTime = LocalDateTime.MIN;
    HashSet<String> listPages = new HashSet<>();
    HashMap<String, Integer> frequecyOs = new HashMap<>();

    public Statistics() {
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        if (logEntry.getTime().isBefore(minTime)) minTime = logEntry.getTime();

        if (logEntry.getTime().isAfter(maxTime)) maxTime = logEntry.getTime();

        if (logEntry.responseCode == 200)
            listPages.add(logEntry.getReferer());

        String os = logEntry.getUserAgent().os;
        if (!frequecyOs.containsKey(os))
            frequecyOs.put(os, 1);
        else
            frequecyOs.put(os, frequecyOs.get(os) + 1);
    }

    public double getTrafficRate() {
        long diffHours = Duration.between(minTime, maxTime).toHours();
        return diffHours > 0 ? (double) totalTraffic / diffHours : totalTraffic;
    }

    public Set<String> getListPages() {
        return new HashSet<>(listPages);
    }

    public Map<String, Double> getFrequecyOs() {
        Map<String, Double> frequecyOsPercent = new HashMap<>();
        int totalCount = 0;
        for(Map.Entry<String, Integer> map : frequecyOs.entrySet()){
            totalCount += map.getValue();
        }

        for (Map.Entry<String, Integer> entry : frequecyOs.entrySet()) {
            frequecyOsPercent.put(entry.getKey(), (double) entry.getValue() / totalCount);
        }
        return frequecyOsPercent;
    }
}
