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

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void updateLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgrade(user)) {
                upgradeLevel(user);
            }
        }
    }
    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
    private void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
    private boolean canUpgrade(User user) {
        Level level = user.getLevel();
        switch (level) {
            case BASIC: return user.getLogin() >= 50;
            case SILVER: return user.getRecommend() >= 30;
            case GOLD: return false;
            default: return false;
        }
    }
}
