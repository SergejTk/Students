package tkachenko.students.dao;

import tkachenko.students.model.Student;

import java.util.List;

/**
 *
 * Created by  S. Tkachenko on 21.10.2016 23:22.
 */
public interface StudentDAO {

    List<Student> getAllStudents();

    Student getStudent(int rollNo);

    void updateStudent(Student student);

    void deleteStudent(Student student);
}
