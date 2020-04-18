package proxy;

public class MyGame implements Game {

    @Override
    @Log
    public void play() {
        System.out.println("Main play...");
    }

    public void play(String smth) {
        System.out.println("Play with parameter " + smth);
    }
}
