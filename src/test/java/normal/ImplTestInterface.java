package normal;

public class ImplTestInterface implements TestInterface{
    static void print3(){
        System.out.println("print3");
    }

    static {
        System.out.println("111");
    }
    public static void main(String[] args) {
        ImplTestInterface implTestInterface = new ImplTestInterface();
        for (int i = 0;i<10;i++){
            implTestInterface.print1();
        }

    }

    @Override
    public void print1() {
        System.out.println("print1");
    }
}
