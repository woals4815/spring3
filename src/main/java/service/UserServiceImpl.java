package service;

import dao.Level;
import dao.UserDao;
import domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.List;


public class UserServiceImpl implements UserService {
    private static final Log log = LogFactory.getLog(UserServiceImpl.class);
    private UserDao userDao;
    private UserLevelUpgradePolicy levelPolicy;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void setLevelPolicy(UserLevelUpgradePolicy levelPolicy) {
        this.levelPolicy = levelPolicy;
    }

    public void updateLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (levelPolicy.canUpgrade(user)) {
                levelPolicy.upgrade(user);
                sendUpgradeEmail(user);
            }
        }
    }
    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
    private void sendUpgradeEmail(User user) {
       SimpleMailMessage message = new SimpleMailMessage();
       message.setTo(user.getEmail());
       message.setFrom("ckrt4815@gmail.com");
       message.setSubject("Upgrade to " + user.getLevel());
       message.setText("업그레이드가 완료 됐습니다.");

       mailSender.send(message);
    }
    private void upgradeLevelsInternal() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (levelPolicy.canUpgrade(user)) {
                levelPolicy.upgrade(user);
                sendUpgradeEmail(user);
            }
        }
    }
}
