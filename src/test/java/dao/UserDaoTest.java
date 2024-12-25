package dao;


import domain.User;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

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
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());
        User user = new User("test", "test", "test");
        userDao.add(user);

        User findUser = userDao.get(user.getId());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(1, userDao.getCount());
    }

    @Test
    void emptyResult()  throws SQLException {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.get("noId");
        });
    }



}