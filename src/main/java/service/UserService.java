package service;

import dao.Level;
import dao.UserDao;
import domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class UserService {
    private static final Log log = LogFactory.getLog(UserService.class);
    private UserDao userDao;
    private UserLevelUpgradePolicy levelPolicy;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void updateLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (levelPolicy.canUpgrade(user)) {
                levelPolicy.upgrade(user);
            }
        }
    }
    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
