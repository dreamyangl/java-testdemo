package normal;

import java.util.Calendar;

public class TestStatic {
    public static int a = 1;

    public static void test(){
        while (true){
            System.out.println(a);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        while (true){
//            System.out.println(a);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
