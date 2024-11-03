package vestige.anticheat;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;

public class ACPlayer {
   private final EntityPlayer entity;
   private final PlayerData data;
   private final ArrayList<AnticheatCheck> checks = new ArrayList();
   private boolean bot;

   public ACPlayer(EntityPlayer entity) {
      this.entity = entity;
      this.data = new PlayerData(entity);
   }

   public EntityPlayer getEntity() {
      return this.entity;
   }

   public PlayerData getData() {
      return this.data;
   }

   public ArrayList<AnticheatCheck> getChecks() {
      return this.checks;
   }

   public boolean isBot() {
      return this.bot;
   }

   public void setBot(boolean bot) {
      this.bot = bot;
   }
}
