package me.rich.module.combat;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.friend.Friend;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class TriggerBot extends Feature {

    EntityLivingBase target;

    public TriggerBot() {
        super("TriggerBot", 0, Category.COMBAT);
        Main.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Main.instance.settingsManager.rSetting(new Setting("Mobs", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Animals", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
        Main.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
    }

    @EventTarget
    public void pank(EventUpdate drain) {
        target = (EntityLivingBase) mc.objectMouseOver.entityHit;

        if (mc.player.getCooledAttackStrength(0) == 1 && mc.player.getDistanceToEntity(target) <= 3 && blob(target)) {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    public boolean blob(EntityLivingBase player) {
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {

            if (player instanceof EntityPlayer && !Main.instance.settingsManager.getSettingByName("Players").getValBoolean()) {
                return false;
            }

            if (player instanceof EntityMob && !Main.instance.settingsManager.getSettingByName("Mobs").getValBoolean()) {
                return false;
            }

            if (player instanceof EntityAnimal && !Main.instance.settingsManager.getSettingByName("Animals").getValBoolean()) {
                return false;
            }

            if (player instanceof EntityVillager && !Main.instance.settingsManager.getSettingByName("Villagers").getValBoolean()) {
                return false;
            }
        }

        if (player.isInvisible() && !Main.instance.settingsManager.getSettingByName("Invisibles").getValBoolean()) {
            return false;
        }


        for (Friend friend : FriendManager.friendManager.friendsList) {
            if (!player.getName().equals(friend.getName()))
                continue;
            return false;
        }

        return player != mc.player;
    }
}
