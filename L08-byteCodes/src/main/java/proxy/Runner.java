package proxy;

public class Runner {

    public static void main(String[] args) {
        Game game = IoC.createProxy(new MyGame(), Game.class);
        game.play();
        game.play("Ho-ho");

        NotAGameAtAll notAGameAtAll = IoC.createProxy(new MyNotAGameAtAll(), NotAGameAtAll.class);
        notAGameAtAll.work();
    }
}
