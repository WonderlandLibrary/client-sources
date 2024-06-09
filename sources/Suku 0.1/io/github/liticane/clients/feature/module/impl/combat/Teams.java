package io.github.liticane.clients.feature.module.impl.combat;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

@Module.Info(name = "Teams", category = Module.Category.COMBAT)
public class Teams extends Module {
    public BooleanProperty scoreboard = new BooleanProperty("Scoreboard",this, false);
    public BooleanProperty armor = new BooleanProperty("Armor",this, false);
    public BooleanProperty color = new BooleanProperty("Color",this, false);

    public boolean isTeammate(EntityLivingBase entity) {
        if (scoreboard.isToggled() && mc.player.getTeam() != null && entity.getTeam() != null && mc.player.isOnSameTeam(entity)) {
            return true;
        }

        if (armor.isToggled() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (mc.player.inventory.armorInventory[3] != null && player.inventory.armorInventory[3] != null) {
                ItemStack myHead = mc.player.inventory.armorInventory[3];
                ItemArmor myItemArmor = (ItemArmor) myHead.getItem();

                ItemStack entityHead = player.inventory.armorInventory[3];
                ItemArmor entityItemArmor = (ItemArmor) entityHead.getItem();

                if (myItemArmor.getColor(myHead) == entityItemArmor.getColor(entityHead)) {
                    return true;
                }
            }
        }

        if (color.isToggled() && mc.player.getDisplayName() != null && entity.getDisplayName() != null) {
            String targetName = entity.getDisplayName().getFormattedText().replace("§r", "");
            String clientName = mc.player.getDisplayName().getFormattedText().replace("§r", "");

            return targetName.startsWith(String.format("§%s", clientName));
        }

        return false;
    }
}
