package main.java.com.ahh.school.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class Student extends PersonEntity<Long> {

    public Student() {
        super();
    }


    public Student(String name, int age, String phone, String gender, String address, SchoolClass schoolClass) {
        super(name, age, phone, gender, address, schoolClass);
    }
}
