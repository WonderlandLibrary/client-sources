 package me.vene.skilled.modules.mods.player;

 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fly extends Module
{
    private Minecraft mc;
    private NumberValue speed;
    
    public Fly() {
        super("Fly", 0, Category.P);
        this.mc = Minecraft.getMinecraft();
        this.addValue(this.speed = new NumberValue("Speed", 3.0, 1.0, 10.0));
    }
    
    @Override
    public void onDisable() {
        if (this.mc.thePlayer.capabilities.isFlying) {
            this.mc.thePlayer.capabilities.isFlying = false;
        }
        this.mc.thePlayer.capabilities.setFlySpeed(0.05f);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.capabilities.setFlySpeed(0.05f * (float)this.speed.getValue());
        this.mc.thePlayer.capabilities.isFlying = true;
    }
}
