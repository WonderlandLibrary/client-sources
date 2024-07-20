/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventAirCube;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.InventoryHelper;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class Spider
extends Module {
    TimerHelper timer = new TimerHelper();
    boolean sneak = false;
    boolean callPlace = false;

    public Spider() {
        super("Spider", 0, Module.Category.MOVEMENT);
        this.settings.add(new Settings("Modes", "MatrixOld", (Module)this, new String[]{"MatrixOld", "MatrixNew", "Region", "GrimAc"}));
        this.settings.add(new Settings("Fast", true, (Module)this, () -> this.currentMode("Modes").equalsIgnoreCase("Region")));
    }

    boolean isBadOver(BlockPos pos) {
        Block block = pos == null ? null : Spider.mc.world.getBlockState(pos).getBlock();
        List<Integer> badBlockIDs = Arrays.asList(96, 167, 54, 130, 146, 58, 64, 71, 193, 194, 195, 196, 197, 324, 330, 427, 428, 429, 430, 431, 154, 61, 23, 158, 145, 69, 107, 187, 186, 185, 184, 183, 107, 116, 84, 356, 404, 151, 25, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 389, 379, 380, 138, 321, 323, 77, 143, 379);
        return !Minecraft.player.isSneaking() && block != null && badBlockIDs.stream().anyMatch(id -> Block.getIdFromBlock(block) == id);
    }

    @Override
    public void onMovement() {
        if (this.currentMode("Modes").equalsIgnoreCase("GrimAc") && Minecraft.player.motionY < -0.07) {
            if (InventoryHelper.doesHotbarHaveBlock() && Spider.mc.playerController.getCurrentGameType() != GameType.ADVENTURE && Spider.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
                boolean badOver;
                EnumFacing enumFace;
                int toSlot;
                BlockPos pos = new BlockPos(Minecraft.player.posX + (Minecraft.player.posX - Minecraft.player.lastReportedPosX), Minecraft.player.posY - 0.9, Minecraft.player.posZ + (Minecraft.player.posZ - Minecraft.player.lastReportedPosZ));
                int slot = Minecraft.player.inventory.currentItem;
                Minecraft.player.inventory.currentItem = toSlot = InventoryUtil.getAnyBlockInHotbar();
                if (slot != toSlot) {
                    Spider.mc.playerController.syncCurrentPlayItem();
                }
                if ((enumFace = BlockUtils.getPlaceableSide(pos)) == null) {
                    return;
                }
                Vec3d placeFaceVec = new Vec3d(pos).addVector(0.5, 0.5, 0.5).addVector((double)enumFace.getFrontOffsetX() * 0.5, (double)enumFace.getFrontOffsetY() * 0.5, (double)enumFace.getFrontOffsetZ() * 0.5);
                float[] rotate = RotationUtil.getNeededFacing(placeFaceVec, false, Minecraft.player, false);
                float prevYaw = Minecraft.player.rotationYaw;
                float prevPitch = Minecraft.player.rotationPitch;
                Minecraft.player.rotationYaw = rotate[0];
                Minecraft.player.rotationPitch = rotate[1];
                Minecraft.player.rotationYawHead = rotate[0];
                Minecraft.player.renderYawOffset = rotate[0];
                Minecraft.player.rotationPitchHead = rotate[1];
                Spider.mc.entityRenderer.getMouseOver(0.0f);
                boolean bl = badOver = Spider.mc.objectMouseOver.getBlockPos() != null && Spider.mc.objectMouseOver.getBlockPos() != null && this.isBadOver(Spider.mc.objectMouseOver.getBlockPos()) && !Minecraft.player.isSneaking();
                if (badOver) {
                    Spider.mc.gameSettings.keyBindSneak.pressed = true;
                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                } else if (Spider.mc.objectMouseOver != null && Spider.mc.objectMouseOver.getBlockPos() != null) {
                    if (!Spider.mc.playerController.processRightClickBlock(Minecraft.player, Spider.mc.world, Spider.mc.objectMouseOver.getBlockPos(), Spider.mc.objectMouseOver.sideHit, Spider.mc.objectMouseOver.hitVec, EnumHand.MAIN_HAND).equals((Object)EnumActionResult.SUCCESS)) {
                        Spider.mc.playerController.processRightClickBlock(Minecraft.player, Spider.mc.world, pos.offset(enumFace), enumFace.getOpposite(), placeFaceVec, EnumHand.MAIN_HAND);
                    }
                    Minecraft.player.swingArm();
                    Spider.mc.gameSettings.keyBindSneak.pressed = false;
                }
                Minecraft.player.rotationYaw = prevYaw;
                Minecraft.player.rotationPitch = prevPitch;
                Spider.mc.entityRenderer.getMouseOver(0.0f);
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(MoveMeHelp.getCuttingSpeed() / (double)1.4f / (double)1.06f);
                Minecraft.player.inventory.currentItem = slot;
                Minecraft.player.fallDistance = 0.0f;
                this.callPlace = false;
            } else {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSpider\u00a7r\u00a77]: " + this.trouble() + ".", false);
                this.toggle(false);
            }
        }
    }

    @Override
    public void onUpdate() {
        if (this.currentMode("Modes").equalsIgnoreCase("MatrixOld")) {
            Minecraft.player.jumpTicks = 0;
            if (Minecraft.player.isCollidedHorizontally && Minecraft.player.ticksExisted % 4 == 0) {
                Minecraft.player.onGround = true;
                Minecraft.player.jump();
                Minecraft.player.motionY = 0.42;
            }
        }
    }

    String trouble() {
        if (Spider.mc.playerController.getCurrentGameType() == GameType.ADVENTURE) {
            return "\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSpider\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u0433\u043c 2.";
        }
        if (Spider.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
            return "\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSpider\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u0433\u043c 3.";
        }
        if (!InventoryHelper.doesHotbarHaveBlock()) {
            return "\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSpider\u00a7r\u00a77]: \u0423 \u0432\u0430\u0441 \u043d\u0435\u0442 \u0431\u043b\u043e\u043a\u0430.";
        }
        return "\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSpider\u00a7r\u00a77]: \u0427\u0442\u043e-\u0442\u043e \u043d\u0435 \u0442\u0430\u043a...";
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (this.actived && this.currentMode("Modes").equalsIgnoreCase("GrimAc")) {
            EventPlayerMotionUpdate.yaw = Minecraft.player.rotationYawHead;
            EventPlayerMotionUpdate.pitch = Minecraft.player.rotationPitchHead;
        }
        if (this.currentMode("Modes").equalsIgnoreCase("Region") && this.actived) {
            if (Spider.mc.playerController.getCurrentGameType() == GameType.ADVENTURE || Spider.mc.playerController.getCurrentGameType() == GameType.SPECTATOR || !InventoryHelper.doesHotbarHaveBlock()) {
                this.toggle(false);
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7lSpider\u00a7r\u00a77]: " + this.trouble() + ".", false);
            } else {
                boolean isRW;
                int block = -1;
                for (int i = 0; i < 9; ++i) {
                    ItemStack s = Minecraft.player.inventory.getStackInSlot(i);
                    if (!(s.getItem() instanceof ItemBlock)) continue;
                    block = i;
                    break;
                }
                boolean bl = isRW = !mc.isSingleplayer() && mc.getCurrentServerData() != null && Spider.mc.getCurrentServerData().serverIP != null && Spider.mc.getCurrentServerData().serverIP.equalsIgnoreCase("mc.reallyworld.ru");
                if (block != -1) {
                    int slot = Minecraft.player.inventory.currentItem;
                    if (isRW && Spider.mc.currentScreen == null && Spider.mc.gameSettings.keyBindSneak.pressed && Minecraft.player.isSneaking() && this.timer.hasReached(50.0) && Minecraft.player.isCollidedHorizontally) {
                        try {
                            if (block != -1 && Spider.mc.objectMouseOver != null && Spider.mc.objectMouseOver.hitVec != null && Spider.mc.objectMouseOver.getBlockPos() != null && Spider.mc.objectMouseOver.sideHit != null) {
                                if (isRW) {
                                    Minecraft.player.inventory.currentItem = block;
                                    Spider.mc.playerController.syncCurrentPlayItem();
                                } else {
                                    Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(block));
                                }
                                float prevPitch = Minecraft.player.rotationPitch;
                                Minecraft.player.rotationPitch = -60.0f;
                                Spider.mc.entityRenderer.getMouseOver(1.0f);
                                Vec3d facing = Spider.mc.objectMouseOver.hitVec;
                                BlockPos stack = Spider.mc.objectMouseOver.getBlockPos();
                                float f = (float)(facing.xCoord - (double)stack.getX());
                                float f1 = (float)(facing.yCoord - (double)stack.getY());
                                float f2 = (float)(facing.zCoord - (double)stack.getZ());
                                Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, BlockPos.ORIGIN.down(61), EnumFacing.UP));
                                Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(stack, Spider.mc.objectMouseOver.sideHit, EnumHand.MAIN_HAND, f, f1, f2));
                                Minecraft.player.rotationPitch = prevPitch;
                                Spider.mc.entityRenderer.getMouseOver(1.0f);
                                if (!isRW) {
                                    Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
                                }
                                e.ground = true;
                                Minecraft.player.onGround = true;
                                Minecraft.player.isCollidedVertically = true;
                                Minecraft.player.isCollidedHorizontally = true;
                                Minecraft.player.isAirBorne = true;
                                Minecraft.player.motionY = this.currentBooleanValue("Fast") ? 0.5 : 0.4198;
                                this.timer.reset();
                            }
                        } catch (Exception o) {
                            o.printStackTrace();
                        }
                        if (isRW) {
                            Minecraft.player.inventory.currentItem = slot;
                        }
                    }
                }
            }
        }
        if (this.currentMode("Modes").equalsIgnoreCase("MatrixNew") && this.actived && Minecraft.player.isCollidedHorizontally && !Minecraft.player.isInWater() && !Minecraft.player.isInLava() && !Minecraft.player.isInWeb) {
            e.x += Math.sin(Math.toRadians(Minecraft.player.rotationYaw)) * 1.0E-11;
            e.z -= Math.cos(Math.toRadians(Minecraft.player.rotationYaw)) * 1.0E-11;
            boolean bl = Minecraft.player.isAirBorne = !Minecraft.player.onGround;
            if (Entity.Getmotiony < 0.0) {
                Entity.motiony = 1.4E-45f;
            }
            if (Minecraft.player.ticksExisted % 2 == 0) {
                e.ground = true;
                Minecraft.player.motionY = 0.42;
            }
        }
    }

    boolean canCollideDown() {
        return this.currentMode("Modes").equalsIgnoreCase("GrimAc");
    }

    @EventTarget
    public void onAir(EventAirCube event) {
        if (!this.actived || !this.currentMode("Modes").equalsIgnoreCase("GrimAc") || Minecraft.player.motionY < -0.15) {
            // empty if block
        }
    }

    @Override
    public void onToggled(boolean actived) {
        if (!actived && this.currentMode("Modes").equalsIgnoreCase("MatrixNew")) {
            Minecraft.player.speedInAir = 0.02f;
            Spider.mc.timer.speed = 1.0;
            Minecraft.player.stepHeight = 0.6f;
        }
        if (!actived && this.currentMode("Modes").equalsIgnoreCase("Grim")) {
            Spider.mc.gameSettings.keyBindSneak.pressed = false;
        }
        super.onToggled(actived);
    }
}

