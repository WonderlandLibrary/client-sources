package me.travis.wurstplus.module.modules.combat;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.util.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;

@Module.Info(name="Travis Aura", category=Module.Category.COMBAT)
public class KillAura extends Module {
    private Setting<Boolean> attackPlayers = this.register(Settings.b("Players", true));
    private Setting<Double> hitRange = this.register(Settings.d("Hit Range", 5.5));
    private Setting<Integer> delay = this.register(Settings.integerBuilder("Delay").withMinimum(0).withValue(6).withMaximum(10).build());
    private Setting<Boolean> switchTo32k = this.register(Settings.b("32k Switch", true));
    private Setting<Boolean> onlyUse32k = this.register(Settings.b("32k Only", false));
    private int hasWaited;
    
    public KillAura() {
        this.hasWaited = 0;
    }
    
    public void onUpdate() {
        if (!this.isEnabled() || mc.player.isDead || mc.world == null) {
            return;
        }
        if (this.hasWaited < this.delay.getValue()) {
            ++this.hasWaited;
            return;
        }
        this.hasWaited = 0;
        for (final Entity entity : mc.world.loadedEntityList) {
            if (entity instanceof EntityLivingBase) {
                if (entity == mc.player) {
                    continue;
                }
                if (mc.player.getDistance(entity) > this.hitRange.getValue() || ((EntityLivingBase)entity).getHealth() <= 0.0f || (!(entity instanceof EntityPlayer) && this.attackPlayers.getValue()) || (entity instanceof EntityPlayer && Friends.isFriend(entity.getName())) || (!this.checkSharpness(mc.player.getHeldItemMainhand()) && this.onlyUse32k.getValue())) {
                    continue;
                }
                attack(entity);
            }
        }
    }
    
    private boolean checkSharpness(ItemStack item) {
        if (item.getTagCompound() == null) {
            return false;
        }
        NBTTagList enchants = (NBTTagList)item.getTagCompound().getTag("ench");
        if (enchants == null) {
            return false;
        }
        for (int i = 0; i < enchants.tagCount(); ++i) {
            NBTTagCompound enchant = enchants.getCompoundTagAt(i);
            if (enchant.getInteger("id") != 16) continue;
            int lvl = enchant.getInteger("lvl");
            if (lvl < 42) break;
            return true;
        }
        return false;
    }

    public void attack(Entity e) {
        boolean holding32k = false;
        if (this.checkSharpness(mc.player.getHeldItemMainhand())) {
            holding32k = true;
        }
        if (this.switchTo32k.getValue().booleanValue() && !holding32k) {
            int newSlot = -1;
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = mc.player.inventory.getStackInSlot(i);
                if (stack == ItemStack.EMPTY || !this.checkSharpness(stack)) continue;
                newSlot = i;
                break;
            }
            if (newSlot != -1) {
                mc.player.inventory.currentItem = newSlot;
                holding32k = true;
            }
        }
        if (this.onlyUse32k.getValue().booleanValue() && !holding32k) {
            return;
        }
        mc.playerController.attackEntity((EntityPlayer)mc.player, e);
        mc.player.swingArm(EnumHand.MAIN_HAND);
    }
    
}