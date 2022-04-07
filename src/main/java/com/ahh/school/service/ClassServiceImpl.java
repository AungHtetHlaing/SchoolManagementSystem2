package main.java.com.ahh.school.service;

import main.java.com.ahh.school.dto.ClassDto;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.repository.ClassRepository;

import java.util.ArrayList;
import java.util.List;

public class ClassServiceImpl implements ClassService{

    private ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }


    @Override
    public List<ClassDto> fetchAll() {
        List<SchoolClass> classEntities = classRepository.fetchAll();
        List<ClassDto> classDtos = new ArrayList<>();
        for (SchoolClass out : classEntities) {
            classDtos.add(new ClassDto(out));
        }
        return classDtos;
    }

    @Override
    public List<ClassDto> fetchAllByName(String name) {
        List<SchoolClass> classEntities =  classRepository.fetchAllByName(name);
        List<ClassDto> classDtos = new ArrayList<>();
        for (SchoolClass out : classEntities) {
            classDtos.add(new ClassDto(out));
        }
        return classDtos;
    }

    @Override
    public boolean save(ClassDto entity) {
        return classRepository.save(entity.getEntity());
    }

    @Override
    public boolean delete(ClassDto entity) {
        return classRepository.delete(entity.getEntity());
    }

    @Override
    public boolean deleteById(Long Id) {
        return classRepository.deleteById(Id);
    }

    @Override
    public ClassDto update(ClassDto entity) {
        SchoolClass schoolClass = classRepository.update(entity.getEntity());
        return new ClassDto(schoolClass);
    }

    @Override
    public ClassDto findOne(Long aLong) {
        SchoolClass schoolClass = classRepository.findOne(aLong);
        return new ClassDto(schoolClass);
    }

}
