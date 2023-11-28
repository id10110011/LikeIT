package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private int age;
    private double rating;
    private String password;
    private Role role = Role.USER;
    private List<Question> questions;

    public User(int id, String name, String surname, String email, int age, double rating, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.rating = rating;
        this.role = role;
        this.questions = new ArrayList<>();
    }

    public User(String name, String surname, String email, int age, double rating, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.age = age;
        this.rating = rating;
        this.password = password;
        this.role = role;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
