package junit;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


public class JunitTest {
    static JunitTest testObject;
    static Set<JunitTest> testObjs = new HashSet<JunitTest>();

    @Test
    public void test1() {
        assertNotSame(this, testObject);
        testObject = this;
        System.out.println(testObject);
    }
    @Test
    void test2() {
        assertNotSame(this, testObject);
        testObject = this;

    }

    @Test
    void test3(){
        assertTrue(!hasItem(this));
        testObjs.add(this);
        testObject = this;
        System.out.println(testObject);
    }

    @Test
    void test4(){
        assertTrue(!hasItem(this));
        testObjs.add(this);
    }

    private boolean hasItem(JunitTest obj) {
        return testObjs.contains(obj);
    }
}
