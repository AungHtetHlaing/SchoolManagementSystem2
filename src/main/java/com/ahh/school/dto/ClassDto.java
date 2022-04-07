package main.java.com.ahh.school.dto;

import main.java.com.ahh.school.entity.BaseEntity;
import main.java.com.ahh.school.entity.SchoolClass;

public class ClassDto extends BaseEntity<Long> {
    private String name;

    public ClassDto(SchoolClass schoolClass) {
        if (schoolClass != null) {
            super.setId(schoolClass.getId());
            this.name = schoolClass.getName();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public SchoolClass getEntity() {
        SchoolClass schoolClass = new SchoolClass(
                this.getName()
        );
        schoolClass.setId(super.getId());
        return schoolClass;
    }
}
