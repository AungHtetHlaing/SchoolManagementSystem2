package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.entity.Teacher;
import main.java.com.ahh.school.repository.TeacherRepository;

import java.util.List;

public class TeacherDaoImpl extends AbstractDaoImpl<Teacher, Long> implements TeacherRepository {

    @Override
    public boolean save(Teacher entity) {
        return super.save(entity);
    }

    @Override
    public List<Teacher> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public Teacher update(Teacher entity) {
        return super.update(entity);
    }

    @Override
    public boolean deleteById(Long aLong) {
        return super.deleteById(aLong);
    }

    @Override
    public List<Teacher> fetchAllByName(String name) {
        return super.fetchAllByName(name);
    }
}
