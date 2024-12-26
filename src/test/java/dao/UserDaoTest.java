package dao;


import domain.User;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;



//RunWith(SpringRunner.class)
//todo: SpringExtension, ExtendWith, ContextConfiguration 좀 더 조사해보기
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
class UserDaoTest {
    private static final Log log = LogFactory.getLog(UserDaoTest.class);
    private UserDao userDao;
    //todo: what is Autowired??
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        userDao = context.getBean("userDao", UserDao.class);
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