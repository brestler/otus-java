package proxy;

public class Runner {

    public static void main(String[] args) throws NoSuchMethodException {
        Game game = IoC.createProxiedGame(new MyGame());
        game.play();
        game.play("HEY");
    }
}
