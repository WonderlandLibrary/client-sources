package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.impl.player.FreeCam;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.MultiBooleanSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.math.RotationUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.move.BlockUtility;
import dev.darkmoon.client.utility.move.PacketUtility;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

@ModuleAnnotation(name = "AutoPotion", category = Category.COMBAT)
public class AutoPotion extends Module {
    public static MultiBooleanSetting potions = new MultiBooleanSetting("Potions", Arrays.asList("Strength", "Speed", "Fire Resistance"));
    public BooleanSetting pvpOnly = new BooleanSetting("PvP Only", false);
    public BooleanSetting autoDisable = new BooleanSetting("Auto Disable", false);
    private final PacketUtility packetUtility = new PacketUtility();
    public TimerHelper timerHelper = new TimerHelper();
    private int tempSlot;
    private float lastReportedPitch;
    private boolean flag;

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (isAnyPotionNeeded() && isNeeded() && lastReportedPitch == lastReportedPitch) {
            int currentSlot = mc.player.inventory.currentItem;
            tempSlot = -1;
            for (Potions potions : Potions.values()) {
                if (!potions.isNeeded()) continue;
                int usedSlot = usePotion(potions);
                if (tempSlot == -1) {
                    tempSlot = usedSlot;
                }
                flag = true;
            }
            if (tempSlot > 8) {
                mc.playerController.pickItem(tempSlot);
            }
            mc.player.connection.sendPacket(new CPacketHeldItemChange(currentSlot));
        }
        if (timerHelper.hasReached(500.0)) {
            reset();
            tempSlot = -2;
        }
        packetUtility.dE(tempSlot == -2);
        if (autoDisable.get() && flag && tempSlot == -2) {
            toggle();
            flag = false;
        }
    }

    @EventTarget
    public void onMotion(EventMotion event) {
        if (!isNeeded() || !isAnyPotionNeeded()) {
            return;
        }
        Vec3d clampedVec = getClampedVec();
        boolean isVecNull = clampedVec == null;
        float[] rotation;
        if (isVecNull) {
            rotation = new float[] { mc.player.rotationYaw, 90.0f };
        } else {
            rotation = RotationUtility.getCorrectRotation(clampedVec, false, 0.0f);
        }
        lastReportedPitch = (isVecNull ? 90.0f : rotation[1]);
        event.setYaw(rotation[0]);
        event.setPitch(lastReportedPitch);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        packetUtility.processPacket(event);
    }

    private Vec3d getClampedVec() {
        ArrayList<AxisAlignedBB> arrayList = new ArrayList<>(mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().grow(0.0, 0.5, 0.0)));
        Vec3d vec3d = mc.player.getPositionVector();
        arrayList.sort(Comparator.comparingDouble(axisAlignedBB -> mc.player.sqDistanceToPlayer(axisAlignedBB.clamp(vec3d))));
        return arrayList.isEmpty() ? null : arrayList.get(0).clamp(vec3d);
    }

    private boolean isNeeded() {
        if (DarkMoon.getInstance().getModuleManager().getModule(FreeCam.class).isEnabled()) {
            return false;
        } else if (pvpOnly.get() && mc.player.hurtTime <= 0) {
            return false;
        } else {
            return !BlockUtility.checkLiquid(0.5F) && !BlockUtility.isBlockUnder(0.5F);
        }
    }

    private boolean isNeeded(Potions potions) {
        if (mc.player.isPotionActive(potions.getPotion()) && mc.player.getActivePotionEffect(potions.getPotion()).getDuration() >= 200) {
            return false;
        }
        return this.getPotionFromInv(potions.getId()) != -1 || this.getPotionFromHotBar(potions.getId()) != -1;
    }

    private void reset() {
        for (Potions potions : Potions.values()) {
            if (!potions.isActive()) continue;
            potions.setNeeded(isNeeded(potions));
        }
    }

    private int usePotion(Potions potions) {
        int slot = getPotionFromHotBar(potions.getId());
        if (slot != -1) {
            this.packetUtility.setCurrentItem(mc.player.inventory.currentItem);
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.entityRenderer.itemRenderer.resetEquippedProgress(EnumHand.MAIN_HAND);
            potions.setNeeded(false);
            timerHelper.reset();
            return slot;
        }
        int slot2 = getPotionFromInv(potions.getId());
        if (slot2 != -1) {
            this.packetUtility.setCurrentItem(mc.player.inventory.currentItem);
            mc.playerController.pickItem(slot2);
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.entityRenderer.itemRenderer.resetEquippedProgress(EnumHand.MAIN_HAND);
            mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            potions.setNeeded(false);
            timerHelper.reset();
            return slot2;
        }
        return -1;
    }

    private int getPotionFromHotBar(int n) {
        for (int i = 0; i < 9; ++i) {
            for (PotionEffect potionEffect : PotionUtils.getEffectsFromStack(mc.player.inventory.getStackInSlot(i))) {
                if (potionEffect.getPotion() != Potion.getPotionById(n) || mc.player.inventory.getStackInSlot(i).getItem() != Items.SPLASH_POTION) continue;
                return i;
            }
        }
        return -1;
    }

    private int getPotionFromInv(int n) {
        for (int i = 9; i < 36; ++i) {
            for (PotionEffect potionEffect : PotionUtils.getEffectsFromStack(mc.player.inventory.getStackInSlot(i))) {
                if (potionEffect.getPotion() != Potion.getPotionById(n) || mc.player.inventory.getStackInSlot(i).getItem() != Items.SPLASH_POTION) continue;
                return i;
            }
        }
        return -1;
    }

    public boolean isAnyPotionNeeded() {
        for (Potions potions : Potions.values()) {
            if (!potions.isActive() || !potions.isNeeded()) continue;
            return true;
        }
        return false;
    }

    enum Potions {
        STRENGTH(MobEffects.STRENGTH, 5, 0),
        SPEED(MobEffects.SPEED, 1, 1),
        FIRE_RESISTANCE(MobEffects.FIRE_RESISTANCE, 12, 2);

        @Getter
        private final Potion potion;
        @Getter
        private final int id;
        private final int active;
        @Getter
        @Setter
        private boolean needed;

        public boolean isActive() {
            return potions.get(active);
        }

        Potions(Potion potion, int id, int active) {
            this.potion = potion;
            this.id = id;
            this.active = active;
        }
    }
}

