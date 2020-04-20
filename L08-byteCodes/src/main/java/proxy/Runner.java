package proxy;

public class Runner {

    public static void main(String[] args) {
        IoC<Game> proxyMaker = new IoC<>();
        Game game = proxyMaker.createProxy(new MyGame());
        game.play();
        game.play("Ho-ho");

        IoC<NotAGameAtAll> anotherProxyMaker = new IoC<>();
        NotAGameAtAll notAGameAtAll = anotherProxyMaker.createProxy(new MyNotAGameAtAll());
        notAGameAtAll.work();
    }
}
