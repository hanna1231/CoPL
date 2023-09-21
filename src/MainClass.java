import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        System.out.println("Please enter a string:");

        Scanner sc = new Scanner(System.in);
        String string = sc.nextLine();
        sc.close();
        System.out.println(string);

    }
}