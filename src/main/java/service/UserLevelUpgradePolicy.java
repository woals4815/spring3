package service;

import domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgrade(User user);
    void upgrade(User user);
}
