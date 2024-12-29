package service;

import domain.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 부가 기능 추가를 위한 proxy 패턴
 * 문제점
 * 1. 구현하고 싶지 않은 인터페이스까지 구현해야 함
 * 2. 부가기능이 필요한 메소드들이 많아질 수 있음 -> 부가 기능 코드 중복 가능성
 */
public class UserServiceTx implements UserService {
    UserService userService;
    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void add(User user) { //위임
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());//부가기능 추가
        try {
            userService.add(user);
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateLevels() {
        userService.updateLevels();
    }
}
