package tkachenko.students.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tkachenko.students.config.BeanConfig;
import tkachenko.students.model.Student;

import java.util.List;

/*
 * Created by  S. Tkachenko on 30.10.2016 22:44.
 */
public class MyApplication {

    private ApplicationContext context =
            new AnnotationConfigApplicationContext(BeanConfig.class);

    private StudentDAO test = context.getBean(StudentDAO.class);

    public List<Student> getAllStudents(){
        return test.getAllStudents();
    }

    public Student getStudent(int id){
        return test.getStudent(id);
    }

    public void updateStudent(Student student){
        test.updateStudent(student);
    }

    public void deleteStudent(Student student){
        test.deleteStudent(student);
    }

}
