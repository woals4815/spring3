package junit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/testApplicationContext.xml")
public class JUnitTest2 {
    private static final Log log = LogFactory.getLog(JUnitTest2.class);
    @Autowired
    ApplicationContext applicationContext;

    static Set<JUnitTest2> testObjs = new HashSet<JUnitTest2>();
    static ApplicationContext contextObj = null;

    @Test
    public void test1() {
        assertTrue(!hasItem(this));
        testObjs.add(this);
        assertTrue(contextObj == null || contextObj == this.applicationContext);
        contextObj = this.applicationContext;
    }

    @Test
    public void test2() {
        assertTrue(!hasItem(this));
        testObjs.add(this);
        assertTrue(contextObj == null || contextObj == this.applicationContext);
        contextObj = this.applicationContext;
    }



    private boolean hasItem(JUnitTest2 testObj) {
        return testObjs.contains(testObj);
    }
}
