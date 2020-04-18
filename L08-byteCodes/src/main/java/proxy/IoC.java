package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IoC {

    static Game createProxiedGame(Game game) {
        InvocationHandler handler = new MyInvocationHandler(game);
        return (Game) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{Game.class}, handler);
    }


    static class MyInvocationHandler implements InvocationHandler {

        private Game game;
        private Set<Method> methodsToLog;

        public MyInvocationHandler(Game game) {
            this.game = game;
            this.methodsToLog = new HashSet<>();
            for (Method method : game.getClass().getDeclaredMethods()) {
                if (method.getDeclaredAnnotation(Log.class) != null) {
                    methodsToLog.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            if (methodsToLog.stream().anyMatch(method1 -> method.getName().equals(method1.getName())
                    && Arrays.equals(method.getParameterTypes(), method1.getParameterTypes()))) {
                System.out.println("Before the play");
                return method.invoke(game, objects);
            }
            return method.invoke(game, objects);
        }
    }
}
