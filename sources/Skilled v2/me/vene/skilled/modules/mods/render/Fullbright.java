 package me.vene.skilled.modules.mods.render;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 
public class Fullbright extends Module
{
    private Minecraft mc;
    
    public Fullbright() {
        super(StringRegistry.register("Fullbright"), 0, Category.R);
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void onEnable() {
        this.mc.gameSettings.gammaSetting = 1000.0f;
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.gammaSetting = 1.0f;
    }
}
