package com.example.xmut_news.pojo;

public class UserJoin {
    private int id;
    private String phone,name,release_title,release_name,release_time,
            release_address,release_phone,state;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
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
    public String getRelease_title() {
        return release_title;
    }
    public void setRelease_title(String release_title) {
        this.release_title = release_title;
    }
    public String getRelease_name() {
        return release_name;
    }
    public void setRelease_name(String release_name) {
        this.release_name = release_name;
    }
    public String getRelease_time() {
        return release_time;
    }
    public void setRelease_time(String release_time) {
        this.release_time = release_time;
    }
    public String getRelease_address() {
        return release_address;
    }
    public void setRelease_address(String release_address) {
        this.release_address = release_address;
    }
    public String getRelease_phone() {
        return release_phone;
    }
    public void setRelease_phone(String release_phone) {
        this.release_phone = release_phone;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    @Override
    public String toString() {
        return "UserJoin [id=" + id + ", phone=" + phone + ", name=" + name + ", release_title=" + release_title
                + ", release_name=" + release_name + ", release_time=" + release_time + ", release_address="
                + release_address + ", release_phone=" + release_phone + ", state=" + state + "]";
    }
}
