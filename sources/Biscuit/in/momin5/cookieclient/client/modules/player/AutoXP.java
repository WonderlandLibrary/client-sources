package in.momin5.cookieclient.client.modules.player;

import in.momin5.cookieclient.api.module.Category;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.settings.SettingBoolean;
import in.momin5.cookieclient.api.setting.settings.SettingNumber;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;

import java.util.List;

public class AutoXP extends Module {

    public SettingBoolean sneakOnly = register(new SettingBoolean("Sneak Only",this, true));
    public SettingNumber maxHeal = register(new SettingNumber("Armor Repair %",this, 90,1,100,5));

    public AutoXP(){
        super("AutoRepair", Category.PLAYER);
    }
    char toMend = 0;

    @Override
    public void onEnable(){
        super.onEnable();
    }

    public void onUpdate() {
        if(nullCheck())
            return;

        int sumOfDamage = 0;

        List<ItemStack> armour = mc.player.inventory.armorInventory;
        for (int i = 0; i < armour.size(); i++) {
            ItemStack itemStack = armour.get(i);
            if (itemStack.isEmpty) {
                continue;
            }

            //this works better than my calculation for some reason, thank you ArmorHUD.java
            float damageOnArmor = (float)(itemStack.getMaxDamage() - itemStack.getItemDamage());
            float damagePercent = 100 - (100 * (1 - damageOnArmor/ itemStack.getMaxDamage()));

            if (damagePercent <= maxHeal.getValue()) {
                if (damagePercent <= 50) {
                    toMend |= 1 << i;
                }
            } else {
                toMend &= ~(1 << i);
            }
        }

        if (toMend > 0) {
            mendArmor(mc.player.inventory.currentItem);
        }
    }

    private void mendArmor(int oldSlot) {
        for (EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer.getDistance(mc.player) < 1 && entityPlayer != mc.player) {
                return;
            }
        }

        if (sneakOnly.isEnabled() && !mc.player.isSneaking()) {
            return;
        }

        int newSlot = findXPSlot();

        if (newSlot == -1) {
            return;
        }

        if (oldSlot != newSlot) {
            mc.player.inventory.currentItem = newSlot;
        }

        mc.player.connection.sendPacket(new CPacketPlayer.Rotation(0, 90, true));
        mc.rightClickMouse();
        mc.player.inventory.currentItem = oldSlot;
    }

    private int findXPSlot() {
        int slot = -1;

        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.EXPERIENCE_BOTTLE) {
                slot = i;
                break;
            }
        }

        return slot;
    }
}
