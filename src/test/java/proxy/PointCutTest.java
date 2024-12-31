package proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import pointcut.Bean;
import pointcut.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int " + "pointcut.Target.minus(int, int) " + "throws java.lang.RuntimeException)");
        /**
         * execution(* minus(int, int)) -> 리턴타입 무시
         * execution(* minus(..)) -> 리턴타입, 파라미터 종류 개수 무시
         * execution(* *(..)) -> 모든 조건 무시
         */

        //class filter pass, method name match pass
        assertTrue(pointcut.getClassFilter().matches(Target.class) && pointcut.getMethodMatcher().matches(
                Target.class.getMethod("minus", int.class, int.class), null
        ));

        //class fitler pass, method name match pass
        assertTrue(pointcut.getClassFilter().matches(Target.class) && !pointcut.getMethodMatcher().matches(
                Target.class.getMethod("plus", int.class, int.class), null
        ));
        //클래스필터, 메소드 필터 둘 다 false
        assertTrue(
                !pointcut.getClassFilter().matches(Bean.class) && !pointcut.getMethodMatcher().matches(
                        Target.class.getMethod("method"), null
                )
        );
    }
    @Test
    public void pointcust() throws Exception {
        targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true);
    }
    public void targetClassPointcutMatches(String expression, boolean... expected) throws Exception {
        pointcutMathces(expression, expected[0], Target.class, "hello");
        pointcutMathces(expression, expected[1], Target.class, "hello", String.class);
        pointcutMathces(expression, expected[2], Target.class, "plus", int.class, int.class);
        pointcutMathces(expression, expected[3], Target.class, "minus", int.class, int.class);
        pointcutMathces(expression, expected[4], Target.class, "method");
        pointcutMathces(expression, expected[5], Bean.class, "method");
    }
    public void pointcutMathces(String expression, Boolean expected, Class<?> clazz, String methodName, Class<?>... args) throws Exception {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        assertEquals(expected, pointcut.getClassFilter().matches(clazz) && pointcut.getMethodMatcher().matches(
                        clazz.getMethod(methodName, args), null
                )
        );
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
