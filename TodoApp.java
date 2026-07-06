// Week 2 Assignment - Todo List Application
// A console app to manage tasks with categories, status tracking and sorting

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Status {
    NOT_STARTED, IN_PROGRESS, COMPLETED
}

class Task {
    String title;
    String description;
    LocalDate dueDate;
    String category;
    Status status;

    public Task(String title, String description, LocalDate dueDate, String category) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.category = category;
        this.status = Status.NOT_STARTED;
    }

    @Override
    public String toString() {
        return title + " | Category: " + category + " | Due: " + dueDate + " | Status: " + status;
    }
}

public class TodoApp {

    List<Task> taskList = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        app.setupDefaultCategories();
        app.showMenu();
    }

    public void setupDefaultCategories() {
        categories.add("Work");
        categories.add("Personal");
        categories.add("Shopping");
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- TODO MENU ---");
            System.out.println("1. Add Task");
            System.out.println("2. Update Task Status");
            System.out.println("3. View All Tasks");
            System.out.println("4. Filter by Category");
            System.out.println("5. Sort by Due Date");
            System.out.println("6. View Progress");
            System.out.println("7. Exit");
            System.out.print("Choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addTask(); break;
                case 2: updateStatus(); break;
                case 3: viewTasks(taskList); break;
                case 4: filterByCategory(); break;
                case 5: sortByDate(); break;
                case 6: viewProgress(); break;
                case 7: System.out.println("Bye!"); return;
                default: System.out.println("Invalid option, try again");
            }
        }
    }

    public void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.print("Due Date (dd-MM-yyyy): ");
        String dateStr = scanner.nextLine();
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            System.out.println("Wrong date format!");
            return;
        }
        System.out.println("Categories: " + categories);
        System.out.print("Category: ");
        String cat = scanner.nextLine();
        if (!categories.contains(cat)) {
            categories.add(cat);
            System.out.println("New category added: " + cat);
        }
        taskList.add(new Task(title, desc, date, cat));
        System.out.println("Task added!");
    }

    public void updateStatus() {
        viewTasks(taskList);
        if (taskList.isEmpty()) return;
        System.out.print("Task number to update: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        Task task = taskList.get(index);
        System.out.println("1: NOT_STARTED  2: IN_PROGRESS  3: COMPLETED");
        int s = scanner.nextInt();
        scanner.nextLine();
        if (s == 1) task.status = Status.NOT_STARTED;
        else if (s == 2) task.status = Status.IN_PROGRESS;
        else if (s == 3) task.status = Status.COMPLETED;
        else System.out.println("Invalid");
        System.out.println("Updated!");
    }

    public void viewTasks(List<Task> list) {
        if (list.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    public void filterByCategory() {
        System.out.print("Category: ");
        String cat = scanner.nextLine();
        List<Task> filtered = new ArrayList<>();
        for (Task task : taskList) {
            if (task.category.equalsIgnoreCase(cat)) {
                filtered.add(task);
            }
        }
        viewTasks(filtered);
    }

    public void sortByDate() {
        List<Task> sorted = new ArrayList<>(taskList);
        sorted.sort((t1, t2) -> t1.dueDate.compareTo(t2.dueDate));
        viewTasks(sorted);
    }

    public void viewProgress() {
        int notStarted = 0, inProgress = 0, completed = 0;
        for (Task task : taskList) {
            if (task.status == Status.NOT_STARTED) notStarted++;
            else if (task.status == Status.IN_PROGRESS) inProgress++;
            else if (task.status == Status.COMPLETED) completed++;
        }
        System.out.println("Not Started: " + notStarted);
        System.out.println("In Progress: " + inProgress);
        System.out.println("Completed: " + completed);
    }
}
