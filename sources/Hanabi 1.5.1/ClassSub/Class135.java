package ClassSub;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

public class Class135 extends Mod
{
    public static final Class208 clickGui;
    ScaledResolution sr;
    public static Value<String> theme;
    int lastWidth;
    public static Class208 hanabi;
    
    
    public Class135() {
        super("ClickGUI", Category.RENDER, true, true, 54);
        this.lastWidth = 0;
        Class135.theme.addValue("Dark");
        Class135.theme.addValue("Light");
        this.setState(false);
    }
    
    @Override
    protected void onEnable() {
        if (Class135.mc.thePlayer == null) {
            return;
        }
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        if (Class135.hanabi == null || this.lastWidth != this.sr.getScaledWidth()) {
            this.lastWidth = this.sr.getScaledWidth();
            Class135.hanabi = new Class208();
        }
        if (Class135.mc.currentScreen instanceof Class208) {
            this.setState(false);
            return;
        }
        Class135.mc.displayGuiScreen((GuiScreen)Class135.hanabi);
        this.setState(false);
    }
    
    static {
        clickGui = new Class208();
        Class135.theme = new Value<String>("ClickGUI", "Theme", 0);
    }
}
