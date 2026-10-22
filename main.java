
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
// ABSTRACT PARENT CLASS
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
        System.out.println("Hello! My name is " + name);
        System.out.println("My ID is " + id);
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
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
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
            System.out.println(
                    "Grade " + (i + 1) + ": " + grades.get(i) + "/20");
        }

        System.out.printf("Average: %.2f/20%n", calculateAverage());

        if (calculateAverage() >= 10) {
            System.out.println("Status: PASSED");
        } else {
            System.out.println("Status: NOT PASSED");
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("\n===== STUDENT DETAILS =====");
        System.out.println(super.toString());
        System.out.println("Department: " + department);
        System.out.println("Study year: " + studyYear);
        System.out.println("Courses enrolled: " + courses.size());
        System.out.printf("Average grade: %.2f/20%n",
                calculateAverage());
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
        this.courses = new ArrayList<>();
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

        if (!courses.contains(course)) {
            courses.add(course);
            course.setTeacher(this);

            System.out.println(getName() + " now teaches "
                    + course.getTitle());
        } else {
            System.out.println("Course already assigned.");
        }
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
        this.students = new ArrayList<>();
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
                + (teacher == null
                ? "Not assigned"
                : teacher.getName()));

        System.out.println("Enrolled students: " + students.size());
    }

    @Override
    public String toString() {
        return "Course ID: " + id
                + " | Title: " + title
                + " | Credits: " + credits
                + " | Teacher: "
                + (teacher == null
                ? "Not assigned"
                : teacher.getName());
    }
}

// ==========================================
// UNIVERSITY MANAGEMENT CLASS
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
        courses
