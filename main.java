
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ==========================================
// INTERFACE
// ==========================================

interface Displayable {
    void displayDetails();
}

// ==========================================
// ABSTRACT PERSON CLASS
// ==========================================

abstract class Person implements Displayable {

    private int id;
    private String name;
    private String email;
    private int age;

    public Person(int id, String name, String email, int age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email.");
        }
        this.email = email;
    }

    public void introduce() {
        System.out.println("Hello, my name is " + name);
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Name: " + name
                + " | Email: " + email
                + " | Age: " + age;
    }
}

// ==========================================
// STUDENT CLASS
// ==========================================

class Student extends Person {

    private String department;
    private int studyYear;
    private List<Course> courses;
    private List<Double> grades;

    public Student(int id, String name, String email, int age,
                   String department, int studyYear) {
        super(id, name, email, age);
        this.department = department;
        this.studyYear = studyYear;
        courses = new ArrayList<>();
        grades = new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public boolean hasGrades() {
        return !grades.isEmpty();
    }

    public void enrollCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (!courses.contains(course)) {
            courses.add(course);
            course.addStudent(this);
            System.out.println(getName() + " enrolled in "
                    + course.getTitle());
        } else {
            System.out.println("Student is already enrolled.");
        }
    }

    public void addGrade(double grade) {
        if (grade < 0 || grade > 20) {
            throw new IllegalArgumentException(
                    "Grade must be between 0 and 20.");
        }

        grades.add(grade);
    }

    public double calculateAverage() {
        if (grades.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (double grade : grades) {
            total += grade;
        }

        return total / grades.size();
    }

    public void displayGrades() {
        System.out.println("\nGrades for " + getName() + ":");

        if (grades.isEmpty()) {
            System.out.println("No grades available.");
            return;
        }

        for (int i = 0; i < grades.size(); i++) {
            System.out.println("Grade " + (i + 1) + ": "
                    + grades.get(i) + "/20");
        }

        System.out.printf("Average: %.2f/20%n", calculateAverage());
        System.out.println(calculateAverage() >= 10
                ? "Status: PASSED" : "Status: NOT PASSED");
    }

    @Override
    public void displayDetails() {
        System.out.println("\n===== STUDENT DETAILS =====");
        System.out.println(super.toString());
        System.out.println("Department: " + department);
        System.out.println("Study year: " + studyYear);
        System.out.println("Courses enrolled: " + courses.size());
        System.out.printf("Average: %.2f/20%n", calculateAverage());
    }

    @Override
    public String toString() {
        return "Student | " + super.toString()
                + " | Department: " + department
                + " | Year: " + studyYear;
    }
}

// ==========================================
// TEACHER CLASS
// ==========================================

class Teacher extends Person {

    private String specialization;
    private double salary;
    private List<Course> courses;

    public Teacher(int id, String name, String email, int age,
                   String specialization, double salary) {
        super(id, name, email, age);
        this.specialization = specialization;
        this.salary = salary;
        courses = new ArrayList<>();
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getSalary() {
        return salary;
    }

    public void assignCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (course.getTeacher() != null
                && course.getTeacher() != this) {
            throw new IllegalArgumentException(
                    "This course already has another teacher.");
        }

        if (!courses.contains(course)) {
            courses.add(course);
        }

        course.setTeacher(this);
        System.out.println(getName() + " teaches "
                + course.getTitle());
    }

    @Override
    public void displayDetails() {
        System.out.println("\n===== TEACHER DETAILS =====");
        System.out.println(super.toString());
        System.out.println("Specialization: " + specialization);
        System.out.println("Salary: " + salary + " TND");
        System.out.println("Courses taught: " + courses.size());
    }

    @Override
    public String toString() {
        return "Teacher | " + super.toString()
                + " | Specialization: " + specialization
                + " | Salary: " + salary + " TND";
    }
}

// ==========================================
// COURSE CLASS
// ==========================================

class Course implements Displayable {

    private int id;
    private String title;
    private int credits;
    private Teacher teacher;
    private List<Student> students;

    public Course(int id, String title, int credits) {
        this.id = id;
        this.title = title;
        this.credits = credits;
        students = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void displayStudents() {
        System.out.println("\nStudents enrolled in " + title + ":");

        if (students.isEmpty()) {
            System.out.println("No students enrolled.");
            return;
        }

        for (Student student : students) {
            System.out.println(student.getName());
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("\n===== COURSE DETAILS =====");
        System.out.println("Course ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Credits: " + credits);
        System.out.println("Teacher: "
                + (teacher == null ? "Not assigned" : teacher.getName()));
        System.out.println("Enrolled students: " + students.size());
    }

    @Override
    public String toString() {
        return "Course ID: " + id
                + " | Title: " + title
                + " | Credits: " + credits
                + " | Teacher: "
                + (teacher == null ? "Not assigned" : teacher.getName());
    }
}

// ==========================================
// UNIVERSITY CLASS
// ==========================================

class University {

    private String name;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Course> courses;

    public University(String name) {
        this.name = name;
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        courses = new ArrayList<>();
    }

    public Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public Teacher findTeacher(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getId() == id) {
                return teacher;
            }
        }
        return null;
    }

    public Course findCourse(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }

        if (findStudent(student.getId()) != null) {
            throw new IllegalArgumentException(
                    "Student ID already exists.");
        }

        students.add(student);
        System.out.println("Student added successfully.");
    }

    public void addTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher cannot be null.");
        }

        if (findTeacher(teacher.getId()) != null) {
            throw new IllegalArgumentException(
                    "Teacher ID already exists.");
        }

        teachers.add(teacher);
        System.out.println("Teacher added successfully.");
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (findCourse(course.getId()) != null) {
            throw new IllegalArgumentException(
                    "Course ID already exists.");
        }

        courses.add(course);
        System.out.println("Course added successfully.");
    }

    // Search by student name
    public void searchStudentByName(String name) {
        boolean found = false;

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                student.displayDetails();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found with name: " + name);
        }
    }

    // Delete a student and remove their course enrollments
    public void deleteStudent(int id) {
        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        for (Course course : courses) {
            course.removeStudent(student);
        }

        students.remove(student);

        System.out.println("Student " + student.getName()
                + " deleted successfully.");
    }

    // Filter students by department
    public void displayStudentsByDepartment(String department) {
        boolean found = false;

        System.out.println("\nStudents in department: " + department);

        for (Student student : students) {
            if (student.getDepartment().equalsIgnoreCase(department)) {
                System.out.println(student);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No students found in this department.");
        }
    }

    public void displayStudents() {
        System.out.println("\n===== ALL STUDENTS =====");

        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void displayTeachers() {
        System.out.println("\n===== ALL TEACHERS =====");

        if (teachers.isEmpty()) {
            System.out.println("No teachers registered.");
            return;
        }

        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    public void displayCourses() {
        System.out.println("\n===== ALL COURSES =====");

        if (courses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }

        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public void displayStatistics() {
        System.out.println("\n===== UNIVERSITY STATISTICS =====");
        System.out.println("University: " + name);
        System.out.println("Total students: " + students.size());
        System.out.println("Total teachers: " + teachers.size());
        System.out.println("Total courses: " + courses.size());

        double totalAverages = 0;
        int studentsWithGrades = 0;

        for (Student student : students) {
            if (student.hasGrades()) {
                totalAverages += student.calculateAverage();
                studentsWithGrades++;
            }
        }

        if (studentsWithGrades > 0) {
            System.out.printf("Mean student average: %.2f/20%n",
                    totalAverages / studentsWithGrades);
        } else {
            System.out.println("No grades recorded yet.");
        }
    }
}

// ==========================================
// MAIN CLASS
// ==========================================

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final University university =
            new University("National Engineering University");

    public static void main(String[] args) {
        addSampleData();

        int choice;

        do {
            displayMenu();
            choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        addTeacher();
                        break;
                    case 3:
                        addCourse();
                        break;
                    case 4:
                        enrollStudent();
                        break;
                    case 5:
                        assignTeacher();
                        break;
                    case 6:
                        addStudentGrade();
                        break;
                    case 7:
                        university.displayStudents();
                        break;
                    case 8:
                        university.displayTeachers();
                        break;
                    case 9:
                        university.displayCourses();
                        break;
                    case 10:
                        displayStudentDetails();
                        break;
                    case 11:
                        displayCourseDetails();
                        break;
                    case 12:
                        displayStudentGrades();
                        break;
                    case 13:
                        university.displayStatistics();
                        break;
                    case 14:
                        university.searchStudentByName(
                                readText("Enter student name: "));
                        break;
                    case 15:
                        university.deleteStudent(
                                readInt("Enter student ID to delete: "));
                        break;
                    case 16:
                        university.displayStudentsByDepartment(
                                readText("Enter department name: "));
                        break;
                    case 0:
                        System.out.println("Thank you for using the system!");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n==================================");
        System.out.println(" UNIVERSITY MANAGEMENT SYSTEM");
        System.out.println("==================================");
        System.out.println("1.  Add student");
        System.out.println("2.  Add teacher");
        System.out.println("3.  Add course");
        System.out.println("4.  Enroll student in course");
        System.out.println("5.  Assign teacher to course");
        System.out.println("6.  Add student grade");
        System.out.println("7.  Display all students");
        System.out.println("8.  Display all teachers");
        System.out.println("9.  Display all courses");
        System.out.println("10. Display student details");
        System.out.println("11. Display course details");
        System.out.println("12. Display student grades");
        System.out.println("13. Display statistics");
        System.out.println("14. Search student by name");
        System.out.println("15. Delete student");
        System.out.println("16. Search students by department");
        System.out.println("0.  Exit");
        System.out.println("==================================");
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Double.parseDouble(
                        scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readText(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static void addStudent() {
        int id = readInt("Student ID: ");
        String name = readText("Name: ");
        String email = readText("Email: ");
        int age = readInt("Age: ");
        String department = readText("Department: ");
        int year = readInt("Study year: ");

        if (age <= 0 || year <= 0) {
            throw new IllegalArgumentException(
                    "Age and study year must be positive.");
        }

        university.addStudent(new Student(
                id, name, email, age, department, year));
    }

    private static void addTeacher() {
        int id = readInt("Teacher ID: ");
        String name = readText("Name: ");
        String email = readText("Email: ");
        int age = readInt("Age: ");
        String specialization = readText("Specialization: ");
        double salary = readDouble("Salary in TND: ");

        if (age <= 0 || salary < 0) {
            throw new IllegalArgumentException(
                    "Age must be positive and salary cannot be negative.");
        }

        university.addTeacher(new Teacher(
                id, name, email, age, specialization, salary));
    }

    private static void addCourse() {
        int id = readInt("Course ID: ");
        String title = readText("Course title: ");
        int credits = readInt("Credits: ");

        if (credits <= 0) {
            throw new IllegalArgumentException(
                    "Credits must be positive.");
        }

        university.addCourse(new Course(id, title, credits));
    }

    private static void enrollStudent() {
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");

        Student student = university.findStudent(studentId);
        Course course = university.findCourse(courseId);

        if (student == null || course == null) {
            System.out.println("Student or course not found.");
            return;
        }

        student.enrollCourse(course);
    }

    private static void assignTeacher() {
        int teacherId = readInt("Teacher ID: ");
        int courseId = readInt("Course ID: ");

        Teacher teacher = university.findTeacher(teacherId);
        Course course = university.findCourse(courseId);

        if (teacher == null || course == null) {
            System.out.println("Teacher or course not found.");
            return;
        }

        teacher.assignCourse(course);
    }

    private static void addStudentGrade() {
        int studentId = readInt("Student ID: ");
        Student student = university.findStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.addGrade(readDouble("Grade out of 20: "));
        System.out.println("Grade added successfully.");
    }

    private static void displayStudentDetails() {
        Student student = university.findStudent(
                readInt("Enter student ID: "));

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            student.displayDetails();
            student.displayGrades();
        }
    }

    private static void displayCourseDetails() {
        Course course = university.findCourse(
                readInt("Enter course ID: "));

        if (course == null) {
            System.out.println("Course not found.");
        } else {
            course.displayDetails();
            course.displayStudents();
        }
    }

    private static void displayStudentGrades() {
        Student student = university.findStudent(
                readInt("Enter student ID: "));

        if (student == null) {
            System.out.println("Student not found.");
        } else {
            student.displayGrades();
        }
    }

    private static void addSampleData() {
        Student student1 = new Student(
                101, "Ahmed", "ahmed@example.com",
                21, "Computer Science", 3);

        Student student2 = new Student(
                102, "Sara", "sara@example.com",
                22, "Software Engineering", 4);

        Teacher teacher = new Teacher(
                201, "Mohamed", "mohamed@example.com",
                40, "Java Programming", 3500);

        Course course1 = new Course(
                301, "Object-Oriented Programming", 4);

        Course course2 = new Course(
                302, "Data Structures", 3);

        university.addStudent(student1);
        university.addStudent(student2);
        university.addTeacher(teacher);
        university.addCourse(course1);
        university.addCourse(course2);

        student1.enrollCourse(course1);
        student1.enrollCourse(course2);
        student2.enrollCourse(course1);

        teacher.assignCourse(course1);
        teacher.assignCourse(course2);

        student1.addGrade(16);
        student1.addGrade(14);
        student2.addGrade(18);

        System.out.println("\nSample data loaded successfully!");
    }
}
