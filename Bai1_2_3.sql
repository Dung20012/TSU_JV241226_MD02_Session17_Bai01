CREATE DATABASE ss1;
USE ss1;

create table students(
	id int primary key auto_increment,
    name varchar(100),
    age int
);

-- Stored Procedure
DELIMITER //
create procedure add_students(in p_name varchar(100), in p_age int)
begin
	insert into students(name, age) values (p_name, p_age);
end //
DELIMITER ;

-- Cập nhật
DELIMITER //
CREATE PROCEDURE update_student(
    IN p_id INT,
    IN p_name VARCHAR(100),
    IN p_age INT
)
BEGIN
    UPDATE students
    SET name = p_name,
        age = p_age
    WHERE id = p_id;
END //
DELIMITER ;

-- Xóa sinh viên
DELIMITER //

CREATE PROCEDURE delete_students_by_age(IN max_age INT)
BEGIN
    DELETE FROM students WHERE age < max_age;
END //

DELIMITER ;
