package tkachenko.students.dao.hbm;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tkachenko.students.dao.StudentDAO;
import tkachenko.students.model.Student;

import java.util.List;

/*
 * Created by S. Tkachenko on 29.10.2016 14:32.
 */

@Repository
@Profile("hbm")
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents(){

        return sessionFactory.getCurrentSession().createCriteria(Student.class).list();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudent(int rollNo){
        return (Student) sessionFactory.getCurrentSession().createCriteria(Student.class).add(Restrictions.eq("id", rollNo));

    }
    @Override
    @Transactional
    public void updateStudent(Student student) {
        sessionFactory.getCurrentSession().saveOrUpdate(student);

    }
    @Override
    @Transactional
    public void deleteStudent(Student student) {
        sessionFactory.getCurrentSession().delete(student);
    }
}
