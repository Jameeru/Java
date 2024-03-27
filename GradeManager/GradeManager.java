import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Grade {
    private double score;
    private String category;

    public Grade(double score, String category) {
        this.score = score;
        this.category = category;
    }

    public double getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }
}

class Course {
    private String name;
    private List<Grade> grades;

    public Course(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addGrade(double score, String category) {
        grades.add(new Grade(score, category));
    }

    public List<Grade> getGrades() {
        return grades;
    }
}

class Student {
    private String name;
    private List<Course> courses;

    public Student(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }
}

public class GradeManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a student
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        Student student = new Student(studentName);

        // Add courses and grades for the student
        while (true) {
            System.out.print("Enter course name (or type 'done' to finish): ");
            String courseName = scanner.nextLine();
            if (courseName.equalsIgnoreCase("done")) {
                break;
            }

            Course course = new Course(courseName);

            while (true) {
                System.out.print("Enter grade category (e.g., Test, Assignment) or type 'done': ");
                String category = scanner.nextLine();
                if (category.equalsIgnoreCase("done")) {
                    break;
                }

                System.out.print("Enter grade for " + category + ": ");
                double score = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                course.addGrade(score, category);
            }

            student.addCourse(course);
        }

        // Calculate and display average grades for each course
        System.out.println("\nGrades for " + student.getName() + ":");
        for (Course course : student.getCourses()) {
            System.out.println("Course: " + course.getName());
            double totalScore = 0;
            int numGrades = 0;
            for (Grade grade : course.getGrades()) {
                System.out.println(grade.getCategory() + ": " + grade.getScore());
                totalScore += grade.getScore();
                numGrades++;
            }
            if (numGrades > 0) {
                double average = totalScore / numGrades;
                System.out.println("Average: " + average);
            } else {
                System.out.println("No grades entered.");
            }
            System.out.println();
        }

        scanner.close();
    }
}
