package kc.netty.test.client;

public class Test {
    public static void main(String[] args) {
        MessageHandler.concurrentLinkedQueue.add("1");
        System.out.println(MessageHandler.concurrentLinkedQueue.size());
    }
}
