import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        while (true) {
            String path = new Scanner(System.in).nextLine();
            File fIle = new File(path);
            boolean fileExists = fIle.exists();
            boolean isDirectory = fIle.isDirectory();
            if (!fileExists || isDirectory){
                System.out.println("Указанный файл не существует или " +
                        "указанный путь является путём к папке, а не к файлу");
                continue;
            }
            else{
                count++;
                System.out.println("Путь указан верно");
                System.out.println("Это файл номер " + count);
            }
        }

    }
}
