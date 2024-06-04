package com.polarware.module.impl.movement;

import com.polarware.component.impl.player.PacketlessDamageComponent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.longjump.*;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;

/**
 * @author Auth
 * @since 3/02/2022
 */
@ModuleInfo(name = "module.movement.longjump.name", description = "module.movement.longjump.description", category = Category.MOVEMENT)
public class LongJumpModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaLongJump("Vanilla", this))
            .add(new NCPLongJump("NCP", this))
            .add(new WatchdogLongJump("Watchdog", this))
            .add(new VulcanLongJump("Vulcan", this))
            .add(new ExtremeCraftLongJump("Extreme Craft", this))
            .add(new MatrixLongJump("Matrix", this))
            .add(new FireBallLongJump("Fire Ball", this))
            .setDefault("Vanilla");

    private final BooleanValue autoDisable = new BooleanValue("Auto Disable", this, true);
    private final BooleanValue fakeDamage = new BooleanValue("Fake Damage", this, false);

    private boolean inAir;

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (autoDisable.getValue() && inAir && mc.thePlayer.onGround) {
            this.toggle();
        }

        inAir = !mc.thePlayer.onGround && !PacketlessDamageComponent.isActive();
    };

    @Override
    protected void onEnable() {
        if (fakeDamage.getValue() && mc.thePlayer.ticksExisted > 1) {
            PlayerUtil.fakeDamage();
        }
    }

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        inAir = false;
    }
}
