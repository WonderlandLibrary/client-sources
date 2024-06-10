 package me.vene.skilled.modules.mods.player;
 
 import java.lang.reflect.Field;
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
import me.vene.skilled.modules.mods.combat.Velocity;
import me.vene.skilled.utilities.StringRegistry;
import me.vene.skilled.values.BooleanValue;
import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.util.Timer;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class TimerModule extends Module
{
    private Field t;
    private static Minecraft mc;
    private NumberValue timerValue;
    private static BooleanValue StrafeOnly;
    
    public TimerModule() {
        super(StringRegistry.register("Timer"), 0, Category.P);
        this.mc = Minecraft.getMinecraft();
        this.addValue(this.timerValue = new NumberValue(StringRegistry.register("Speed"), 1.07, 0.1, 2.0));
        try {
            this.t = Minecraft.class.getDeclaredField(new String(new char[] { 'f', 'i', 'e', 'l', 'd', '_', '7', '1', '4', '2', '8', '_', 'T' }));
        }
        catch (Exception er) {
            try {
                this.t = Minecraft.class.getDeclaredField(new String(new char[] { 't', 'i', 'm', 'e', 'r' }));
            }
            catch (Exception ex) {}
        }
        if (this.t != null) {
            this.t.setAccessible(true);
        }
    }
    
    @Override
    public void onDisable() {
        this.timerField().timerSpeed = 1.0f;
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.theWorld != null && this.mc.thePlayer != null) {
    	       return;
   	          }
            try {
                this.timerField().timerSpeed = (float)this.timerValue.getValue();
            }
            catch (NullPointerException ex) {}
    }

    public Timer timerField() {
        try {
            return (Timer)this.t.get(this.mc);
        }
        catch (IllegalAccessException ex2) {
            final Exception er;
            final Exception ex = er = null;
            return null;
        }
    }
}
