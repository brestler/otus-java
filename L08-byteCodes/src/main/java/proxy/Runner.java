package proxy;

public class Runner {

    public static void main(String[] args) {
        Game game = IoC.createProxiedGame(new MyGame());
        game.play();
        game.play("Ho-ho");
    }
}
