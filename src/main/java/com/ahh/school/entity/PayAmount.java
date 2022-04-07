package main.java.com.ahh.school.entity;

import javax.persistence.*;

@Entity
@Table(name = "pay_amount")
public class PayAmount extends BaseEntity<Long> {


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "class_id")
    private SchoolClass schoolClass;

    @Column
    private int teacher_amount;

    @Column
    private int student_amount;

    public PayAmount() {
    }

    public PayAmount(SchoolClass schoolClass, int teacher_amount, int student_amount) {
        this.schoolClass = schoolClass;
        this.teacher_amount = teacher_amount;
        this.student_amount = student_amount;
    }


    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public int getTeacher_amount() {
        return teacher_amount;
    }

    public void setTeacher_amount(int teacher_amount) {
        this.teacher_amount = teacher_amount;
    }

    public int getStudent_amount() {
        return student_amount;
    }

    public void setStudent_amount(int student_amount) {
        this.student_amount = student_amount;
    }
}
