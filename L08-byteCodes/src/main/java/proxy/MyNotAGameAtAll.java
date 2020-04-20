package proxy;

public class MyNotAGameAtAll implements NotAGameAtAll {

    @Log
    @Override
    public void work() {
        System.out.println("Work...");
    }
}
