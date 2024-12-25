package dao;


import domain.User;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;



class UserDaoTest {
    private static final Log log = LogFactory.getLog(UserDaoTest.class);
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.userDao = context.getBean("userDao", UserDao.class);
    }

    @Test
    void addAndGet() throws SQLException {
        User user = new User();
        user.setId("test");
        user.setName("test");
        user.setPassword("test");
        userDao.add(user);
        User findUser = userDao.get(user.getId());
        assertEquals(user.getName(), findUser.getName());
    }

}