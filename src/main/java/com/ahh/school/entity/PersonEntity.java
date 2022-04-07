package main.java.com.ahh.school.entity;


import javax.persistence.*;

@MappedSuperclass
public class PersonEntity<T> extends BaseEntity<T> {

    @Column(length = 30)
    private String name;

    @Column(length = 3)
    private int age;

    @Column(length = 11)
    private String phone;

    @Column(length = 6)
    private String gender;

    @Column(length = 500)
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    public PersonEntity() {
    }

    public PersonEntity(String name, int age, String phone, String gender, String address, SchoolClass schoolClass) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.schoolClass = schoolClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

}
