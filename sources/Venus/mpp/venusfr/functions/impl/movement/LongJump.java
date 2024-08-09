/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import mpp.venusfr.utils.player.MouseUtil;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.Pose;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

@FunctionRegister(name="LongJump", type=Category.Movement)
public class LongJump
extends Function {
    boolean placed;
    int counter;
    public ModeSetting mod = new ModeSetting("\u041c\u043e\u0434", "Slap", "Slap");
    StopWatch stopWatch = new StopWatch();

    public LongJump() {
        this.addSettings(this.mod);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (this.mod.is("Slap") && !LongJump.mc.player.isInWater()) {
            int n = InventoryUtil.getSlotInInventoryOrHotbar();
            if (n == -1) {
                this.print("\u0423 \u0432\u0430\u0441 \u043d\u0435\u0442 \u043f\u043e\u043b\u0443\u0431\u043b\u043e\u043a\u043e\u0432 \u0432 \u0445\u043e\u0442\u0431\u0430\u0440\u0435!");
                this.toggle();
                return;
            }
            int n2 = LongJump.mc.player.inventory.currentItem;
            RayTraceResult rayTraceResult = MouseUtil.rayTraceResult(2.0, LongJump.mc.player.rotationYaw, 90.0f, LongJump.mc.player);
            if (rayTraceResult instanceof BlockRayTraceResult) {
                BlockRayTraceResult blockRayTraceResult = (BlockRayTraceResult)rayTraceResult;
                if (MoveUtils.isMoving()) {
                    if ((double)LongJump.mc.player.fallDistance >= 0.8 && LongJump.mc.world.getBlockState(LongJump.mc.player.getPosition()).isAir() && !LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).isAir() && LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).isSolid() && !(LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock() instanceof SlabBlock) && !(LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock() instanceof StairsBlock)) {
                        LongJump.mc.player.inventory.currentItem = n;
                        this.placed = true;
                        LongJump.mc.playerController.processRightClickBlock(LongJump.mc.player, LongJump.mc.world, Hand.MAIN_HAND, blockRayTraceResult);
                        LongJump.mc.player.inventory.currentItem = n2;
                        LongJump.mc.player.fallDistance = 0.0f;
                    }
                    LongJump.mc.gameSettings.keyBindJump.pressed = false;
                    if (LongJump.mc.player.isOnGround() && !LongJump.mc.gameSettings.keyBindJump.pressed && this.placed && LongJump.mc.world.getBlockState(LongJump.mc.player.getPosition()).isAir() && !LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).isAir() && LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).isSolid() && !(LongJump.mc.world.getBlockState(blockRayTraceResult.getPos()).getBlock() instanceof SlabBlock) && this.stopWatch.isReached(750L)) {
                        LongJump.mc.player.setPose(Pose.STANDING);
                        this.stopWatch.reset();
                        this.placed = false;
                    } else if (LongJump.mc.player.isOnGround() && !LongJump.mc.gameSettings.keyBindJump.pressed) {
                        LongJump.mc.player.jump();
                        this.placed = false;
                    }
                }
            } else if (LongJump.mc.player.isOnGround() && !LongJump.mc.gameSettings.keyBindJump.pressed) {
                LongJump.mc.player.jump();
                this.placed = false;
            }
        }
    }

    @Subscribe
    public void onMoving(MovingEvent movingEvent) {
    }

    @Subscribe
    public void onFlag(EventPacket eventPacket) {
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SPlayerPositionLookPacket) {
            SPlayerPositionLookPacket sPlayerPositionLookPacket = (SPlayerPositionLookPacket)iPacket;
            this.placed = false;
            this.counter = 0;
            LongJump.mc.player.setPose(Pose.STANDING);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.counter = 0;
        this.placed = false;
    }
}

