package Day_4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class NotesApp {
	
	private static final String NOTES_DIR = "notes";
	private static final Scanner scanner = new Scanner (System.in); 
	
	public static void main(String[] args) {
		
		System.out.println("___Welcome to the Notes App___\n");
		createNotesDirectory();
		
		while(true) {
			
			showMenu();
			int choice = getChoice();
			try {
				switch(choice) {
				    case 1 -> createNote();
				    case 2 -> viewAllNotes();
				    case 3 -> readNote();
				    case 4 -> deleteNote();
				    case 5 -> {System.out.println("Exiting... Thank you ^-^\n"); return;}
				    default -> System.out.println("Invalid choice ! Please choose between 1 to 5\n");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid Input ! Please try again...\n");
				scanner.nextLine();
			}
		}
	}

	private static void deleteNote() {
        System.out.print("Enter the note title to delete : ");
        String title = scanner.nextLine().trim();
        File noteFile = new File(NOTES_DIR+"/"+title+".txt");

        if (!noteFile.exists()) {
            System.out.println("Note not found !\n");
            return;
        }

        if (noteFile.delete()) {
            System.out.println("Note deleted successfully\n");
        } else {
            System.out.println("Failed to delete the note\n");
        }
    }

	private static void readNote() {
        System.out.print("Enter the note title to read : ");
        String title = scanner.nextLine().trim();
        File noteFile = new File(NOTES_DIR+"/"+title+".txt");

        if (!noteFile.exists()) {
            System.out.println("Note not found !\n");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(noteFile))) {
            System.out.println("\nNote Content :");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("  " + line);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error reading note : "+e.getMessage()+"\n");
        }
    }

	private static void viewAllNotes() {
		File folder = new File(NOTES_DIR);
		File[] files = folder.listFiles((dir,name) -> name.endsWith(".txt"));
		
		if(files == null || files.length == 0) {
			System.out.println("No notes found !\n");
			return;
		}
		
		System.out.println("All Saved Notes : ");
		for(File file : files) {
			System.out.println("-> "+file.getName());
		}
		System.out.println();
	}

	private static void createNote() {
		System.out.print("Enter note title (without space) : ");
		String title = scanner.nextLine().trim();
		if(title == null || title.contains(" ") || title.isEmpty()) {
	        System.out.println("Invalid title ! Avoid empty or space in title\n");
	        return;
	    }
		System.out.print("Type your note below (type 'END' on a new line to finish : ");
		StringBuilder content = new StringBuilder();
		
		while(true) {
			String line = scanner.nextLine();
			if(line.equalsIgnoreCase("END")) break;
			content.append(line).append("\n");
		}
		
		File noteFile = new File(NOTES_DIR+"/"+title+".txt");
        try (FileWriter writer = new FileWriter(noteFile)) {
            writer.write(content.toString());
            System.out.println("Note saved as '"+title +".txt'\n");
        } catch (IOException e) {
            System.out.println("Error saving note : "+e.getMessage()+"\n");
        }
    }

	private static int getChoice() {
		try {
			int choice = scanner.nextInt();
	        scanner.nextLine();
	        return choice;
		}
		catch(InputMismatchException e) {
			scanner.nextLine();
			return -1;
		}
	}

	private static void showMenu() {
		System.out.println("1. CreateNote");
		System.out.println("2. ViewAllNotes");
		System.out.println("3. ReadNote");
		System.out.println("4. DeleteNote");
		System.out.println("5. Exit\n");
		System.out.print("Enter your choice : ");
	}

	private static void createNotesDirectory() {
		File dir = new File(NOTES_DIR);
		if(!dir.exists()) {
			if(dir.mkdir()) {
				System.out.println("'notes' Folder created successfully...\n ");
			}
			else {
				System.out.println("Failed to create 'notes' folder !\n");
			}
		}
	}

}
