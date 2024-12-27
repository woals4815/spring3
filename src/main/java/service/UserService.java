package service;

import dao.Level;
import dao.UserDao;
import domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

public class UserService {
    private static final Log log = LogFactory.getLog(UserService.class);
    private UserDao userDao;
    private UserLevelUpgradePolicy levelPolicy;
    private DataSource dataSource;
    private PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void setLevelPolicy(UserLevelUpgradePolicy levelPolicy) {
        this.levelPolicy = levelPolicy;
    }

    public void updateLevels() throws Exception {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.getAll();
            for (User user : users) {
                System.out.println(levelPolicy.canUpgrade(user));
                if (levelPolicy.canUpgrade(user)) {
                    System.out.println(user.getLevel());
                    levelPolicy.upgrade(user);
                }
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        } finally {

        }


    }
    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
