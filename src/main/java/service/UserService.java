package service;

import dao.Level;
import dao.UserDao;
import domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;




public class UserService {
    private static final Log log = LogFactory.getLog(UserService.class);
    private UserDao userDao;
    private UserLevelUpgradePolicy levelPolicy;
    private DataSource dataSource;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

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
                    sendUpgradeEmail(user);
                }
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
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
}
