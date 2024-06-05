package me.superskidder.lune.modules.render;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.block.state.BlockState;

public class Animation extends Mod {

    public static Mode<Enum> mode = new Mode("Mode", (Enum[]) renderMode.values(), (Enum) renderMode.Autumn);
    public static Num<Number> transformFirstPersonItem;
    public static Num<Number> MathHelper;
    public static Num<Number> translate = new Num<Number>("Y", 0.15, -1.0, 1.0);
    public static Num<Number> translate1 = new Num<Number>("X", 0.0, -1.0, 1.0);
    public static Num<Number> translate2 = new Num<Number>("Z", 0.0, -1.0, 1.0);
    public static Bool<Boolean> sanimation = new Bool("SwingAnimation", true);
    public static Num<Number> slow = new Num("Slow", 10, 0, 15);


    public Animation() {
        super("Animation", ModCategory.Render,"Sword block animation");
        addValues(mode, translate, translate1, translate2, sanimation, slow);
    }

    public static enum renderMode {
        Leain, ETB, Nov, Avatar, Jello, Swing, Luna, Swank, Slide, Jigsaw, Prohibit, Exhibition, OLD, Autumn, SLIDE, External, Rotation;
    }
}
