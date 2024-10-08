public class UserAgent {
    final String os;
    final String browser;

    public UserAgent(String line) {
        this.os = parseOs(line);
        this.browser = parseBrowser(line);
    }

    private String parseOs(String line) {
        if (line.contains("Windows")) return "Windows";
        if (line.contains("Mac OS")) return "Mac OS";
        if (line.contains("Linux")) return "Linux";
        return "Unknown";
    }

    private String parseBrowser(String line) {
        if (line.contains("Edge")) return "Edge";
        if (line.contains("Firefox")) return "Firefox";
        if (line.contains("Chrome")) return "Chrome";
        if (line.contains("Opera")) return "Opera";
        return "Other";
    }

    public String getOsType() {
        return os;
    }

    public String getBrowserType() {
        return browser;
    }
}
