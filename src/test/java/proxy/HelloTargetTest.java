package proxy;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.*;

class HelloTargetTest {
    @Test
    public void simpleProxy() {
        HelloTarget target = new HelloTarget();
        assertEquals("Hello jaemin", target.sayHello("jaemin"));
    }

    @Test
    public void simpleProxy2() {
        Hello proxiedHello = new HelloUppercase(new HelloTarget());
        assertEquals("HELLO JAEMIN", proxiedHello.sayHello("jaemin"));
    }
    @Test
    public void simpleProxy3() {
        Hello proxiedHello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(
                        new HelloTarget()
                )
        );
        assertEquals("HELLO JAEMIN", proxiedHello.sayHello("jaemin"));
    }
    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setTarget(new HelloTarget());
        factoryBean.addAdvice(new UppercaseAdvice());
        Hello proxiedHello = (Hello) factoryBean.getObject();

        String result = proxiedHello.sayHello("jaemin");
        assertEquals("HELLO JAEMIN", result);
    }

    @Test
    public void pointCutTest() {
        ProxyFactoryBean pf = new ProxyFactoryBean();
        pf.setTarget(new HelloTarget());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        pf.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice())); //advisor = pointcut(메소드 선정 알고리즘) + advice(부가기능)

        Hello proxiedHello = (Hello) pf.getObject();

        String result = proxiedHello.sayHello("jaemin");
        assertEquals("HELLO JAEMIN", result);
    }
}