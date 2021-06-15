package normal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MulParamsTest {
    private int param;
    private int result;

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {2, 4},
                {0, 0},
                {-3, -6},
        });
    }

    //构造函数，对变量进行初始化
    public MulParamsTest(int param, int result){
        this.param = param;
        this.result = result;
    }

    @Test
    public void square(){
        assertEquals(result, param*2);
    }
}
