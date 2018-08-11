package lab.model.aop;

import lab.model.Bar;
import lab.model.Customer;
import lab.model.CustomerBrokenException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:aop.xml")
public class AopAspectJExceptionTest {

    @Autowired
    private Bar bar;

    @Autowired
    private Customer customer;

    @Before
    public void setUp() {
        customer.setBroke(true);
    }

    @Test(expected = CustomerBrokenException.class)
    public void testAfterThrowingAdvice() {
        bar.sellSquishee(customer);
    }

    @After
    public void tearDown() {
        customer.setBroke(false);
    }
}