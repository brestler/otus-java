import test_framework.annotations.AfterEach;
import test_framework.annotations.BeforeEach;
import test_framework.annotations.Test;

public class MyTest {

    @BeforeEach
    public void before() {
        System.out.println("Before test");
    }

    @AfterEach
    public void after() {
        System.out.println("After test");
    }

    @Test
    public void testOne(){
        System.out.println("Inside test one");
    }

    @Test
    public void exceptionTest() {
        System.out.println("Inside exception test");
        throw new RuntimeException();
    }

    @Test
    public void testTwo() {
        System.out.println("Inside test two");
    }
}
