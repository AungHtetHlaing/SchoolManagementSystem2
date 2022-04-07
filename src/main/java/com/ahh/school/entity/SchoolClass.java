package main.java.com.ahh.school.entity;

import javax.persistence.*;

@Entity
@Table(name = "school_class")
public class SchoolClass extends BaseEntity<Long>{
    @Column(length = 200)
    private String name;

    public SchoolClass() {
    }

    public SchoolClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
