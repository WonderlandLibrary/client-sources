package com.alan.clients.module.impl.player.scaffold.sprint;
import com.alan.clients.component.impl.player.Slot;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.player.SlotUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;

import java.util.Objects;

public class BlocksMCSprint extends Mode<Scaffold> {





    public BlocksMCSprint(String name, Scaffold parent) {
            super(name, parent);
        }


        @EventLink
        public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
            if(mc.thePlayer.onGround) {
              //  event.setPosY(event.getPosY() + 0.00001);
            }

        };
        @Override
        public void onEnable() {

            if (!(PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.lastGroundY - 2, mc.thePlayer.posY) instanceof BlockAir)) {
                getParent().startY = mc.thePlayer.lastGroundY - 1;
            }
        }

        @Override
        public void onDisable() {

            if (!(PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.lastGroundY - 2, mc.thePlayer.posY) instanceof BlockAir)) {
                getParent().startY = mc.thePlayer.lastGroundY - 1;
            }
        }

        @EventLink(value = Priorities.LOW)
        public final Listener<PreUpdateEvent> onPreUpdate = event -> {
            boolean start = mc.thePlayer.lastGroundY == getParent().startY;
            //   ChatUtil.display("airtick: "+mc.thePlayer.offGroundTicks);
            if (getComponent(Slot.class).getItemStack() != null &&
                    mc.thePlayer.posY > getParent().startY &&
                    mc.thePlayer.posY + MoveUtil.predictedMotion(mc.thePlayer.motionY, 3) <
                            getParent().startY + 1) {

                // Getting ItemSlot
                getComponent(Slot.class).setSlot(SlotUtil.findBlock());

                if ((getComponent(Slot.class).getItemStack()).realStackSize > 0) {
                    mc.rightClickMouse();
                }

//            getParent().offset = getParent().offset.add(0, 1, 0);

            }

            if (!start && mc.thePlayer.onGround) {
               // MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance());
                mc.thePlayer.jump();

                MoveUtil.strafe();

            }


            if(mc.thePlayer.offGroundTicks == 4 && !mc.gameSettings.keyBindJump.isKeyDown()){
                mc.thePlayer.motionY = mc.thePlayer.motionY -MoveUtil.UNLOADED_CHUNK_MOTION;
            }



            if(mc.thePlayer.offGroundTicks == 1 && !mc.gameSettings.keyBindJump.isKeyDown()){
                MoveUtil.strafe();
            }
            if (PlayerUtil.blockRelativeToPlayer(0, mc.thePlayer.motionY, 0) != Blocks.air && mc.thePlayer.offGroundTicks > 2) {
                MoveUtil.strafe();
            }


            mc.thePlayer.omniSprint = MoveUtil.isMoving();
        };
    }


