package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import optifine.Config;
import optifine.MatchBlock;
import optifine.PlayerItemModel;

public class PlayerConfiguration {
   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
   private boolean initialized = false;

   public void renderPlayerItems(ModelBiped var1, AbstractClientPlayer var2, float var3, float var4) {
      PacketRemapper[] var5 = MatchBlock.b();
      if(this.initialized) {
         int var6 = 0;
         if(var6 < this.playerItemModels.length) {
            PlayerItemModel var7 = this.playerItemModels[var6];
            var7.render(var1, var2, var3, var4);
            ++var6;
         }
      }

   }

   public boolean isInitialized() {
      return this.initialized;
   }

   public void setInitialized(boolean var1) {
      this.initialized = var1;
   }

   public PlayerItemModel[] getPlayerItemModels() {
      return this.playerItemModels;
   }

   public void addPlayerItemModel(PlayerItemModel var1) {
      this.playerItemModels = (PlayerItemModel[])((PlayerItemModel[])((PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, var1)));
   }
}
