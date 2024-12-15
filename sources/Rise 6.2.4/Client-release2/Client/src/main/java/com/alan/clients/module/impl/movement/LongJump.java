package com.alan.clients.module.impl.movement;

import com.alan.clients.component.impl.player.PacketlessDamageComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.longjump.*;
import com.alan.clients.module.impl.movement.longjump.WatchdogBowLongJump;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.movement.longjump.name"}, description = "module.movement.longjump.description", category = Category.MOVEMENT)
public class LongJump extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaLongJump("Vanilla", this))
            .add(new NCPLongJump("NCP", this))
            .add(new VulcanLongJump("Vulcan", this))
            .add(new ExtremeCraftLongJump("Extreme Craft", this))
            .add(new FireBallLongJump("Fire Ball", this))
            .add(new WatchdogLongJump("Watchdog", this))
           // .add(new WatchdogBowLongJump("Watchdog Bow", this))
            .add(new WatchdogFireball("Watchdog Fire Ball",this))
            .add(new MatrixLongJump("Matrix (Deprecated)", this))
            .add(new SparkyLongJump("Sparky", this))
            .add(new Vulcan2LongJump("Vulcan 2", this))
            .setDefault("Vanilla");

    public final BooleanValue autoDisable = new BooleanValue("Auto Disable", this, true);
    private final BooleanValue fakeDamage = new BooleanValue("Fake Damage", this, false);

    private boolean inAir;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (autoDisable.getValue() && inAir && mc.thePlayer.onGround && !mode.getValue().getName().equals("Watchdog")) {
            this.toggle();
        }

        inAir = !mc.thePlayer.onGround && !PacketlessDamageComponent.isActive();
    };

    @Override
    public void onEnable() {
        if (fakeDamage.getValue() && mc.thePlayer.ticksExisted > 1) {
            PlayerUtil.fakeDamage();
        }
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0F;
        inAir = false;
    }
}
