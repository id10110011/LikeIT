package dao;

import entities.Role;
import entities.User;

import java.util.List;

public interface UserDAO {
    int registerUser(String name, String surname, String email, int age, String password);
    User authorization(String email, String password);
    void updateUser(int u_id, double u_rating);
    void updateUser(int u_id, String name, String surname, int age);
    List<User> getUsers();
    void removeUser(int u_id);
}
