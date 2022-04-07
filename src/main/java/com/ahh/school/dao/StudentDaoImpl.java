package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.entity.Student;
import main.java.com.ahh.school.repository.StudentRepository;

import java.util.List;

public class StudentDaoImpl extends AbstractDaoImpl<Student,Long> implements StudentRepository {

    @Override
    public boolean save(Student entity) {
        return super.save(entity);
    }

    @Override
    public List<Student> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public Student update(Student entity) {
        return super.update(entity);
    }

    @Override
    public boolean deleteById(Long aLong) {
        return super.deleteById(aLong);
    }

    @Override
    public List<Student> fetchAllByName(String name) {
        return super.fetchAllByName(name);
    }
}
