package kc.model.led;

public abstract class LedModel {
    /**
     * 灯当前状态
     * @return
     */
    public LedColorEnum status(){
        return LedColorEnum.green;
    }

    public abstract void action();

}
