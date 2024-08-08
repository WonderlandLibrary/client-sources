package in.momin5.cookieclient.client.modules.combat;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public KillAura(){
        super("KillAura", Category.COMBAT);
    }

    public SettingNumber rangeA = register(new SettingNumber("Range", this, 4, 1, 6, 0.2));
    public SettingBoolean passiveMobsA = register( new SettingBoolean("Passive", this, false));
    public SettingBoolean hostileMobsA = register( new SettingBoolean("Hostile", this, false));
    public SettingBoolean playersA = register( new SettingBoolean("Players", this, true));
    public SettingBoolean swordOnly = register( new SettingBoolean("Sword Only", this, true));
    public SettingBoolean rotate = register(new SettingBoolean("Rotate",this,false));


    public void onUpdate() {
        if (mc.player == null || mc.player.isDead) return;
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(entity -> entity != mc.player)
                .filter(entity -> mc.player.getDistance(entity) <= rangeA.getValue())
                .filter(entity -> !entity.isDead)
                .filter(entity -> attackCheck(entity))
                .sorted(Comparator.comparing(s -> mc.player.getDistance(s)))
                .collect(Collectors.toList());

        targets.forEach(target -> {
            if(swordOnly.isEnabled() && !(mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                return;
            }

            //if(rotate.isEnabled()){
            //  RotationUtils.faceBlockPacket(target.getPosition());
            //}

            attack(target);
        });
    }


    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }

    public void attack(Entity e) {
        if (mc.player.getCooledAttackStrength(0) >= 1){
            mc.playerController.attackEntity(mc.player, e);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }

    private boolean attackCheck(Entity entity) {

        if (playersA.isEnabled() && entity instanceof EntityPlayer){
            if (((EntityPlayer) entity).getHealth() > 0) {
                return true;
            }
        }
        if (passiveMobsA.isEnabled() && entity instanceof EntityAnimal) {
            if (entity instanceof EntityTameable) {
                return false;
            }
            else {
                return true;
            }
        }

        if (hostileMobsA.isEnabled() && entity instanceof EntityMob) {
            return true;
        }
        return false;
    }


}
