 package me.vene.skilled.modules.mods.render;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import net.minecraftforge.client.event.RenderPlayerEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import org.lwjgl.opengl.GL11;
 
public class Chams extends Module
{
    public Chams() {
        super("Chams", 0, Category.R);
    }
    
    @SubscribeEvent
    public void Method153(final RenderPlayerEvent.Pre var1) {
        GL11.glEnable(32823);
        GL11.glPolygonOffset(1.0f, -1100000.0f);
    }
    
    @SubscribeEvent
    public void Method154(final RenderPlayerEvent.Post var1) {
        GL11.glDisable(32823);
        GL11.glPolygonOffset(1.0f, 1100000.0f);
    }
}
