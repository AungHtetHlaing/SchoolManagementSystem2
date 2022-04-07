package main.java.com.ahh.school.entity;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher extends PersonEntity<Long> {


    public Teacher() {
        super();

    }

    public Teacher(String name, int age, String phone, String gender, String address, SchoolClass schoolClass) {
        super(name, age, phone, gender, address, schoolClass);

    }

}
