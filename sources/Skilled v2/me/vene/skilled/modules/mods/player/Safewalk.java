 package me.vene.skilled.modules.mods.player;

 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.settings.KeyBinding;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.MathHelper;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class Safewalk extends Module
{
    private Minecraft mc;
    private static boolean c;
    private static boolean d;
    
    public Safewalk() {
        super(StringRegistry.register("Safewalk"), 0, Category.P);
        this.mc = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void p(final TickEvent.PlayerTickEvent e) {
        if (!this.e()) {
            return;
        }
        if (this.mc.thePlayer.onGround) {
            if (this.eob()) {
                this.sh(Safewalk.d = true);
                Safewalk.c = true;
            }
            else if (Safewalk.d) {
                this.sh(Safewalk.d = false);
            }
        }
        if (Safewalk.c && this.mc.thePlayer.capabilities.isFlying) {
            this.sh(false);
            Safewalk.c = false;
        }
    }
    
    private void sh(final boolean sh) {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), sh);
    }
    
    public boolean e() {
        return this.mc.thePlayer != null && this.mc.theWorld != null;
    }
    
    public boolean eob() {
        final double x = this.mc.thePlayer.posX;
        final double y = this.mc.thePlayer.posY - 1.0;
        final double z = this.mc.thePlayer.posZ;
        final BlockPos p = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
        return this.mc.theWorld.isAirBlock(p);
    }
    
    static {
        Safewalk.c = false;
        Safewalk.d = false;
    }
}
