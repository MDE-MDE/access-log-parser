public class UserAgent {
    final String os;
    final String browser;
    final boolean isBot;

    public UserAgent(String line) {
        this.os = parseOs(line);
        this.browser = parseBrowser(line);
        this.isBot = isBot(line);
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

    private boolean isBot(String line) {
        return line.toLowerCase().contains("bot");
    }

    public boolean isBot() {
        return isBot;
    }

    public String getOsType() {
        return os;
    }

    public String getBrowserType() {
        return browser;
    }
}
