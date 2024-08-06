package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.TimerUtil;

//@RegisterModule(
//        name = "Fast Use",
//        uniqueId = "fastuseWTF",
//        description = "Eat Quickly On Verus",
//        category = ModuleCategory.Player
//)
public class FastUse extends Module {

    boolean timered = false;

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        if (
            C.p().isUsingItem() &&
            C.p()
                .getInventory()
                .getStack(C.p().getInventory().selectedSlot)
                .getItem()
                .isFood()
        ) {
            if (C.p().isOnGround()) {
                TimerUtil.setTPSMulti(100);
                MovementUtil.jump();
                timered = true;
            }
        }

        if (timered && !C.p().isUsingItem()) {
            TimerUtil.resetTPS();
            timered = false;
        }
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
