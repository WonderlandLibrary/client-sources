package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import net.minecraft.util.math.Vec3d;

//@RegisterModule(
//        name = "Reset VL",
//        uniqueId = "vlReset",
//        description = "Resets Violation Level",
//        category = ModuleCategory.Player
//)
public class ResetVL extends Module {

    boolean jump = false;
    boolean jumped = false;

    @Override
    protected void onEnable() {
        jump = true;
    }

    @Override
    protected void onDisable() {
        jump = false;
        jumped = false;
    }

    @SubscribeEvent(EventBus.Priority.HIGH)
    public void onMotionPre(MotionEvent.Pre e) {
        if (jumped) {
            if (MovementUtil.airTicks > 9) C.p()
                .setVelocity(new Vec3d(0, C.p().getVelocity().y / 4.8, 0));
            if (C.p().isOnGround()) ModuleManager.toggle(ResetVL.class, false);
        }

        if (jump && C.p().isOnGround()) {
            MovementUtil.jump();
            jump = false;
            jumped = true;
        }
    }
}
