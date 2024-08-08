package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.tabgui.Listener2;
import net.futureclient.client.modules.render.tabgui.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.mh;
import net.futureclient.client.utils.Value;
import net.futureclient.client.qa;
import net.futureclient.client.Ea;

public class TabGui extends Ea
{
    public qa D;
    private Value<Boolean> useEnter;
    
    public TabGui() {
        super("TabGui", new String[] { mh.M("\u0014?\"\u001957"), "tabui", mh.M("4?\"-") }, false, -23445, Category.RENDER);
        this.useEnter = new Value<Boolean>(true, new String[] { "UseEnter", mh.M("\u001b.*%,"), "AllowEnter", mh.M("\u000b3;.*%,") });
        this.M(new Value[] { this.useEnter });
        this.M(new n[] { new Listener1(this) });
        this.M(new n[] { new Listener2(this) });
    }
    
    public static Value M(final TabGui tabGui) {
        return tabGui.useEnter;
    }
    
    public static Minecraft getMinecraft() {
        return TabGui.D;
    }
}
