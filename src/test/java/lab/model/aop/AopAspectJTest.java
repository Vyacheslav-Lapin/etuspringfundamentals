package lab.model.aop;

import lab.common.TestUtils;
import lab.model.ApuBar;
import lab.model.Bar;
import lab.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aop.xml")
public class AopAspectJTest {

    @Autowired
    Bar bar;

    @Autowired
    Customer customer;

    String message;

    @Before
    public void setUp() {
        message = TestUtils.fromSystemOutPrintln(() -> bar.sellSquishee(customer));
    }

    @Test
    public void testBeforeAdvice() {
        assertTrue("Before advice is not good enought...", message.contains("Hello"));
        assertTrue("Before advice is not good enought...", message.contains("How are you doing?"));
    }

    @Test
    public void testAfterAdvice() {
        assertTrue("After advice is not good enought...", message.contains("Good Bye!"));
    }

    @Test
    public void testAfterReturningAdvice() {
        assertTrue("Customer is broken", message.contains("Good Enough?"));
    }

    @Test
    public void testAroundAdvice() {
        assertTrue("Around advice is not good enought...", message.contains("Hi!"));
        assertTrue("Around advice is not good enought...", message.contains("See you!"));
    }

    @Test
    public void testAllAdvices() {
        assertFalse("barObject instanceof ApuBar", bar instanceof ApuBar);
    }
}