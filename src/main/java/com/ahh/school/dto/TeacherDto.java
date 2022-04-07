package main.java.com.ahh.school.dto;

import main.java.com.ahh.school.entity.PersonEntity;
import main.java.com.ahh.school.entity.Teacher;

public class TeacherDto extends PersonEntity<Long> {

    public TeacherDto(Teacher teacher) {
        if (teacher != null) {
            super.setName(teacher.getName());
            super.setAge(teacher.getAge());
            super.setAddress(teacher.getAddress());
            super.setGender(teacher.getGender());
            super.setId(teacher.getId());
            super.setPhone(teacher.getPhone());
            super.setSchoolClass(teacher.getSchoolClass());
        }
    }

    public Teacher getEntity() {
        Teacher teacher = new Teacher(
                super.getName(),
                super.getAge(),
                super.getPhone(),
                super.getGender(),
                super.getAddress(),
                super.getSchoolClass()
        );
        teacher.setId(super.getId());
        return teacher;
    }
}
