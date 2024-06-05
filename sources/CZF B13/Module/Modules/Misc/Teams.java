package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class Teams extends Module {
    public Teams() {
        super("Teams", new String[]{}, ModuleType.World);
    }

    public static boolean isOnSameTeam(Entity entity) {
        if (!Objects.requireNonNull(ModuleManager.getModuleByClass(Teams.class)).isEnabled())
            return false;
        if (mc.thePlayer == null)
            return false;
        EntityPlayer entity1 = (EntityPlayer) entity;
        if (mc.thePlayer.getTeam() != null && entity1.getTeam() != null &&
                mc.thePlayer.getTeam().isSameTeam(entity1.getTeam())
        ) {
            return true;
        }
        if (mc.thePlayer.getDisplayName() != null && entity1.getDisplayName() != null) {
            String targetName = entity1.getDisplayName().getFormattedText().replace("¡ìr", "");
            String clientName = mc.thePlayer.getDisplayName().getFormattedText().replace("¡ìr", "");
            if (targetName.startsWith("T") && clientName.startsWith("T")) {
                return targetName.charAt(1) == clientName.charAt(1);
            }
        }
//        if (mc.thePlayer.inventory.armorInventory[3] != null && entity1.inventory.armorInventory[3] != null) {
//            ItemStack myHead = mc.thePlayer.inventory.armorInventory[3];
//            ItemArmor myItemArmor = (ItemArmor) myHead.getItem();
//            ItemStack entityHead = entity1.inventory.armorInventory[3];
//            ItemArmor entityItemArmor = (ItemArmor) myHead.getItem();
//            if (myItemArmor.getColor(myHead) == entityItemArmor.getColor(entityHead))
//                return true;
//        }
        if (mc.thePlayer.getDisplayName() != null && entity1.getDisplayName() != null) {
            String targetName = entity1.getDisplayName().getFormattedText().replace("¡ìr", "");
            String clientName = mc.thePlayer.getDisplayName().getFormattedText().replace("¡ìr", "");
            return targetName.startsWith("¡ì" + clientName.charAt(1));
        }
        return false;
    }
}
