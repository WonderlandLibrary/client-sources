 package me.vene.skilled.modules.mods.player;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.settings.KeyBinding;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class WTap extends Module
{
    private long nextTapDown;
    private long nextTapUp;
    private NumberValue chanceValue;
    private Minecraft mc;
    
    public WTap() {
        super(StringRegistry.register("WTap"), 0, Category.C);
        this.nextTapDown = 0L;
        this.nextTapUp = 0L;
        this.chanceValue = new NumberValue("Chance", 60.0, 1.0, 100.0);
        this.mc = Minecraft.getMinecraft();
        this.addValue(this.chanceValue);
    }
    
    @Override
    public void onHurtAnimation() {
        if (this.mc.thePlayer.isDead) {
            return;
        }
        if (this.mc.theWorld == null || this.mc.currentScreen != null) {
            return;
        }
        if (!this.mc.thePlayer.isSprinting()) {
            return;
        }
        if (this.chanceValue.getValue() >= MathUtil.random.nextInt(100) && this.nextTapUp == 0L && this.nextTapDown == 0L) {
            this.nextTapUp = System.currentTimeMillis() + 40L + MathUtil.random.nextInt(325);
        }
    }
    
    @Override
    public void onRenderTick(final TickEvent.RenderTickEvent renderTickEvent) {
        if (this.mc.thePlayer == null || this.mc.theWorld == null || this.mc.currentScreen != null) {
            return;
        }
        if (this.mc.thePlayer.isDead) {
            return;
        }
        if (!this.mc.thePlayer.isSprinting() && this.nextTapUp > 0L) {
            this.nextTapUp = 0L;
            return;
        }
        if (System.currentTimeMillis() - this.nextTapUp > 0L && this.nextTapUp != 0L) {
            final int forwardKey = this.mc.gameSettings.keyBindForward.getKeyCode();
            KeyBinding.setKeyBindState(forwardKey, false);
            KeyBinding.onTick(forwardKey);
            this.nextTapDown = System.currentTimeMillis() + 90L + MathUtil.random.nextInt(50);
            this.nextTapUp = 0L;
        }
        else if (System.currentTimeMillis() - this.nextTapDown > 0L && this.nextTapDown != 0L) {
            final int forwardKey = this.mc.gameSettings.keyBindForward.getKeyCode();
            KeyBinding.setKeyBindState(forwardKey, true);
            KeyBinding.onTick(forwardKey);
            this.nextTapDown = 0L;
        }
    }
}
