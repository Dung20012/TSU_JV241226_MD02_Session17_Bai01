package Exercise01;

import Database.ConnectDatabase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentManager {
    public void addStudent(List<Student> students){
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            conn = ConnectDatabase.getConnection();
            if (conn != null){
                conn.setAutoCommit(false); // Bắt đầu transaction

                for (Student student : students){
                    stmt = conn.prepareCall("{CALL add_students(?, ?)}");
                    stmt.setString(1, student.getName());
                    stmt.setInt(2, student.getAge());
                    stmt.executeUpdate();
                }

                conn.commit(); // Cam kết transaction
                System.out.println("Đã thêm sinh viên thành công");
            }
        }catch (SQLException e){
            try {
                conn.rollback(); // Rollback nếu có lỗi
                System.out.println("Đã xảy ra lỗi, rollback transaction");
            }catch (SQLException rollbackEx){
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void updateStudent(Student student){
        Connection conn = null;
        CallableStatement stmt = null;

        try {
            conn = ConnectDatabase.getConnection();
            if (conn != null){
                conn.setAutoCommit(false); // Bắt đầu transaction

//                Gọi stored procedure để cập nhật sinh viên
                stmt = conn.prepareCall("{CALL update_student(?, ?, ?)}");
                stmt.setInt(1, student.getId());
                stmt.setString(2, student.getName());
                stmt.setInt(3, student.getAge());
                stmt.executeUpdate();

                conn.commit(); // Cam kết transaction
                System.out.println("Cập nhật thông tin sinh viên thành công");
            }
        }catch (SQLException e){
            try {
                conn.rollback(); // Rollback nếu có lỗi
                System.out.println("Đã xảy ra lỗi, rollback");
            }catch (SQLException rollbackEx){
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void deleteStudentByAge(Scanner scanner) {
        int age = Validator.getInt(scanner,"Nhập tuổi để xóa những học sinh có độ tuổi nhỏ hơn : ");
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            conn = ConnectDatabase.getConnection() ;
            if (conn != null) {
                conn.setAutoCommit(false);
                stmt = conn.prepareCall("{CALL delete_students_by_age(?)}");
                stmt.setInt(1, age);
                int rs = stmt.executeUpdate();
                conn.commit();
                if (rs == 0) {
                    System.out.println("Không tim thấy học sinh nào có tuổi nhỏ hơn : " + age);
                }else {
                    System.out.println("Xóa thành công "+ rs + " học sinh có tuổi nhỏ hơn : " + age);
                }
            }
        } catch (Exception e) {
            try {
                conn.rollback();
            }catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        }finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public class Validator {
        public static int getInt(Scanner scanner, String message) {
            System.out.print(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ!");
                scanner.next(); // bỏ qua dữ liệu sai
                System.out.print(message);
            }
            int value = scanner.nextInt();
            scanner.nextLine(); // clear buffer
            return value;
        }
    }
}
