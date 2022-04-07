package main.java.com.ahh.school.dto;

import main.java.com.ahh.school.entity.BaseEntity;
import main.java.com.ahh.school.entity.PayAmount;
import main.java.com.ahh.school.entity.SchoolClass;

public class PayAmountDto extends BaseEntity<Long> {

    private SchoolClass schoolClass;
    private int teacher_amount;
    private int student_amount;

    public PayAmountDto(PayAmount payAmount) {
        if (payAmount != null) {
            super.setId(payAmount.getId());
            this.schoolClass = payAmount.getSchoolClass();
            this.teacher_amount = payAmount.getTeacher_amount();
            this.student_amount = payAmount.getStudent_amount();
        }
    }

    public PayAmount getEntity() {
        PayAmount payAmount = new PayAmount(
                this.schoolClass,
                this.teacher_amount,
                this.student_amount
        );
        payAmount.setId(super.getId());
        return payAmount;
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
