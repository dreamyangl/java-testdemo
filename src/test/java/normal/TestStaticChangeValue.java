package normal;

public class TestStaticChangeValue {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                TestStatic.test();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TestStatic.a = 2;
            }
        }).start();

    }
}
