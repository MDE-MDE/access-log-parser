import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число и нажмите <Enter>:  ");
        int firstNumb = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число и нажмите <Enter>:  ");
        int secondNumb = new Scanner(System.in).nextInt();
        System.out.println("Сумма чисел: " + (firstNumb + secondNumb));
        System.out.println("Разность чисел: " + (firstNumb - secondNumb));
        System.out.println("Произведение чисел: " + (firstNumb * secondNumb));
        if (secondNumb != 0) {
            System.out.println("Частное чисел: " + (double)(firstNumb / secondNumb));
        } else {
            System.out.println("Деление на ноль невозможно!");
        }
    }
}
