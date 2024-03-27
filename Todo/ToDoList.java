import java.util.ArrayList;
import java.util.Scanner;

class Task {
    private String name;
    private boolean completed;

    public Task(String name) {
        this.name = name;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

public class ToDoList {
    private ArrayList<Task> tasks;
    private Scanner scanner;

    public ToDoList() {
        tasks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addTask(String taskName) {
        Task task = new Task(taskName);
        tasks.add(task);
        System.out.println("Task added: " + taskName);
    }

    public void displayTasks() {
        System.out.println("Tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println((i + 1) + ". " + task.getName() + (task.isCompleted() ? " - Completed" : ""));
        }
    }

    public void markTaskAsCompleted(int index) {
        if (index >= 1 && index <= tasks.size()) {
            Task task = tasks.get(index - 1);
            task.setCompleted(true);
            System.out.println("Task marked as completed: " + task.getName());
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public void deleteTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            Task task = tasks.remove(index - 1);
            System.out.println("Task deleted: " + task.getName());
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public void start() {
        System.out.println("Welcome to the To-Do List Application!");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter task name:");
                    String taskName = scanner.nextLine();
                    addTask(taskName);
                    break;
                case 2:
                    displayTasks();
                    break;
                case 3:
                    System.out.println("Enter the index of the task to mark as completed:");
                    int completedIndex = scanner.nextInt();
                    markTaskAsCompleted(completedIndex);
                    break;
                case 4:
                    System.out.println("Enter the index of the task to delete:");
                    int deleteIndex = scanner.nextInt();
                    deleteTask(deleteIndex);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        toDoList.start();
    }
}
