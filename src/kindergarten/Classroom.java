package kindergarten;

import java.util.*;
import java.io.*;

/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student
 * in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given
 * seat), and
 * - a Student array parallel to seatingAvailability to show students filed into
 * seats
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in
 * studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine; // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs; // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability; // represents the classroom seats that are available to students
    private Student[][] studentsSitting; // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * 
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom(SNode l, SNode m, boolean[][] a, Student[][] s) {
        studentsInLine = l;
        musicalChairs = m;
        seatingAvailability = a;
        studentsSitting = s;
    }

    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in
     * line.
     * 
     * Reads students from input file and inserts these students in alphabetical
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the
     * file, say x
     * 2) x lines containing one student per line. Each line has the following
     * student
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom(String filename) {
        try {
            Scanner sc = new Scanner(new File(filename), "UTF-8");
            sc.close();
            int numStudents = sc.nextInt();
            Student[] students = new Student[numStudents];
            for (int i = 0; i < students.length; i++) {
                String firstName = sc.next(), lastName = sc.next();
                int age = sc.nextInt();
                students[i] = new Student(firstName, lastName, age);
            }
            for (int i = 0; i < students.length; i++)
                for (int j = i + 1; j < students.length; j++)
                    if (students[i].compareNameTo(students[j]) > 0) {
                        Student temp = students[i];
                        students[i] = students[j];
                        students[j] = temp;
                    }
            for (int i = students.length - 1; i >= 0; i--) {
                SNode n = new SNode(students[i], studentsInLine);
                studentsInLine = n;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of
     * available seats inside the classroom. Imagine that unavailable seats are
     * broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an
     * available seat)
     * 
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
        try {
            Scanner sc = new Scanner(new File(seatingChart));
            int r = sc.nextInt();
            int c = sc.nextInt();
            seatingAvailability = new boolean[r][c];
            studentsSitting = new Student[r][c];
            for (int i = 0; i < seatingAvailability.length; i++)
                for (int j = 0; j < seatingAvailability[i].length; j++)
                    seatingAvailability[i][j] = sc.nextBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the
     * front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into
     * studentsSitting according to
     * seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents() {
        if (musicalChairs != null)
            for (int i = 0; i < 1; i++)
                for (int j = 0; j < 1; j++)
                    if (seatingAvailability[i][j]) {
                        studentsSitting[i][j] = musicalChairs.getStudent();
                        seatingAvailability[i][j] = false;
                        if (musicalChairs == musicalChairs.getNext())
                            musicalChairs = null;
                    }
        if (musicalChairs == null)
            for (int i = 0; i < seatingAvailability.length; i++)
                for (int j = 0; j < seatingAvailability[i].length; j++)
                    if (seatingAvailability[i][j]) {
                        studentsSitting[i][j] = studentsInLine.getStudent();
                        seatingAvailability[i][j] = false;
                        if (studentsInLine.getNext() == null) {
                            studentsInLine = null;
                            return;
                        } else
                            studentsInLine = studentsInLine.getNext();
                    }
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then
     * moves
     * into second row.
     */
    public void insertMusicalChairs() {
        for (int i = 0; i < studentsSitting.length; i++)
            for (int j = 0; j < studentsSitting[i].length; j++) {
                Student s = studentsSitting[i][j];
                if (s != null) {
                    studentsSitting[i][j] = null;
                    seatingAvailability[i][j] = true;
                    if (musicalChairs == null) {
                        SNode temp = new SNode();
                        temp.setStudent(s);
                        musicalChairs = temp;
                        temp.setNext(musicalChairs);
                    } else {
                        SNode temp = new SNode(s, musicalChairs.getNext());
                        musicalChairs.setNext(temp);
                        musicalChairs = temp;
                    }
                }
            }
    }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is
     * only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using
     * StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first
     * student in the
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in
     * studentsInLine
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students
     * can be seated.
     */
    public void playMusicalChairs() {
        if (musicalChairs == null)
            insertMusicalChairs();
        int musicalLength = 1;
        SNode temp = musicalChairs.getNext();
        do {
            temp = temp.getNext();
            musicalLength++;
        } while (temp != musicalChairs);
        do {
            Student s;
            Random r = new Random(2022);
            int loser = r.nextInt(musicalLength);
            musicalLength--;
            SNode prev = musicalChairs, curr = musicalChairs.getNext();
            if (loser == 0) {
                temp = curr.getNext();
                musicalChairs.setNext(temp);
                s = curr.getStudent();
            }
            for (int i = 0; i < loser; i++) {
                prev = curr;
                curr = curr.getNext();
            }
            if (curr == musicalChairs) {
                prev.setNext(musicalChairs.getNext());
                musicalChairs = prev;
            }
            prev.setNext(curr.getNext());
            s = curr.getStudent();
            SNode n = new SNode(s, null);
            if (studentsInLine == null)
                studentsInLine = n;
            else if (studentsInLine.getStudent().getHeight() >= n.getStudent().getHeight()) {
                temp = studentsInLine;
                studentsInLine = n;
                studentsInLine.setNext(temp);
            } else {
                prev = null;
                curr = studentsInLine;
                while (curr != null)
                    if (curr.getStudent().getHeight() < n.getStudent().getHeight()) {
                        prev = curr;
                        curr = curr.getNext();
                        continue;
                    } else
                        break;
                if (prev != null) {
                    prev.setNext(n);
                    n.setNext(curr);
                }
            }
        } while (musicalLength > 1);
        Student winner = null;
        if (musicalChairs == musicalChairs.getNext()) {
            winner = musicalChairs.getStudent();
            musicalChairs = null;
        }
        boolean isWinnerSeated = false;
        for (int i = 0; i < seatingAvailability.length; i++) {
            for (int j = 0; j < seatingAvailability[i].length; j++)
                if (seatingAvailability[i][j]) {
                    studentsSitting[i][j] = winner;
                    seatingAvailability[i][j] = false;
                    isWinnerSeated = true;
                    break;
                }
            if (isWinnerSeated)
                break;
        }
        seatStudents();
    }

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is
     * not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * 
     * @param firstName the first name
     * @param lastName  the last name
     * @param height    the height of the student
     */
    public void addLateStudent(String firstName, String lastName, int height) {
        if (studentsInLine != null) {
            SNode temp = studentsInLine;
            while (temp.getNext() != null)
                temp = temp.getNext();
            temp.setNext(new SNode(new Student(firstName, lastName, height), null));
        } else if (musicalChairs != null) {
            SNode n = new SNode(new Student(firstName, lastName, height), musicalChairs.getNext());
            musicalChairs.setNext(n);
            musicalChairs = n;
        } else
            for (int i = 0; i < seatingAvailability.length; i++)
                for (int j = 0; j < seatingAvailability[i].length; j++)
                    if (seatingAvailability[i][j]) {
                        seatingAvailability[i][j] = false;
                        studentsSitting[i][j] = new Student(firstName, lastName, height);
                        return;
                    }
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName  the student's last name
     */
    public void deleteLeavingStudent(String firstName, String lastName) {
        Student s = new Student(firstName, lastName, 0);
        if (studentsInLine != null) {
            SNode prev = null, temp = studentsInLine;
            if (temp != null && s.compareNameTo(temp.getStudent()) == 0) {
                studentsInLine = temp.getNext();
                return;
            }
            while (temp != null && s.compareNameTo(temp.getStudent()) != 0) {
                prev = temp;
                temp = temp.getNext();
            }
            if (temp == null)
                return;
            prev.setNext(temp.getNext());
        } else if (musicalChairs != null) {
            SNode curr = musicalChairs.getNext(), prev = new SNode();
            while (s.compareNameTo(curr.getStudent()) != 0) {
                if (curr.getNext() == musicalChairs.getNext())
                    break;
                prev = curr;
                curr = curr.getNext();
            }
            if (curr == musicalChairs.getNext() && curr.getNext() == musicalChairs.getNext())
                musicalChairs.setNext(null);
            if (curr == musicalChairs.getNext()) {
                prev = musicalChairs.getNext();
                while (prev.getNext() != musicalChairs.getNext())
                    prev = prev.getNext();
                musicalChairs.setNext(curr.getNext());
                prev.setNext(musicalChairs.getNext());
            } else if (curr.getNext() == musicalChairs)
                prev.setNext(musicalChairs.getNext());
            else
                prev.setNext(curr.getNext());
        } else
            for (int i = 0; i < studentsSitting.length; i++)
                for (int j = 0; j < studentsSitting[i].length; j++)
                    if (studentsSitting[i][j] != null)
                        if (s.compareNameTo(studentsSitting[i][j]) == 0) {
                            studentsSitting[i][j] = null;
                            seatingAvailability[i][j] = true;
                            return;
                        }

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine() {

        // Print studentsInLine
        System.out.println("Students in Line:");
        if (studentsInLine == null)
            System.out.println("EMPTY");
        for (SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext()) {
            System.out.print(ptr.getStudent().print());
            if (ptr.getNext() != null)
                System.out.print(" -> ");
        }
        for (int i = 1; i <= 2; i++)
            System.out.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents() {

        System.out.println("Sitting Students:");

        if (studentsSitting != null)
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {
                    String stringToPrint = "";
                    if (studentsSitting[i][j] == null)
                        if (!seatingAvailability[i][j])
                            stringToPrint = "X";
                        else
                            stringToPrint = "EMPTY";
                    else
                        stringToPrint = studentsSitting[i][j].print();
                    System.out.print(stringToPrint);
                    for (int o = 0; o < (10 - stringToPrint.length()); o++)
                        System.out.print(" ");
                }
                System.out.println();
            }
        else
            System.out.println("EMPTY");
        System.out.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs() {
        System.out.println("Students in Musical Chairs:");
        if (musicalChairs == null) {
            System.out.println("EMPTY");
            System.out.println();
            return;
        }
        SNode ptr;
        for (ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext())
            System.out.print(ptr.getStudent().print() + " -> ");
        if (ptr == musicalChairs)
            System.out.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        System.out.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() {
        return studentsInLine;
    }

    public void setStudentsInLine(SNode l) {
        studentsInLine = l;
    }

    public SNode getMusicalChairs() {
        return musicalChairs;
    }

    public void setMusicalChairs(SNode m) {
        musicalChairs = m;
    }

    public boolean[][] getSeatingAvailability() {
        return seatingAvailability;
    }

    public void setSeatingAvailability(boolean[][] a) {
        seatingAvailability = a;
    }

    public Student[][] getStudentsSitting() {
        return studentsSitting;
    }

    public void setStudentsSitting(Student[][] s) {
        studentsSitting = s;
    }

}
