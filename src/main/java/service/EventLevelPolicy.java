package service;

import dao.Level;
import domain.User;

public class EventLevelPolicy implements UserLevelUpgradePolicy {
    @Override
    public boolean canUpgrade(User user) {
        Level level = user.getLevel();
        switch (level) {
            case BASIC: return user.getLogin() >=30;
            case SILVER: return user.getRecommend() >= 20;
            case GOLD:
                return false;
            default:
                throw new IllegalStateException("Cannot upgrade when gold level");
        }
    }

    @Override
    public void upgrade(User user) {
        user.upgradeLevel();
    }
}
