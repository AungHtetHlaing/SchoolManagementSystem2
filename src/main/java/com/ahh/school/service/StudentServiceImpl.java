package main.java.com.ahh.school.service;

import main.java.com.ahh.school.dto.StudentDto;
import main.java.com.ahh.school.entity.Student;
import main.java.com.ahh.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService{

    private StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StudentDto> fetchAll() {
        List<Student> students = repository.fetchAll();
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student out : students) {
            studentDtos.add(new StudentDto(out));
        }
        return studentDtos;
    }

    @Override
    public List<StudentDto> fetchAllByName(String name) {
        List<Student> students = repository.fetchAllByName(name);
        List<StudentDto> studentDtos = new ArrayList<>();
        for (Student out : students) {
            studentDtos.add(new StudentDto(out));
        }
        return studentDtos;
    }

    @Override
    public boolean save(StudentDto entity) {
        return repository.save(entity.getEntity());
    }

    @Override
    public boolean delete(StudentDto entity) {
        return repository.delete(entity.getEntity());
    }

    @Override
    public boolean deleteById(Long Id) {
        return repository.deleteById(Id);
    }

    @Override
    public StudentDto update(StudentDto entity) {
        Student student = repository.update(entity.getEntity());
        return new StudentDto(student);
    }

    @Override
    public StudentDto findOne(Long aLong) {

        Student student = repository.findOne(aLong);
        return new StudentDto(student);
    }
}
