package vestige.module.impl.combat;

import net.minecraft.entity.player.EntityPlayer;
import vestige.module.Category;
import vestige.module.Module;

public class Teams extends Module {
   public Teams() {
      super("Teams", Category.COMBAT);
   }

   public boolean canAttack(EntityPlayer entity) {
      if (!this.isEnabled()) {
         return true;
      } else if (mc.thePlayer.getTeam() != null && entity.getTeam() != null) {
         Character targetColor = entity.getDisplayName().getFormattedText().charAt(1);
         Character playerColor = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
         return !playerColor.equals(targetColor);
      } else {
         return false;
      }
   }
}
