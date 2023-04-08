package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String string = "CREATE TABLE `mydbtest`.`users` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` TINYINT NULL,\n" +
                "  PRIMARY KEY (`id`));";
        Util util = new Util();

        try(Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(string);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String string = "DROP TABLE `mydbtest`.`users`;";
        Util util = new Util();
        try(Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(string);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String string = "INSERT INTO mydbtest.users (id, name, lastName, age) VALUES (?, ?, ?, ?)";
        Util util = new Util();
        try(Connection connection = util.getConnection()) {
            preparedStatement = connection.prepareStatement(string);
            preparedStatement.setLong(1, 0);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3, lastName);
            preparedStatement.setByte(4,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String string = "DELETE FROM `USERS` WHERE `id` = " + id + " LIMIT 1;";
        Util util = new Util();

        try(Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(string);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String string = "SELECT id, name, lastName, age FROM users";
        //Statement statement = null;
        Util util = new Util();

        try(Connection connection = util.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(string);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String string = "DELETE FROM users";
        Util util = new Util();

        try(Connection connection = util.getConnection();
            Statement statement = connection.createStatement()) {
            statement.execute(string);
        } catch (SQLException e) {
            //throw new RuntimeException(e);
        }
    }
}
