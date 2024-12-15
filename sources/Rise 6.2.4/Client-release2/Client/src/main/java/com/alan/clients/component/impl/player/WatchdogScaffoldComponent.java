package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2f;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class WatchdogScaffoldComponent extends Component {
    private boolean scaffold = false;
    private boolean jump = false;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        ItemStack stack = getComponent(Slot.class).getItemStack();
        if (stack == null || stack.realStackSize <= 0) return;
        if (getModule(Scaffold.class).sprint.getValue().getName().equals("Watchdog Jump") || getModule(Scaffold.class).sprint.getValue().getName().equals("Watchdog Fast")) {
            if (jump && !mc.gameSettings.keyBindJump.isKeyDown() && getModule(Scaffold.class).isEnabled() && !getModule(Speed.class).isEnabled() && MoveUtil.isMoving()) {
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MoveUtil.strafe(-.16);
                } else {
                    MoveUtil.strafe(-.1);
                }

                mc.thePlayer.safeWalk = true;
                RotationComponent.setSmoothed(false);
                RotationComponent.setRotations(new Vector2f((float) (mc.thePlayer.rotationYaw - (100 - (1E-14)) + (Math.random() - 0.5) * 3), (float) (86)), 10, MovementFix.OFF);// Store the BlockPos in a variabl

                if (!getModule(Speed.class).isEnabled()) {
                    mc.rightClickMouse();
                    getModule(Scaffold.class).offset = getModule(Scaffold.class).offset.add(0, -1, 0);
                }


                jump = false;
                scaffold = true;
            } else if (!mc.gameSettings.keyBindJump.isKeyDown() && scaffold && !getModule(Speed.class).isEnabled() && MoveUtil.isMoving()) {
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    MoveUtil.strafe(-.05);
                } else {
                    MoveUtil.strafe(-.03);
                }
                if (!getModule(Speed.class).isEnabled()) {
                    mc.rightClickMouse();
                    getModule(Scaffold.class).offset = getModule(Scaffold.class).offset.add(0, -1, 0);
                }

                mc.thePlayer.safeWalk = true;
                scaffold = false;
            }

            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                jump = true;
            }
        }


    };

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (jump && !mc.gameSettings.keyBindJump.isKeyDown() && getModule(Scaffold.class).isEnabled() && !getModule(Speed.class).isEnabled() && MoveUtil.isMoving()) {
            mc.rightClickMouse();
            getModule(Scaffold.class).offset = getModule(Scaffold.class).offset.add(0, -1, 0);
        }
    };
}
