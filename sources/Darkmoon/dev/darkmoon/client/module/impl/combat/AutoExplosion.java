package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.event.misc.EventObsidianPlaced;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.misc.TimerHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(name = "AutoExplosion", category = Category.COMBAT)
public class AutoExplosion extends Module {
    public static BooleanSetting safely = new BooleanSetting("Explode Safely", false);
    public static BooleanSetting onlyOwn = new BooleanSetting("Explode Only Own", false);
    public static TimerHelper timerHelper = new TimerHelper();
    private final List<BlockPos> crystals = new ArrayList<>();

    @EventTarget
    public void onObsidian(EventObsidianPlaced event) {
        final int oldSlot = mc.player.inventory.currentItem;
        final BlockPos pos = event.getPos();
        int crystal = getSlotWithCrystal();
        if (crystal >= 0) {
            mc.player.inventory.currentItem = getSlotWithCrystal();
            mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.UP, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), EnumHand.MAIN_HAND);
            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.player.inventory.currentItem = oldSlot;
            crystals.add(new BlockPos(pos.getX(), pos.getY() + 1.5D, pos.getZ()));
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (crystals.size() >= 10) {
            crystals.remove(0);
        }

        for (Entity entity : mc.world.loadedEntityList) {
            if (!(entity instanceof EntityEnderCrystal)) continue;
            if (mc.player.getDistance(entity) <= 6 && !entity.isDead) {
                if (onlyOwn.get() && !crystals.contains(entity.getPosition())) continue;
                if ((mc.player.posY >= entity.posY || !mc.player.onGround) && safely.get()) continue;
                if (timerHelper.hasReached(200)) {
                    mc.playerController.attackEntity(mc.player, entity);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    timerHelper.reset();
                }
            }
        }
    }

    public int getSlotIDFromItem(Item item) {
        int slot = -1;
        for (int i = 0; i < 36; i++) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() == item) {
                slot = i;
                break;
            }
        }
        if (slot < 9 && slot != -1) {
            slot = slot + 36;
        }
        return slot;
    }

    private int getSlotWithCrystal() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemEndCrystal) {
                return i;
            }
        }
        return -1;
    }
}
