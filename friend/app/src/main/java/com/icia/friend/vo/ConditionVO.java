package com.icia.friend.vo;

public class ConditionVO {
    private int headcount;
    private String gender;
    private int age;

    public int getHeadcount() {
        return headcount;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ConditionVO{" +
                ", headcount=" + headcount +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
