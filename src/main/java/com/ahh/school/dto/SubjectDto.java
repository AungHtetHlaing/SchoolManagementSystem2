package main.java.com.ahh.school.dto;

import main.java.com.ahh.school.entity.BaseEntity;
import main.java.com.ahh.school.entity.SchoolClass;
import main.java.com.ahh.school.entity.Subject;

import java.util.List;

public class SubjectDto extends BaseEntity<Long> {
    private String name;
    private List<SchoolClass> schoolClasses;

    public SubjectDto(Subject subject) {
        if (subject != null) {
            super.setId(subject.getId());
            this.name = subject.getName();
            this.schoolClasses = subject.getSchoolClasses();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public void setSchoolClasses(List<SchoolClass> schoolClasses) {
        this.schoolClasses = schoolClasses;
    }

    public Subject getEntity() {
        Subject subject = new Subject(
                this.getName()
        );
        subject.setId(super.getId());
        subject.setSchoolClasses(this.schoolClasses);
        return subject;
    }
}
