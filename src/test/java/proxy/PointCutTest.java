package proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointCutTest {
    @Test
    public void classNamePointcutAdvisor() {
        NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return clazz.getSimpleName().startsWith("HelloW");
                    }
                };
            }
        };
        classMethodPointcut.setMappedName("sayH*");
        checkAdvised(new HelloTarget(), classMethodPointcut, false);
        checkAdvised(new HelloWord(), classMethodPointcut, true);
    }
    private void checkAdvised(
            Object target,
            Pointcut pointcut,
            boolean advised
    ) {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        pfBean.setTarget(target);
        pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
        Hello proxiedHello = (Hello) pfBean.getObject();

        if (advised) {
            assertEquals("HELLO JAEMIN", proxiedHello.sayHello("jaemin"));
        } else {
            assertEquals("Hello jaemin", proxiedHello.sayHello("jaemin"));
        }
    }
}
