package ThreadDome;

public class ThreadAB {
    public void testAB(){
        ThreadMath threadMath = new ThreadMath();
        Thread threadA = new Thread(){
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                new ThreadMath().hello(thread.getName());
            }
        };
        threadA.start();

        Thread threadB = new Thread(){
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                new ThreadMath().hello(thread.getName());
            }
        };
        threadB.start();
    }
}
