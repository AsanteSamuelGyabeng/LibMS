package com.example.libms.model;

import com.example.libms.model.UserModel;

public class Admin extends UserModel {
    private int accessLevel;

    public Admin(int id, String name, String email, String password, int accessLevel) {
        super(id, name, email, password, "Admin");
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void displayUserRole() {
        System.out.println(getName() + " is an Admin with access level: " + accessLevel);
    }
}
