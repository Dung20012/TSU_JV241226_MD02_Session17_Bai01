package Exercise01;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        List<Student> students = Arrays.asList(
                new Student(0, "Nguyễn Văn A", 20),
                new Student(1, "Nguyễn Văn B", 19),
                new Student(2, "Nguyễn Văn C", 22)
        );
        manager.addStudent(students);
    }
}
