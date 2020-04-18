package proxy;

public class MyGame implements Game {

    @Log
    @Override
    public void play() {
        System.out.println("Main play...");
    }

    @Override
    public void play(String smth) {
        System.out.println("Play with parameter " + smth);
    }
}
