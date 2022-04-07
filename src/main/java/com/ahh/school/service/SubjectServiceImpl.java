package main.java.com.ahh.school.service;

import main.java.com.ahh.school.dto.SubjectDto;
import main.java.com.ahh.school.entity.Subject;
import main.java.com.ahh.school.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

public class SubjectServiceImpl implements SubjectService{
    private SubjectRepository subjectRepository;
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    @Override
    public List<SubjectDto> fetchAll() {
        List<Subject> subjects = subjectRepository.fetchAll();
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject out : subjects) {
            subjectDtos.add(new SubjectDto(out));
        }
        return subjectDtos;
    }

    @Override
    public List<SubjectDto> fetchAllByName(String name) {
        List<Subject> subjects = subjectRepository.fetchAllByName(name);
        List<SubjectDto> subjectDtos = new ArrayList<>();
        for (Subject out : subjects) {
            subjectDtos.add(new SubjectDto(out));
        }
        return subjectDtos;
    }

    @Override
    public boolean save(SubjectDto entity) {
        return subjectRepository.save(entity.getEntity());
    }

    @Override
    public boolean delete(SubjectDto entity) {
        return subjectRepository.delete(entity.getEntity());
    }

    @Override
    public boolean deleteById(Long Id) {
        return subjectRepository.deleteById(Id);
    }

    @Override
    public SubjectDto update(SubjectDto entity) {
        Subject subject = subjectRepository.update(entity.getEntity());
        return new SubjectDto(subject);
    }

    @Override
    public SubjectDto findOne(Long aLong) {
        Subject subject = subjectRepository.findOne(aLong);
        return new SubjectDto(subject);
    }
}
