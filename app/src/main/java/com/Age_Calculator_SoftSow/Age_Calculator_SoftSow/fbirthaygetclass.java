package com.Age_Calculator_SoftSow.Age_Calculator_SoftSow;

public class fbirthaygetclass {
    private String name, birthday, next_birthday, imageUrl, age_txt, key ,id;

    public fbirthaygetclass() {}

    public fbirthaygetclass(String key, String name, String birthday, String next_birthday, String imageUrl, String age_txt, String id) {
        this.name = name;
        this.birthday = birthday;
        this.next_birthday = next_birthday;
        this.imageUrl = imageUrl;
        this.age_txt = age_txt;
        this.key = key;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getNext_birthday() { return next_birthday; }
    public void setNext_birthday(String next_birthday) { this.next_birthday = next_birthday; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getAge_txt() { return age_txt; }
    public void setAge_txt(String age_txt) { this.age_txt = age_txt; }
}