package cn.Hanabi.modules.Render;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;

public class HitAnimation extends Mod
{
    public static Value<String> mode;
    
    
    public HitAnimation() {
        super("HitAnimation", Category.RENDER);
        HitAnimation.mode.addValue("Sigma");
        HitAnimation.mode.addValue("Hanabi");
        HitAnimation.mode.addValue("1.7");
        HitAnimation.mode.addValue("Vanilla");
        HitAnimation.mode.addValue("Luna");
        HitAnimation.mode.addValue("Jigsaw");
        HitAnimation.mode.addValue("ExhibitionSwang");
        HitAnimation.mode.addValue("ExhibitionSwank");
        HitAnimation.mode.addValue("ExhibitionSwong");
    }
    
    static {
        HitAnimation.mode = new Value<String>("HitAnimation", "Mode", 0);
    }
}
