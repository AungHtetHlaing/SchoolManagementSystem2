package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.entity.Subject;
import main.java.com.ahh.school.repository.SubjectRepository;

import java.util.List;

public class SubjectDaoImpl extends AbstractDaoImpl<Subject,Long> implements SubjectRepository {
    @Override
    public Subject findOne(Long aLong) {
        return super.findOne(aLong);
    }

    @Override
    public boolean save(Subject entity) {
        return super.save(entity);
    }

    @Override
    public List<Subject> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public Subject update(Subject entity) {
        return super.update(entity);
    }

    @Override
    public boolean delete(Subject entity) {
        return super.delete(entity);
    }

    @Override
    public boolean deleteById(Long aLong) {
        return super.deleteById(aLong);
    }

    @Override
    public List<Subject> fetchAllByName(String name) {
        return super.fetchAllByName(name);
    }
}
