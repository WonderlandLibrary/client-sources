/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventCanPlaceBlock;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.ElytraBoost;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Command.impl.Clip;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class HighJump
extends Module {
    public static HighJump get;
    public Settings GroundJump;
    public Settings JumpMode;
    public Settings JumpPulse;
    public Settings MotionY;
    public Settings WaterJump;
    public Settings WaterPulse;
    public Settings WaterYPort;
    public Settings CactusJump;
    public Settings CactusPulse;
    public Settings CactusVastum;
    public Settings WebJump;
    public Settings WebPulse;
    public Settings WebBoost;
    public Settings SelfWebPlace;
    TimerHelper timer = new TimerHelper();
    TimerHelper wait = new TimerHelper();
    boolean toDo = false;
    int popitka = 0;
    double togglePos;
    BlockPos state;
    public static boolean toPlace;
    boolean doPlace = false;
    int ticksLeft = 0;

    public HighJump() {
        super("HighJump", 0, Module.Category.MOVEMENT);
        this.GroundJump = new Settings("GroundJump", true, (Module)this);
        this.settings.add(this.GroundJump);
        this.JumpMode = new Settings("JumpMode", "MatrixNew", this, new String[]{"Normal", "MatrixOld", "MatrixNew", "MatrixWait", "MatrixDestruct"}, () -> this.GroundJump.bValue);
        this.settings.add(this.JumpMode);
        this.JumpPulse = new Settings("JumpPulse", 1.0f, 10.0f, 0.0f, this, () -> this.GroundJump.bValue && (this.JumpMode.currentMode.equalsIgnoreCase("Normal") || this.JumpMode.currentMode.equalsIgnoreCase("MatrixWait")));
        this.settings.add(this.JumpPulse);
        this.MotionY = new Settings("MotionY", 3.0f, 10.0f, 0.6f, this, () -> this.GroundJump.bValue && (this.JumpMode.currentMode.equalsIgnoreCase("MatrixNew") || this.JumpMode.currentMode.equalsIgnoreCase("MatrixDestruct")));
        this.settings.add(this.MotionY);
        this.WaterJump = new Settings("WaterJump", false, (Module)this);
        this.settings.add(this.WaterJump);
        this.WaterPulse = new Settings("WaterPulse", 10.0f, 16.0f, 0.0f, this, () -> this.WaterJump.bValue);
        this.settings.add(this.WaterPulse);
        this.WaterYPort = new Settings("WaterYPort", true, (Module)this, () -> this.WaterJump.bValue);
        this.settings.add(this.WaterYPort);
        this.CactusJump = new Settings("CactusJump", true, (Module)this);
        this.settings.add(this.CactusJump);
        this.CactusPulse = new Settings("CactusPulse", 3.0f, 10.0f, 0.0f, this, () -> this.CactusJump.bValue);
        this.settings.add(this.CactusPulse);
        this.CactusVastum = new Settings("CactusVastum", false, (Module)this, () -> this.CactusJump.bValue);
        this.settings.add(this.CactusVastum);
        this.WebJump = new Settings("WebJump", true, (Module)this);
        this.settings.add(this.WebJump);
        this.WebPulse = new Settings("WebPulse", 3.0f, 10.0f, 0.0f, this, () -> this.WebJump.bValue);
        this.settings.add(this.WebPulse);
        this.WebBoost = new Settings("WebBoost", false, (Module)this, () -> this.WebJump.bValue);
        this.settings.add(this.WebBoost);
        this.SelfWebPlace = new Settings("SelfWebPlace", true, (Module)this, () -> this.WebJump.bValue);
        this.settings.add(this.SelfWebPlace);
        get = this;
    }

    private boolean stackIsBlockStack(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemBlock;
    }

    private void selfPlace(EnumHand hand) {
        double y = Minecraft.player.posY;
        for (double offset : new double[]{0.42f, 0.7531999805212024, 1.0013359791121417, 1.1661092609382138}) {
            mc.getConnection().sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, y + offset, Minecraft.player.posZ, false));
            Minecraft.player.setPosition(Minecraft.player.posX, y + offset, Minecraft.player.posZ);
        }
        float prevPitch = Minecraft.player.rotationPitch;
        Minecraft.player.rotationPitch = 90.0f;
        HighJump.mc.entityRenderer.getMouseOver(1.0f);
        if (HighJump.mc.objectMouseOver != null && HighJump.mc.objectMouseOver.getBlockPos() != null && HighJump.mc.objectMouseOver.hitVec != null && HighJump.mc.objectMouseOver.sideHit != null) {
            HighJump.mc.playerController.processRightClickBlock(Minecraft.player, HighJump.mc.world, HighJump.mc.objectMouseOver.getBlockPos(), HighJump.mc.objectMouseOver.sideHit, HighJump.mc.objectMouseOver.hitVec, hand);
        }
        Minecraft.player.rotationPitch = prevPitch;
        HighJump.mc.entityRenderer.getMouseOver(1.0f);
    }

    private void sentBlockPlacement(boolean canUseInventory) {
        int oldSlot = Minecraft.player.inventory.currentItem;
        int currentSlot = -1;
        EnumHand placeHand = null;
        ItemStack offStack = Minecraft.player.getHeldItemOffhand();
        if (this.stackIsBlockStack(offStack)) {
            placeHand = EnumHand.OFF_HAND;
        } else {
            ItemStack mainStack = Minecraft.player.getHeldItemMainhand();
            if (this.stackIsBlockStack(mainStack)) {
                placeHand = EnumHand.MAIN_HAND;
            }
        }
        if (placeHand == null) {
            for (int slot = 0; slot < (canUseInventory ? 44 : 8); ++slot) {
                ItemStack stackInSlot = Minecraft.player.inventory.getStackInSlot(slot);
                if (!this.stackIsBlockStack(stackInSlot)) continue;
                currentSlot = slot;
                placeHand = EnumHand.MAIN_HAND;
                break;
            }
            if (placeHand == EnumHand.MAIN_HAND && currentSlot != -1) {
                if (currentSlot <= 8) {
                    Minecraft.player.inventory.currentItem = currentSlot;
                    HighJump.mc.playerController.syncCurrentPlayItem();
                } else {
                    HighJump.mc.playerController.windowClick(0, currentSlot, oldSlot, ClickType.SWAP, Minecraft.player);
                    HighJump.mc.playerController.windowClickMemory(0, currentSlot, oldSlot, ClickType.SWAP, Minecraft.player, 50);
                }
                this.selfPlace(placeHand);
                if (currentSlot <= 8) {
                    Minecraft.player.inventory.currentItem = oldSlot;
                    HighJump.mc.playerController.syncCurrentPlayItem();
                }
                return;
            }
        }
        if (placeHand != null) {
            this.selfPlace(placeHand);
        }
    }

    private void onEnableMatrixDestructPulse() {
        if (this.MotionY.fValue == 0.0f || !Minecraft.player.onGround) {
            if (!Minecraft.player.onGround) {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.getName() + "\u00a7r\u00a77] \u00a77\u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0435\u0441\u044c \u043d\u0430 \u0437\u0435\u043c\u043b\u0435.", false);
            }
            if (this.MotionY.fValue == 0.0f) {
                Client.msg("\u00a7f\u00a7lModules:\u00a7r \u00a77[\u00a7l" + this.getName() + "\u00a7r\u00a77] \u00a77\u043d\u0430\u0441\u0440\u043e\u0439\u043a\u0430 \u0438\u0437\u043b\u0438\u0448\u043d\u0435 \u043c\u0430\u043b\u0430.", false);
            }
            this.toggle(false);
            return;
        }
        this.sentBlockPlacement(true);
        Minecraft.player.motionY = this.MotionY.fValue;
        Minecraft.player.jumpMovementFactor = 0.0f;
        Minecraft.player.multiplyMotionXZ(0.0f);
        this.toggle(false);
    }

    @EventTarget
    public void onPacketReceive(EventReceivePacket event) {
        SPacketChat packet;
        Packet packet2;
        if (!this.actived || Minecraft.player == null) {
            return;
        }
        if (this.GroundJump.bValue && this.JumpMode.currentMode.equalsIgnoreCase("MatrixWait") && (packet2 = event.getPacket()) instanceof SPacketChat && (packet = (SPacketChat)packet2).getChatComponent().getUnformattedText().contains("\u0418\u0437\u0432\u0438\u043d\u0438\u0442\u0435, \u043d\u043e \u0432\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435")) {
            this.toDo = true;
            event.setCancelled(true);
        }
    }

    @Override
    public void onUpdate() {
        if (this.actived) {
            if (this.GroundJump.bValue) {
                this.groundJump();
            }
            if (this.CactusJump.bValue) {
                this.cactusJump();
            }
            if (this.WaterJump.bValue) {
                this.waterLeave();
            }
            if (this.WebJump.bValue) {
                this.webLeave();
            }
        }
    }

    void groundJump() {
        if (this.JumpMode.currentMode.equalsIgnoreCase("MatrixNew")) {
            float jump = this.MotionY.fValue;
            float speed = 0.0f;
            MoveMeHelp.setSpeed(0.0);
            MoveMeHelp.setCuttingSpeed(0.0);
            Minecraft.player.connection.sendPacket(new CPacketPlayer(true));
            if (Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                Minecraft.player.motionY = 0.42;
            }
            new Thread(() -> {
                if (this.togglePos == 0.0) {
                    this.togglePos = Minecraft.player.posY;
                }
                Minecraft.player.motionY = jump;
                HighJump.mc.timer.speed = 1.9;
                try {
                    TimeUnit.MILLISECONDS.sleep(75L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Minecraft.player.motionY = (double)jump - (0.098 + 0.01 * (double)(jump * 2.0f - 2.0f));
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(1.0E-45);
                Minecraft.player.jumpMovementFactor = 0.0f;
                HighJump.mc.timer.speed = 1.0;
                if (this.actived) {
                    this.toggle(false);
                }
                if (this.togglePos != 0.0) {
                    Minecraft.player.posY = this.togglePos;
                }
            }).start();
        }
        if (this.JumpMode.currentMode.equalsIgnoreCase("MatrixOld") && HighJump.mc.gameSettings.keyBindJump.isKeyDown()) {
            Minecraft.player.jump();
            MoveMeHelp.setSpeed(0.0);
            Minecraft.player.motionY += 0.55;
            HighJump.mc.timer.speed = 1.45f;
        }
        if (this.JumpMode.currentMode.equalsIgnoreCase("MatrixWait")) {
            boolean has;
            boolean bl = has = this.state != null && this.wait.hasReached(250.0f + HighJump.mc.world.getBlockState(this.state).getBlockHardness(HighJump.mc.world, this.state) * 1400.0f);
            if (this.state != null && has && (Minecraft.player.onGround || JesusSpeed.isJesused) || this.toDo && Minecraft.player.onGround) {
                Minecraft.player.motionY = this.currentFloatValue("JumpPulse");
                MoveMeHelp.setSpeed(0.0);
                MoveMeHelp.setCuttingSpeed(0.0);
                Minecraft.player.jumpMovementFactor = 0.0f;
                this.wait.reset();
                this.toDo = false;
            }
            if (Minecraft.player.motionY > 0.43 || Minecraft.player.motionY < -0.6) {
                Minecraft.player.jumpMovementFactor = 0.0f;
                Minecraft.player.setSprinting(true);
            }
            CopyOnWriteArrayList<BlockPos> mixPoses = new CopyOnWriteArrayList<BlockPos>();
            Vec3d ePos = new Vec3d(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ);
            float r = 5.0f;
            for (float x = -5.0f; x < 5.0f; x += 1.0f) {
                for (float y = -5.0f; y < 0.0f; y += 1.0f) {
                    for (float z = -5.0f; z < 5.0f; z += 1.0f) {
                        BlockPos poss = new BlockPos((double)x + ePos.xCoord, (double)y + ePos.yCoord, (double)z + ePos.zCoord);
                        Block block = HighJump.mc.world.getBlockState(poss).getBlock();
                        if (block == Blocks.AIR || block == Blocks.BARRIER || block == Blocks.BEDROCK || poss == null || !(Minecraft.player.getDistanceAtEye(poss.getX(), poss.getY(), poss.getZ()) <= 5.0)) continue;
                        mixPoses.add(poss);
                    }
                }
            }
            if (mixPoses.size() != 0) {
                mixPoses.sort(Comparator.comparing(current -> Float.valueOf(HighJump.mc.world.getBlockState((BlockPos)current).getBlockHardness(HighJump.mc.world, (BlockPos)current))));
                this.state = (BlockPos)mixPoses.get(0);
                if (this.state != null && !this.toDo && !has) {
                    Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.state, EnumFacing.UP));
                    Minecraft.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.state, EnumFacing.UP));
                }
            }
        }
    }

    void cactusJump() {
        if (BlockCactus.canLeave && Minecraft.player.hurtTime > 0) {
            if (this.CactusVastum.bValue && Minecraft.player.onGround) {
                Minecraft.player.onGround = false;
                Minecraft.player.setPosition(Minecraft.player.posX + (double)1.0E-6f, Minecraft.player.posY + (double)(1000.0f * (this.CactusPulse.fValue / 10.0f)), Minecraft.player.posZ + (double)1.0E-6f);
                Minecraft.player.motionY = 0.0;
            } else {
                Minecraft.player.motionY = this.CactusPulse.fValue;
            }
            BlockCactus.canLeave = false;
        }
    }

    private final int getSlotWebInHotbar() {
        return InventoryUtil.getItemInHotbar(Item.getItemFromBlock(Blocks.WEB));
    }

    private final boolean haveWebInOffhand() {
        return Minecraft.player.getHeldItemOffhand().getItem() == Item.getItemFromBlock(Blocks.WEB);
    }

    private final boolean haveWebInInventory() {
        return this.haveWebInOffhand() || this.getSlotWebInHotbar() != -1;
    }

    boolean canPlaceWeb(double x, double y, double z) {
        return HighJump.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.AIR && !Speed.posBlock(x, y, z);
    }

    private final EnumFacing getPlaceableSide(BlockPos var0) {
        for (EnumFacing v3 : EnumFacing.values()) {
            IBlockState v5;
            BlockPos v4 = var0.offset(v3);
            if (!HighJump.mc.world.getBlockState(v4).getBlock().canCollideCheck(HighJump.mc.world.getBlockState(v4), false) || (v5 = HighJump.mc.world.getBlockState(v4)).getMaterial().isReplaceable()) continue;
            return v3;
        }
        return null;
    }

    private final boolean lookingAtPos(float yaw, float pitch, BlockPos pos, float range) {
        RayTraceResult zalupa = Minecraft.player.rayTraceCustom(range, mc.getRenderPartialTicks(), yaw, pitch);
        Vec3d hitVec = zalupa.hitVec;
        if (hitVec == null) {
            return false;
        }
        if (hitVec.xCoord - (double)pos.getX() > 1.0 || hitVec.xCoord - (double)pos.getX() < 0.0) {
            return false;
        }
        if (hitVec.yCoord - (double)pos.getY() > 1.0 || hitVec.yCoord - (double)pos.getY() < 0.0) {
            return false;
        }
        return !(hitVec.zCoord - (double)pos.getZ() > 1.0) && !(hitVec.zCoord - (double)pos.getZ() < 0.0);
    }

    private final void placeWeb(EnumHand hand, BlockPos currentPosToPlace) {
        if (currentPosToPlace == null) {
            return;
        }
        EnumFacing v4 = this.getPlaceableSide(currentPosToPlace);
        if (v4 == null) {
            return;
        }
        EnumFacing v2 = v4.getOpposite();
        BlockPos v1 = currentPosToPlace.offset(v4);
        Vec3d v3 = new Vec3d(v1).addVector(0.5, 0.5, 0.5).add(new Vec3d(v2.getDirectionVec()).scale(0.5));
        if (InventoryUtil.getItemInHotbar(Item.getItemFromBlock(Blocks.WEB)) != -1 && hand == EnumHand.MAIN_HAND && Minecraft.player.inventory.currentItem != InventoryUtil.getItemInHotbar(Item.getItemFromBlock(Blocks.WEB))) {
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(InventoryUtil.getItemInHotbar(Item.getItemFromBlock(Blocks.WEB))));
        }
        HighJump.mc.playerController.processRightClickBlock(Minecraft.player, HighJump.mc.world, v1, v2, v3, hand);
        if (InventoryUtil.getItemInHotbar(Item.getItemFromBlock(Blocks.WEB)) != -1 && hand == EnumHand.MAIN_HAND && Minecraft.player.inventory.currentItem != InventoryUtil.getItemInHotbar(Item.getItemFromBlock(Blocks.WEB))) {
            Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
        }
    }

    @EventTarget
    public void can(EventCanPlaceBlock event) {
        if (this.actived && this.doPlace) {
            double x = Minecraft.player.posX;
            int y = (int)Minecraft.player.posY;
            double z = Minecraft.player.posZ;
            this.placeWeb(this.haveWebInOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, new BlockPos(x, (double)y, z));
            this.doPlace = false;
        }
    }

    void webLeave() {
        float ex;
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (toPlace) {
            this.placeWeb(this.haveWebInOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, BlockUtils.getEntityBlockPos(Minecraft.player));
            toPlace = false;
        }
        if (Minecraft.player.isInWeb) {
            Minecraft.player.motionY = 0.0;
            MoveMeHelp.setSpeed(0.0);
            Minecraft.player.motionY += 1.98;
        }
        if (this.SelfWebPlace.bValue && this.haveWebInInventory() && this.canPlaceWeb(x, y, z)) {
            toPlace = true;
            ++this.ticksLeft;
            if (Minecraft.player.isCollidedVertically && Minecraft.player.onGround && !Minecraft.player.isJumping()) {
                Minecraft.player.jump();
            }
            if (this.ticksLeft == 1) {
                this.doPlace = true;
            } else if (this.ticksLeft < 1) {
                Minecraft.player.multiplyMotionXZ(0.0f);
                Minecraft.player.jumpMovementFactor = 0.0f;
            }
        }
        if ((HighJump.mc.world.getBlockState(new BlockPos(x, y - (double)(ex = 0.2f), z)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x - (double)0.3f, y - (double)ex, z)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x, y - (double)ex, z - (double)0.3f)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x - (double)0.3f, y - (double)ex, z - (double)0.3f)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x + (double)0.3f, y - (double)ex, z)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x, y - (double)ex, z + (double)0.3f)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x + (double)0.3f, y - (double)ex, z + (double)0.3f)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x + (double)0.3f, y - (double)ex, z - (double)0.3f)).getBlock() == Blocks.WEB || HighJump.mc.world.getBlockState(new BlockPos(x - (double)0.3f, y - (double)ex, z + (double)0.3f)).getBlock() == Blocks.WEB) && HighJump.mc.world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.WEB && Minecraft.player.fallDistance == 0.0f && !Minecraft.player.isInWeb && Minecraft.player.motionY < 0.0) {
            Minecraft.player.motionY = this.WebPulse.fValue;
            if (this.WebBoost.bValue) {
                MoveMeHelp.setSpeed(2.9f);
                MoveMeHelp.setCuttingSpeed(2.735849380493164);
            }
        }
    }

    public boolean waterLeaveCanSolid() {
        return Minecraft.player != null && get != null && this.actived && this.WaterJump.bValue;
    }

    void waterLeave() {
        double x = Minecraft.player.posX;
        double y = Minecraft.player.posY;
        double z = Minecraft.player.posZ;
        if (this.waterLeaveCanSolid() && Minecraft.player.onGround && Minecraft.player.posY == (double)((int)Minecraft.player.posY + 1) - 1.0E-5) {
            if (this.WaterYPort.bValue) {
                Clip.goClip(ElytraBoost.canElytra() ? 190.0 : 50.0, 0.0, ElytraBoost.canElytra());
                if (!ElytraBoost.canElytra()) {
                    Clip.goClip(50.0, 1.0, false);
                }
            } else {
                Minecraft.player.motionY = this.WaterPulse.fValue - 0.2f;
            }
        } else {
            boolean boost;
            boolean bl = boost = HighJump.mc.world.getBlockState(new BlockPos(x, y + 1.0, z)).getBlock() == Blocks.AIR && !(HighJump.mc.world.getBlockState(new BlockPos(x, y - 1.0E-10, z)).getBlock() instanceof BlockLiquid);
            if (HighJump.mc.world.getBlockState(new BlockPos(x, y + 0.1, z)).getBlock() instanceof BlockLiquid && Minecraft.player.fallDistance == 0.0f) {
                double speedUp;
                HighJump.mc.gameSettings.keyBindJump.pressed = false;
                Minecraft.player.motionY = speedUp = boost ? 0.42 : 0.19;
                Entity.motiony = speedUp - 0.01;
                Minecraft.player.onGround = false;
            }
        }
    }

    @Override
    public void onToggled(boolean actived) {
        toPlace = false;
        this.ticksLeft = 0;
        this.doPlace = false;
        this.wait.reset();
        this.toDo = false;
        this.togglePos = this.actived ? Minecraft.player.posY : 0.0;
        BlockCactus.canLeave = false;
        if (!actived && this.GroundJump.bValue && this.JumpMode.currentMode.equalsIgnoreCase("MatrixOld") && HighJump.mc.timer.speed == (double)1.45f) {
            HighJump.mc.timer.speed = 1.0;
        }
        if (actived && this.JumpMode.currentMode.equalsIgnoreCase("MatrixDestruct")) {
            this.onEnableMatrixDestructPulse();
        }
        super.onToggled(actived);
    }

    static {
        toPlace = true;
    }
}

