import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {

    final String ipAddr;
    final LocalDateTime time;
    final HttpMethod method;
    final String path;
    final int responseCode;
    final int responseSize;
    final String referer;
    final UserAgent userAgent;

    enum HttpMethod {
        GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE, PATCH
    }

    public LogEntry(String line) {
        this.ipAddr = parseIpAddress(line);
        this.time = parseTime(line);
        this.method = HttpMethod.valueOf(parseMethod(line));
        this.path = parsePath(line);
        this.responseCode = parseResponseCode(line);
        this.responseSize = parseResponseSize(line);
        this.referer = parseReferer(line);
        this.userAgent = new UserAgent(parseUserAgent(line));
    }

    private String parseIpAddress(String line) {
        return line.substring(0, line.indexOf(" "));
    }

    private LocalDateTime parseTime(String line) {
        String str = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.parse(str, dt);
        return zdt.toLocalDateTime();
    }

    private String parseMethod(String line) {
        return line.substring(line.indexOf("\"") + 1, line.indexOf(" /"));
    }

    private String parsePath(String line) {
        return line.substring(line.indexOf(" /") + 1, line.indexOf(" HTTP/"));
    }

    private int parseResponseCode(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[8]);
    }

    private int parseResponseSize(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[9]);
    }

    private String parseReferer(String line) {
        String[] parts = line.split(" ");
        if (parts.length < 12) return "-";
        return parts[10].replace("\"", "");
    }

    private String parseUserAgent(String line) {
        String[] parts = line.split(" ");
        if (parts.length < 12) return "-";
        line = line.substring(0, line.length() - 1);
        return line.substring(line.lastIndexOf("\"") + 1);
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}
