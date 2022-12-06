package kindergarten;

import java.util.*;
import java.io.BufferedInputStream;

/**
 * This class is designed to test each method in the Transit file interactively
 * 
 * @author Ethan Chou
 * @author Maksims Kurjanovics Kravcenko
 * @author Kal Pandit
 */

public class Driver {
	public static void main(String[] args) {
		String[] methods = { "makeClassroom", "setupSeats", "seatStudents", "insertMusicalChairs", "playMusicalChairs",
				"addLateStudent", "deleteLeavingStudent" };
		String[] options = { "Test a new input file", "Test another method on the same file", "Quit" };
		int controlChoice = 1;
		Classroom studentClassroom = new Classroom();

		do {
			Scanner sc = new Scanner(System.in);
			sc.useLocale(Locale.US);
			System.out.print("Enter a student info input file => ");
			String inputFile = sc.next();
			do {
				System.out.println("\nWhat method would you like to test?");
				for (int i = 0; i < methods.length; i++)
					System.out.printf("%d. %s\n", i + 1, methods[i]);
				System.out.print("Enter a number => ");
				int choice = sc.nextInt();
				switch (choice) {
					case 1:
						studentClassroom = testMakeClassroom(inputFile);
						studentClassroom.printClassroom();
						break;
					case 2:
						testSetupSeats(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 3:
						testSeatStudents(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 4:
						testInsertMusicalChairs(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 5:
						testPlayMusicalChairs(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 6:
						testAddStudent(studentClassroom);
						studentClassroom.printClassroom();
						break;
					case 7:
						testDeleteStudent(studentClassroom);
						studentClassroom.printClassroom();
						break;
					default:
						System.out.println("Not a valid option!");
				}
				sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
				sc.useLocale(Locale.US);
				System.out.println("What would you like to do now?");
				for (int i = 0; i < 3; i++)
					System.out.printf("%d. %s\n", i + 1, options[i]);
				System.out.print("Enter a number => ");
				controlChoice = sc.nextInt();
			} while (controlChoice == 2);
			sc.close();
		} while (controlChoice == 1);
	}

	private static Classroom testMakeClassroom(String filename) {
		// Call student's makeList method
		Classroom studentClassroom = new Classroom();
		studentClassroom.makeClassroom(filename);
		return studentClassroom;
	}

	private static void testSetupSeats(Classroom studentClassroom) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a seating availability input file => ");
		String inputFile = sc.next();
		studentClassroom.setupSeats(inputFile);
	}

	private static void testSeatStudents(Classroom studentClassroom) {
		studentClassroom.seatStudents();
	}

	private static void testInsertMusicalChairs(Classroom studentClassroom) {
		studentClassroom.insertMusicalChairs();
	}

	private static void testPlayMusicalChairs(Classroom studentClassroom) {
		System.out.println("Here is the classroom after a long game of musical chairs: ");
		studentClassroom.playMusicalChairs();
		System.out.println();
	}

	private static void testAddStudent(Classroom studentClassroom) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nWrite the student's first name -> ");
		String studentName = sc.next();
		System.out.print("\nWrite the student's last name -> ");
		String lastName = sc.next();
		System.out.print("\nWrite the student's height as a number -> ");
		int height = sc.nextInt();
		studentClassroom.addLateStudent(studentName, lastName, height);
	}

	private static void testDeleteStudent(Classroom studentClassroom) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nWrite the student's first name -> ");
		String firstName = sc.next();
		System.out.print("\nWrite the student's last name -> ");
		String lastName = sc.next();
		studentClassroom.deleteLeavingStudent(firstName, lastName);
	}
}
