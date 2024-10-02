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
                    int min = Integer.MAX_VALUE, max = 0, amountLine = 0;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if(length > 1024) throw new LengthException("Завершение подсчета кол-ва строк, " +
                                "минимальной и максимальной строки в файле. " +
                                "Строка длиннее 1024 символов.");
                        amountLine++;
                        if (max < length) max = length;
                        if (min > length) min = length;

                    }
                    System.out.println("Общее количество строк в файле: " + amountLine);
                    System.out.println("Длина самой длинной строки в файле: " + max);
                    System.out.println("Длина самой короткой строки в файле: " + min);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
