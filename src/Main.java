import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        while (true) {
            try {
                String path = new Scanner(System.in).nextLine();
                File fIle = new File(path);
                boolean fileExists = fIle.exists();
                boolean isDirectory = fIle.isDirectory();
                if (!fileExists || isDirectory) {
                    System.out.println("Указанный файл не существует или " +
                            "указанный путь является путём к папке, а не к файлу");
                    continue;
                } else {
                    count++;
                    System.out.println("Путь указан верно");
                    System.out.println("Это файл номер " + count);

                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader =
                            new BufferedReader(fileReader);
                    String line;
                    Statistics statistics = new Statistics();
                    while ((line = reader.readLine()) != null) {
                        LogEntry logEntry = new LogEntry(line);
                        statistics.addEntyty(logEntry);
                    }
                    System.out.println("Объем часового трафика: " + statistics.getTrafficRate());

                    /*
                    int amountLine = 0, yndexBotCount = 0, googleBotCount = 0;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if(length > 1024) throw new LengthException("Завершение подсчета кол-ва строк в файле. " +
                                "Строка длиннее 1024 символов.");
                        amountLine++;
                        if (!line.contains("YandexBot") && !line.contains("Googlebot")) continue;
                        String userAgent = line.substring(line.indexOf("compatible"), line.length()-1);
                        String[] parts = userAgent.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();
                            if (fragment.contains("/")) fragment = fragment.substring(0, fragment.indexOf("/"));
                            if (fragment.equals("Googlebot")) googleBotCount++;
                            if (fragment.equals("YandexBot")) yndexBotCount++;
                        }
                    }
                    System.out.println("Доля запросов от YandexBot относительно общего числа запросов: "
                            + (double)yndexBotCount/amountLine * 100);
                    System.out.println("Доля запросов от Googlebot относительно общего числа запросов: "
                            + (double)googleBotCount/amountLine * 100);
                     */
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
