package com.sapo.edu.demo.repository;

import com.sapo.edu.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Autowired
    private Environment env;
    @Override
    public User insecureSQLinjectionLogin(User user) {
        String sql = "SELECT * FROM users WHERE username = ' " +user.getUsername() +" 'AND password = ' "+user.getPassword()+"'";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }
    @Override
    public User secureSQLinjectionLogin(User user) throws SQLException {
        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User resultUser = null;

        try {
            // Khởi tạo connection
            connection = DriverManager.getConnection(url, username, password);

            // Tạo PreparedStatement với câu lệnh SQL có chứa placeholder (?)
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            // Thực hiện truy vấn bằng PreparedStatement
            resultSet = statement.executeQuery();

            // Duyệt ResultSet để lấy dữ liệu và trả về đối tượng User
            while (resultSet.next()) {
                resultUser = new User();
                resultUser.setId(resultSet.getInt("id"));
                resultUser.setUsername(resultSet.getString("username"));
                resultUser.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng tất cả các resource đã sử dụng (ResultSet, PreparedStatement, Connection)
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return resultUser;
    }
    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        User user = jdbcTemplate.queryForObject(sql, rowMapper, username);
        return user;
    }



}
