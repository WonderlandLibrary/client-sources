/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;
import org.lwjgl.input.Mouse;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.event.events.EventReceivePacket;
import ru.govno.client.event.events.EventRotationJump;
import ru.govno.client.event.events.EventRotationStrafe;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.FreeCam;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.modules.JesusSpeed;
import ru.govno.client.module.modules.Speed;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.RotationUtil;
import ru.govno.client.utils.Math.BlockUtils;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Movement.MoveMeHelp;

public class PotionThrower
extends Module {
    public static PotionThrower get;
    public boolean forceThrow;
    public boolean callThrowPotions;
    private final TimerHelper timerThrow = new TimerHelper();
    private final List<ClickMemory> clickMemories = new ArrayList<ClickMemory>();
    private final TimerHelper timerClickMemories = new TimerHelper();
    private List<Potions> toThrowList = new ArrayList<Potions>();
    private boolean canThrow;
    float[] rotate;

    public PotionThrower() {
        super("PotionThrower", 0, Module.Category.COMBAT);
        this.settings.add(new Settings("TicksFromSpawn", 60.0f, 200.0f, 0.0f, this));
        this.settings.add(new Settings("ThrowDelay", 750.0f, 1000.0f, 200.0f, this));
        this.settings.add(new Settings("DisableOnThrow", false, (Module)this));
        this.settings.add(new Settings("OnlyInPVP", false, (Module)this));
        this.settings.add(new Settings("OnSnackFloorSwing", false, (Module)this));
        this.settings.add(new Settings("RefillHotbar", false, (Module)this));
        this.settings.add(new Settings("HealthPotions", false, (Module)this));
        this.settings.add(new Settings("MinHealth", 14.0f, 20.0f, 4.0f, this, () -> this.currentBooleanValue("HealthPotions")));
        get = this;
    }

    public boolean isStackPotion(ItemStack stack, Potions potion) {
        if (stack == null) {
            return false;
        }
        Item item = stack.getItem();
        if (item == Items.SPLASH_POTION) {
            int id = 5;
            switch (potion) {
                case STRENGTH: {
                    id = 5;
                    break;
                }
                case SPEED: {
                    id = 1;
                    break;
                }
                case FIRERES: {
                    id = 12;
                    break;
                }
                case INSTANT_HEALTH: {
                    id = 6;
                    break;
                }
                case REGENERATION: {
                    id = 10;
                }
            }
            for (PotionEffect effect : PotionUtils.getEffectsFromStack(stack)) {
                if (effect.getPotion() != Potion.getPotionById(id)) continue;
                return true;
            }
        }
        return false;
    }

    private int getPotionSlot(Potions potion, boolean inInventory) {
        for (int i = inInventory ? 44 : 8; i >= (inInventory ? 8 : 0); --i) {
            if (!this.isStackPotion(Minecraft.player.inventory.getStackInSlot(i), potion)) continue;
            return i;
        }
        return -1;
    }

    private int getPotionId(Potions potion) {
        return potion == Potions.STRENGTH ? 5 : (potion == Potions.SPEED ? 1 : (potion == Potions.FIRERES ? 12 : (potion == Potions.REGENERATION ? 10 : (potion == Potions.INSTANT_HEALTH ? 6 : 5))));
    }

    private boolean isActivePotion(Potions potion) {
        Potion eff = Potion.getPotionById(this.getPotionId(potion));
        return Minecraft.player != null && Minecraft.player.isPotionActive(eff) && Minecraft.player.getActivePotionEffect(eff).getDuration() > 10;
    }

    private List<Potions> potsList(boolean healthPot, float healthMin) {
        if (healthPot && Minecraft.player.getHealth() < healthMin) {
            return Arrays.asList(Potions.SPEED, Potions.STRENGTH, Potions.FIRERES, Potions.REGENERATION, Potions.INSTANT_HEALTH);
        }
        return Arrays.asList(Potions.SPEED, Potions.STRENGTH, Potions.FIRERES, Potions.REGENERATION);
    }

    private List<Potions> potsListToThrow(boolean healthPot, float healthMin) {
        return this.potsList(healthPot, healthMin).stream().filter(potion -> this.getPotionSlot((Potions)((Object)potion), false) != -1 && !this.isActivePotion((Potions)((Object)potion))).collect(Collectors.toList());
    }

    private boolean inPvpTime() {
        return !GuiBossOverlay.mapBossInfos2.isEmpty() && GuiBossOverlay.mapBossInfos2.values().stream().map(BossInfo::getName).map(ITextComponent::getFormattedText).filter(name -> name.contains("PVP")).filter(Objects::nonNull).toArray().length != 0;
    }

    private boolean canThrowing(int ticksAlive, List<Potions> toThrow, float delay, boolean checkPvp, boolean onlySNP) {
        return !(toThrow.isEmpty() || !Minecraft.player.isCollidedVertically && !Minecraft.player.isCollidedHorizontally && !this.forceThrow && (!((double)Minecraft.player.fallDistance > 0.2) || PotionThrower.mc.world.getCollisionBoxes(Minecraft.player, Minecraft.player.boundingBox.offsetMinDown(1.0)).isEmpty()) || Minecraft.player.ticksExisted < ticksAlive || !this.timerThrow.hasReached(delay) || PotionThrower.mc.currentScreen != null && PotionThrower.mc.currentScreen instanceof GuiContainer && !(PotionThrower.mc.currentScreen instanceof GuiInventory) || !(MathUtils.getDifferenceOf(Entity.Getmotiony, 0.0) < 1.5) || FreeCam.get.actived || Minecraft.player.isHandActive() && Minecraft.player.getActiveHand() != null && Minecraft.player.getActiveHand().equals((Object)EnumHand.MAIN_HAND) || Minecraft.player.isElytraFlying() || PotionThrower.mc.playerController.getIsHittingBlock() && (double)PotionThrower.mc.playerController.curBlockDamageMP > 0.5 || JesusSpeed.get.actived && (JesusSpeed.isSwimming || JesusSpeed.isJesused) || Speed.get.actived && Speed.snowGround && Speed.snowGo || checkPvp && !this.inPvpTime() || onlySNP && (!Minecraft.player.isSneaking() || !(Minecraft.player.rotationPitch > 85.0f) || !Mouse.isButtonDown(0)));
    }

    private boolean hasEmptySlotsInHotbar() {
        for (int i = 0; i <= 8; ++i) {
            if (!(Minecraft.player.inventory.getStackInSlot(i).getItem() instanceof ItemAir)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate() {
        boolean reFill = this.currentBooleanValue("RefillHotbar") && !Minecraft.player.isCreative();
        this.toThrowList = this.potsListToThrow(this.currentBooleanValue("HealthPotions"), this.currentFloatValue("MinHealth"));
        this.canThrow = this.canThrowing((int)this.currentFloatValue("TicksFromSpawn"), this.toThrowList, this.currentFloatValue("ThrowDelay"), this.currentBooleanValue("OnlyInPVP"), this.currentBooleanValue("OnSnackFloorSwing"));
        if (!this.clickMemories.isEmpty()) {
            this.clickMemories.removeIf(ClickMemory::getToRemove);
            this.clickMemories.forEach(clickMemory -> {
                if (clickMemory != null && this.timerClickMemories.hasReached(100.0) && !clickMemory.getToRemove()) {
                    PotionThrower.mc.playerController.windowClick(0, clickMemory.slotTo, clickMemory.slotAt, ClickType.SWAP, Minecraft.player);
                    clickMemory.setToRemove();
                    this.timerClickMemories.reset();
                }
            });
        }
        if (this.callThrowPotions) {
            if (!this.toThrowList.isEmpty()) {
                int prevHandSlot;
                this.setHead(null, this.rotate[0], this.rotate[1]);
                int lastSlot = prevHandSlot = Minecraft.player.inventory.currentItem;
                for (Potions potion : this.toThrowList) {
                    int invPotionSlot;
                    boolean isStackedPotion;
                    int potionSlot = this.getPotionSlot(potion, false);
                    if (potionSlot == -1) continue;
                    boolean bl = isStackedPotion = Minecraft.player.inventory.getStackInSlot((int)potionSlot).stackSize > 1;
                    if (Minecraft.player.inventory.currentItem != potionSlot) {
                        Minecraft.player.inventory.currentItem = potionSlot;
                        PotionThrower.mc.playerController.syncCurrentPlayItem();
                        lastSlot = potionSlot;
                    }
                    Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    if (!reFill || isStackedPotion || (invPotionSlot = this.getPotionSlot(potion, true)) == -1) continue;
                    this.clickMemories.add(new ClickMemory(invPotionSlot, potionSlot));
                    this.timerClickMemories.reset();
                }
                if (prevHandSlot != lastSlot) {
                    Minecraft.player.inventory.currentItem = prevHandSlot;
                    PotionThrower.mc.playerController.syncCurrentPlayItem();
                }
                this.forceThrow = false;
            }
            if (this.currentBooleanValue("DisableOnThrow")) {
                this.toggle();
            }
            this.callThrowPotions = false;
        }
        super.onUpdate();
    }

    @Override
    public void onToggled(boolean actived) {
        if (actived) {
            this.forceThrow = false;
            this.callThrowPotions = false;
        } else {
            this.canThrow = false;
            this.forceThrow = false;
            this.callThrowPotions = false;
        }
        super.onToggled(actived);
    }

    private void setHead(EventPlayerMotionUpdate event, float yaw, float pitch) {
        Minecraft.player.rotationYawHead = yaw;
        Minecraft.player.renderYawOffset = yaw;
        Minecraft.player.rotationPitchHead = pitch;
        HitAura.get.rotationsCombat = new float[]{yaw, pitch};
        HitAura.get.rotationsVisual = HitAura.get.rotationsCombat;
        if (event == null) {
            return;
        }
        event.setYaw(yaw);
        event.setPitch(pitch);
    }

    private double getXVec(Vec3d vec) {
        return vec != null ? vec.xCoord : 0.0;
    }

    private double getYVec(Vec3d vec) {
        return vec != null ? vec.zCoord : 0.0;
    }

    private double getZVec(Vec3d vec) {
        return vec != null ? vec.zCoord : 0.0;
    }

    private Vec3d posToRotate() {
        ArrayList<Vec3d> nonAirList = new ArrayList<Vec3d>();
        float xzR = 1.15f;
        int yrP = 2;
        boolean yrM = true;
        double xMe = Minecraft.player.getPositionVector().xCoord;
        double yMe = Minecraft.player.getPositionVector().yCoord;
        double zMe = Minecraft.player.getPositionVector().zCoord;
        if (PotionThrower.mc.world == null) {
            return null;
        }
        for (double x = xMe - (double)xzR; x < xMe + (double)xzR; x += 1.0) {
            for (double z = zMe - (double)xzR; z < zMe + (double)xzR; z += 1.0) {
                for (double y = yMe + (double)yrP; y > yMe - (double)yrM; y -= 1.0) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (pos == null || !BlockUtils.blockMaterialIsCurrent(pos)) continue;
                    nonAirList.add(new Vec3d((double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5));
                }
            }
        }
        if (nonAirList != null && nonAirList.size() > 1) {
            nonAirList.sort(Comparator.comparing(nonAir -> -Minecraft.player.getDistanceAtEye(this.getXVec((Vec3d)nonAir), this.getYVec((Vec3d)nonAir), this.getZVec((Vec3d)nonAir), 0.8f)));
        }
        return nonAirList == null || nonAirList.size() == 0 ? null : (Vec3d)nonAirList.get(0);
    }

    private float calculatePitch() {
        double speed = MoveMeHelp.getSpeed();
        float max = 89.0f;
        double delta = MathUtils.clamp(Math.sqrt(speed * speed) / (double)0.55f, 0.0, 0.5) - MathUtils.clamp(Math.abs(Math.sqrt(Entity.Getmotiony * Entity.Getmotiony)) * 9.0, 0.0, 0.333333);
        return (float)(89.0 - (double)max * MathUtils.clamp(delta * 3.0, 0.0, 1.0));
    }

    private float[] getRotate(Vec3d to) {
        float moveYaw = MoveMeHelp.getCuttingSpeed() > 0.05 ? (float)MoveMeHelp.getMotionYaw() + 30.0f : Minecraft.player.rotationYaw;
        float[] rotate = new float[]{moveYaw, this.calculatePitch()};
        if (!MoveMeHelp.isBlockAboveHeadSolo() && MoveMeHelp.getSpeed() > 0.2 && Minecraft.player.onGround && to == null) {
            rotate = new float[]{moveYaw, this.calculatePitch()};
        } else if (MoveMeHelp.isBlockAboveHeadSolo()) {
            rotate = new float[]{moveYaw, -89.0f};
        } else if (to != null) {
            rotate = RotationUtil.getNeededFacing(to, false, Minecraft.player, false);
        }
        return rotate;
    }

    @EventTarget
    public void onPlayerUpdate(EventPlayerMotionUpdate event) {
        if (!this.canThrow) {
            return;
        }
        this.rotate = this.getRotate(this.posToRotate());
        if (this.rotate == null) {
            return;
        }
        if (!(Minecraft.player.isSneaking() && Minecraft.player.rotationPitch > 85.0f && Mouse.isButtonDown(0))) {
            this.setHead(event, this.rotate[0], this.rotate[1]);
        }
        this.timerThrow.reset();
        this.canThrow = false;
        this.callThrowPotions = true;
    }

    @EventTarget
    public void onSilentStrafe(EventRotationStrafe event) {
        if (this.canThrow && this.rotate != null) {
            event.setYaw(this.rotate[0]);
        }
    }

    @EventTarget
    public void onSilentJump(EventRotationJump event) {
        if (this.canThrow && this.rotate != null) {
            event.setYaw(this.rotate[0]);
        }
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        Packet packet = event.getPacket();
        if (packet instanceof SPacketEntityStatus) {
            SPacketEntityStatus status = (SPacketEntityStatus)packet;
            if (Minecraft.player != null && status.entityId == Minecraft.player.getEntityId() && status.getOpCode() == 35) {
                this.forceThrow = true;
            }
        }
    }

    private static enum Potions {
        STRENGTH,
        SPEED,
        FIRERES,
        INSTANT_HEALTH,
        REGENERATION;

    }

    private class ClickMemory {
        private final int slotTo;
        private final int slotAt;
        private boolean toRemove;

        private ClickMemory(int slotTo, int slotAt) {
            this.slotTo = slotTo;
            this.slotAt = slotAt;
        }

        private void setToRemove() {
            this.toRemove = true;
        }

        private boolean getToRemove() {
            return this.toRemove;
        }
    }
}

