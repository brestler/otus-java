package proxy;

public class Runner {

    public static void main(String[] args) {
        MyGame myGame = new MyGame();
        Game game = IoC.createProxy(myGame);
        game.play();
        game.play("Ho-ho");

        Other other = IoC.createProxy(myGame);
        other.doOther();

        NotAGameAtAll notAGameAtAll = IoC.createProxy(new MyNotAGameAtAll());
        notAGameAtAll.work();
    }
}
