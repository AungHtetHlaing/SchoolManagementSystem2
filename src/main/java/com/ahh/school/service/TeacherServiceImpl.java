package main.java.com.ahh.school.service;

import main.java.com.ahh.school.dto.TeacherDto;
import main.java.com.ahh.school.entity.Teacher;
import main.java.com.ahh.school.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService{

    private TeacherRepository repository;

    public TeacherServiceImpl(TeacherRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TeacherDto> fetchAll() {
        List<Teacher> teachers = repository.fetchAll();
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (Teacher out : teachers) {
            teacherDtos.add(new TeacherDto(out));
        }
        return teacherDtos;
    }

    @Override
    public List<TeacherDto> fetchAllByName(String name) {
        List<Teacher> teachers = repository.fetchAllByName(name);
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (Teacher out : teachers) {
            teacherDtos.add(new TeacherDto(out));
        }
        return teacherDtos;
    }

    @Override
    public boolean save(TeacherDto entity) {
        return repository.save(entity.getEntity());
    }

    @Override
    public boolean delete(TeacherDto entity) {
        return repository.delete(entity.getEntity());
    }

    @Override
    public boolean deleteById(Long Id) {
        return repository.deleteById(Id);
    }

    @Override
    public TeacherDto update(TeacherDto entity) {
        Teacher teacher = repository.update(entity.getEntity());
        return new TeacherDto(teacher);
    }

    @Override
    public TeacherDto findOne(Long aLong) {
        Teacher teacher = repository.findOne(aLong);
        return new TeacherDto(teacher);
    }
}
