package normal;

import com.alibaba.fastjson.JSONObject;
import testjava8.Test;

public class TestClass {
    private int id;
    private String name;
    private int age = this.id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        TestClass testClass = new  TestClass();
        testClass.setId(1);
        testClass.setName("test");
        System.out.println(testClass.getAge());

        System.out.println(JSONObject.toJSONString(testClass));
    }
}
