package arsenic.module.impl.other;

import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.BooleanProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@ModuleInfo(name = "Targets", category = ModuleCategory.Other)
public class Targets extends Module {
    public static BooleanProperty teamates = new BooleanProperty("Target TeamMates", false);
    public static boolean isATeamMate(Entity entity) {
        try {
            EntityPlayer bruhentity = (EntityPlayer) entity;
            if (mc.thePlayer.isOnSameTeam((EntityLivingBase) entity) || mc.thePlayer.getDisplayName()
                    .getUnformattedText().startsWith(bruhentity.getDisplayName().getUnformattedText().substring(0, 2))) {
                return true;
            }
        } catch (Exception fhwhfhwe) {
        }
        return false;
    }
}