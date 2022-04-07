package main.java.com.ahh.school.dao;

import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.repository.ClassRepository;

import java.util.List;

public class ClassDaoImpl extends AbstractDaoImpl<SchoolClass,Long> implements ClassRepository {

    @Override
    public boolean save(SchoolClass entity) {
        return super.save(entity);
    }

    @Override
    public List<SchoolClass> fetchAll() {
        return super.fetchAll();
    }

    @Override
    public SchoolClass update(SchoolClass entity) {
        return super.update(entity);
    }

    @Override
    public boolean deleteById(Long aLong) {
        return super.deleteById(aLong);
    }

    @Override
    public List<SchoolClass> fetchAllByName(String name) {
        return super.fetchAllByName(name);
    }
}
