 package me.vene.skilled.modules.mods.combat;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.settings.KeyBinding;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class Sprint extends Module
{
    private Minecraft mc;
    
    public Sprint() {
        super(StringRegistry.register("Sprint"), 0, Category.C);
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void onEnable() {
        final int forwardKey = this.mc.gameSettings.keyBindSprint.getKeyCode();
        KeyBinding.setKeyBindState(forwardKey, true);
        KeyBinding.onTick(forwardKey);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.thePlayer != null && this.mc.theWorld != null) {
            final int forwardKey = this.mc.gameSettings.keyBindSprint.getKeyCode();
            KeyBinding.setKeyBindState(forwardKey, true);
            KeyBinding.onTick(forwardKey);
        }
    }
    
    @Override
    public void onDisable() {
        final int forwardKey = this.mc.gameSettings.keyBindSprint.getKeyCode();
        KeyBinding.setKeyBindState(forwardKey, false);
        KeyBinding.onTick(forwardKey);
    }
}
