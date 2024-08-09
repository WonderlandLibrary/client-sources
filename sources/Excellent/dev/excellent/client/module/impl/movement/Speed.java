package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.network.play.client.CConfirmTransactionPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

@ModuleInfo(name = "Speed", description = "Позволяет вам быстро бегать.", category = Category.MOVEMENT)
public class Speed extends Module {
    public final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Грим"),
                    new SubMode("Грим новые"),
                    new SubMode("FunTime"),
                    new SubMode("Матрикс")
            ).setDefault("Грим новые");

    @Override
    protected void onEnable() {
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        mc.timer.resetSpeed();
    }


    private final Listener<MotionEvent> onMotion = event -> {
        if (mode.is("Матрикс")) {
            handleNewMatrix();
        }
    };


    private final Listener<PacketEvent> onPacket = event -> {
        if (mode.is("Грим новые")) {
            if (event.getPacket() instanceof CConfirmTransactionPacket p) {
                event.cancel();
            }
            if (event.getPacket() instanceof SPlayerPositionLookPacket p) {
                mc.player.setPacketCoordinates(new Vector3d(p.getX(), p.getY(), p.getZ()));
                mc.player.setRawPosition(p.getX(), p.getY(), p.getZ());
                toggle();
            }
        }
    };
    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mode.is("Грим")) {
            handleGrim();
        }

        if (mode.is("Грим новые")) {
            handleNewGrim();
        }
        if (mode.is("FunTime")) {
            if (!PlayerUtil.isFuntime()) {
                ChatUtil.addText("Данная функция работает только на FunTime!");
                this.toggle();
                return;
            }
            if (mc.player.inventory.getCurrentItem().isEmpty()) {
                return;
            }
            if (!mc.player.isSpectator()) {
                CPlayerDiggingPacket.Action action = CPlayerDiggingPacket.Action.DROP_ITEM;
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(action, mc.player.getPosition(), Direction.UP));
            }

            double currentSpeedMultiplier = MoveUtil.speedSqrt();
            final double accelerationStep = 1.1;
            currentSpeedMultiplier += accelerationStep;
            currentSpeedMultiplier = Mathf.clamp(0, 1.5, currentSpeedMultiplier);
            if (mc.player.isOnGround()) {
                double speed = MoveUtil.speedSqrt() * currentSpeedMultiplier;
                MoveUtil.setSpeed(speed);
            }
        }
    };

    private void handleGrim() {
        if (MoveUtil.isMoving() && mc.player.isOnGround() && mc.player.isInLiquid()) {
            mc.player.jump();
        }
        if (mc.player.isOnGround()) {
            mc.timer.setSpeed(0.821F);
        }
        if (mc.player.fallDistance > 0.1 && mc.player.fallDistance < 1) {
            mc.timer.setSpeed(1 + (1F - Math.floorMod((long) 2.520, (long) 2.600)));
        }
        if (mc.player.fallDistance >= 1) {
            mc.timer.setSpeed(0.91F);
        }
    }

    private final TimerUtil timerUtil = TimerUtil.create();
    private boolean boosting;

    private void handleNewGrim() {
        if (timerUtil.hasReached(1150)) {
            boosting = true;
        }
        if (timerUtil.hasReached(7000)) {
            boosting = false;
            timerUtil.reset();
        }
        if (boosting) {
            if (mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed) {
                mc.player.jump();
            }
            mc.timer.setSpeed(mc.player.ticksExisted % 2 == 0 ? 1.5f : 1.2f);
        } else {
            mc.timer.setSpeed(0.05f);
        }
    }

    private void handleNewMatrix() {
        if (MoveUtil.isMoving() && mc.player.isOnGround() && mc.player.isInLiquid()) {
            mc.player.jump();
        }
        if (mc.player.isOnGround()) {
            mc.timer.setSpeed(1.1f);
        }
        if (mc.player.fallDistance > 0.1 && mc.player.fallDistance < 1) {
            mc.timer.setSpeed(1 + (1F - Math.floorMod((long) 2.520, (long) 2.600)));
        }
        if (mc.player.fallDistance >= 1) {
            mc.timer.setSpeed(0.978F);
        }
    }

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    public int getSlabSlotInHotbar() {
        int finalSlot = -1;
        for (int i = 0; i < 9; ++i) {
            if (Block.getBlockFromItem(mc.player.inventory.getStackInSlot(i).getItem()) instanceof SlabBlock) {
                finalSlot = i;
            }
        }
        return finalSlot;
    }
}
