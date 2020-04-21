import test_framework.TestRunner;

import java.lang.reflect.InvocationTargetException;

public class Run {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TestRunner.runTests("MyTest");
    }
}
