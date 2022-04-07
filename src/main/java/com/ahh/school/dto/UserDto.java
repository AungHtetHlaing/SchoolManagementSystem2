package main.java.com.ahh.school.dto;

import main.java.com.ahh.school.entity.BaseEntity;
import main.java.com.ahh.school.entity.User;


public class UserDto extends BaseEntity<Long> {
    private String name;
    private String password;

    public UserDto(User user) {
        if (user != null) {
            super.setId(user.getId());
            this.name = user.getName();
            this.password = user.getPassword();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getEntity() {
        User user = new User();
        user.setId(super.getId());
        user.setName(this.name);
        user.setPassword(this.password);
        return user;
    }
}
