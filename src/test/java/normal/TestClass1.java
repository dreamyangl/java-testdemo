package normal;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestClass1 {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "TestClass1{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public static void main(String[] args) {
        TestClass1 testClass = TestClass1.builder()
                .id(1)
                .name("张三")
                .build();

        System.out.println(JSONObject.toJSONString(testClass));
    }
}
