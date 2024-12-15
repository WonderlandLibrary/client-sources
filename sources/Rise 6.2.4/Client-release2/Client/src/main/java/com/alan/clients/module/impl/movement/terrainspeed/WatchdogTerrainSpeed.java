package com.alan.clients.module.impl.movement.terrainspeed;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.other.MoveEvent;
import com.alan.clients.module.impl.combat.KillAura;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.movement.TerrainSpeed;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.potion.Potion;

public final class WatchdogTerrainSpeed extends Mode<TerrainSpeed> {
    private int ticksSinceOnStairs, ticksSinceOnSlab;

    public WatchdogTerrainSpeed(String name, TerrainSpeed parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<StrafeEvent> strafeEventListener = event -> {
        if ((PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ) instanceof BlockSlab) && mc.thePlayer.posY == Math.floor(mc.thePlayer.posY) + 0.5 && !getModule(Scaffold.class).isEnabled() && !getModule(Speed.class).isEnabled() && getModule(KillAura.class).target == null && !mc.gameSettings.keyBindBack.isKeyDown()) {
            if(mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                double[] value = new double[]{ 0.276, 0.309, 0.349, 0.3967};

                MoveUtil.strafe(value[Math.min(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier(), value.length - 1)]);
            } else {
                MoveUtil.strafe(0.27);
            }

            ticksSinceOnSlab = 0;
        } else {
            ticksSinceOnSlab++;
            if (ticksSinceOnSlab == 1) {
                event.setFriction(event.getFriction() * 0.9F);
            }
        }
        if ((PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ) instanceof BlockStairs) && !getModule(Scaffold.class).isEnabled() && !getModule(Speed.class).isEnabled() && getModule(KillAura.class).target == null && mc.gameSettings.keyBindForward.isKeyDown()) {
            if(mc.thePlayer.posY == Math.floor(mc.thePlayer.posY) + 0.5 || ticksSinceOnStairs == 0) {
                event.setFriction((float) (event.getFriction() * 3.98));
                ticksSinceOnStairs = 0;
            }
        } else {
            ticksSinceOnStairs++;
            if (ticksSinceOnStairs == 1) {
                event.setFriction((float) (event.getFriction() * 0.1));
            }
        }
    };

    @EventLink
    public final Listener<MoveEvent> moveEventListener = event -> {
        if(ticksSinceOnStairs < 2) {
            event.setPosY(-0.0784);
            mc.gameSettings.keyBindJump.setPressed(false);
        }
    };
}
