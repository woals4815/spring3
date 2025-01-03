package service;

import dao.Level;
import dao.UserDao;
import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import proxy.TransactionHandler;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/testApplicationContext.xml")
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserDao userDao;
    @Autowired
    private MailSender mailSender;
    @Autowired
    ApplicationContext context;
    @Autowired
    UserService userService;

    List<User> users;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @BeforeEach
    void setUp() {

        userDao.deleteAll();
        users = new ArrayList<User>();
        users.add(new User(
                "jaemin", "jeongjaemin", "p1", Level.BASIC, 50, 0, "email"
        ));
        users.add(new User(
                "jaemin2", "jeongjaemin2", "p1", Level.BASIC, 50, 0, "email"
        ));
        users.add(new User(
                "jaemin3", "jeongjaemin3", "p1", Level.SILVER, 50, 59, "email"
        ));
        for (User user : users) {
            userDao.add(user);
        }
    }

    @Test
    public void bean() {
        assertNotNull(userServiceImpl);
    }
    @Test
    public void bean2() {
        assertNotNull(userDao);
    }

    @Test
    @DirtiesContext
    public void testUpgradeLevels() throws Exception {
        userService.updateLevels();
    }
}