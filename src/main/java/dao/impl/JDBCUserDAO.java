package dao.impl;

import dao.UserDAO;
import entities.Role;
import entities.User;
import org.mindrot.jbcrypt.BCrypt;
import pool.BasicConnectionPool;
import pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDAO implements UserDAO {
    private final BasicConnectionPool pool = BasicConnectionPool.getInstance();
    @Override
    public int registerUser(String name, String surname, String email, int age, String password) {
        Connection connection = pool.getConnection();
        User user = new User(name ,surname, email, age, 0, password, Role.USER);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO USER (u_name, u_surname, u_email, u_age," +
                            " u_rating, u_password, u_role) VALUES(?, ? ,?, ?, ?, ?, ?)"

            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setDouble(5, user.getRating());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setString(7, user.getRole().getName());

            preparedStatement.executeUpdate();

            int userId = 0;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }
            pool.releaseConnection(connection);
            return userId;
        } catch (SQLException e) {
            pool.releaseConnection(connection);
            throw new RuntimeException("Email address is already used");
        }
    }

    @Override
    public User authorization(String email, String password) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user where u_email = ?"
            );
            preparedStatement.setString(1, email);

            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                final boolean isPasswordValid = BCrypt.checkpw(password, result.getString("u_password"));
                if (isPasswordValid) {
                    final int id = result.getInt("u_id");
                    final String name = result.getString("u_name");
                    final String surname = result.getString("u_surname");
                    final int age = result.getInt("u_age");
                    final double rating = result.getInt("u_rating");
                    final Role role = Role.valueOf(result.getString("u_role"));
                    pool.releaseConnection(connection);
                    return new User(id, name, surname, email, age, rating, role);
                } else {
                    throw new Exception("Incorrect email or password");
                }
            } else {
                throw new Exception("Incorrect email or password");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateUser(int u_id, double u_rating) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE user SET u_rating = ? WHERE u_id = ?"
            );
            preparedStatement.setDouble(1, u_rating);
            preparedStatement.setInt(2, u_id);

            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                pool.releaseConnection(connection);
            } else {
                throw new Exception("Incorrect user id");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateUser(int u_id, String name, String surname, int age) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE user " +
                            "SET u_name = ?, u_surname = ?, u_age = ?" +
                            "WHERE u_id = ?"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, u_id);

            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                pool.releaseConnection(connection);
            } else {
                throw new Exception("Incorrect user id");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM user"
            );

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                final int id = result.getInt("u_id");
                final String name = result.getString("u_name");
                final String surname = result.getString("u_surname");
                final String email = result.getString("u_email");
                final int age = result.getInt("u_age");
                final double rating = result.getInt("u_rating");
                final Role role = Role.valueOf(result.getString("u_role"));
                users.add(new User(id, name, surname, email, age, rating, role));
            }
            pool.releaseConnection(connection);
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
        return users;
    }

    @Override
    public void removeUser(int u_id) {
        Connection connection = pool.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM user WHERE u_id = ?"
            );
            preparedStatement.setInt(1, u_id);

            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                pool.releaseConnection(connection);
            } else {
                throw new Exception("Incorrect user id");
            }
        } catch (Exception e) {
            pool.releaseConnection(connection);
            throw new RuntimeException(e.getMessage());
        }
    }
}
