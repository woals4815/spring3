package proxy;

import org.junit.jupiter.api.Test;

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
}