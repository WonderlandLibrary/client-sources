package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.utils.BlockUtil;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

public final class DoubleAnchor extends Module implements TickListener {
    private BlockPos targetBlockPos;
    private int interactionCount;

    public DoubleAnchor() {
        super("Double Anchor", "Helps you do the air place/double anchor", 0, Category.COMBAT);
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.targetBlockPos = null;
        this.interactionCount = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (this.mc.currentScreen == null) {
            if (this.mc.player == null) {
                throw new AssertionError();
            }
            if (this.mc.player.getMainHandStack().isOf(Items.RESPAWN_ANCHOR)) {
                if (this.mc.world == null) {
                    throw new AssertionError();
                }
                HitResult target = this.mc.crosshairTarget;
                if (target instanceof BlockHitResult blockHitResult) {
                    if (BlockUtil.canExplodeAnchor(blockHitResult.getBlockPos()) && GLFW.glfwGetMouseButton(this.mc.getWindow().getHandle(), 1) == 1) {
                        if (blockHitResult.getBlockPos().equals(this.targetBlockPos)) {
                            if (this.interactionCount >= 1) {
                                return;
                            }
                        } else {
                            this.targetBlockPos = blockHitResult.getBlockPos();
                            this.interactionCount = 0;
                        }
                        this.mc.getNetworkHandler().sendPacket(new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, blockHitResult, 0));
                        ++this.interactionCount;
                    }
                }
            }
        }
    }
}
