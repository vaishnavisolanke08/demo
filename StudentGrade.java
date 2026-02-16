import java.util.ArrayList;
import java.util.Scanner;

public class StudentGrade{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<Integer> studentMarks = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();

        // Input student data
        for (int i = 0; i < n; i++) {
            sc.nextLine(); // clear buffer

            System.out.print("\nEnter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter marks: ");
            int marks = sc.nextInt();

            studentNames.add(name);
            studentMarks.add(marks);
        }

        // Calculations
        int total = 0;
        int highest = studentMarks.get(0);
        int lowest = studentMarks.get(0);

        for (int marks : studentMarks) {
            total += marks;

            if (marks > highest) {
                highest = marks;
            }

            if (marks < lowest) {
                lowest = marks;
            }
        }

        double average = (double) total / n;

        // Summary Report
        System.out.println("\n===== Student Summary Report =====");
        System.out.println("Name\t\tMarks");

        for (int i = 0; i < n; i++) {
            System.out.println(studentNames.get(i) + "\t\t" + studentMarks.get(i));
        }

        System.out.println("\nAverage Marks : " + average);
        System.out.println("Highest Marks : " + highest);
        System.out.println("Lowest Marks  : " + lowest);

        sc.close();
    }
}
