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
    HashSet<String> existsListPages = new HashSet<>();
    HashSet<String> noExistsListPages = new HashSet<>();
    HashMap<String, Integer> frequecyOs = new HashMap<>();
    HashMap<String, Integer> frequecyBrowser = new HashMap<>();
    int totalVisits = 0;
    int errorResponses = 0;
    HashSet<String> ipAddrs = new HashSet<>();
    HashMap<Integer, Integer> visitsPerSecond = new HashMap<>();
    HashSet<String> referer = new HashSet<>();
    HashMap<String, Integer> visitsPerUser = new HashMap<>();


    public Statistics() {
    }

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        if (logEntry.getTime().isBefore(minTime)) minTime = logEntry.getTime();

        if (logEntry.getTime().isAfter(maxTime)) maxTime = logEntry.getTime();

        if (logEntry.getResponseCode() == 200)
            existsListPages.add(logEntry.getReferer());

        if (logEntry.getResponseCode() == 404)
            noExistsListPages.add(logEntry.getReferer());

        String os = logEntry.getUserAgent().getOsType();
        frequecyOs.put(os, frequecyOs.getOrDefault(os, 0) +1);

        /*if (!frequecyOs.isEmpty())
            frequecyOs.put(os, 1);
        else
            frequecyOs.put(os, frequecyOs.get(os) + 1);
*/
        String browser = logEntry.getUserAgent().getBrowserType();
        frequecyBrowser.put(browser, frequecyBrowser.getOrDefault(browser, 0) +1);

        /*if (!frequecyBrowser.isEmpty())
            frequecyBrowser.put(browser, 1);
        else
            frequecyBrowser.put(browser, frequecyBrowser.get(browser) + 1);
*/
        if (!logEntry.getUserAgent().isBot()) {
            totalVisits++;
            ipAddrs.add(logEntry.getIpAddr());
            int second = logEntry.getTime().getSecond();
            visitsPerSecond.put(second, visitsPerSecond.getOrDefault(second, 0) +1);
            String ip = logEntry.getIpAddr();
            visitsPerUser.put(ip, visitsPerUser.getOrDefault(ip, 0) +1);
        }

        if (logEntry.getResponseCode() >= 400 && logEntry.getResponseCode() < 600) {
            errorResponses++;
        }

        if (!logEntry.getReferer().equals("-")) {
            String ref = logEntry.getReferer().split("/")[2];
            if (ref.contains("&")) referer.add(ref.substring(0,ref.indexOf("&")));
            else referer.add(ref);
        }
    }

    public double getTrafficRate() {
        long diffHours = Duration.between(minTime, maxTime).toHours();
        return diffHours > 0 ? (double) totalTraffic / diffHours : totalTraffic;
    }

    public double getAverageNumberVisitsOfSitePerHour() {
        long diffHours = Duration.between(minTime, maxTime).toHours();
        return diffHours > 0 ? (double) totalVisits / diffHours : totalVisits;
    }

    public double getAverageNumberErrorRequestPerHour() {
        long diffHours = Duration.between(minTime, maxTime).toHours();
        return diffHours > 0 ? (double) errorResponses / diffHours : errorResponses;
    }

    public double getAverageTrafficPerUser() {
        return !ipAddrs.isEmpty() ? (double) totalVisits / ipAddrs.size() : totalVisits;
    }

    public Set<String> getExistsListPages() {
        return new HashSet<>(existsListPages);
    }

    public Set<String> getNoExistsListPages() {
        return new HashSet<>(noExistsListPages);
    }

    public Set<String> getSites() {
        return new HashSet<>(referer);
    }

    public Map<Integer, Integer> getVisitsPerSecond() {
        return new HashMap<>(visitsPerSecond);
    }

    public Map<String, Integer> getMaxVisitPerUser() {
        return new HashMap<>(visitsPerUser);
    }

    public Map<String, Double> getFrequecyOs() {
        Map<String, Double> frequecyOsPercent = new HashMap<>();
        int totalCount = 0;
        for (Map.Entry<String, Integer> entry : frequecyOs.entrySet()) {
            totalCount += entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : frequecyOs.entrySet()) {
            frequecyOsPercent.put(entry.getKey(), (double) entry.getValue() / totalCount);
        }
        return frequecyOsPercent;
    }

    public Map<String, Double> getFrequecyBrowser() {
        Map<String, Double> frequecyBrPercent = new HashMap<>();
        int totalCount = 0;
        for (Map.Entry<String, Integer> entry : frequecyBrowser.entrySet()) {
            totalCount += entry.getValue();
        }

        for (Map.Entry<String, Integer> entry : frequecyBrowser.entrySet()) {
            frequecyBrPercent.put(entry.getKey(), (double) entry.getValue() / totalCount);
        }
        return frequecyBrPercent;
    }
}
