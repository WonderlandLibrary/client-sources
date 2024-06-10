 package me.vene.skilled.modules.mods.player;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class InvMove extends Module
{
    private int left;
    private int right;
    private int backkey;
    private int forward;
    
    public InvMove() {
        super(StringRegistry.register("InvMove"), 0, Category.P);
        this.left = 30;
        this.right = 32;
        this.backkey = Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode();
        this.forward = Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode();
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
    }
}
