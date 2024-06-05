package cn.Hanabi.modules.Combat;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;

public class Hitbox extends Mod
{
    public static Value<Double> size;
    
    
    public Hitbox() {
        super("Hitbox", Category.COMBAT);
    }
    
    public static float getSize() {
        return (float)(ModManager.getModule("Hitbox").isEnabled() ? Hitbox.size.getValueState() : 0.10000000149011612);
    }
    
    static {
        Hitbox.size = new Value<Double>("Hitbox_Size", 0.25, 0.1, 1.0, 0.05);
    }
}
