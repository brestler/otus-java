package test_framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class TestRunner {

    public static void runTests(String targetClassName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> aClass = Class.forName(targetClassName);
        TestClassMethodsContainer container = new TestClassMethodsContainer(aClass);
        int failedTests = 0;
        for (Method test : container.getTests()) {
            Object testInstance = aClass.getConstructor().newInstance();
            invokeAll(container.getBeforeEachMethods(), testInstance);
            try {
                test.invoke(testInstance);
            } catch (InvocationTargetException e) {
                failedTests++;
            }
            invokeAll(container.getAfterEachMethods(), testInstance);
        }
        System.out.println();
        System.out.println("************** Test results **************");
        System.out.printf("Total amount of tests: %d\n", container.getTotalTestsCount());
        System.out.printf("Succeed: %d\n", (container.getTotalTestsCount() - failedTests));
        System.out.printf("Failed: %d\n", failedTests);
    }

    private static void invokeAll(List<Method> methods, Object instance) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(instance);
        }
    }
}
