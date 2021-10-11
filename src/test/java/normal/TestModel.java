package normal;

import testjava8.Test;

public class TestModel {
    private String s;
    public TestModel build(){
        this.s = "111";
        return this;
    }
    public TestModel setString(){
        this.s = "222";
        System.out.println(this.s);
        return this;
    }

    public static void main(String[] args) {
        new TestModel().build().setString();
    }
}
