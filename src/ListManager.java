import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ListManager {

    private static ArrayList<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = "list.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        char choice;
        loadListFromFile();

        do {
            displayMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.next().charAt(0);
            scanner.nextLine(); // Consume the newline character

            switch (Character.toUpperCase(choice)) {
                case 'A':
                    addItem();
                    break;
                case 'D':
                    deleteItem();
                    break;
                case 'V':
                    printList();
                    break;
                case 'O':
                    openListFromFile();
                    break;
                case 'S':
                    saveListToFile();
                    break;
                case 'C':
                    clearList();
                    break;
                case 'N':
                    createNewList();
                    break;
                case 'Q':
                    quitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (Character.toUpperCase(choice) != 'Q');
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("N - Create a new list");
        System.out.println("Q - Quit");
        System.out.println();
        displayList();
    }

    private static void displayList() {
        if (itemList.isEmpty()) {
            System.out.println("List is empty.");
        } else {
            System.out.println("Current List:");
            for (int i = 0; i < itemList.size(); i++) {
                System.out.println((i + 1) + ". " + itemList.get(i));
            }
        }
        System.out.println();
    }

    private static void addItem() {
        System.out.print("Enter the item to add: ");
        String newItem = scanner.nextLine();
        if (!newItem.isEmpty()) {
            itemList.add(newItem);
            needsToBeSaved = true;
            System.out.println("Item added successfully.");
        } else {
            System.out.println("Error: Item cannot be empty. Try again.");
        }
    }

    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        displayList();
        System.out.print("Enter the number of the item to delete (0 to cancel): ");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= itemList.size()) {
            itemList.remove(choice - 1);
            needsToBeSaved = true;
            System.out.println("Item deleted successfully.");
        } else if (choice != 0) {
            System.out.println("Invalid index. No item deleted.");
        }
    }

    private static void printList() {
        displayList();
    }

    private static void openListFromFile() {
        if (needsToBeSaved) {
            System.out.print("Warning: Unsaved changes will be lost. Continue? (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            if (Character.toUpperCase(confirmation) != 'Y') {
                return;
            }
        }

        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the filename to load: ");
        String newFileName = scanner.nextLine();
        loadListFromFile(newFileName);
        needsToBeSaved = false;
        System.out.println("List loaded successfully.");
    }

    private static void saveListToFile() {
        if (!itemList.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(currentFileName))) {
                for (String item : itemList) {
                    writer.println(item);
                }
                needsToBeSaved = false;
                System.out.println("List saved successfully.");
            } catch (IOException e) {
                System.out.println("Error saving the list to file.");
            }
        } else {
            System.out.println("List is empty. Nothing to save.");
        }
    }

    private static void loadListFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            itemList.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                itemList.add(line);
            }
            currentFileName = fileName;
        } catch (IOException e) {
            System.out.println("Error loading the list from file.");
        }
    }

    private static void loadListFromFile() {
        loadListFromFile(currentFileName);
    }

    private static void clearList() {
        if (!itemList.isEmpty()) {
            System.out.print("Are you sure you want to clear the list? (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            if (Character.toUpperCase(confirmation) == 'Y') {
                itemList.clear();
                needsToBeSaved = true;
                System.out.println("List cleared successfully.");
            }
        } else {
            System.out.println("List is already empty.");
        }
    }

    private static void createNewList() {
        if (needsToBeSaved) {
            System.out.print("Warning: Unsaved changes will be lost. Continue? (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            if (Character.toUpperCase(confirmation) != 'Y') {
                return;
            }
        }

        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the base filename for the new list: ");
        String baseFileName = scanner.nextLine();
        currentFileName = baseFileName + ".txt";
        itemList.clear();
        needsToBeSaved = true;
        System.out.println("New list created successfully.");
    }

    private static void quitProgram() {
        if (needsToBeSaved) {
            System.out.print("Warning: Unsaved changes will be lost. Continue? (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            if (Character.toUpperCase(confirmation) != 'Y') {
                return;
            }
        }

        saveListToFile();
        System.out.println("Exiting the program. Goodbye!");
    }
}
