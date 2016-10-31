package tkachenko.students.dao.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import tkachenko.students.dao.StudentDAO;
import tkachenko.students.model.Student;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/*
 * Created by S. Tkachenko on 29.10.2016 15:18.
 */


@Repository
@Profile("jdbc")
public class StudentDAOImpl implements StudentDAO{

    private JdbcTemplate jdbcTemplate ;
    private RowMapper rowMapper = new RowMapper();

    private static class RowMapper implements org.springframework.jdbc.core.RowMapper<Student>{
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException{
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setKurs(rs.getInt("kurs"));
            student.setGroup(rs.getInt("class"));
            return student;
        }
    }


    @Resource
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Student> getAllStudents() {
        return jdbcTemplate.query("SELECT * FROM  students", rowMapper);
    }

    @Override
    public Student getStudent(int rollNo) {

        return jdbcTemplate.queryForObject("SELECT *  FROM  students WHERE id = ?", rowMapper , rollNo);
    }

    public void updateStudent(Student student) {
        int count = jdbcTemplate.update("UPDATE students SET name = ?, kurs = ?, class = ? WHERE id = ?",
                student.getName(), student.getKurs(), student.getGroup(), student.getId());
        if (count == 0) {
            jdbcTemplate.update("INSERT INTO  students (id, name, kurs, class) VALUES (?, ?, ?, ? ) ",
                    student.getId(), student.getName(), student.getKurs(), student.getGroup());
        }


    }

    public void deleteStudent(Student student) {
        jdbcTemplate.update("DELETE FROM students WHERE id = ?", student.getId());

    }
}
