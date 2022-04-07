package main.java.com.ahh.school.dto;

import main.java.com.ahh.school.entity.PersonEntity;
import main.java.com.ahh.school.entity.Student;

public class StudentDto extends PersonEntity<Long> {

    public StudentDto(Student student) {
        if (student != null) {
            super.setName(student.getName());
            super.setAge(student.getAge());
            super.setAddress(student.getAddress());
            super.setGender(student.getGender());
            super.setId(student.getId());
            super.setPhone(student.getPhone());
            super.setSchoolClass(student.getSchoolClass());
        }
    }

    public Student getEntity() {
        Student student = new Student(
                super.getName(),
                super.getAge(),
                super.getPhone(),
                super.getGender(),
                super.getAddress(),
                super.getSchoolClass()
        );
        student.setId(super.getId());
        return student;
    }
}
