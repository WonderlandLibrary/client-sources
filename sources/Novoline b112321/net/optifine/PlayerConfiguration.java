package net.optifine;

import net.acE;
import net.asJ;
import net.minecraft.client.model.ModelBiped;
import net.optifine.Config;
import net.optifine.MatchBlock;
import net.optifine.PlayerItemModel;

public class PlayerConfiguration {
   private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
   private boolean initialized = false;

   public void a(ModelBiped var1, asJ var2, float var3, float var4) {
      acE[] var5 = MatchBlock.b();
      if(this.initialized) {
         int var6 = 0;
         if(var6 < this.playerItemModels.length) {
            PlayerItemModel var7 = this.playerItemModels[var6];
            var7.a(var1, var2, var3, var4);
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
