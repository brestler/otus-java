
package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IoC {

    @SuppressWarnings("unchecked")
    static <T> T createProxy(T proxyThis) {
        InvocationHandler handler = new MyInvocationHandler(proxyThis);
        return (T) Proxy.newProxyInstance(IoC.class.getClassLoader(), proxyThis.getClass().getInterfaces(), handler);
    }


    static class MyInvocationHandler<T> implements InvocationHandler {

        private T targetObject;
        private Set<Method> methodsToLog;

        public MyInvocationHandler(T targetObject) {
            this.targetObject = targetObject;
            this.methodsToLog = new HashSet<>();
            for (Method method : targetObject.getClass().getDeclaredMethods()) {
                if (method.getDeclaredAnnotation(Log.class) != null) {
                    methodsToLog.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            if (shouldLogBeforeInvocation(method)) {
                System.out.println("Before action");
            }
            return method.invoke(targetObject, objects);
        }

        private boolean shouldLogBeforeInvocation(Method method) {
            return methodsToLog.stream().anyMatch(methodToLog -> method.getName().equals(methodToLog.getName())
                    && Arrays.equals(method.getParameterTypes(), methodToLog.getParameterTypes()));
        }
    }
}
