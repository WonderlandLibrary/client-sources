package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.n;
import net.futureclient.client.lC;
import net.futureclient.client.pg;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class ClickGui extends Ea
{
    public NumberValue scale;
    
    public ClickGui() {
        super("ClickGui", new String[] { "ClickGui", "ClickUI", "GUI" }, false, 16777215, Category.RENDER);
        this.scale = new NumberValue(1.0, 4.24399158E-315, 0.0, 1.273197475E-314, new String[] { "Scale", "Scaling", "Scal" });
        this.M(new Value[] { this.scale });
    }
    
    public void B() {
        final boolean b = false;
        super.B();
        pg.M().M().M((n)new lC(this));
        this.M(b);
    }
    
    public static Minecraft getMinecraft() {
        return ClickGui.D;
    }
}
