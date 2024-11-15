// Decompiled by the rizzer xd

package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.events.PacketReceiveEvent;
import dev.lvstrng.argon.event.events.Render3DEvent;
import dev.lvstrng.argon.event.listeners.PacketReceiveListener;
import dev.lvstrng.argon.event.listeners.Render3DListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.RenderUtil;
import dev.lvstrng.argon.utils.TargetUtil;
import net.minecraft.block.entity.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;

import java.awt.*;
import java.util.Iterator;

public final class StorageESP extends Module implements Render3DListener, PacketReceiveListener {
    private final IntSetting alphaSetting;
    private final BooleanSetting donutBypassSetting;
    private final BooleanSetting tracersSetting;

    public StorageESP() {
        super("Storage ESP", "Renders storage blocks through walls", 0, Category.RENDER);
        this.alphaSetting = new IntSetting("Alpha", 1.0, 255.0, 125.0, 1.0);
        this.donutBypassSetting = new BooleanSetting("Donut Bypass", false);
        this.tracersSetting = new BooleanSetting("Tracers", false).setDescription("Draws a line from your player to the storage block");
        this.addSettings(new Setting[]{alphaSetting, donutBypassSetting, tracersSetting});
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(PacketReceiveListener.class, this);
        this.eventBus.registerPriorityListener(Render3DListener.class, this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.unregister(PacketReceiveListener.class, this);
        this.eventBus.unregister(Render3DListener.class, this);
        super.onDisable();
    }

    @Override
    public void onRender3D(final Render3DEvent event) {
        renderStorageBlocks(event);
    }

    // sponsor: argon client
    private Color getBlockColor(final BlockEntity blockEntity, final int alpha) {
        if (blockEntity instanceof TrappedChestBlockEntity) {
            return new Color(200, 91, 0, alpha);
        } else if (blockEntity instanceof ChestBlockEntity) {
            return new Color(156, 91, 0, alpha);
        } else if (blockEntity instanceof EnderChestBlockEntity) {
            return new Color(117, 0, 255, alpha);
        } else if (blockEntity instanceof MobSpawnerBlockEntity) {
            return new Color(138, 126, 166, alpha);
        } else if (blockEntity instanceof ShulkerBoxBlockEntity) {
            return new Color(134, 0, 158, alpha);
        } else if (blockEntity instanceof FurnaceBlockEntity) {
            return new Color(125, 125, 125, alpha);
        } else if (blockEntity instanceof BarrelBlockEntity) {
            return new Color(255, 140, 140, alpha);
        } else if (blockEntity instanceof EnchantingTableBlockEntity) {
            return new Color(80, 80, 255, alpha);
        }
        return new Color(255, 255, 255, 0);
    }

    private void renderStorageBlocks(final Render3DEvent event) {
        Camera camera = this.mc.gameRenderer.getCamera();
        if (camera == null) {
            return;
        }

        MatrixStack matrixStack = event.matrices;
        matrixStack.push();
        Vec3d cameraPosition = camera.getPos();
        matrixStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

        Iterator<WorldChunk> chunksIterator = TargetUtil.chunks().toList().iterator();
        while (chunksIterator.hasNext()) {
            WorldChunk chunk = chunksIterator.next();
            for (BlockPos blockPos : chunk.getBlockEntityPositions()) {
                BlockEntity blockEntity = this.mc.world.getBlockEntity(blockPos);
                renderBlockEntity(matrixStack, blockPos, blockEntity);
            }
        }
        matrixStack.pop();
    }

    private void renderBlockEntity(MatrixStack matrixStack, BlockPos blockPos, BlockEntity blockEntity) {
        RenderUtil.method428(
                matrixStack,
                blockPos.getX() + 0.1f,
                blockPos.getY() + 0.05f,
                blockPos.getZ() + 0.1f,
                blockPos.getX() + 0.9f,
                blockPos.getY() + 0.85f,
                blockPos.getZ() + 0.9f,
                getBlockColor(blockEntity, alphaSetting.getValueInt())
        );

        if (tracersSetting.getValue()) {
            Vec3d entityPosition = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
            RenderUtil.method429(matrixStack, getBlockColor(blockEntity, 255), this.mc.crosshairTarget.getPos(), entityPosition);
        }
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (donutBypassSetting.getValue() && event.packet instanceof ChunkDeltaUpdateS2CPacket) {
            event.cancelEvent();
        }
    }
}
