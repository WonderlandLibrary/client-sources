// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.ghost;

import net.minecraft.client.settings.KeyBinding;
import xyz.niggfaclient.utils.other.MathUtils;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AutoClicker", description = "Auto Clicks for you", cat = Category.GHOST)
public class AutoClicker extends Module
{
    private final DoubleProperty maxCPS;
    private final DoubleProperty minCPS;
    private final Property<Boolean> jitter;
    private final TimerUtil timer;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public AutoClicker() {
        this.maxCPS = new DoubleProperty("Max CPS", 10.0, 1.0, 20.0, 1.0);
        this.minCPS = new DoubleProperty("Min CPS", 10.0, 1.0, 20.0, 1.0);
        this.jitter = new Property<Boolean>("Jitter", false);
        this.timer = new TimerUtil();
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + Math.round(this.maxCPS.getValue() * 100.0 / 100.0));
            if (Mouse.isButtonDown(0) && this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                this.mc.gameSettings.keyBindAttack.setPressed(true);
            }
            else if (this.mc.currentScreen == null && !this.mc.thePlayer.isBlocking()) {
                Mouse.poll();
                if (Mouse.isButtonDown(0) && this.timer.hasElapsed(1000 / MathUtils.getRandomInRange(this.minCPS.getValue().intValue(), this.maxCPS.getValue().intValue()))) {
                    this.sendClick(0, true);
                    this.sendClick(0, false);
                    this.timer.reset();
                }
                if (this.jitter.getValue() && Mouse.isButtonDown(0)) {
                    this.mc.thePlayer.rotationYaw += (float)MathUtils.getRandomInRange(-0.5, 0.5);
                    this.mc.thePlayer.rotationPitch += (float)MathUtils.getRandomInRange(-0.5, 0.5);
                }
            }
        });
    }
    
    private void sendClick(final int button, final boolean state) {
        final int keyBind = (button == 0) ? this.mc.gameSettings.keyBindAttack.getKeyCode() : this.mc.gameSettings.keyBindUseItem.getKeyCode();
        KeyBinding.setKeyBindState((button == 0) ? this.mc.gameSettings.keyBindAttack.getKeyCode() : this.mc.gameSettings.keyBindUseItem.getKeyCode(), state);
        if (state) {
            KeyBinding.onTick(keyBind);
        }
    }
}
