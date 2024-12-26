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
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;



//RunWith(SpringRunner.class)
//todo: SpringExtension, ExtendWith, ContextConfiguration 좀 더 조사해보기
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DirtiesContext// 어플리케이션 컨텍스트 공유를 허용하지 않게 해줌
class UserDaoTest {
    private static final Log log = LogFactory.getLog(UserDaoTest.class);
    @Autowired
    private UserDao userDao;
    //todo: what is Autowired??
    //Answer: autowired가 붙은 인스턴스 변수가 있으면 변수타입과 일치하는 컨테스트 내의 빈을 찾아서 주입해준다.
    //그리고 ApplicationContext는 자기 자신도 빈으로 등록한다

    @BeforeEach
    public void setUp() {
        //만약 test 시에 datasource를 바꾸고 싶을 때 applicationContext 파일을 수정하기엔 위험하므로 위와 같이 바꿔 끼워준다
        DataSource ds = new SingleConnectionDataSource(
                "jdbc:mysql://localhost/test", "root", "woals4815", true
        );
        JdbcContext jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(ds);

        userDao.setJdbcContext(jdbcContext);
    }


    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();

        User user = new User("test", "test", "test");

        userDao.add(user);

        User findUser = userDao.get(user.getId());
        assertEquals(user.getName(), findUser.getName());
        assertEquals(1, userDao.getCount());
    }

//    @Test
//    void emptyResult()  throws SQLException {
//        assertThrows(EmptyResultDataAccessException.class, () -> {
//            userDao.get("noId");
//        });
//    }
}