package ThreadDome;

public class ThreadMath {

    public void hello(String name){
        synchronized(ThreadMath.class){
            System.out.println(this);
            System.out.println(name+"  hello  正在执行");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+"  hello  end");
        }
    }
}
