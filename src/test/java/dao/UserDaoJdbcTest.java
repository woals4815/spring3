package dao;


import domain.User;
import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;



//RunWith(SpringRunner.class)
//todo: SpringExtension, ExtendWith, ContextConfiguration 좀 더 조사해보기
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/testApplicationContext.xml")
@DirtiesContext// 어플리케이션 컨텍스트 공유를 허용하지 않게 해줌
class UserDaoJdbcTest {
    private static final Log log = LogFactory.getLog(UserDaoJdbcTest.class);
    @Autowired
    private UserDaoJdbc userDaoJdbc;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDaoJdbc userDao;

    User user = new User("test", "test", "test", Level.BASIC, 1, 0, "email");
    User user2 = new User("test2", "test2", "test2", Level.BASIC, 1, 0, "email");
    User user3 = new User("test3", "test3", "test3", Level.BASIC, 1, 0, "email");

    static private int num = 0;
    //todo: what is Autowired??
    //Answer: autowired가 붙은 인스턴스 변수가 있으면 변수타입과 일치하는 컨테스트 내의 빈을 찾아서 주입해준다.
    //그리고 ApplicationContext는 자기 자신도 빈으로 등록한다

    @BeforeEach
    public void setUp() {
        //만약 test 시에 datasource를 바꾸고 싶을 때 applicationContext 파일을 수정하기엔 위험하므로 위와 같이 바꿔 끼워준다
        DataSource ds = new SingleConnectionDataSource(
                "jdbc:mysql://localhost/test", "root", "woals4815", true
        );
        userDaoJdbc.setDataSource(ds);
    }
    @AfterEach
    public void tearDown() throws SQLException {
        userDaoJdbc.deleteAll();
    }


    @Test
    void addAndGet() throws SQLException {
        userDaoJdbc.deleteAll();
        userDaoJdbc.add(user);

        User findUser = userDaoJdbc.get(user.getId());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(1, userDaoJdbc.getCount());
        num += 1;
        System.out.println(num);
    }

    @Test
    void emptyResult()  throws SQLException {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDaoJdbc.get("noId");
        });
        num += 1;
        System.out.println(num);
    }

    @Test
    void getAllTest() throws SQLException {

        userDaoJdbc.add(user);
        userDaoJdbc.add(user2);
        userDaoJdbc.add(user3);
        assertEquals(3, userDaoJdbc.getAll().size());
        num += 1;
        System.out.println(num);
    }
    @Test
    void getAllEmtpy() throws SQLException {
        assertEquals(0, userDaoJdbc.getAll().size());
        num += 1;
        System.out.println(num);
    }

    @Test
    void testGet() throws SQLException {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDaoJdbc.get("test");
        });
        num += 1;
        System.out.println(num);
    }

    @Test
    void duplicateKey() {
        userDaoJdbc.add(user);
        assertThrows(DuplicateKeyException.class, () -> {
            userDaoJdbc.add(user);
        });
        num += 1;
        System.out.println(num);
    }
    @Test
    void duplicateKey2() {
        num += 1;
        System.out.println(num);
        try {
            userDaoJdbc.add(user);
            userDaoJdbc.add(user);
        } catch (DuplicateKeyException ex) {

            SQLException sqlEx = (SQLException) ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertTrue(set.translate(null, null, sqlEx) instanceof DuplicateKeyException);
        }
    }

    @Test
    void updateTest() {
        userDaoJdbc.add(user);
        user.setName("test2");
        userDao.update(user);

        User userget = userDaoJdbc.get(user.getId());
        assertEquals("test2", userget.getName());
        num += 1;
        System.out.println(num);
    }
}