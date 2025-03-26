package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

public class get_realClass {
    private String name, age, birthday, zodiac_trait, chinese_trait, next_birt,Wzodiac_sig,Czodiac_sig,img,img2,c_id;

    public get_realClass() {}

    public get_realClass(String name, String age, String birthday, String img, String zodiac_trait, String img2, String chinese_trait, String next_birt, String c_id, String wzodiac_sig, String czodiac_sig) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.img = img;
        this.zodiac_trait = zodiac_trait;
        this.img2 = img2;
        this.chinese_trait = chinese_trait;
        this.next_birt = next_birt;
        this.c_id = c_id;
        Wzodiac_sig = wzodiac_sig;
        Czodiac_sig = czodiac_sig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getZodiac_trait() {
        return zodiac_trait;
    }

    public void setZodiac_trait(String zodiac_trait) {
        this.zodiac_trait = zodiac_trait;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getChinese_trait() {
        return chinese_trait;
    }

    public void setChinese_trait(String chinese_trait) {
        this.chinese_trait = chinese_trait;
    }

    public String getNext_birt() {
        return next_birt;
    }

    public void setNext_birt(String next_birt) {
        this.next_birt = next_birt;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getWzodiac_sig() {
        return Wzodiac_sig;
    }

    public void setWzodiac_sig(String wzodiac_sig) {
        Wzodiac_sig = wzodiac_sig;
    }

    public String getCzodiac_sig() {
        return Czodiac_sig;
    }

    public void setCzodiac_sig(String czodiac_sig) {
        Czodiac_sig = czodiac_sig;
    }

}