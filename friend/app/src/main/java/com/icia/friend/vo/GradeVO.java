package com.icia.friend.vo;

public class GradeVO {
    private int standard;
    private String grade;

    @Override
    public String toString() {
        return "GradeVO{" +
                "standard=" + standard +
                ", grade='" + grade + '\'' +
                '}';
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
