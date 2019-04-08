package com.example.xmut_news.pojo;

public class UserRelease {
    private String phone,name,title,detail,image,address,time;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserRelease{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
