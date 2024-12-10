package com.example.libms.model;

public class Patron extends UserModel {
    private String libraryCardNumber;

    public Patron(int id, String name, String email, String password, String libraryCardNumber) {
        super(id, name, email, password, "Patron");
        this.libraryCardNumber = libraryCardNumber;
    }

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    @Override
    public void displayUserRole() {
        System.out.println(getName() + " is a Patron with card number: " + libraryCardNumber);
    }
}
