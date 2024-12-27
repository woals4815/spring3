package dao;

import domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        UserDaoJdbc userDaoJdbc = context.getBean("userDao", UserDaoJdbc.class);
        User user = new User();
        user.setId("1");
        user.setName("name");
        user.setPassword("password");
        userDaoJdbc.add(user);
    }
}
