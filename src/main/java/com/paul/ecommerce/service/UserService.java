package com.paul.ecommerce.service;

import com.paul.ecommerce.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Service
public class UserService {
    private final JdbcTemplate jdbcTemplate;
    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void createUser(User request) throws Exception{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/ecommerce_db", "root", "chilekwa");
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String userName = request.getUserName();
        String email = request.getEmail();
        String password = request.getPassword();
        String role = request.getRole();
        String create_at = request.getCreated_at();

        String q = "INSERT INTO users(firstName, lastName, userName, email, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(q);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, userName);
        statement.setString(4, email);
        statement.setString(5, password);
        statement.setString(6, role);

        statement.executeUpdate();
    }

    public Optional<User> findByUserName(String username) throws Exception{
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("created_at")// Adjust fields based on your table structure
                )
        );
        return Optional.ofNullable(user);

    }

    public void login(User request) throws Exception{
        // SQL query to fetch user by username
        String sql = "SELECT userName, password FROM users WHERE userName = ?";
        System.out.println("Executing query: " + sql);

        // Fetch user from database
        User user1 = jdbcTemplate.queryForObject(
                sql,
                new Object[]{request.getUserName()},
                (rs, rowNum) -> {
                     User user = new User(
                            rs.getInt("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("userName"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("created_at")
                    );
                    user.setUserName(rs.getString("userName"));
                    user.setPassword(rs.getString("password"));
                    return user;
                }
        );
    }
}
